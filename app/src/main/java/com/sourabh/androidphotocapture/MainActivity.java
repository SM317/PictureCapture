package com.sourabh.androidphotocapture;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourabh.androidphotocapture.Utility.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import me.echodev.resizer.Resizer;

public class MainActivity extends BaseActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 201;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE1 = 100;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE2 = 101;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE3 = 102;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE4 = 103;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE5 = 104;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODEGALLERY = 300;

    private String imageName = "", filePath = "";
    private Uri lasstUri;
    CardView card_view_sample1_picture, card_view_sample2_picture, card_view_sample3_picture, card_view_sample4_picture, card_view_sample5_picture;
    TextView sample1_picture, sample2_picture, sample3_picture, sample4_picture, sample5_picture,txt_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAllUIComponents();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE1){
            imageName = data.getStringExtra(Constants.KEY_IMAGE_NAME);
            filePath = data.getStringExtra(Constants.KEY_FILE_PATH);
            sample1_picture.setText(imageName);
            previewCapturedImage();
        }
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE2){
            imageName = data.getStringExtra(Constants.KEY_IMAGE_NAME);
            filePath = data.getStringExtra(Constants.KEY_FILE_PATH);
            sample2_picture.setText(imageName);
            previewCapturedImage();
        }
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE3){
            imageName = data.getStringExtra(Constants.KEY_IMAGE_NAME);
            filePath = data.getStringExtra(Constants.KEY_FILE_PATH);
            sample3_picture.setText(imageName);
            previewCapturedImage();
        }
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE4){
            imageName = data.getStringExtra(Constants.KEY_IMAGE_NAME);
            filePath = data.getStringExtra(Constants.KEY_FILE_PATH);
            sample4_picture.setText(imageName);
            previewCapturedImage();
        }
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE5){
            imageName = data.getStringExtra(Constants.KEY_IMAGE_NAME);
            filePath = data.getStringExtra(Constants.KEY_FILE_PATH);
            sample5_picture.setText(imageName);
            previewCapturedImage();
        }
        if (requestCode == 2) {
            lasstUri = data.getData();
            String[] filePath1 = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(lasstUri,filePath1, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath1[0]);
            filePath = c.getString(columnIndex);
            File f = new File(filePath);
            sample1_picture.setText(f.getName());
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(filePath));
            writeToSDFile(f.getName(),thumbnail);
//            viewImage.setImageBitmap(thumbnail);
        }
    }


    public void getAllUIComponents()
    {
        card_view_sample1_picture = (CardView) findViewById(R.id.card_view_sample1_picture);
        card_view_sample2_picture = (CardView) findViewById(R.id.card_view_sample2_picture);
        card_view_sample3_picture = (CardView) findViewById(R.id.card_view_sample3_picture);
        card_view_sample4_picture = (CardView) findViewById(R.id.card_view_sample4_picture);
        card_view_sample5_picture = (CardView) findViewById(R.id.card_view_sample5_picture);

        txt_submit = (TextView) findViewById(R.id.txt_submit);
        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sample1_picture.getText() != null && sample1_picture.getText() != "")
                {
                    Intent intent = new Intent(MainActivity.this, CustomGalleryActivity.class);
                    startActivity(intent);
                    intent = null;
                }
            }
        });

        sample1_picture = (TextView) findViewById(R.id.sample1_picture);
        sample1_picture.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.upload_image, 0);
        sample1_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        sample2_picture = (TextView) findViewById(R.id.sample2_picture);
        sample2_picture.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.upload_image, 0);
        sample2_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sample2_picture.getText() != null && sample2_picture.getText() != "") {
                    showImagePreviewDialog(Constants.KEY_IMG_TYPE_SAMPLE2, CAMERA_CAPTURE_IMAGE_REQUEST_CODE2, sample2_picture.getText().toString(),2);
                }
                else {
                    openCamera(Constants.KEY_IMG_TYPE_SAMPLE2, CAMERA_CAPTURE_IMAGE_REQUEST_CODE2,2);
                }
            }
        });

        sample3_picture = (TextView) findViewById(R.id.sample3_picture);
        sample3_picture.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.upload_image, 0);
        sample3_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sample3_picture.getText() != null && sample3_picture.getText() != "") {
                    showImagePreviewDialog(Constants.KEY_IMG_TYPE_SAMPLE3, CAMERA_CAPTURE_IMAGE_REQUEST_CODE3, sample3_picture.getText().toString(),3);
                }
                else {
                    openCamera(Constants.KEY_IMG_TYPE_SAMPLE3, CAMERA_CAPTURE_IMAGE_REQUEST_CODE3,3);
                }
            }
        });

        sample4_picture = (TextView) findViewById(R.id.sample4_picture);
        sample4_picture.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.upload_image, 0);
        sample4_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sample4_picture.getText() != null && sample4_picture.getText() != "") {
                    showImagePreviewDialog(Constants.KEY_IMG_TYPE_SAMPLE4, CAMERA_CAPTURE_IMAGE_REQUEST_CODE4, sample4_picture.getText().toString(),4);
                }
                else {
                    openCamera(Constants.KEY_IMG_TYPE_SAMPLE4, CAMERA_CAPTURE_IMAGE_REQUEST_CODE4,4);
                }
            }
        });

        sample5_picture = (TextView) findViewById(R.id.sample5_picture);
        sample5_picture.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.upload_image, 0);
        sample5_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sample5_picture.getText() != null && sample5_picture.getText() != "") {
                    showImagePreviewDialog(Constants.KEY_IMG_TYPE_SAMPLE5, CAMERA_CAPTURE_IMAGE_REQUEST_CODE5, sample5_picture.getText().toString(),5);
                }
                else {
                    openCamera(Constants.KEY_IMG_TYPE_SAMPLE5, CAMERA_CAPTURE_IMAGE_REQUEST_CODE5,5);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE
            );
        }
        else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_REQUEST_CODE
            );
        }
        else {
          enableTheControl();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                //permission granted  start reading

            } else {
                Toast.makeText(this, R.string.ERROR_NO_PERMISSION_TO_ACCESS_CAMERA, Toast.LENGTH_SHORT).show();
                exitApp();
            }
        }
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                //permission granted  start reading
                enableTheControl();
            } else {
                Toast.makeText(this, R.string.ERROR_NO_PERMISSION_READ_WRITE_TO_STORAGE, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void enableTheControl()
    {
        txt_submit.setBackgroundColor(getResources().getColor(R.color.colorOrange,null));
        txt_submit.setEnabled(true);
    }
    private void exitApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void previewCapturedImage() {
        try {
            File sourceFile = new File(filePath);
            try {
                Bitmap resizedImage = new Resizer(MainActivity.this)
                        .setTargetLength(1080)
                        .setQuality(70)
                        .setOutputFormat("JPG")
                        .setSourceImage(sourceFile)
                        .getResizedBitmap();
                File file = new File(Constants.PC_DIRECTORY + Constants.PICTURE_FOLDER, imageName);
                if (file.exists()) file.delete();
                FileOutputStream out = new FileOutputStream(file);
                resizedImage.compress(Bitmap.CompressFormat.JPEG, 50, out);
                resizedImage.recycle();
                resizedImage = null;
                out.flush();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void showImagePreviewDialog(final String imageType, final int imageRequestCode,  String prevImageName,final int userId) {
        try {
            LayoutInflater li = LayoutInflater.from(MainActivity.this);
            View imagePreview = li.inflate(R.layout.activity_image_view, null);
            TextView textView = (TextView) imagePreview.findViewById(R.id.tv_lable);
            ImageView imageView = (ImageView) imagePreview.findViewById(R.id.image_view);
            Button btn_imageview_ok = (Button) imagePreview.findViewById(R.id.btn_imageview_ok);
            Button btn_imageview_new = (Button) imagePreview.findViewById(R.id.btn_imageview_new);


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setView(imagePreview);
            final AlertDialog previewDialog = alertDialogBuilder.create();
            previewDialog.setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

            previewDialog.show();

            btn_imageview_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    previewDialog.dismiss();
                }
            });

            btn_imageview_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    previewDialog.dismiss();
                    openCamera(imageType, imageRequestCode,userId);
                }
            });

            //Set Image
            if (prevImageName != null && !prevImageName.isEmpty()) {
                textView.setText(prevImageName);
                File imgFile = new File(Constants.PC_DIRECTORY + Constants.PICTURE_FOLDER + File.separator + prevImageName);
                if (imgFile.exists()) {
                    imageView.setImageBitmap(makeImageOrientation(prevImageName,imgFile));
                }
                else
                {
                    File imgFileGallery = new File(filePath);
                    if(imgFileGallery.exists())
                    {
                        Bitmap bitmap = BitmapFactory.decodeFile(imgFileGallery.getAbsolutePath());
                        imageView.setImageBitmap(bitmap);
                    }
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void openCamera(String imageType, int requestCode,int userId){
        Intent intent = new Intent(MainActivity.this, ImageCapture.class);
        intent.putExtra(Constants.KEY_USER_ID, userId);
        intent.putExtra(Constants.KEY_IMAGE_TYPE, imageType);
        startActivityForResult(intent, requestCode);
        intent = null;
    }

    private void selectImage() {
        if (sample1_picture.getText() != null && sample1_picture.getText() != "") {
            showImagePreviewDialog(Constants.KEY_IMG_TYPE_SAMPLE1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE1, sample1_picture.getText().toString(),1);
            return;
        }
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    openCamera(Constants.KEY_IMG_TYPE_SAMPLE1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE1,1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }
}
