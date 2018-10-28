package dev.alirio.radiowakeuparc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev.alirio.radiowakeuparc.actionListeners.RadioListRecyclerListTouchListener;
import dev.alirio.radiowakeuparc.adapters.RadioStationAdapter;
import dev.alirio.radiowakeuparc.pojos.RadioStation;
import dev.alirio.radiowakeuparc.restServices.RadioStationDetailInfo;
import dev.alirio.radiowakeuparc.restServices.RadioStationURLChecker;


/**
 * SearchRadioStationActivity represents the view where the user has the posibility to search for
 * radio stations in a Rest API
 * author: Alirio Rivera
 */
public class SearchRadioStationActivity extends Activity {

    private List<RadioStation> radioStationsList;
    private RecyclerView radioListStationsRecyclerView;
    private RadioStationAdapter radioStationsAdapter;
    private ProgressBar progressSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_radio_station);

        radioStationsList = new ArrayList<>();
        radioListStationsRecyclerView = (RecyclerView) findViewById(R.id.RadioStationsList);
        radioStationsAdapter = new RadioStationAdapter(radioStationsList);

        LinearLayoutManager viewRadioListLayout = new LinearLayoutManager(getApplicationContext());
        radioListStationsRecyclerView.setLayoutManager(viewRadioListLayout);
        radioListStationsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        radioListStationsRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        radioListStationsRecyclerView.addOnItemTouchListener(new RadioListRecyclerListTouchListener(this,
                radioListStationsRecyclerView, new RadioListRecyclerListTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                RadioStationURLChecker checkRadioURL = new RadioStationURLChecker(radioStationsList.get(position), SearchRadioStationActivity.this);
                checkRadioURL.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            }

            @Override
            public void onLongClick(View view, int position) {
                // I think it is better for the app to manage only when the user clicks on the screen and not take into
                // account long clicks
            }
        }));

        progressSearch = (ProgressBar) findViewById(R.id.radioStationSearchProgress);
        progressSearch.setVisibility(View.GONE);

        radioListStationsRecyclerView.setAdapter(radioStationsAdapter);

    }


    public void searchRadioStation(View view){

        TextView userText = (TextView) this.findViewById(R.id.radioStationSearchText);
        if (!userText.getText().toString().equals(this.getString(R.string.voidRadioStationSet))){
            progressSearch.setVisibility(View.VISIBLE);
            RadioStationDetailInfo searchRadiosFromUserInput = new RadioStationDetailInfo(userText.getText().toString(), this);
            searchRadiosFromUserInput.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        }else{
            Toast.makeText(SearchRadioStationActivity.this, "Please type a Radio Station Name to Search!!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void updateRadioListRecycler(List<RadioStation> radiosFromRestAPI){
        progressSearch.setVisibility(View.GONE);
        radioStationsList.clear();
        if (radiosFromRestAPI != null && radiosFromRestAPI.size()>0){
            for (RadioStation radio : radiosFromRestAPI){
                radioStationsList.add(radio);
            }
            Toast.makeText(SearchRadioStationActivity.this, String.valueOf(radioStationsList.size()) + " radio stations found. You can use your finger to scroll over the list and select the one you wish to play!!",
                    Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(SearchRadioStationActivity.this, "No Radio Station was found for the text you entered. Please try again!!",
                    Toast.LENGTH_LONG).show();
        }
        radioStationsAdapter.notifyDataSetChanged();
    }
}