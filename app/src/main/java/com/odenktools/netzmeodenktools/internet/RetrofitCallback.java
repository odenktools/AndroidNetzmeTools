package com.odenktools.netzmeodenktools.internet;

import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Generic Retofit2 Callback, dibuat karena untuk "PARSING ERROR" response dari server.
 *
 * @param <T> Generic Response.
 * @author Odenktools.
 */
public abstract class RetrofitCallback<T> extends DisposableObserver<T> {

    public T response;

    private String defaultMessage = "UNKNOWN_ERROR";
    private String apiVersion = "1.0";

    /**
     * Network issues
     */
    public static final int BAD_NETWORK = 1002;

    /**
     * Connection error
     */
    public static final int CONNECT_ERROR = 1003;

    /**
     * connection timed out
     */
    public static final int CONNECT_TIMEOUT = 1004;

    /**
     * Custom Success Callback saat RxJava status_code = 200.
     */
    public abstract void onSuccess(T res);

    /**
     * Custom Error Callback saat RxJava status_code != 200.
     */
    public abstract void onErrorException(int errorCode, String message);

    @Override
    public void onNext(@NotNull T t) {
        onSuccess(t);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(@NotNull Throwable e) {
        String message;
        if (e instanceof HttpException) {
            int statusCode = ((HttpException) e).code();
            if (statusCode >= 300 && statusCode <= 307 && statusCode != 306) {
                onErrorException(300, "Tidak bisa koneksi ke server.");
                return;
            }
            //------------------
            InputStream i = Objects.requireNonNull(
                    Objects.requireNonNull(((HttpException) e)
                            .response()).errorBody()).byteStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(i));
            StringBuilder errorResult = new StringBuilder();
            String line;
            try {
                while ((line = r.readLine()) != null) {
                    errorResult.append(line).append('\n');
                }
            } catch (IOException io) {
                Log.e("RETROFIT_ERROR", String.format("STATUS CODE = %s MESSAGE = %s",
                        statusCode, io.getMessage()));
            }
            message = errorResult.toString();
            try {
                JSONObject error = new JSONObject(message);
                BaseResponse<Object> baseResponseModel = new BaseResponse<>();
                // Parsing Metadata
                RetrofitCallback.this.parseErrorMeta(baseResponseModel, error);
                // Ambil exception 1 line message.
                if (statusCode == 406) {
                    RetrofitCallback.this.parseMetaError406(baseResponseModel, error);
                } else {
                    RetrofitCallback.this.parseErrorArray(statusCode, baseResponseModel, error);
                }
                onErrorException(statusCode, this.defaultMessage);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        } else if (e instanceof SocketTimeoutException) {
            message = "SOCKET_CONNECTION_TIMEOUT";
            onErrorException(CONNECT_TIMEOUT, message);
        } else if (e instanceof ConnectException) {
            message = "Tidak dapat koneksi ke server";
            onErrorException(CONNECT_TIMEOUT, message);
        } else if (e instanceof TimeoutException) {
            message = "Gangguan koneksi ke server";
            onErrorException(CONNECT_TIMEOUT, message);
        } else if (e instanceof SocketException) {
            message = "Gangguan koneksi ke server";
            onErrorException(BAD_NETWORK, message);
        } else if (e instanceof UnknownHostException) {
            message = "NO_INTERNET_CONNECTION";
            onErrorException(CONNECT_ERROR, message);
        } else if (e instanceof InterruptedIOException) {
            message = "CONNECTION_TIMEOUT";
            onErrorException(CONNECT_TIMEOUT, message);
        } else {
            if (TextUtils.isEmpty(e.getMessage())) {
                message = "UNKNOWN ERROR";
                onErrorException(500, message);
            } else {
                onErrorException(500, e.getMessage());
            }
        }
    }

    private void parseErrorMeta(BaseResponse<Object> baseResponseModel, JSONObject error) throws JSONException {
        if (error.has("meta")) {
            JSONObject metas = error.getJSONObject("meta");
            Integer errorCode = metas.has("code") ? metas.getInt("code") : 400;
            this.defaultMessage = metas.has("message") ? metas.getString("message")
                    : this.defaultMessage;
        }
    }

    private void parseErrorArray(Integer statusCode, BaseResponse<Object> baseResponseModel,
                                 JSONObject error) throws JSONException {
        if (error.has("errors")) {
            JSONArray errors = error.getJSONArray("errors");
            StringBuilder errorMessage = new StringBuilder();
            ArrayList<Object> list = new ArrayList<>();
            if (error.length() > 0) {
                //baseResponseModel.setIsErrorArray(true);
            }
            if (statusCode == 422 || statusCode == 403) {
                for (int i = 0; i < errors.length(); i++) {
                    if (errors.get(i) instanceof String) {
                        String dataString = errors.getString(i);
                        Log.e("ERRORS 422 OR 403......", dataString);
                        errorMessage.append(dataString).append(System.getProperty("line.separator"));
                    } else {
                        JSONObject data = errors.getJSONObject(i);
                        Iterator keys = data.keys();
                        while (keys.hasNext()) {
                            String currentDynamicKey = (String) keys.next();
                            JSONArray currentDynamicValue = data.getJSONArray(currentDynamicKey);
                            int jsonArraySize = currentDynamicValue.length();
                            if (jsonArraySize > 0) {
                                for (int ii = 0; ii < jsonArraySize; ii++) {
                                    String nameObj = currentDynamicValue.getString(ii);
                                    Log.e("ERRORS 422 OR 403......", nameObj);
                                    errorMessage.append(nameObj).append(System.getProperty("line.separator"));
                                }
                            }
                        }
                    }
                }
            }
            //baseResponseModel.setErrors(list);
            this.defaultMessage = errorMessage.toString();
        }
    }

    /**
     * Jika error dari backend 406, biasanya error terjadi karena data integrity.
     */
    private void parseMetaError406(BaseResponse<Object> baseResponseModel,
                                   JSONObject error) throws JSONException {

        if (error.has("meta")) {
            JSONObject metas = error.getJSONObject("meta");
            if (metas.has("errors")) {
                JSONArray errors = metas.getJSONArray("errors");
                StringBuilder errorMessage = new StringBuilder();
                ArrayList<Object> list = new ArrayList<>();
                //baseResponseModel.setIsErrorArray(false);
                for (int i = 0; i < errors.length(); i++) {
                    if (errors.get(i) instanceof String) {
                        String dataString = errors.getString(i);
                        Log.e("ERRORS 406", dataString);
                        errorMessage.append(dataString).append(System.getProperty("line.separator"));
                    } else {
                        JSONObject data = errors.getJSONObject(i);
                        Iterator keys = data.keys();
                        while (keys.hasNext()) {
                            String errorString = (String) keys.next();
                            String currentDynamicValue = data.getString(errorString);
                            Log.e("ERRORS 406......", currentDynamicValue);
                            errorMessage.append(currentDynamicValue).append(System.getProperty("line.separator"));
                        }
                    }
                }

                //baseResponseModel.setErrors(list);
                this.defaultMessage = errorMessage.toString();
            }
        }
    }
}
