package com.example.sdp_bfit.calories;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.sdp_bfit.MainActivity;
import com.example.sdp_bfit.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    private PreviewView previewView;
    private TextView textView;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    public static String UPCValue ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        previewView = findViewById(R.id.previewView);
        //bind the camera lifecyle to its own lifecycle owner,so no need to worry about opening/closing the camera
        cameraProviderFuture = ProcessCameraProvider.getInstance(CameraActivity.this);
        textView = findViewById(R.id.orientation);
        //add listener to get value from cameraProciderFuture
        cameraProviderFuture.addListener(new Runnable() {
                   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                   @Override
                   public void run() {
                       try{
                           ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                           bindImageAnalysis(cameraProvider);

                       }catch(ExecutionException | InterruptedException e){
                           e.printStackTrace();
                       }
                   }
               },ContextCompat.getMainExecutor(CameraActivity.this));
    }

    //anlayze image use case
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider){
        //unbind previous use case
        Preview preview = null;
        if (cameraProvider == null) {
            return;
        }
        if (preview != null) {
            cameraProvider.unbind();
        }
        //Initialize preview object
         preview = new Preview.Builder().build();
        //setup camera , select type of lens prefer
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        //get surface provider and set it on Preview instance
        preview.setSurfaceProvider(previewView.createSurfaceProvider());

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder().setTargetResolution(new Size(1280,720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy imageProxy) {
                QRScanningActivity qrScanningActivity = new QRScanningActivity();
                @SuppressLint("UnsafeExperimentalUsageError")
                Image mediaImage = imageProxy.getImage();
                if (mediaImage!=null) {
                    InputImage image = InputImage.fromMediaImage(mediaImage,imageProxy.getImageInfo().getRotationDegrees());

                        //set detection options
                        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                                .setBarcodeFormats(
                                        Barcode.FORMAT_QR_CODE,Barcode.FORMAT_AZTEC
                                )
                                .build();

                        //get detector
                        BarcodeScanner scanner = BarcodeScanning.getClient();


                        Task<List<Barcode>> result = scanner.process(image)
                                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                                    @Override
                                    public void onSuccess(List<Barcode> barcodes) {
                                        //scan success

                                        //get barcode
                                        for (Barcode barcode:barcodes){
                                            Rect bounds = barcode.getBoundingBox();
                                            Point[] corners = barcode.getCornerPoints();
                                            String rawValue = barcode.getRawValue();
                                            int valueType = barcode.getValueType();
                                            //get information from barcode
                                            //can check out API reference for more value type
//                                            switch(valueType){
//                                                case Barcode.TYPE_URL:
//                                                    String title = barcode.getUrl().getTitle();
//                                                    String rawValueBarcode  = barcode.getRawValue();
//                                                    View main = getLayoutInflater().inflate(R.layout.fragment_calories_form_bfast,null);
//                                                    View calories = getLayoutInflater().inflate(R.layout.fragment_calories_main,null);
//                                                    TextView textView = calories.findViewById(R.id.calories);
//                                                    textView.setText(url);
// load the text view
//                                                    EditText txt = main.findViewById(R.id.editTextMealbfast);

                                                    Toast.makeText(CameraActivity.this, "Qr code detected", Toast.LENGTH_LONG).show();
                                                    Toast.makeText(CameraActivity.this, rawValue, Toast.LENGTH_LONG).show();
                                                        UPCValue = rawValue;
//                                                    txt.setText(url);
                                                    break;

//                                            }
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                                          @Override
                                                          public void onFailure(@NonNull Exception e) {
                                                              //task failed
                                                              Toast.makeText(CameraActivity.this, "Unable to detect barcode/QR", Toast.LENGTH_SHORT).show();
                                                          }
                                                      }

                                ).addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                                    @Override
                                    public void onComplete(@NonNull Task<List<Barcode>> task) {
                                        mediaImage.close();
                                        imageProxy.close();
                                        // When the image is from CameraX analysis use case, must call image.close() on received
                                        // images when finished using them. Otherwise, new images may not be received or the camera
                                        // may stall.
                                    }
                                });
                        ;

                        // detector stop running





                }
            }
        });


        OrientationEventListener orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                textView.setText(Integer.toString(orientation));
            }
        };
        orientationEventListener.enable();

        //execute the analysis
        try {
            cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis, preview);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Unable to scan qr/barcode", Toast.LENGTH_SHORT).show();
        }
    }



}
