package com.sw926.imagefileselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;

/**
 * Created by huasheng on 16/6/13.
 */
public class HsImageSelectorCrop {

    public HsImageSelectorCrop(Context context, Callback callback) {
        this(context);
        setCallback(callback);
    }

    private static final String TAG = HsImageSelectorCrop.class.getSimpleName();

    private Callback mCallback;
    private ImagePickHelper mImagePickHelper;
    private HsImageCaptureHelper mImageTaker;

    public HsImageSelectorCrop(final Context context) {
        mImagePickHelper = new ImagePickHelper(context);
        mImagePickHelper.setCallback(new ImagePickHelper.Callback() {
            @Override
            public void onSuccess(String file) {
                AppLogger.d(TAG, "select image from sdcard: " + file);
                handleResult(file);
            }

            @Override
            public void onError() {
                handleError();
            }
        });

        mImageTaker = new HsImageCaptureHelper();
        mImageTaker.setCallback(new ImageCaptureHelper.Callback() {
            @Override
            public void onSuccess(String file) {
                AppLogger.d(TAG, "select image from camera: " + file);
                handleResult(file);
            }


            @Override
            public void onError() {
                handleError();
            }
        });
    }

    public static void setDebug(boolean debug) {
        AppLogger.DEBUG = debug;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mImagePickHelper.onActivityResult(requestCode, resultCode, data);
        mImageTaker.onActivityResult(requestCode, resultCode, data);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode ==ImagePickHelper.READ_EXTERNAL_STORAGE_REQUEST_CODE_CROP ){
            mImagePickHelper.onRequestPermissionsResult(ImagePickHelper.READ_EXTERNAL_STORAGE_REQUEST_CODE_CROP, permissions, grantResults);
        }else if(requestCode ==HsImageCaptureHelper.CAMERA_REQUEST_CODE_CROP){
            mImageTaker.onRequestPermissionsResult(HsImageCaptureHelper.CAMERA_REQUEST_CODE_CROP, permissions, grantResults);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        mImageTaker.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mImageTaker.onRestoreInstanceState(savedInstanceState);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void selectImage(Activity activity) {
        mImagePickHelper.selectorImage(activity,ImagePickHelper.READ_EXTERNAL_STORAGE_REQUEST_CODE_CROP);
    }


    public void takePhoto(Activity activity) {
        mImageTaker.captureImageHs(activity,HsImageCaptureHelper.CAMERA_REQUEST_CODE_CROP);
    }


    private void handleResult(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            mCallback.onSuccess(fileName);
        } else {
            mCallback.onError();
        }
    }

    private void handleError() {
        if (mCallback != null) {
            mCallback.onError();
        }
    }

    public interface Callback {
        void onSuccess(String file);

        void onError();
    }
}
