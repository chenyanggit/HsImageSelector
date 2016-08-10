package com.sw926.imagefileselector;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by huasheng on 16/8/3.
 *
 * @author <a href="mailto:huasheng@2dfire.com">花生</a>.
 */
public class HsImageCaptureHelper extends  ImageCaptureHelper{

    public static final int CAMERA_REQUEST_CODE = 0x21;
    public static final int CAMERA_REQUEST_CODE_CROP = 0x22;

    private  Activity activity =null;

    public void captureImageHs(Activity activity,int permissioncode) {
        if (Build.VERSION.SDK_INT >= 16) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                this.activity = activity;
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                        permissioncode);
            } else {
                captureImage(activity);
            }
        } else {
            captureImage(activity);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
         if (requestCode == CAMERA_REQUEST_CODE) {
             Log.e("cy","onRequestPermissionsResult CAMERA_REQUEST_CODE");
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImageHs(activity,CAMERA_REQUEST_CODE);
                return;
            }
        }else if (requestCode == CAMERA_REQUEST_CODE_CROP) {
             Log.e("cy","onRequestPermissionsResult CAMERA_REQUEST_CODE_CROP");
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImageHs(activity,CAMERA_REQUEST_CODE_CROP);
                return;
            }
        }
    }
}
