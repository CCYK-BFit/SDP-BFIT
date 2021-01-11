package com.example.sdp_bfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.*
import com.google.mlkit.vision.barcode.BarcodeScanner
//import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

// class NutriApp : AppCompatActivity(), {
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            setContentView(R.layout.activity_nutri_app)
//        }
//       var itemName =""
//
//    }
//    public suspend fun getFoodInfo(View: android.view.View){
//        try{
//            val result = {}
//        }
//        catch(e:Exception){
//            e.printStackTrace()
//        }
//    }
//    private fun callNutriAPI(apiURL: String):String?{
//        var result:String? = ""
//        var url:URL;
//        var connection: HttpURLConnection? = null
//        try{
//            url=URL(apiURL)
//            connection = url.openConnection() as HttpURLConnection
//            connection.setRequestProperty("x-rapidapi-host","")
//            //set rapid-api key
//            connection.setRequestProperty("x-rapidapi-key","")
//            connection.setRequestProperty("","")
//            //set request method - POST
//            connection.requestMethod = "POST"
//            val `in` = connection.inputStream
//            val reader = InputStreamReader(`in`)
//            //read the response data
//            var data = reader.read()
//            while (data != -1){
//                val current = data.toChar()
//                result += current
//                data = reader.read()
//            }
//            return result
//        }catch(e:Exception){
//            e.printStackTrace()
//        }
//        return null
//    }
//}