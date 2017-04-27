package com.base7.jd.birdapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base7.jd.birdapp.R;

import java.util.ArrayList;

/**
 * Created by jd on 15-Mar-17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<String> categoryArrayList = new ArrayList<>();

    private int color = 0;

    public CategoryAdapter(ArrayList<String> categoryArrayList) {

        this.categoryArrayList.clear();
        this.categoryArrayList = categoryArrayList;
        notifyDataSetChanged();
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_layout, parent, false);

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.layout);

        switch (color) {
            case 0:
                relativeLayout.setBackgroundColor(view.getResources().getColor(R.color.listItemOne));
                color++;
                break;
            case 1:
                relativeLayout.setBackgroundColor(view.getResources().getColor(R.color.listItemTwo));
                color++;
                break;
            case 2:
                relativeLayout.setBackgroundColor(view.getResources().getColor(R.color.listItemThree));
                color++;
                break;
            default:
                relativeLayout.setBackgroundColor(view.getResources().getColor(R.color.listItemFour));
                color = 0;
                break;
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.categoryName.setText(categoryArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryName;

        public ViewHolder(View itemView) {
            super(itemView);

            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
        }
    }
}
