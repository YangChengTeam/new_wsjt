package com.yc.wsjt.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.RedRecordInfo;
import com.yc.wsjt.bean.RoleInfo;
import com.yc.wsjt.common.AppDatabase;
import com.yc.wsjt.ui.adapter.RecordListAdapter;
import com.yc.wsjt.ui.custom.RedPackageUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangdinghui on 2019/5/29.
 * 群红包结果页面
 */
public class QunHongBaoActivity extends Activity {

    @BindView(R.id.layout_title)
    LinearLayout mTitleLayout;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.tv_red_remark)
    TextView mRedRemarkTv;

    @BindView(R.id.tv_red_money)
    TextView mRedMoneyTv;

    @BindView(R.id.tv_total_money)
    TextView mTotalInfoTv;

    @BindView(R.id.record_list_view)
    RecyclerView mRecordListView;

    RecordListAdapter recordListAdapter;

    private float redMoney;

    private Long redId;

    private int redCount;

    private AppDatabase mAppDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qun_hongbao_pre);
        ButterKnife.bind(this);
        //StatusBarUtil.setTranslucentForImageView(this, 0, mTitleLayout);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.red_top_color), 0);
        mAppDatabase = ((App) getApplication()).getAppDatabase();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            redId = Long.valueOf(bundle.getInt("redId", 0));
            mSendUserNameTv.setText(bundle.getString("send_user_name", ""));
            mRedRemarkTv.setText(bundle.getString("red_remark", "恭喜发财，大吉大利"));
            redMoney = bundle.getFloat("red_money");
            redCount = bundle.getInt("red_count", 1);
            mRedMoneyTv.setText("¥" + redMoney);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.user_head_def);
            Glide.with(this).load(bundle.getString("send_user_head")).apply(requestOptions).into(mSendUserHeadIv);
        }

        recordListAdapter = new RecordListAdapter(this, null);
        mRecordListView.setLayoutManager(new LinearLayoutManager(this));
        mRecordListView.setAdapter(recordListAdapter);

        List<RedRecordInfo> list = new ArrayList<>();

        if (mAppDatabase.redRecordInfoDao().getListByRedId(redId) != null && mAppDatabase.redRecordInfoDao().getListByRedId(redId).size() > 0) {
            list = mAppDatabase.redRecordInfoDao().getListByRedId(redId);
        } else {
            RedPackageUtils redPackageUtils = new RedPackageUtils();

            if (App.getApp().qunChatInfo != null) {
                if (App.getApp().qunChatInfo.getRoleList() != null && App.getApp().qunChatInfo.getRoleList().size() > 0) {
                    int roleSize = App.getApp().qunChatInfo.getRoleList().size();
                    List<BigDecimal> result = redPackageUtils.spiltRedPackets(new BigDecimal(redMoney), redCount);
                    Logger.i("分配金额--->" + JSON.toJSONString(result));
                    if (roleSize > redCount) {
                        roleSize = redCount;
                    }

                    for (int i = 0; i < roleSize; i++) {
                        RoleInfo roleInfo = App.getApp().qunChatInfo.getRoleList().get(i);
                        RedRecordInfo redRecordInfo = new RedRecordInfo();
                        redRecordInfo.setRedPackageId(redId);
                        redRecordInfo.setUserName(roleInfo.getNickname());
                        redRecordInfo.setUserHead(roleInfo.getAvatar());
                        redRecordInfo.setRedAmount(result.get(i) + "元");
                        redRecordInfo.setReceiveDate(TimeUtils.getNowString());
                        if (i == 0) {
                            redRecordInfo.setLuck(true);
                        }
                        mAppDatabase.redRecordInfoDao().insert(redRecordInfo);
                        list.add(redRecordInfo);
                    }
                }
            }
        }

        mTotalInfoTv.setText(redCount + "个红包共" + mRedMoneyTv.getText() + "元");
        recordListAdapter.setNewData(list);
    }
}
