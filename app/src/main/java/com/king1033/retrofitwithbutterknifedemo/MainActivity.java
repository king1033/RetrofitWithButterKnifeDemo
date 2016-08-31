package com.king1033.retrofitwithbutterknifedemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.king1033.retrofitwithbutterknifedemo.adapter.MyExpandAdapter;
import com.king1033.retrofitwithbutterknifedemo.bean.OpenServiceBean;
import com.king1033.retrofitwithbutterknifedemo.http.HttpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "king1033";
    ProgressDialog progressDialog;
    @BindView(R.id.gift_openserver_list)
    ExpandableListView mExpandableListView;
    private Map<String,ArrayList<OpenServiceBean.InfoBean>> rootDatas = new HashMap<>();
    private MyExpandAdapter myExpandAdapter;
    private List<String> keysList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载中......");
        initListView();
        openServer();
    }

    private void initListView() {
        myExpandAdapter = new MyExpandAdapter(rootDatas,this,keysList);
        mExpandableListView.setAdapter(myExpandAdapter);
    }

    /**
     * 开服
     */
    private void openServer() {
        progressDialog.show();
        HttpUtils.init().queryOpenServer().enqueue(new Callback<OpenServiceBean>() {
            @Override
            public void onResponse(Call<OpenServiceBean> call, Response<OpenServiceBean> response) {
                formatResult(response.body());
            }

            @Override
            public void onFailure(Call<OpenServiceBean> call, Throwable t) {

            }
        });
    }

    /**
     * 格式化结果
     * @param openServiceBean
     */
    private void formatResult(OpenServiceBean openServiceBean) {
        List<OpenServiceBean.InfoBean> info = openServiceBean.getInfo();
        for (int i = 0; i < info.size(); i++) {
            //分类
            OpenServiceBean.InfoBean infoBean = info.get(i);
            String addtime = infoBean.getAddtime();
            if (!rootDatas.containsKey(addtime)) {
                keysList.add(addtime);
                ArrayList<OpenServiceBean.InfoBean> infoBeenList = new ArrayList<>();
                infoBeenList.add(infoBean);
                rootDatas.put(addtime,infoBeenList);
            } else {
                rootDatas.get(addtime).add(infoBean);
            }
        }
        Log.i(TAG, "formatResult: ");
        Collections.sort(keysList);
        progressDialog.dismiss();
        myExpandAdapter.notifyDataSetChanged();

        for (int i = 0; i < keysList.size(); i++) {
            mExpandableListView.expandGroup(i);
        }

    }
}
