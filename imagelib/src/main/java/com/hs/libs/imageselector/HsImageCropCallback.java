package com.hs.libs.imageselector;


import com.sw926.imagefileselector.ImageCropper;

import java.io.File;

/**
 * Created by huasheng on 16/6/13.
 */
public abstract class HsImageCropCallback implements HsImageCropper.ImageCropperCallback{
    /**
     * 文件处理成功回调事件.
     * @param file
     */
    public abstract void onFileCropSucess(File file);

    /**
     * 文件处理失败回调事件.
     */
    public abstract void onFileCropFailure(String errMessage);

    @Override
    public void onCropperCallback(ImageCropper.CropperResult result, File srcFile, File outFile) {

        if (result == ImageCropper.CropperResult.success) {
            onFileCropSucess(outFile);
        } else if (result == ImageCropper.CropperResult.error_illegal_input_file) {
            onFileCropFailure("input file error");
        } else if (result == ImageCropper.CropperResult.error_illegal_out_file) {
            onFileCropFailure("output file error");
        }
    }
}
