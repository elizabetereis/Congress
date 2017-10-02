package com.example.elizabete.congress;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;

/**
 * Created by elizabete on 27/09/17.
 */

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    private TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_congress_splash);
        textView = (TextView) this.findViewById(R.id.textView2);
        textView.setText("Running...");
        CongressDAO dao = new CongressDAO(SplashActivity.this);
        if(dao.getLista().size() ==0){
            new JSONParse().execute();
            textView.append("\nConsumiu do webservice");
        }else{
            textView.append("\nConsumiu do SQLite");
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, CongressActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

    //consumindo dados
    public class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(SplashActivity.this);

            pDialog.setMessage("Obtendo Dados");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONArray json = null;
            JSONArray link = null;
            JSONObject jObj = null;
            CongressDAO dao = new CongressDAO(SplashActivity.this);

            json = Json();
            dao.dropAll();

            try {
                // Getting JSON Array
                link = json;

                for (int i = 0; i < link.length(); i++) {

                    jObj = link.getJSONObject(i);
                    CongressValue congressValue = new CongressValue();

                    congressValue.setId(jObj.getInt("idCongress"));
                    congressValue.setName(jObj.getString("name"));
                    congressValue.setReviewDeadline(jObj.getString("reviewDeadline"));
                    congressValue.setSubmissionDeadline(jObj.getString("submissionDeadline"));

                    dao.salvar(congressValue);
                    dao.close();

                }
            }catch(Exception e){
                e.printStackTrace();

            }

            return jObj;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                pDialog.dismiss();
                Intent i = new Intent(SplashActivity.this, CongressActivity.class);
                startActivity(i);
                finish();
            } catch (Exception e) {

            }
        }
    }


    public static JSONArray Json(){

        JSONArray json = null;
        String resp = null;
        HttpURLConnection urlConn = null;

        try {
            // Create connection to send GCM Message request.
            URL url1 = new URL("http://192.168.1.5/tedb-congress/public/congresses");
            urlConn = (HttpURLConnection) url1.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setDoInput(true);
            // Starts the query
            urlConn.connect();

            InputStream inputStream = urlConn.getInputStream();
            resp = IOUtils.toString(inputStream);
            json = new JSONArray(resp);

            return json;

        }catch (Exception e){
            e.printStackTrace();

        }

        return null;

    }

}

