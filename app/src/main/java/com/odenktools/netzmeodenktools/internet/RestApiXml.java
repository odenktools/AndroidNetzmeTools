package com.odenktools.netzmeodenktools.internet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.money.Money;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RestApiXml {

    private Map<String, String> requestHeaders = new HashMap<>();

    private Retrofit.Builder builder = null;

    private static final Object OBJ_SYNC = new Object();

    private static volatile RestApiXml Instance = new RestApiXml();

    private String baseUrl = "https://paway-dev.netzme.com/";

    @SuppressWarnings("unused")
    public String getBaseUrl() {
        synchronized (OBJ_SYNC) {
            return baseUrl;
        }
    }

    public void setBaseUrl(String baseUrl) {
        synchronized (OBJ_SYNC) {
            this.baseUrl = baseUrl;
        }
    }

    public static RestApiXml singleton() {
        RestApiXml localInstance = Instance;
        if (localInstance == null) {
            synchronized (RestApiXml.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new RestApiXml();
                }
            }
        }
        return localInstance;
    }

    @SuppressWarnings("unused")
    public void builder(Retrofit.Builder builder) {
        synchronized (OBJ_SYNC) {
            this.builder = builder;
        }
    }

    private Retrofit.Builder getBuilder(String baseUrl) {
        synchronized (OBJ_SYNC) {
            return this.builder != null ? this.builder : this.defaultBuilder(baseUrl);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public Retrofit.Builder defaultBuilder(String baseUrl) {
        synchronized (OBJ_SYNC) {
            if (!baseUrl.endsWith("/")) {
                baseUrl = baseUrl + "/";
            }
            OkHttpClient client = defaultRetrofit();
            builder = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy())));
            return builder;
        }
    }

    private Gson serializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Money.class, new MoneyAdapter());
        return gsonBuilder
                .setLenient()
                .create();
    }

    private static Interceptor moneyInterceptor() {
        return chain -> {
            Request.Builder builder = chain.request().newBuilder();
            String cred = Credentials.basic("truemoney", "contactme");
            builder.addHeader("Authorization", cred);
            return chain.proceed(builder.build());
        };
    }
    /**
     * <pre>{@code
     *
     * OkHttpClient client = this.defaultRetrofit(withCache);
     * Gson gson = new GsonBuilder().setLenient().serializeNulls().create();
     * return new Retrofit.Builder()
     *     .client(client)
     *     .baseUrl(BASE_URL)
     *     .addConverterFactory(new RetrofitToStringConverter())
     *     .addConverterFactory(GsonConverterFactory.create(gson))
     *     .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
     * }</pre>
     */
    @SuppressWarnings("WeakerAccess")
    public OkHttpClient defaultRetrofit() {
        synchronized (OBJ_SYNC) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(40000, TimeUnit.SECONDS);
            builder.readTimeout(40000, TimeUnit.SECONDS);
            builder.writeTimeout(40000, TimeUnit.SECONDS);
            builder.addInterceptor(headerInterceptor());
            builder.addInterceptor(moneyInterceptor());
            builder.followRedirects(false);
            builder.followSslRedirects(false);
            builder.cache(null);

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);

            return builder.build();
        }
    }

    /**
     * <pre>{@code
     *
     * RestApi.get().setBaseUrl(Constanta.BASE_URL);
     * Observable<ApiResult> requestData = RestApi
     *     .get()
     *     .setApiService(GetApiEndpoint.class)
     *     .getPopularMovie(BuildConfig.API_KEY, maps);
     * }</pre>
     */
    public <T> T service(Class<T> clazz, String baseUrl) {
        synchronized (OBJ_SYNC) {
            Retrofit retrofit = this.getBuilder(baseUrl).build();
            return retrofit.create(clazz);
        }
    }

    public <T> T service(Class<T> clazz) {
        synchronized (OBJ_SYNC) {
            Retrofit retrofit = this.getBuilder(getBaseUrl()).build();
            return retrofit.create(clazz);
        }
    }

    /**
     * Set request headers.
     *
     * @param requestHeaders data header yang akan dipassing.
     */
    public RestApiXml setRequestHeaders(Map<String, String> requestHeaders) {
        synchronized (OBJ_SYNC) {
            this.requestHeaders = requestHeaders;
            return this;
        }
    }

    /**
     * Intercept an request.
     *
     * @return Interceptor
     */
    private Interceptor headerInterceptor() {

        synchronized (OBJ_SYNC) {
            return chain -> {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Cache-Control", "no-cache");
                builder.addHeader("Accept", "application/json");
                for (final String headerName : this.requestHeaders.keySet()) {
                    final String headerValue = this.requestHeaders.get(headerName);
                    assert headerValue != null;
                    builder.header(headerName, headerValue);
                }
                return chain.proceed(builder.build());
            };
        }
    }
}
