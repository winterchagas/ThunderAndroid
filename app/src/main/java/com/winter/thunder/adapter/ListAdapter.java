package com.winter.thunder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.winter.thunder.R;
import com.winter.thunder.model.ListItem;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder>{

    private List<ListItem> listData;
    private LayoutInflater inflater;
    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
        void onSecondaryIconClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public ListAdapter(List<ListItem> listData, Context c){
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }


    class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView title, subTitle;
         ImageView thumbnail, secondaryIcon;
         View container;

        public ListHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView)itemView.findViewById(R.id.im_item_icon);
            secondaryIcon = (ImageView)itemView.findViewById(R.id.im_item_icon_secondary);
            secondaryIcon.setOnClickListener(this);
            subTitle = (TextView)itemView.findViewById(R.id.lbl_item_sub_title);
            title = (TextView)itemView.findViewById(R.id.lbl_item_text);
            container = itemView.findViewById(R.id.cont_item_root);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cont_item_root){
                itemClickCallback.onItemClick(getAdapterPosition());
            } else {
                itemClickCallback.onSecondaryIconClick(getAdapterPosition());
            }
        }
    }

    @Override
    public ListAdapter.ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        ListItem item = listData.get(position);
        holder.title.setText(item.getTitle());
        holder.subTitle.setText(item.getSubTitle());
        holder.thumbnail.setImageResource(item.getImageResId());
        //holder.icon.setImageResource(item.getImageResId());
        if (item.isFavourite()){
            holder.secondaryIcon.setImageResource(R.drawable.ic_navigation_black_36dp);
        } else {
            holder.secondaryIcon.setImageResource(R.drawable.ic_navigation_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    /*public void setListData(ArrayList<ListItem> exerciseList) {
        this.listData.clear();
        this.listData.addAll(exerciseList);
    }
*/



}
