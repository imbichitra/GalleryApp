package com.bichi.galaryapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.bichi.galaryapp.FullScreenViewActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GridViewImageAdapter extends BaseAdapter{

    private Activity _activity;
    private ArrayList<String> _filePaths =new ArrayList<>();
    private int imageWidth;

    public GridViewImageAdapter(Activity _activity, ArrayList<String> _filePaths, int imageWidth) {
        this._activity = _activity;
        this._filePaths = _filePaths;
        this.imageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return this._filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return this._filePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(_activity);
        } else {
            imageView = (ImageView) convertView;
        }

        // get screen dimensions
        Bitmap image = decodeFile(_filePaths.get(position),imageWidth,imageWidth+70);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,imageWidth+70));
        imageView.setImageBitmap(image);

        //image view click listener
        imageView.setOnClickListener(new OnImageClickListener(position));

        return imageView;
    }
    class OnImageClickListener implements View.OnClickListener{
        int _position;
        OnImageClickListener(int position){
            this._position = position;
        }
        @Override
        public void onClick(View v) {
            //on select grid view image
            //launch full screen activity
            Intent i=new Intent(_activity,FullScreenViewActivity.class);
            i.putExtra("position",_position);
            _activity.startActivity(i);
        }
    }
    /*
     * Resizing image size
     */
    private static Bitmap decodeFile(String filePath, int WIDTH, int HIGHT) {
        try {

            File f = new File(filePath);

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
                    && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
