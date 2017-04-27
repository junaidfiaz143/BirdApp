package com.base7.jd.birdapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.datamodels.Clutch;

import java.util.ArrayList;

/**
 * Created by jd on 22-Jan-17.
 */

public class ClutchAdapter extends RecyclerView.Adapter<ClutchAdapter.ViewHolder> {

    private ArrayList<Clutch> clutchList = new ArrayList<>();

    public ClutchAdapter(ArrayList<Clutch> clutchList) {
        this.clutchList = clutchList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_relation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Clutch clutch = clutchList.get(position);
        holder.maleId.append(clutch.getMaleId());
        holder.femaleId.append(clutch.getFemaleId());
        holder.clutchId.append(clutch.getClutchId());
    }

    @Override
    public int getItemCount() {
        return clutchList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView maleId, femaleId, clutchId;

        ViewHolder(View itemView) {
            super(itemView);

            maleId = (TextView) itemView.findViewById(R.id.maleId);
            femaleId = (TextView) itemView.findViewById(R.id.femaleId);
            clutchId = (TextView) itemView.findViewById(R.id.clutchId);
        }
    }

}
