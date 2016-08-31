package com.king1033.retrofitwithbutterknifedemo.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author:king1033
 * @Desc:
 * @Time:2016/8/31
 */
public class HttpUtils {

    private static final String BASE_URL = "http://www.1688wan.com";
    public static HttpService mHttpService;
    public static HttpService init() {
        if (mHttpService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mHttpService = retrofit.create(HttpService.class);
        }
        return mHttpService;
    }

}
