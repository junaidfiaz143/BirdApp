package com.base7.jd.birdapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.datamodels.Relation;

import java.util.ArrayList;

/**
 * Created by jd on 26-Feb-17.
 */

public class RelationAdapter extends RecyclerView.Adapter<RelationAdapter.ViewHolder> {

    private ArrayList<Relation> relationsList;

    public RelationAdapter(ArrayList<Relation> relationsList) {
        this.relationsList = relationsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_relation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Relation relation = relationsList.get(position);
        holder.maleId.append(relation.getMaleId());
        holder.femaleId.append(relation.getFemaleId());
        holder.clutchId.append(relation.getClutchId());
    }

    @Override
    public int getItemCount() {
        return relationsList.size();
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
