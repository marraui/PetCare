package com.example.camilorosales.petcare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class PetAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Pet> mDataset;
    private static RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener;

    public static class PetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View mView;
        public PetViewHolder(View v){
            super(v);
            mView = v;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public PetAdapter(Context context, List<Pet> dataset){
        mContext = context;
        mDataset = dataset;
        mRecyclerViewOnItemClickListener = new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("pet", mDataset.get(position));
                Intent intent = new Intent(mContext, PetDetailsActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        };
    }

    public void update(Pet pet){
        if (pet != null){
            for (int i = 0; i < mDataset.size(); i++){
                Log.d("updateDatasetAdapter", "mDataset[" + i + "]: " + mDataset.get(i).getName());
                Log.d("updateDatasetAdapter", "pet: " + pet.getName());
                if (mDataset.get(i).getName().equals(pet.getName())){
                    Log.d("updateDatasetAdapter", "object is being updated");
                    mDataset.get(i).setName(pet.getName());
                    notifyItemChanged(i);
                    return;
                }
            }
            mDataset.add(pet);
            notifyItemInserted(mDataset.size() - 1);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View petView = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        PetViewHolder vh = new PetViewHolder(petView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PetViewHolder pvh = (PetViewHolder) holder;
        View view = pvh.mView;
        TextView petName = view.findViewById(R.id.pet_name);
        petName.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
