package com.technorapper.root.ui.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.technorapper.root.R;
import com.technorapper.root.data.data_model.LocationTable;
import com.technorapper.root.databinding.ListItemsBinding;
import com.technorapper.root.interfaces.RecyclerViewClickListener;


import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {


    private Context context;
    private List<LocationTable> list;
    private RecyclerViewClickListener listener;


    public ListAdapter(List<LocationTable> list, Context context, RecyclerViewClickListener listener) {
        this.list = list;
        this.listener = listener;
        this.context = context;
        //    this.width = width;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_items, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        holder.binding.setBaseUrl(preference.getImageBaseUrl());
        holder.binding.setModel(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ListItemsBinding binding;

        public MyViewHolder(@NonNull ListItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.delete.setOnClickListener(view -> {

                listener.onClick(view, getAdapterPosition());
            });

            binding.llMain.setOnClickListener(view ->
                    {

                        listener.onClick(view, getAdapterPosition());
                    }
            );


        }
    }

}
