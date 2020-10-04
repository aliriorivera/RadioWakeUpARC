package dev.alirio.radiowakeuparc.restServices;


import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.alirio.radiowakeuparc.SearchRadioStationActivity;
import dev.alirio.radiowakeuparc.pojos.RadioStation;

/**
 * author: Alirio Rivera
 * RadioStationDetailInfo consumes the REST API exposed in the RadioStationRESTAPIConsumer and parse the result as
 * a new List of radio stations depending on the user input.
 */
public class RadioStationDetailInfo extends RadioStationRESTAPIConsumer{

    private List<RadioStation> radiosFromRestAPI;
    private SearchRadioStationActivity triggedActivity;

    public RadioStationDetailInfo(String userText, SearchRadioStationActivity triggedActivity) {

        super("https://de1.api.radio-browser.info/json/stations/byname/"+userText);
        this.radiosFromRestAPI = new ArrayList<>();
        this.triggedActivity = triggedActivity;
    }

    @Override
    public void buildRadioResultListFromJsonString(String restResult){
        System.out.println("URL RESULT: " + restResult);
        try{
            JSONArray jsonFromAPI = new JSONArray(restResult);
            for (int i = 0; i < jsonFromAPI.length(); i++)
            {
                JSONObject jsonObj = jsonFromAPI.getJSONObject(i);
                RadioStation radio = new RadioStation(
                        jsonObj.get("stationuuid").toString(),
                        jsonObj.get("name").toString(),
                        jsonObj.get("country").toString(),
                        jsonObj.get("tags").toString(),
                        jsonObj.get("url").toString());

                radiosFromRestAPI.add(radio);
            }
        }catch (JSONException jsonException){
            Toast.makeText(triggedActivity, "Error trying to parse the information From the Radio stations API!!.",
            Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        triggedActivity.updateRadioListRecycler(radiosFromRestAPI);
    }
}
