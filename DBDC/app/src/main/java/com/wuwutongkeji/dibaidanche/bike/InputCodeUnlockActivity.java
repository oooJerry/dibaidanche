package com.wuwutongkeji.dibaidanche.bike;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseAppCompatActivity;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.common.widget.ContainsEmojiEditText;

import butterknife.BindView;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import zxing.camera.CameraManager;

/**
 * Created by Mr.Bai on 2017/9/19.
 */

public class InputCodeUnlockActivity extends BaseAppCompatActivity {

    public static final int CODE_PERMISSION = 10;
    public static String KEY_RESULT = "KEY_RESULT";

    boolean hasPermission;

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.edit_inputCode)
    ContainsEmojiEditText editInputCode;
    @BindView(R.id.btn_unlocking)
    Button btnUnlocking;
    @BindView(R.id.btn_scanCode)
    LinearLayout btnScanCode;
    @BindView(R.id.btn_light)
    LinearLayout btnLight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bike_lock_inputcode;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra(KEY_RESULT,"");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        btnScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editInputCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btnUnlocking.setEnabled(!TextUtil.isEmpty(editable.toString().trim()));
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

        btnUnlocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = editInputCode.getText().toString().trim();

                if(TextUtil.isEmpty(code)){
                    showMsg("请输入自行车上的编号");
                    return;
                }

                Intent intent = getIntent();
                intent.putExtra(KEY_RESULT,code);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

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
        CameraManager mCameraManager = CameraManager.get();
        btnLight.setSelected(mCameraManager.isFlight());
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionGen.with(this)
                .addRequestCode(CODE_PERMISSION)
                .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .request();
    }
}
