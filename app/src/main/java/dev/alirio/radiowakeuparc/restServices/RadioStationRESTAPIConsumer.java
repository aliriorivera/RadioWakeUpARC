package dev.alirio.radiowakeuparc.restServices;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

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
 * RadioStationRESTAPIConsumer consumes a Rest API which returns a list of radio stations depending
 * on the parameter typed by the app user
 * author: Alirio Rivera
 */
public class RadioStationRESTAPIConsumer extends AsyncTask<Void, Void ,String >{


    private String userInsert;
    private String restURL ;
    private List<RadioStation> radiosFromRestAPI;
    private SearchRadioStationActivity searchStationCurrentActivity;

    public RadioStationRESTAPIConsumer(String userText, SearchRadioStationActivity triggedActivity){
        this.userInsert = userText;
        this.restURL = "http://www.radio-browser.info/webservice/json/stations/byname/";
        this.searchStationCurrentActivity = triggedActivity;
        this.radiosFromRestAPI = new ArrayList<>();
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(restURL + userInsert);
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
                buildRadioResultListFromJsonString(res);

                return res;
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        searchStationCurrentActivity.updateRadioListRecycler(getRadiosFromRestAPI());
        //TODO fetch icon URL and get the image to show in the application
    }

    private void buildRadioResultListFromJsonString(String restResult){
        try{
            JSONArray jsonFromAPI = new JSONArray(restResult);
            for (int i = 0; i < jsonFromAPI.length(); i++)
            {
                JSONObject jsonObj = jsonFromAPI.getJSONObject(i);
                RadioStation radio = new RadioStation(
                        jsonObj.get("name").toString(),
                        jsonObj.get("country").toString(),
                        jsonObj.get("tags").toString(),
                        jsonObj.get("url").toString());

                radiosFromRestAPI.add(radio);
            }
        }catch (JSONException jsonException){

        }
    }

    public List<RadioStation> getRadiosFromRestAPI() {
        return radiosFromRestAPI;
    }
}
