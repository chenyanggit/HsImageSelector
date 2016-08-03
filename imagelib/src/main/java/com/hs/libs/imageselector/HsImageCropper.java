package com.hs.libs.imageselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sw926.imagefileselector.HsImageSelectorCrop;
import com.sw926.imagefileselector.ImageCropper;

import java.io.File;

/**
 * Created by huasheng on 16/6/13.
 */
public class HsImageCropper extends ImageCropper {


    private int mOutPutX = 1280;
    private int mOutPutY = 1280;
    private int mAspectX = 1;
    private int mAspectY = 1;

    private Activity currentConotext;

    private boolean flag = false;

    /**
     * <code>选择照片</code>
     */
    public static final Integer CROP_SELECT = new Integer(1);
    /**
     * <code>拍照</code>
     */
    public static final Integer CROP_CAMERA = new Integer(2);



    private HsImageCropCallback hsimageCropCallback;

    private HsImageSelectorCrop hsImageSelectorCrop;

    public HsImageCropper(Activity activity,HsImageCropCallback hsimageCropCallback) {
        super(activity);
        currentConotext = activity;
        initImageSelector(activity);
        setCallback(hsimageCropCallback);
        this.hsimageCropCallback = hsimageCropCallback;
    }

    private  void initImageSelector(Context context){
        hsImageSelectorCrop = new HsImageSelectorCrop(context,new HsImageSelectorCrop.Callback() {
            @Override
            public void onSuccess(String file) {
                cropImage(new File(file));
            }

            @Override
            public void onError() {
                if(hsimageCropCallback != null){
                    hsimageCropCallback.onFileCropFailure("phote file input err");
                }
            }
        });
    }


    @Override
    public void cropImage(File srcFile) {
        super.setOutPut(mOutPutX,mOutPutY);
        super.setOutPutAspect(mAspectX,mAspectY);
        super.cropImage(srcFile);
    }

    @Override
    public void setOutPut(int width, int height) {
        this.mOutPutX = width;
        this.mOutPutY = height;
    }

    @Override
    public void setOutPutAspect(int width, int height) {
        this.mAspectX = width;
        this.mAspectY = height;
    }




    /**
     * 开始截图.
     * @param type
     */
    public void startCrop(Integer type) {
        if(CROP_CAMERA.equals(type)){
            flag = true;
            hsImageSelectorCrop.takePhoto(currentConotext);
        }else if(CROP_SELECT.equals(type)){
            hsImageSelectorCrop.selectImage(currentConotext);
            flag = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(flag){
            hsImageSelectorCrop.onActivityResult(requestCode, resultCode, data);
            flag = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        hsImageSelectorCrop.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        hsImageSelectorCrop.onRestoreInstanceState(savedInstanceState);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        hsImageSelectorCrop.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
