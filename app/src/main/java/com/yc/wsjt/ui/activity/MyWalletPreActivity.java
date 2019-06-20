package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.ckr.decoration.DividerGridItemDecoration;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.QuickInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.TencentServerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyWalletPreActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tencent_list)
    RecyclerView mTencentListView;

    @BindView(R.id.third_list)
    RecyclerView mThirdListView;

    @BindView(R.id.tv_money)
    TextView mMoneyTv;

    private String[] quickNames = {"信用卡还款", "手机充值", "理财通", "生活缴费", "Q币充值", "城市服务", "腾讯公益"};

    private String[] thirds = {"火车票机票", "滴滴出行", "京东优选", "美团外卖", "电影演出赛事", "吃喝玩乐", "酒店", "拼多多", "蘑菇街女装", "唯品会特卖", "转转二手", "贝克找房"};

    private DividerGridItemDecoration itemDecoration;

    private int[] quickImages = {
            R.mipmap.xyk_icon,
            R.mipmap.sjcz_icon,
            R.mipmap.lct_icon,
            R.mipmap.shjf_icon,
            R.mipmap.qbcz_icon,
            R.mipmap.csfw_icon,
            R.mipmap.txgy_icon};

    private int[] thirdImages = {
            R.mipmap.hcpjp_icon,
            R.mipmap.didi_icon,
            R.mipmap.jdyx_icon,
            R.mipmap.mtwm_icon,
            R.mipmap.dyyc_icon,
            R.mipmap.chwl_icon,
            R.mipmap.jd_icon,
            R.mipmap.pdd_icon,
            R.mipmap.mgj_icon,
            R.mipmap.wph_icon,
            R.mipmap.zz_icon,
            R.mipmap.bk_icon};

    TencentServerAdapter tencentServerAdapter;

    TencentServerAdapter thirdServerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_wallet_show;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initViews() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mMoneyTv.setText("¥" + bundle.getString("money"));
        }

        List<QuickInfo> list = new ArrayList<>();
        for (int i = 0; i < quickImages.length; i++) {
            QuickInfo quickInfo = new QuickInfo();
            quickInfo.setLocalImg(quickImages[i]);
            quickInfo.setName(quickNames[i]);
            list.add(quickInfo);
        }

        List<QuickInfo> list1 = new ArrayList<>();
        for (int i = 0; i < thirdImages.length; i++) {
            QuickInfo quickInfo = new QuickInfo();
            quickInfo.setLocalImg(thirdImages[i]);
            quickInfo.setName(thirds[i]);
            list1.add(quickInfo);
        }

        DividerGridItemDecoration.Builder builder = new DividerGridItemDecoration.Builder(getContext(), LinearLayoutManager.VERTICAL, 3);
        builder.setShowOtherStyle(true).setDivider(R.drawable.bg_divider_list);
        itemDecoration = builder.build();
        mTencentListView.addItemDecoration(itemDecoration);
        mTencentListView.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));

        tencentServerAdapter = new TencentServerAdapter(this, list, ScreenUtils.getScreenWidth() / 3);
        mTencentListView.setAdapter(tencentServerAdapter);

        thirdServerAdapter = new TencentServerAdapter(this, list1, ScreenUtils.getScreenWidth() / 3);
        mThirdListView.setAdapter(thirdServerAdapter);

        mThirdListView.addItemDecoration(itemDecoration);
        mThirdListView.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
