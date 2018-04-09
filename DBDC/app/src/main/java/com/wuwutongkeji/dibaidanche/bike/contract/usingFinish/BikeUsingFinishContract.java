package com.wuwutongkeji.dibaidanche.bike.contract.usingFinish;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.entity.ConsumeEntity;

/**
 * Created by Mr.Bai on 17/10/5.
 */

public interface BikeUsingFinishContract {

    abstract class BikeUsingFinishBasePresenter extends BasePresenter<BikeUsingFinishContract.BikeUsingFinishBaseView> {

    }

    interface BikeUsingFinishBaseView extends BaseDependView<BikeUsingFinishContract.BikeUsingFinishBasePresenter> {

        void loadData(ConsumeEntity data);
    }

}
