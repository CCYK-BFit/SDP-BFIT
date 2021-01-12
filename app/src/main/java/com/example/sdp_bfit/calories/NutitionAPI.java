package com.example.sdp_bfit.calories;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.telecom.Call;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.example.sdp_bfit.calories.CameraActivity.UPCValue;

public class NutitionAPI extends AppCompatActivity {
//    CameraActivity scanner = new CameraActivity();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        //create new thread to run network call
        //u cannot run network call or main thread bcos ur activity risk being killed by  the os
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://edamam-food-and-grocery-database.p.rapidapi.com/parser?")
                .newBuilder();
        urlBuilder.addQueryParameter("upc",UPCValue);
        String url = urlBuilder.build().toString();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-key", "72ee5eb339msh2147fe6de6ac3bfp13e0a2jsnbe315dc6079a")
                .addHeader("x-rapidapi-host", "edamam-food-and-grocery-database.p.rapidapi.com")
                .build();

//        Response response = client.newCall(request).execute();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                final String myResponse = response.body().string();

                NutitionAPI.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            //main body of the json result
                            JSONObject json = new JSONObject(myResponse);
//                                String mealName =json.getJSONObject("nutrients").getString("ENERC_KCAL");
                            //to retrieve nested json object: foodlabel
                            JSONArray hints = json.getJSONArray("hints");
                            JSONObject result = hints.getJSONObject(0);
                            JSONObject food = result.getJSONObject("food");
                            String foodLabel = food.getString("label");
                            //to retrieve energy kcal
                            JSONObject nutrients = food.getJSONObject("nutrients");
                            String kcal = nutrients.getString("ENERC_KCAL");

//                            textView.setText(foodLabel);
//                            textView2.setText(kcal);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }




        });

    }


    }

