package dev.alirio.radiowakeuparc.restServices;

import android.content.Intent;
import android.widget.Toast;

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
        super("http://www.radio-browser.info/webservice/v2/json/url/"+radioStation.getId());
        this.radioStation = radioStation;
        this.triggedActivity = triggedActivity;

    }

    @Override
    public void buildRadioResultListFromJsonString(String restResult) {
        try{
                JSONObject jsonObj = new JSONObject(restResult);

                if(jsonObj != null && Boolean.valueOf(jsonObj.get("ok").toString())){
                    radioStation.setUrl(jsonObj.get("url").toString());
                    RadioWakeupActivity.DEFAULT_RADIO_STATION = radioStation;
                }else{
                    radioStation = null;
                    Toast.makeText(triggedActivity, "" +
                                    "Radio Station cannot be played (Maybe the radio stream is down) please select another Radio Station!!.",
                            Toast.LENGTH_LONG).show();
                }

        }catch (JSONException jsonException){
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