//
// Name                 Martin Brown
// Student ID           S1718539
// Programme of Study   Computing
//

package com.example.britishearthquakemap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Item> mItems = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<Item> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        // set the text values on home page
        final Item item = mItems.get(position);

            holder.listTitle.setText(item.location);
            holder.listMag.setText(item.magnitude);

            Double magDouble = Double.parseDouble(holder.listMag.getText().toString());

            // colour code the magnitude label
            if (magDouble >= 2) {
                holder.listMag.setTextColor(Color.RED);
            } else if (magDouble >= 1.3){
                holder.listMag.setTextColor(Color.rgb(244, 161, 66));
            } else {
                holder.listMag.setTextColor(Color.GRAY);
            }

            holder.listDate.setText(item.dateTime);

        // when item in list is clicked pass over the item clicked ons details
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, IndividualActivity.class);
                intent.putExtra("title", item.title);
                intent.putExtra("description", item.description);
                intent.putExtra("link", item.link);
                intent.putExtra("dateTime", item.dateTime);
                intent.putExtra("category", item.category);
                intent.putExtra("geoLat", item.geoLat);
                intent.putExtra("geoLong", item.geoLong);
                intent.putExtra("location", item.location);
                intent.putExtra("depth", item.depth);
                intent.putExtra("magnitude", item.magnitude);

                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        //TextView description;
        TextView link;
        //TextView category;
        TextView geoLat;
        TextView geoLong;
        TextView location;
        TextView depth;
        TextView magnitude;
        TextView dateTime;
        TextView listMag;
        TextView listTitle;
        TextView listDate;

        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            // find the items
            title = itemView.findViewById(R.id.listTitle);


            //description = itemView.findViewById(R.id.description);
            link = itemView.findViewById(R.id.link);
            dateTime = itemView.findViewById(R.id.dateTime);
            //category = itemView.findViewById(R.id.category);
            geoLat = itemView.findViewById(R.id.geoLat);
            geoLong = itemView.findViewById(R.id.geoLong);
            location = itemView.findViewById(R.id.location);
            depth = itemView.findViewById(R.id.depth);
            magnitude = itemView.findViewById(R.id.magnitude);
            listMag = itemView.findViewById(R.id.listMag);

            listTitle = itemView.findViewById(R.id.listTitle);
            listDate = itemView.findViewById(R.id.listDate);

            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}















