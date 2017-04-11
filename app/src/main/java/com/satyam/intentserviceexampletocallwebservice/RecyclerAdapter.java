package com.satyam.intentserviceexampletocallwebservice;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.GmailVH> {
    ArrayList<ModelClass> modelClasses;
    public RecyclerAdapter(ArrayList<ModelClass> modelClasses) {
        this.modelClasses=modelClasses;
    }

    @Override
    public GmailVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_listitem, viewGroup, false);
        return new GmailVH(view);
    }

    @Override
    public void onBindViewHolder(GmailVH gmailVH, int i) {
        gmailVH.txttitle.setText(modelClasses.get(i).getMobile().toString());
    }
    @Override
    public int getItemCount() {
        return modelClasses.size();
    }

    public class GmailVH extends RecyclerView.ViewHolder{
        TextView txttitle;
        public GmailVH(View itemView) {
            super(itemView);
            txttitle= (TextView) itemView.findViewById(R.id.txttitle);
        }
    }
}