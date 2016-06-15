package com.hs.libs.imageselector;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by huasheng on 16/6/13.
 */
public abstract class HsImageSelectCallback implements HsImageSelector.Callback{
    @Override
    public void onSuccess(String filepath) {
        if (TextUtils.isEmpty(filepath)) {
            onFileSelectFailure();
        }else{
            onFileSelectSucess(new File(filepath));
        }
    }

    @Override
    public void onError() {
        onFileSelectFailure();
    }

    /**
     * 文件处理成功回调事件.
     * @param file
     */
    public abstract void onFileSelectSucess(File file);

    /**
     * 文件处理失败回调事件.
     */
    public abstract void onFileSelectFailure();

}
