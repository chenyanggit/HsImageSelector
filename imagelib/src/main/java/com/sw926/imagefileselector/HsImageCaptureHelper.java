package com.sw926.imagefileselector;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by huasheng on 16/8/3.
 *
 * @author <a href="mailto:huasheng@2dfire.com">花生</a>.
 */
public class HsImageCaptureHelper extends  ImageCaptureHelper{

    private static final int CAMERA_REQUEST_CODE = 0x12;

    private  Activity activity =null;

    public void captureImageHs(Activity activity) {
        if (Build.VERSION.SDK_INT >= 16) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                this.activity = activity;
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST_CODE);
            } else {
                captureImage(activity);
            }
        } else {
            captureImage(activity);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImageHs(activity);
                return;
            }
        }
    }
}
