package com.wuwutongkeji.dibaidanche.bike.contract.feedback;

import android.net.Uri;
import android.text.Spanned;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.entity.OptionTypeEntity;

import java.io.File;
import java.util.List;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public interface BikeFeedbackContract {

    abstract class BikeFeedbackBasePresenter extends BasePresenter<BikeFeedbackContract.BikeFeedbackBaseView> {

        public abstract void onSubmit(String bicycleNum, int feedbackType, String feedbackContent, List<Uri> imgs);
    }

    interface BikeFeedbackBaseView extends BaseDependView<BikeFeedbackContract.BikeFeedbackBasePresenter> {

        void onLoadOption(List<OptionTypeEntity> data);
    }
}
