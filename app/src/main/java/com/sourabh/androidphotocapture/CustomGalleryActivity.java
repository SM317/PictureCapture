package com.sourabh.androidphotocapture;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sourabh.androidphotocapture.Utility.Constants;

import java.io.File;
import java.util.ArrayList;

public class CustomGalleryActivity extends BaseActivity {

    private ImageAdapter imageAdapter;
    ArrayList<String> imageList = new ArrayList<String>();// list of file paths
    File[] listFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_gallery);
        setupActionBar();
        getFromSdcard();
        GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);

        if(imageList.size() > 0)
        {
            imageAdapter = new ImageAdapter();
            imagegrid.setAdapter(imageAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getFromSdcard()
    {
        File file = new File(Constants.PC_DIRECTORY + Constants.PICTURE_FOLDER);
        if (file.isDirectory())
        {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++)
            {
                imageList.add(listFile[i].getAbsolutePath());
            }
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return imageList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.gallery_item, null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            File f = new File(imageList.get(position));
            if (f.getName().contains("sample"))
            holder.imageview.setImageBitmap(makeImageOrientation(f.getName(),f));
            else
            {
               Bitmap bit = BitmapFactory.decodeFile(f.getAbsolutePath());
                holder.imageview.setImageBitmap(bit);
            }
            return convertView;
        }
    }
    class ViewHolder {
        ImageView imageview;


    }
}
