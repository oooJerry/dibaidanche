package com.wuwutongkeji.dibaidanche.navigation.contract.help;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.entity.HelpEntity;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/3.
 */

public interface HelpContract {

    abstract class HelpBasePresenter extends BasePresenter<HelpContract.HelpBaseView>{

    }

    interface HelpBaseView extends BaseDependView<HelpContract.HelpBasePresenter> {

        void loadData(List<HelpEntity> data);
    }
}
