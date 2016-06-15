package com.hs.libs.imageselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.sw926.imagefileselector.ImageFileSelector;


/**
 * Created by huasheng on 16/6/13.
 */
public class HsImageSelector extends ImageFileSelector {
    private int maxWidth = 1280;
    private int maxHeight = 1280;
    private int quality = 80;

    private boolean flag = false;

    public HsImageSelector(Context context,HsImageSelectCallback callback) {
        super(context);
        setCallback(callback);
    }

    /**
     * 设置压缩后的文件大小
     *
     * @param maxWidth  压缩后文件宽度
     * @param maxHeight 压缩后文件高度
     */
    @Override
    public void setOutPutImageSize(int maxWidth, int maxHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    /**
     * 设置压缩后保存图片的质量
     *
     * @param quality 图片质量 0 - 100
     */
    @Override
    public void setQuality(int quality) {
        this.quality = quality;
    }

    @Override
    public void selectImage(Activity activity) {
        flag = true;
        super.setOutPutImageSize(maxWidth,maxHeight);
        super.setQuality(quality);
        super.selectImage(activity);
    }
    @Override
    public void takePhoto(Activity activity) {
        flag = true;
        super.setOutPutImageSize(maxWidth,maxHeight);
        super.setQuality(quality);
        super.takePhoto(activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(flag){
            super.onActivityResult(requestCode, resultCode, data);
            flag = false;
        }
    }
}
