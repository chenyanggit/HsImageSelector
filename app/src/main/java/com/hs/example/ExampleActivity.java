package com.hs.example;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hs.libs.imageselector.HsImageCropCallback;
import com.hs.libs.imageselector.HsImageCropper;
import com.hs.libs.imageselector.HsImageSelectCallback;
import com.hs.libs.imageselector.HsImageSelector;
import com.sw926.imagefileselector.ImageFileSelectorNew;

import java.io.File;

public class ExampleActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;
    private TextView mTvPath;
    private HsImageSelector mImageFileSelector;
    private HsImageCropper mImageCropper;

    private EditText mEtWidth;
    private EditText mEtHeight;

    private Button mBtnCrop;

    private File mCurrentSelectFile;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ImageFileSelectorNew.setDebug(true);

        findViewById(R.id.btn_from_sdcard).setOnClickListener(this);
        findViewById(R.id.btn_from_camera).setOnClickListener(this);
        findViewById(R.id.btn_crop).setOnClickListener(this);

        mImageView = (ImageView) findViewById(R.id.iv_image);
        mTvPath = (TextView) findViewById(R.id.tv_path);
        mEtWidth = (EditText) findViewById(R.id.et_width);
        mEtHeight = (EditText) findViewById(R.id.et_height);
        mBtnCrop = (Button) findViewById(R.id.btn_crop);

        mImageFileSelector = new HsImageSelector(this,new HsImageSelectCallback() {
            @Override
            public void onFileSelectSucess(File file) {
                if (!TextUtils.isEmpty(file.getAbsolutePath())) {
                    loadImage(file.getAbsolutePath());
                    mCurrentSelectFile = file;
                    mBtnCrop.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(ExampleActivity.this, "select image file error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFileSelectFailure() {
                Toast.makeText(ExampleActivity.this, "select image file error", Toast.LENGTH_LONG).show();
            }
        });

        mImageCropper = new HsImageCropper(this,new HsImageCropCallback() {
            /**
             * 文件处理成功回调事件.
             *
             * @param file
             */
            @Override
            public void onFileCropSucess(File file) {
                loadImage(file.getPath());
            }
            /**
             * 文件处理失败回调事件.
             *
             * @param errMessage
             */
            @Override
            public void onFileCropFailure(String errMessage) {
                Toast.makeText(ExampleActivity.this, errMessage, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadImage(final String file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = BitmapFactory.decodeFile(file);
                File imageFile = new File(file);
                final StringBuilder builder = new StringBuilder();
                builder.append("path: ");
                builder.append(file);
                builder.append("\n\n");
                builder.append("length: ");
                builder.append((int) (imageFile.length() / 1024d));
                builder.append("KB");
                builder.append("\n\n");
                builder.append("image size: (");
                builder.append(bitmap.getWidth());
                builder.append(", ");
                builder.append(bitmap.getHeight());
                builder.append(")");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                        mTvPath.setText(builder.toString());
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageFileSelector.onActivityResult(requestCode, resultCode, data);
        mImageCropper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mImageFileSelector.onSaveInstanceState(outState);
        mImageCropper.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageFileSelector.onRestoreInstanceState(savedInstanceState);
        mImageCropper.onRestoreInstanceState(savedInstanceState);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mImageFileSelector.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mImageCropper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initImageFileSelector() {
        int w = 0;
        if (!TextUtils.isEmpty(mEtWidth.getText().toString())) {
            w = Integer.parseInt(mEtWidth.getText().toString());
        }
        int h = 0;
        if (!TextUtils.isEmpty(mEtHeight.getText().toString())) {
            h = Integer.parseInt(mEtHeight.getText().toString());
        }
        mImageFileSelector.setOutPutImageSize(w, h);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_from_camera: {
//                initImageFileSelector();
                mImageFileSelector.takePhoto(this);
                break;
            }
            case R.id.btn_from_sdcard: {
//                initImageFileSelector();
                mImageFileSelector.selectImage(this);
                break;
            }
            case R.id.btn_crop: {
                int w = 0;
                if (!TextUtils.isEmpty(mEtWidth.getText().toString())) {
                    w = Integer.parseInt(mEtWidth.getText().toString());
                }
                int h = 0;
                if (!TextUtils.isEmpty(mEtHeight.getText().toString())) {
                    h = Integer.parseInt(mEtHeight.getText().toString());
                }
//                    mImageCropper.setOutPut(w, h);
//                    mImageCropper.setOutPutAspect(2, 4);
                    mImageCropper.startCrop(HsImageCropper.CROP_SELECT);
                break;
            }
        }
    }
}
