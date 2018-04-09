package com.wuwutongkeji.dibaidanche.bike;

import android.Manifest;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseAppCompatActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.net.NetDataManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.util.DateUtil;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Vector;

import butterknife.BindView;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import zxing.camera.CameraManager;
import zxing.decoding.CaptureActivityHandler;
import zxing.decoding.InactivityTimer;
import zxing.view.ViewfinderView;

/**
 * Created by Mr.Bai on 2017/9/18.
 */

public class ScanCodeUnlockActivity extends BaseAppCompatActivity implements SurfaceHolder.Callback {

    public static final int CODE_PERMISSION = 10;
    public static final int REQ_INPUT = 11;

    @BindView(R.id.scanner_view)
    SurfaceView scannerView;
    @BindView(R.id.viewfinder_content)
    ViewfinderView viewfinderContent;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.btn_inputCode)
    LinearLayout btnInputCode;
    @BindView(R.id.btn_light)
    LinearLayout btnLight;


    boolean vibrate;
    boolean playBeep;
    boolean hasSurface;
    boolean hasPermission;
    String characterSet;
    MediaPlayer mediaPlayer;
    InactivityTimer inactivityTimer;
    Vector<BarcodeFormat> decodeFormats;
    CaptureActivityHandler handler;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bike_lock_scancode;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnInputCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onOpenLock("702002");
                Intent intent = AppIntent.getInputCodeUnlockActivity(mContext);
                startActivityForResult(intent,REQ_INPUT);
            }
        });
        btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!hasPermission){
                    showMsg("请获取相机权限");
                    return;
                }
                view.setSelected(!view.isSelected());
                CameraManager cameraManager = CameraManager.get();

                cameraManager.flashHandler();
            }
        });

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionFail(requestCode = CODE_PERMISSION)
    public void doFailSomething() {
        hasPermission = false;
        showMsg("权限获取失败");
    }

    //权限申请成功
    @PermissionSuccess(requestCode = CODE_PERMISSION)
    public void doSomething() {
        hasPermission = true;
        CameraManager.init();
        btnLight.setSelected(CameraManager.get().isFlight());
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface && hasPermission) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        hasSurface = false;
    }


    private void initCamera(SurfaceHolder surfaceHolder) {

        if(null != handler){
            handler.quitSynchronously();
            handler = null;
        }
        try {
            CameraManager manager = CameraManager.get();
            manager.closeDriver();
            manager.openDriver(surfaceHolder);
            if(manager.isFlight()){
                manager.flashHandler();
            }
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }

//        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
//        }
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(0.10f, 0.10f);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(200L);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public ViewfinderView getViewfinderView() {
        return viewfinderContent;
    }


    public void drawViewfinder() {
        viewfinderContent.drawViewfinder();
    }

    public Handler getHandler() {
        return handler;
    }


    @Override
    public void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = scannerView.getHolder();
        if (hasSurface && hasPermission) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

        PermissionGen.with(this)
                .addRequestCode(CODE_PERMISSION)
                .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .request();
    }

    private void onOffCamera(){
        CameraManager manager = CameraManager.get();
        if(null != handler){
            handler.quitSynchronously();
            handler = null;
        }
        if(hasPermission){
            if(manager.isFlight()){
                manager.flashHandler();
            }
            manager.closeDriver();
        }
    }

    @Override
    public void onDestroy() {
        inactivityTimer.shutdown();
        onOffCamera();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            if(requestCode == REQ_INPUT){
                String code = data.getStringExtra(InputCodeUnlockActivity.KEY_RESULT);

                if(TextUtil.isEmpty(code)){
                    finish();
                }else {
                    onOpenLock(code);
                }
            }
        }
    }

    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = TextUtil.getScanCode(result.getText());
        if (TextUtils.isEmpty(resultString)) {
            showMsg("扫描失败,请重试!");
            finish();
        } else {
            onOpenLock(resultString);
        }
    }


    private void onOpenLock(final String bicycleNum){

        final String userId = SharedPreferencesUtil.getUser().getId();

        Log.d(TAG,"bicycleNum = " + bicycleNum);

        NetDataManager.getInstance()
                .bicycle_openLock(userId,bicycleNum)
                .subscribe(new DefaultNetSubscriber<LockEntity>(mLoadingDialog) {
                    @Override
                    public void onCompleted(LockEntity openLockEntity) {

                        if(openLockEntity.getCode() != 0){
                            showMsg(openLockEntity.getReason());
                            finish();
                        }else{
                            openLockEntity.setCreateTime(DateUtil.long2String(
                                    System.currentTimeMillis(),DateUtil.yyyy_MM_dd_HH__mm__ss));
                            openLockEntity.setBicycleNum(bicycleNum);
                            openLockEntity.setUserId(userId);
                            EventBus.getDefault().post(openLockEntity);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(dialog.isShowing()){
                            dialog.dismiss();
                        }
                        showMsg(e.getMessage());
                        finish();
                    }
                });
    }
}
