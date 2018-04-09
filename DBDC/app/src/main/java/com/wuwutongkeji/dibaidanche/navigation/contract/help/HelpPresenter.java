package com.wuwutongkeji.dibaidanche.navigation.contract.help;

import com.wuwutongkeji.dibaidanche.common.config.AppInterface;
import com.wuwutongkeji.dibaidanche.entity.HelpEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Bai on 17/10/3.
 */

public class HelpPresenter extends HelpContract.HelpBasePresenter {

    @Override
    protected void onAttach() {
        super.onAttach();

        List<HelpEntity> data = new ArrayList<>();
        data.add(new HelpEntity("实名认证", AppInterface.HOWTO_AUTH));
        data.add(new HelpEntity("如何报修", AppInterface.HOWTO_REPAIRS));
        data.add(new HelpEntity("如何关锁与复位密码", AppInterface.HOWTO_CLOSE));
        data.add(new HelpEntity("如何开锁", AppInterface.HOWTO_OPEN));
        data.add(new HelpEntity("如何取车和停车", AppInterface.HOWTO_STOP));
        data.add(new HelpEntity("使用的拜单车如何计费", AppInterface.HOWTO_FINISH));
        data.add(new HelpEntity("押金是多少", AppInterface.HOWTO_HOWMUCH));
        data.add(new HelpEntity("邀请好友后,为何没有相应奖励", AppInterface.HOWTO_NOREWARD));
        data.add(new HelpEntity("如何与客服人员联系", AppInterface.HOWTO_CONNECT));

        mDependView.loadData(data);
    }
}
