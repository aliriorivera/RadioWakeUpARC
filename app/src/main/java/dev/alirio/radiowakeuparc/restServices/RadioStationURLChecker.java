package dev.alirio.radiowakeuparc.restServices;

import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dev.alirio.radiowakeuparc.RadioWakeupActivity;
import dev.alirio.radiowakeuparc.SearchRadioStationActivity;
import dev.alirio.radiowakeuparc.pojos.RadioStation;

/**
 * author: Alirio Rivera
 * RadioStationURLChecker consumes the REST API exposed in the RadioStationRESTAPIConsumer and updates
 * the Radio station in the main activity with the information of the selected radio station
 */
public class RadioStationURLChecker extends RadioStationRESTAPIConsumer{

    public RadioStation radioStation;
    private SearchRadioStationActivity triggedActivity;

    public RadioStationURLChecker(RadioStation radioStation, SearchRadioStationActivity triggedActivity) {
        super("https://de1.api.radio-browser.info/json/stations/byuuid/"+radioStation.getId());
        this.radioStation = radioStation;
        this.triggedActivity = triggedActivity;

    }

    @Override
    public void buildRadioResultListFromJsonString(String restResult) {

        System.out.println("OTRO RESULT " + restResult);
        try{
                JSONArray listUUIDStations = new JSONArray(restResult);
                JSONObject jsonObj = null;
                if (listUUIDStations != null && listUUIDStations.length() > 0){
                    jsonObj = new JSONObject(listUUIDStations.get(0).toString());
                }

                System.out.println("ID RESULT " + jsonObj.get("lastcheckok").toString());

                if(jsonObj != null && jsonObj.get("lastcheckok").toString().equals("1")){
                    radioStation.setUrl(jsonObj.get("url_resolved").toString());
                    System.out.println("RADIO STATION: " + radioStation.getUrl());
                    RadioWakeupActivity.DEFAULT_RADIO_STATION = radioStation;
                }else{
                    radioStation = null;
                    Toast.makeText(triggedActivity, "" +
                                    "Radio Station cannot be played (Maybe the radio stream is down) please select another Radio Station!!.",
                            Toast.LENGTH_LONG).show();
                }

        }catch (JSONException jsonException){
            System.out.println("**************");
            jsonException.printStackTrace();
            System.out.println("**************");
            Toast.makeText(triggedActivity, "Error trying to check the radio station stream!!.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent setNewRadioStationIntent = new Intent(triggedActivity, RadioWakeupActivity.class);
        triggedActivity.startActivity(setNewRadioStationIntent);
        //TODO fetch icon URL and get the image to show in the application
    }
}