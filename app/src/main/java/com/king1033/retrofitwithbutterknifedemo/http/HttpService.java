package com.king1033.retrofitwithbutterknifedemo.http;



import com.king1033.retrofitwithbutterknifedemo.bean.OpenServiceBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @Author:king1033
 * @Desc:
 * @Time:2016/8/31
 */
public interface HttpService {
    @GET("/majax.action?method=getJtkaifu")
    Call<OpenServiceBean> queryOpenServer();
}
