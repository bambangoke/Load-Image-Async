package com.orion.loadimageasync;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imageDog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageDog = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progress_horizontal);
    }

    public void loadImage(View view) {
        final String url = "https://i.imgur.com/tGbaZCY.jpg";

        new ImageDownloadTask(this).execute(url);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            Dexter.withActivity(this)
//                    .withPermissions(
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    .withListener(new MultiplePermissionsListener() {
//                        @Override
//                        public void onPermissionsChecked(MultiplePermissionsReport report) {
//                            // check if all permissions are granted
//                            if (report.areAllPermissionsGranted()) {
//                                // do you work now
//                                Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
//                                new ImageDownloadTask(MainActivity.this).execute(url);
//                            }
//
//                            // check for permanent denial of any permission
//                            if (report.isAnyPermissionPermanentlyDenied()) {
//                                // permission is denied permanently, navigate user to app settings
//                            }
//                        }
//
//                        @Override
//                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                            token.continuePermissionRequest();
//                        }
//                    })
//                    .onSameThread()
//                    .check();
//        } else {
//            new ImageDownloadTask(this).execute(url);
//        }
    }
}
