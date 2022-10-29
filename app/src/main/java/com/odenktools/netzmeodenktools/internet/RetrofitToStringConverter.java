package com.odenktools.netzmeodenktools.internet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Parsing Secara Manual JSON.
 *
 * @author Odenktools
 * @since 2.0.0
 */
class RetrofitToStringConverter extends Converter.Factory {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json");

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (String.class.equals(type)) {
            return (Converter<ResponseBody, String>) ResponseBody::string;
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        if (String.class.equals(type)) {
            return (Converter<String, RequestBody>) value -> {
                byte[] bytes = value.getBytes();
                return RequestBody.create(MEDIA_TYPE, bytes);
            };
        }
        return null;
    }
}
