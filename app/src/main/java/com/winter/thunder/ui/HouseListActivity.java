package com.winter.thunder.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.winter.thunder.R;
import com.winter.thunder.adapter.ListAdapter;
import com.winter.thunder.model.ListItem;
import com.winter.thunder.model.ThunderData;

import java.util.ArrayList;

public class HouseListActivity extends AppCompatActivity implements ListAdapter.ItemClickCallback{

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    String itemChosen1 = MainActivity.itemChosen;
    private RecyclerView recView;
    private ListAdapter adapter;
    private ArrayList listData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.cinza_mais_claro));
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#2a2b2b")));

        listData = (ArrayList) ThunderData.getListData(this, itemChosen1);
        recView = (RecyclerView)findViewById(R.id.rec_list);
        //Check out GridLayoutManager and StaggeredGridLayoutManager
        recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListAdapter(listData, this);
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);
    }

    @Override
    public void onItemClick(int p) {
        ListItem item = (ListItem) listData.get(p);
        Intent i = new Intent(this, DetailActivity.class);
        Bundle extras = new Bundle();
        extras.putString(EXTRA_QUOTE, item.getId());
        i.putExtra(BUNDLE_EXTRAS, extras);
        startActivity(i);
    }

    @Override
    public void onSecondaryIconClick(int p) {
        ListItem item = (ListItem) listData.get(p);
        //update our data
        if (item.isFavourite()){
            item.setFavourite(false);
        } else {
            item.setFavourite(true);
        }
        //pass new data to adapter and update
        //adapter.setListData(listData);
        adapter.notifyDataSetChanged();
    }
}
