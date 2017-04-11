package com.satyam.intentserviceexampletocallwebservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadResultReceiver.Receiver {
    RecyclerView recycler_first;
    RecyclerAdapter adapter;
    DownloadResultReceiver mReceiver;
    ProgressDialog progressDialog;
    ArrayList<ModelClass> modelClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_first = (RecyclerView) findViewById(R.id.recycler_first);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        modelClasses=new ArrayList<>();
        Getdata();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_first.setLayoutManager(mLayoutManager);
        recycler_first.setItemAnimator(new DefaultItemAnimator());
        recycler_first.addItemDecoration(new DividerItemDecoration(getApplicationContext(), null));
        recycler_first.addOnItemTouchListener(new RecyclerTouchListener(this, recycler_first, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), "Click=" + position, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClick(View view, final int position) {
                        Toast.makeText(getApplicationContext(), "LongClick=" + position, Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

    public void Getdata() {
        try {
            String url;
            url="Enter your url";
            Log.d("checkUrl", "checkUrl: " + url);
            mReceiver = new DownloadResultReceiver(new Handler());
            mReceiver.setReceiver(MainActivity.this);
            Intent intent = new Intent(Intent.ACTION_SYNC, null, MainActivity.this, DownloadService.class);
            intent.putExtra("url", url);
            intent.putExtra("receiver", mReceiver);
            intent.putExtra("requestId", "111");
            startService(intent);
        } catch (Exception e) {
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        progressDialog.show();
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:
                break;
            case DownloadService.STATUS_FINISHED:
                progressDialog.dismiss();
                String data = resultData.getString("result");
                Log.d("data", "data=" + data);

/*
                try {
                    JSONArray jArray = new JSONArray(data);
                    for (int AR = 0; AR < jArray.length(); AR++) {
                        JSONObject jObject = jArray.getJSONObject(AR);
                        String Mobile = jObject.getString("Mobile");
                        ModelClass modelClass=new ModelClass(Mobile);
                        modelClasses.add(modelClass);
                    }
                    adapter = new RecyclerAdapter(modelClasses);
                    recycler_first.setAdapter(adapter);

                } catch (Exception e) {

                }
*/


                break;
            case DownloadService.STATUS_ERROR:
                Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
