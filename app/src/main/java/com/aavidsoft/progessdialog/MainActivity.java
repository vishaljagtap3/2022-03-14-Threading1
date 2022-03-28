package com.aavidsoft.progessdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnDownload;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownload = findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new DownloadThread().execute((String) null);
                String [] files = {
                        "https://bitcode.in/android/file1",
                        "https://bitcode.in/android/file2",
                        "https://bitcode.in/android/file3",
                };
                new DownloadThread().execute(files);
            }
        });
    }

    class DownloadThread extends AsyncTask<String, Integer, Float>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("tag", "onPre: " + Thread.currentThread().getName());

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("BitCode");
            progressDialog.setMessage("Downloading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected Float doInBackground(String ... files) {

            Log.e("tag", "doInBg: " + Thread.currentThread().getName());

            for (String file : files) {
                progressDialog.setMessage("Downloading " + file);
                for (int i = 0; i <= 100; i++) {
                    try {
                        //btnDownload.setText(i + "%"); //Not recommended
                        //publishProgress(i); //will work
                        publishProgress(new Integer[] {i});

                        Thread.sleep(10);
                        progressDialog.setProgress(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            return 3.14f;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            btnDownload.setText(values[0] + " -- %");
        }

        @Override
        protected void onPostExecute(Float res) {
            super.onPostExecute(res);
            Log.e("tag", "onPost: " + Thread.currentThread().getName());
            btnDownload.setText(res  +  "");
            progressDialog.dismiss();
        }
    }
}