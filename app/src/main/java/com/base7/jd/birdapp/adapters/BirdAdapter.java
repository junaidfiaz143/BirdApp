package com.base7.jd.birdapp.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.datamodels.Bird;
import com.base7.jd.birdapp.dialog.bird_detail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by M.Junaid Fiaz on 30-Nov-16.
 */
public class BirdAdapter extends RecyclerView.Adapter<BirdAdapter.ViewHolder> {

    private FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
    private int color = 0;

    private Context context;

    private ArrayList<Bird> birdList = new ArrayList<>();

    public BirdAdapter(ArrayList<Bird> birdList, Context context) {
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
        holder.birdId.setText("id: " + bird.getId());
        holder.birdAge.setText(bird.getAge());
        holder.birdGender.setText(bird.getGender());
    }

    @Override
    public int getItemCount() {
        return birdList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView birdId, birdAge, birdGender;

        ImageView btnDelete;

        ViewHolder(View itemView) {
            super(itemView);

            birdId = (TextView) itemView.findViewById(R.id.birdId);
            birdAge = (TextView) itemView.findViewById(R.id.birdAge);
            birdGender = (TextView) itemView.findViewById(R.id.birdGender);

            btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("warning");
                    dialog.setMessage("Are you sure to delete your data?");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            DatabaseReference rf = FirebaseDatabase.getInstance().getReference().child(fbUser.getUid()).child("birds");
                            rf.child(birdId.getText().toString()).removeValue();
                            notifyDataSetChanged();
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    Dialog d = dialog.create();
                    d.show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bird_detail detail = new bird_detail();
                    Bundle args = new Bundle();
                    args.putString("id", birdId.getText().toString());
                    detail.setArguments(args);
                    detail.show(((Activity) context).getFragmentManager(), "dialog");
                }
            });


        }

    }
}
