package com.wuwutongkeji.dibaidanche.bike.contract.maintain;

import android.net.Uri;
import android.text.Spanned;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.entity.OptionTypeEntity;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public interface BikeMaintainContract {

    abstract class BikeMaintainBasePresenter extends BasePresenter<BikeMaintainContract.BikeMaintainBaseView> {
        
        public abstract void onSubmit(String bicycleNum, int feedbackType, String feedbackContent, List<Uri> imgs);
    }

    interface BikeMaintainBaseView extends BaseDependView<BikeMaintainContract.BikeMaintainBasePresenter> {

        void onLoadOption(List<OptionTypeEntity> data);
    }
}
