package com.fullsail.ce6.student;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fullsail.ce6.student.Database.DatabaseHelper;
import com.fullsail.ce6.student.Fragment.MainFragment;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    FrameLayout frameLayout;
    DatabaseHelper db;
    ProgressDialog progressDialog;
    final String NYTimes_URL = "http://api.nytimes.com/svc/topstories/v2/movies.json?api-key=2565e41d00034a51841f961d23972c9f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        linearLayout=findViewById(R.id.llTextView);
        frameLayout=findViewById(R.id.frame);
        EnableRuntimePermission();
    }


    public void EnableRuntimePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            if (isInternetAvailable(this)) {
                linearLayout.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait While Loading Data");
                progressDialog.setCancelable(false);

                getSupportFragmentManager().beginTransaction().replace(R.id.frame, MainFragment.newInstance()).commit();
                DataTask dataTask = new DataTask();
                dataTask.execute(NYTimes_URL);
            }else {
                Toast.makeText(MainActivity.this,"Please check internet connection",Toast.LENGTH_LONG).show();
                linearLayout.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){

                if (isInternetAvailable(this)) {
                    linearLayout.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);

                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Please Wait While Loading Data");
                    progressDialog.setCancelable(false);

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, MainFragment.newInstance()).commit();
                    DataTask dataTask = new DataTask();
                    dataTask.execute(NYTimes_URL);
                }else {
                    Toast.makeText(MainActivity.this,"Please check internet connection",Toast.LENGTH_LONG).show();
                    linearLayout.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.GONE);
                }
            }
        }
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @SuppressLint("StaticFieldLeak")
    public class DataTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream is = urlConnection.getInputStream();
                result = IOUtils.toString(is);
                Thread.sleep(200);
                parseResult(result);
                publishProgress();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

            }
            return result;
        }

        private void parseResult(String result) {

            try {
                JSONObject response = new JSONObject(result);
                JSONArray it = response.optJSONArray("results");
                Articles art;
                if (it!= null && it.length() > 0) {
                    for (int i = 0; i < it.length(); i++) {
                        JSONObject arrayNYTime = it.getJSONObject(i);
                        String title = arrayNYTime.getString("title");
                        art = new Articles();
                        art.setTitle(title);

                        String url_desc = arrayNYTime.getString("abstract");
                        art.setBody(url_desc);

                        String imageLink = "";
                        JSONArray is = arrayNYTime.optJSONArray("multimedia");
                        if (is != null && is.length() > 3) {
                            JSONObject obj = is.getJSONObject(3);
                            imageLink = obj.getString("url");
                            art.setThumbnailURL(imageLink);
                        }
                        Log.d("Tag","title="+title+"\nurl_desc"+url_desc+"\nimageLink"+imageLink);
                        db.insertData(title, imageLink,url_desc);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });

            }
        }
    }
}
