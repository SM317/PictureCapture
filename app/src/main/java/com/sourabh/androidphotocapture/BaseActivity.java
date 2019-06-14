package com.sourabh.androidphotocapture;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sourabh.androidphotocapture.Utility.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


public abstract class BaseActivity extends AppCompatActivity {
    private static final String LOG_TAG = BaseActivity.class.getSimpleName();
    PowerManager pm;
    private String mTitle;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void setupActionBar() {
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public String getScreenTitle() {
        return mTitle;
    }

    protected void setScreenTitle(String mTitle) {
        this.mTitle = mTitle;
        //setThemeColor();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }


    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(ContextCompat.getColor(this, color));
    }

    protected void hideSoftKeypad() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }


    @Override
    protected void onResume() {
        // checkSession();

        super.onResume();
    }



    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void updateLocation() {


    }

    public void writeToSDFile(String filename,Bitmap finalBitmap) {
        try
        {
            File file = new File (Constants.PC_DIRECTORY + Constants.PICTURE_FOLDER, filename);
            FileOutputStream fos = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Bitmap makeImageOrientation(String imgPath,File path)
    {
        Bitmap bitmap = BitmapFactory.decodeFile(path.getAbsolutePath());
        try {
            ExifInterface exif = new ExifInterface(path.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            }
            else if (orientation == 3) {
                matrix.postRotate(180);
            }
            else if (orientation == 8) {
                matrix.postRotate(270);
            }
            else if (orientation == 0) {
                matrix.postRotate(90);
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); // rotating bitmap
        }
        catch (Exception e) {

        }
        return bitmap;
    }
}
