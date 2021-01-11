package com.example.sdp_bfit.calories;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Size;
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
import androidx.core.content.ContextCompat;

import com.example.sdp_bfit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class QRScanningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void scanBarcode(InputImage image){
        //set detection options
        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_QR_CODE,Barcode.FORMAT_AZTEC
                )
                .build();

        //get detector
        BarcodeScanner scanner = BarcodeScanning.getClient();

        //run detector
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
//                            switch(valueType){
//                                case Barcode.TYPE_URL:
//                                    String title = barcode.getUrl().getTitle();
//                                    String url = barcode.getUrl().getUrl();
//                                    break;
//
//                            }
                            Toast.makeText(QRScanningActivity.this, rawValue, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //task failed
                        Toast.makeText(QRScanningActivity.this, "Unable to detect barcode/QR", Toast.LENGTH_SHORT).show();
                    }
                });

        // detector stop running

    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider){
//        //Initialize preview object
//        Preview preview = new Preview.Builder().build();
//        //setup camera , select type of lens prefer
//        CameraSelector cameraSelector = new CameraSelector.Builder()
//                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
//        //get surface provider and set it on Preview instance
//        preview.setSurfaceProvider(previewView.createSurfaceProvider());
//        ImageAnalysis imageAnalysis =
//                new ImageAnalysis.Builder().setTargetResolution(new Size(1280,720))
//                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
//        //set keep only latest so that only 1 image is being process one at a time
//        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
//            @Override
//            public void analyze(@NonNull ImageProxy imageProxy) {
//                QRScanningActivity qrScanningActivity = new QRScanningActivity();
//                @SuppressLint("UnsafeExperimentalUsageError")
//                Image mediaImage = imageProxy.getImage();
//                if (mediaImage!=null) {
//                    InputImage image = InputImage.fromMediaImage(mediaImage,imageProxy.getImageInfo().getRotationDegrees());
//                    scanBarcode(image);
//
//
//                }
//            }
//        });
}
