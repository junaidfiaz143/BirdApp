package com.base7.jd.birdapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.datamodels.Bird;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by M.Junaid Fiaz on 30-Nov-16.
 */
public class GenderAdapter extends RecyclerView.Adapter<GenderAdapter.ViewHolder> {

    private int color = 0;

    private Context context;

    private ArrayList<Bird> birdList = new ArrayList<>();

    public GenderAdapter(ArrayList<Bird> birdList, Context context) {
        this.birdList = birdList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);


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

        Bird bird = birdList.get(position);
        holder.birdId.setText(bird.getId());

    }

    @Override
    public int getItemCount() {
        return birdList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView birdId;

        ViewHolder(View itemView) {
            super(itemView);

            birdId = (TextView) itemView.findViewById(R.id.birdId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("id", birdId.getText());
                    ((Activity) context).setResult(RESULT_OK, intent);
                    ((Activity) context).finish();
                }
            });


        }
    }
}
