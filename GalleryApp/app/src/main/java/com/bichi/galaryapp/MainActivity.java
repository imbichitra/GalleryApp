package com.bichi.galaryapp;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.GridView;

import com.bichi.galaryapp.adapter.GridViewImageAdapter;
import com.bichi.galaryapp.helper.AppConstant;
import com.bichi.galaryapp.helper.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Utils utils;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private GridViewImageAdapter adapter;
    private GridView gridView;
    private int columnWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.grid_view);
        utils = new Utils(this);
        InitilizeGridLayout();

        //loading all image paths from SD card
        imagePaths = utils.getFilePaths();

        //Gridview adapter
        adapter = new GridViewImageAdapter(MainActivity.this,imagePaths,columnWidth);

        //setting grid view adapter
        gridView.setAdapter(adapter);
    }
    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConstant.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);
        Log.d("main", "InitilizeGridLayout: columnWidth"+columnWidth);
        gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }
}
