package com.bichi.galaryapp.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class Utils {
    private Context _context;

    public Utils(Context context){
        this._context=context;
    }
    //read file paths from SDCard
    public ArrayList<String> getFilePaths() {
        ArrayList<String> filePaths = new ArrayList<String>();

        File directory = new File(
                android.os.Environment.getExternalStorageDirectory()
                        + File.separator + AppConstant.PHOTO_ALBUM);
        Log.d(TAG, "getFilePaths: "+android.os.Environment.getExternalStorageDirectory()
                + File.separator + AppConstant.PHOTO_ALBUM);
        // check for directory
        if (directory.isDirectory()) {
            // getting list of file paths
            File[] listFiles = directory.listFiles();

            // Check for count
            if (listFiles!=null && listFiles.length > 0) {

                // loop through all files
                for (int i = 0; i < listFiles.length; i++) {

                    // get file path
                    String filePath = listFiles[i].getAbsolutePath();

                    // check for supported file extension
                    if (isSupportedFile(filePath)) {
                        // Add image path to array list
                        filePaths.add(filePath);
                    }
                }
            } else {
                // image directory is empty
                Toast.makeText(
                        _context,
                        AppConstant.PHOTO_ALBUM
                                + " is empty. Please load some images in it !",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
            alert.setTitle("Error!");
            alert.setMessage(AppConstant.PHOTO_ALBUM
                    + " directory path is not valid! Please set the image directory name AppConstant.java class");
            alert.setPositiveButton("OK", null);
            alert.show();
        }

        return filePaths;
    }

    //Check supported file extensions
    private boolean isSupportedFile(String filepath){
        String ext = filepath.substring(filepath.lastIndexOf(".")+1,
                filepath.length());

        return AppConstant.FILE_EXTN.contains(ext.toLowerCase(Locale.getDefault()));
    }

    /*
     * getting screen width
     */
    public int getScreenWidth(){
        int columnWidth;
        WindowManager wm =(WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        final Point point=new Point();
        try{
            display.getSize(point);
        }catch (java.lang.NoSuchMethodError ignore){
            //older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }

}
