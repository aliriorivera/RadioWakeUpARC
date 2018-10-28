package dev.alirio.radiowakeuparc.restServices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dev.alirio.radiowakeuparc.SearchRadioStationActivity;
import dev.alirio.radiowakeuparc.pojos.RadioStation;

/**
 * RadioStationRESTAPIConsumer consumes a Rest API and returns the result as a String value
 * author: Alirio Rivera
 */
public abstract class RadioStationRESTAPIConsumer extends AsyncTask<Void, Void ,String >{

    private String restURL ;

    public RadioStationRESTAPIConsumer(String restURL){
        this.restURL = restURL.replaceAll("\\s", "");// if the user types more than one string the API fails, so eliminate spaces!!;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(restURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                String res = stringBuilder.toString();
                this.buildRadioResultListFromJsonString(res);

                return res;
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            Toast.makeText(null, "Error trying to get information about the Radio Stations available!!.",
                    Toast.LENGTH_LONG).show();
            return null;
        }
    }

    abstract public void buildRadioResultListFromJsonString(String restResult);
}
