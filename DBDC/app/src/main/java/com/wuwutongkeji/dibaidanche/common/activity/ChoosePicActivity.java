package com.wuwutongkeji.dibaidanche.common.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.util.DeviceUtil;
import com.wuwutongkeji.dibaidanche.common.util.PictureUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Mr.Bai on 17/9/17.
 */

public class ChoosePicActivity extends Activity implements View.OnClickListener {

    public String TAG = getClass().getSimpleName();

    private static final int REQ_GALLERY = 333;
    private static final int REQUEST_CODE_PICK_IMAGE = 222;
    private static final int SUCCESSCODE = 100;

    public static String KEY_PIC = "KEY_PIC";

    private String mPublicPhotoPath;
    @BindView(R.id.lin_all)
    LinearLayout linAll;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_pic);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.take_pic, R.id.take_gallery, R.id.lin_all})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_pic:
                showTakePicture();
                break;
            case R.id.take_gallery:
                getImageFromAlbum();
                break;
            case R.id.lin_all:
                this.finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    //权限申请成功
    @PermissionSuccess(requestCode = SUCCESSCODE)
    public void doSomething() {
        //申请成功
        startTake();
    }

    @PermissionFail(requestCode = SUCCESSCODE)
    public void doFailSomething() {
        showMsg("权限获取失败");
    }

    private void startTake() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断是否有相机应用
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //创建临时图片文件
            File photoFile = null;
            try {
                photoFile = PictureUtils.createPublicImageFile();
                mPublicPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //设置Action为拍照
            if (photoFile != null) {
                takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                Uri photoURI;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoURI = FileProvider.getUriForFile(this, DeviceUtil.getPackageName(), photoFile);
                    takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                } else {
                    //7.0以下使用这种方式创建一个Uri
                    photoURI = Uri.fromFile(photoFile);
                }

                //这里加入flag
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQ_GALLERY);
            }
        }
    }


    /**
     * 获取相册中的图片
     */
    public void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    //拍照的功能
    private void showTakePicture() {
        PermissionGen.with(this)
                .addRequestCode(SUCCESSCODE)
                .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;
        int type = 0;

        switch (requestCode) {
            //拍照
            case REQ_GALLERY:
                if (resultCode != Activity.RESULT_OK) return;
                uri = Uri.parse(mPublicPhotoPath);
                PictureUtils.galleryAddPic(mPublicPhotoPath, this);
                type = 0;
                break;
            //获取相册的图片
            case REQUEST_CODE_PICK_IMAGE:
                if (data == null) return;
                uri = data.getData();
                type = 1;
                break;
        }
        gulpImagemin(uri,type);
    }

    private String uri2String(int type,Uri uri){
        String path;
        if(type == 0){ // 相机
            path = uri.getPath();
        }else{
            int sdkVersion = Integer.valueOf(Build.VERSION.SDK);
            if (sdkVersion >= 19) {
                path = PictureUtils.getPath_above19(this, uri);
            } else {
                path = PictureUtils.getFilePath_below19(this, uri);
            }
        }
        return path;
    }
    private void gulpImagemin(final Uri uri,int type) {

        Log.d(TAG," uri:  " + uri + " type: " + type);

        if(null == uri){
            return;
        }
        final String path = uri2String(type,uri);

        Log.d(TAG,"path:  " + path);

        Luban.with(this)
                .load(path)                                   // 传人要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                .setTargetDir(new File(path).getParentFile().getPath())                       // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {

                        if(file.length() > 2*1048576){
                            showMsg("文件不能大于2M");
                            return;
                        }


                        Intent intent = new Intent();
                        intent.putExtra(KEY_PIC, file.getPath());
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                        if(new File(path).length() > 2*1048576){
                            showMsg("文件不能大于2M");
                            return;
                        }
                        Intent intent = new Intent();
                        intent.putExtra(KEY_PIC, path);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }).launch();    //启动压缩
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void showMsg(String msg){
        Snackbar.make(linAll, msg, Snackbar.LENGTH_SHORT).show();
    }
}
