package com.orion.loadimageasync;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by macoke on 2019-07-12.
 * Defines the background task to download and then load the image within the ImageView
 */
public class ImageDownloadTask extends AsyncTask<String, Integer, Bitmap> {

    private static final String TAG = "ImageDownloadTask";

    private WeakReference<MainActivity> activityWeakReference;

    ImageDownloadTask(MainActivity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        MainActivity activity = activityWeakReference.get();

        if (activity == null || activity.isFinishing()) {
            return;
        }

        activity.progressBar.setVisibility(View.VISIBLE);
    }

    protected Bitmap doInBackground(String... addresses) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            // 1. Declare a URL Connection
            URL url = new URL(addresses[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 2. Open InputStream to connection
            conn.connect();
            in = conn.getInputStream();
            // 3. Download and decode the bitmap using BitmapFactory
            bitmap = BitmapFactory.decodeStream(in);

            int fileLength = conn.getContentLength();

//            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            InputStream input = new URL(addresses[0]).openStream();

//            OutputStream output = new FileOutputStream("/sdcard/dog.jpg");

            byte[] data = new byte[1024];
            long total = 0;
            int count;

            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    in.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
//                output.write(data, 0, count);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception while closing inputstream" + e);
                }
        }
        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        MainActivity activity = activityWeakReference.get();

        if (activity == null || activity.isFinishing()) {
            return;
        }

        activity.progressBar.setProgress(values[0]);
    }

    // Fires after the task is completed, displaying the bitmap into the ImageView
    @Override
    protected void onPostExecute(Bitmap result) {

        MainActivity activity = activityWeakReference.get();

        if (activity == null || activity.isFinishing()) {
            return;
        }

        // Set bitmap image for the result
        activity.imageDog.setImageBitmap(result);
        activity.progressBar.setVisibility(View.GONE);
    }
}
