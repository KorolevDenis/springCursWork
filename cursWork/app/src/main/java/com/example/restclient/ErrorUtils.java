package com.example.restclient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                APIClient.getClient()
                .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error = new APIError();

        try {
            int code = response.code();
            if (code == 404 || code == 502) {
                if (response.body() == null) {
                    error = new APIError(code, "cannot connect to server");
                }
            } else {
                error = converter.convert(response.errorBody());
            }
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}