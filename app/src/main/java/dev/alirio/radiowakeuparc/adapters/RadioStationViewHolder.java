package dev.alirio.radiowakeuparc.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dev.alirio.radiowakeuparc.R;


/**
 * RadioStationViewHolder holds a single row element of the recycled view for each
 * radio Station shown on the view
 * author: Alirio Rivera
 */
public class RadioStationViewHolder extends RecyclerView.ViewHolder{
    public TextView name, tags, country;

    public RadioStationViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.radioStationName);
        tags = (TextView) view.findViewById(R.id.radioStationTags);
        country = (TextView) view.findViewById(R.id.radioStationCountry);
    }
}