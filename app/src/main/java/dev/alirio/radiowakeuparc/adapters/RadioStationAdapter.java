package dev.alirio.radiowakeuparc.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.alirio.radiowakeuparc.R;

import java.util.List;

import dev.alirio.radiowakeuparc.pojos.RadioStation;

/**
 * RadioStationAdapter adapts the RadioStation elements to a recycled view to be shown on the view
 * author: Alirio Rivera
 */
public class RadioStationAdapter  extends RecyclerView.Adapter<RadioStationViewHolder>{

    private List<RadioStation> radioStationsList;


    public RadioStationAdapter(List<RadioStation> radioStationsList){
        this.radioStationsList = radioStationsList;
    }

    @NonNull
    @Override
    public RadioStationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rowView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.radio_station_row, viewGroup, false);
        return new RadioStationViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull RadioStationViewHolder radioStationViewHolder, int i) {
        RadioStation radio = radioStationsList.get(i);
        radioStationViewHolder.name.setText(radio.getName());
        radioStationViewHolder.tags.setText(radio.getTags());
        radioStationViewHolder.country.setText(radio.getCountry());
    }

    @Override
    public int getItemCount() {
        return radioStationsList.size();
    }
}
