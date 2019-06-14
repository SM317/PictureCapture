package com.sourabh.androidphotocapture.Utility;

import android.os.Environment;

public class Constants {

    public static final String PC_TEMP_IMAGE_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/PhotoCapture/aPhotoCaptureTempIMG";
    public static final String PC_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/PhotoCapture";

    public static final String PICTURE_FOLDER = "/picFolder";
    public static final String BACKUP_PICTURE_FOLDER = "/BKPImage";
    public static final String KEY_USER_ID = "userId";

    public static final String KEY_IMAGE_TYPE = "imageType";

    public static final String KEY_IMG_TYPE_SAMPLE1 = "SamplePic1";
    public static final String KEY_IMG_TYPE_SAMPLE2 = "SamplePic2";
    public static final String KEY_IMG_TYPE_SAMPLE3 = "SamplePic3";
    public static final String KEY_IMG_TYPE_SAMPLE4 = "SamplePic4";
    public static final String KEY_IMG_TYPE_SAMPLE5 = "SamplePic5";

    public static final String KEY_BAR_CODE = "barCode";
    public static final String KEY_FILE_PATH = "filePath";
    public static final String KEY_IMAGE_NAME = "imageName";



	
}
