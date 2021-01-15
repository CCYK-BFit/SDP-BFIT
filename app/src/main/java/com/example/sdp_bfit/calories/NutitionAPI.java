package com.example.sdp_bfit.calories;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.telecom.Call;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.MainActivity;
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
      Meal meal = new Meal();
    private CaloriesViewModel caloriesViewModel;
    String foodLabel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //initialze view model
        caloriesViewModel = new ViewModelProvider(this).get(CaloriesViewModel.class);
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
                .url(url).get()
                .addHeader("x-rapidapi-key", "72ee5eb339msh2147fe6de6ac3bfp13e0a2jsnbe315dc6079a")
                .addHeader("x-rapidapi-host", "edamam-food-and-grocery-database.p.rapidapi.com")
                .build();

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
                             foodLabel = food.getString("label");
                            //to retrieve energy kcal
                            JSONObject nutrients = food.getJSONObject("nutrients");
                            String kcal = nutrients.getString("ENERC_KCAL");
                            //TO-DO: PASS FOOD NAME AND KCAL TO TEXTVIEW INSIDE FORM FRAGMENT
//                            MutableLiveData<String> mealName = null,mealCal = null;
//                            mealName.setValue(foodLabel);
//                            mealCal.setValue(kcal);







//                            textView.setText(foodLabel);
//                            textView2.setText(kcal);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }




        });
        Toast.makeText(this, foodLabel, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(NutitionAPI.this, MainActivity.class);
        startActivity(intent);
    }
}

