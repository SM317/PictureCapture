package com.sourabh.androidphotocapture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;

import com.sourabh.androidphotocapture.Utility.Constants;

import java.io.File;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class ImageCapture extends Activity {

    private static int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public Uri fileUri; // file url to store image/video
    public String imageName = "";
    private Context context;
    public int userId;
    public String imageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            //Below code added is for camera issue on android 7+
            // Basically It will ignore URI exposure
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                if (extras.containsKey(Constants.KEY_USER_ID)) {
                    userId = extras.getInt(Constants.KEY_USER_ID);
                    imageType = extras.getString(Constants.KEY_IMAGE_TYPE);
                    switch (imageType) {
                        default:
                            CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
                            break;

                    }
                }
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            intent.putExtra("return-data", true);
            // start the image capture Intent
            (ImageCapture.this).startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
        catch (Exception e)
        {
//            AppLogger.appendLog("CommonCaptureImage-onCreate " , e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_FILE_PATH, fileUri.getPath());
        if(resultCode == 0) {
            intent.putExtra(Constants.KEY_IMAGE_NAME, "");
            intent.putExtra(Constants.KEY_IMAGE_TYPE, "");
        }
        else {
            intent.putExtra(Constants.KEY_IMAGE_NAME, imageName);
            intent.putExtra(Constants.KEY_IMAGE_TYPE, imageType);
        }
        setResult(CAMERA_CAPTURE_IMAGE_REQUEST_CODE,intent);
        finish();
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onBackPressed() {
        //startActivity(new Intent(this, CommonCaptureImage.class));
        super.onBackPressed();
        finish();
    }

    private Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private File getOutputMediaFile(int type) {
        // External sdcard location
        String img_dir = Constants.PC_DIRECTORY + Constants.PICTURE_FOLDER;
        File mediaStorageDir = new File(img_dir);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name

        File mediaFile;
        String name = "";
        switch (imageType) {
            case Constants.KEY_IMG_TYPE_SAMPLE1:
                name = "sample1.jpg";
                break;
            case Constants.KEY_IMG_TYPE_SAMPLE2:
                name = "sample2.jpg";
                break;
            case Constants.KEY_IMG_TYPE_SAMPLE3:
                name = "sample3.jpg";
                break;
            case Constants.KEY_IMG_TYPE_SAMPLE4:
                name = "sample4.jpg";
                break;
            case Constants.KEY_IMG_TYPE_SAMPLE5:
                name = "sample5.jpg";
                break;
            default:
                name = "NewImage.jpg";
                break;
        }
        imageName = userId + "_" + name;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + imageName);
        } else {
            return null;
        }
        return mediaFile;
    }
}
