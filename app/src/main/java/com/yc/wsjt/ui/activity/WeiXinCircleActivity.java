package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daasuu.bl.ArrowDirection;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.google.android.material.appbar.AppBarLayout;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.CircleBaseSetInfo;
import com.yc.wsjt.bean.CircleInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.CircleListAdapter;
import com.yc.wsjt.ui.custom.AppBarStateChangeListener;
import com.yc.wsjt.ui.custom.RoundedCornersTransformation;
import com.yc.wsjt.ui.custom.VipPayTypeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/30.
 */
public class WeiXinCircleActivity extends BaseActivity implements View.OnClickListener, VipPayTypeDialog.PayListener {

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_circle_title)
    TextView mTitleTv;

    @BindView(R.id.iv_take_phone)
    ImageView mTakePhoneIv;

    @BindView(R.id.iv_circle_cover)
    ImageView mCircleCoverIv;

    @BindView(R.id.tv_role_name)
    TextView mRoleNameTv;

    @BindView(R.id.iv_role_head)
    ImageView mRoleHeadIv;

    @BindView(R.id.circle_list)
    RecyclerView circleListView;

    CircleListAdapter circleListAdapter;

    CircleBaseSetInfo circleBaseSetInfo;

    private PopupWindow popupWindow;

    private LinearLayout mPraiseLayout;

    private LinearLayout mCommentLayout;

    private int circleId;

    private boolean isUse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.transparent), 0);
        StatusBarUtil.setDarkMode(this);
    }

    @Override
    protected void initViews() {
        vipPayTypeDialog.setPayListener(this);
        View popView = LayoutInflater.from(this).inflate(R.layout.parse_toast_view, null);
        mPraiseLayout = popView.findViewById(R.id.layout_praise);
        mCommentLayout = popView.findViewById(R.id.layout_comment);

        BubbleLayout bubbleLayout = (BubbleLayout) popView;
        popupWindow = BubblePopupHelper.create(this, bubbleLayout);

        mPraiseLayout.setOnClickListener(this);
        mCommentLayout.setOnClickListener(this);

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Logger.i("STATE", state.name());
                if (state == State.EXPANDED) {
                    Logger.i("展开--->");
                    changeState(true);
                } else if (state == State.COLLAPSED) {
                    Logger.i("折叠--->");
                    changeState(false);
                } else {
                    //中间状态
                }
            }
        });

        circleListAdapter = new CircleListAdapter(this, null);
        circleListView.setLayoutManager(new LinearLayoutManager(this));
        circleListView.setAdapter(circleListAdapter);
        circleListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_moments) {
                    circleId = circleListAdapter.getData().get(position).getId();
                    int[] location = new int[2];
                    view.getLocationInWindow(location);
                    bubbleLayout.setArrowDirection(ArrowDirection.RIGHT);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - SizeUtils.dp2px(200), location[1] - view.getHeight());
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        isUse = bundle.getBoolean("is_use", false);
        if (!isUse) {
            if (openVipDialog != null && !openVipDialog.isShowing()) {
                openVipDialog.show();
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (mAppDatabase.circleBaseSetInfoDao().getItemById(PhoneUtils.getDeviceId()) != null) {
                circleBaseSetInfo = mAppDatabase.circleBaseSetInfoDao().getItemById(PhoneUtils.getDeviceId());
                RequestOptions options = new RequestOptions();
                options.transform(new RoundedCornersTransformation(SizeUtils.dp2px(8), 0));
                Glide.with(this).load(circleBaseSetInfo.getRoleHead()).apply(options).into(mRoleHeadIv);
                Glide.with(this).load(circleBaseSetInfo.getCoverImage()).into(mCircleCoverIv);
                mRoleNameTv.setText(circleBaseSetInfo.getRoleName());
            }

            if (mAppDatabase.circleInfoDao().getListByDId(PhoneUtils.getDeviceId()) != null) {
                List<CircleInfo> tempList = new ArrayList<>();
                for (CircleInfo circleInfo : mAppDatabase.circleInfoDao().getListByDId(PhoneUtils.getDeviceId())) {
                    circleInfo.setCommentInfos(mAppDatabase.commentInfoDao().getListByCId(circleInfo.getId()));
                    tempList.add(circleInfo);
                }
                circleListAdapter.setNewData(tempList);
                circleListAdapter.notifyDataSetChanged();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void changeState(boolean expand) {
        if (expand) {
            mBackIv.setImageResource(R.mipmap.pyq_white_back);
            mTitleTv.setVisibility(View.GONE);
            mTakePhoneIv.setImageResource(R.mipmap.wechat_phone_white);
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.transparent), 0);
            StatusBarUtil.setDarkMode(this);
        } else {
            mBackIv.setImageResource(R.mipmap.pyq_black_back);
            mTitleTv.setVisibility(View.VISIBLE);
            mTakePhoneIv.setImageResource(R.mipmap.wechat_phone_black);
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.get_money_color), 0);
            StatusBarUtil.setLightMode(this);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_praise) {
            Intent intent = new Intent(this, AddPraiseActivity.class);
            intent.putExtra("circle_id", circleId);
            startActivity(intent);
            if(popupWindow != null && popupWindow.isShowing()){
                popupWindow.dismiss();
            }
        }

        if (v.getId() == R.id.layout_comment) {
            if(popupWindow != null && popupWindow.isShowing()){
                popupWindow.dismiss();
            }
            Intent intent = new Intent(this, AddCommentActivity.class);
            intent.putExtra("circle_id", circleId);
            startActivity(intent);
        }
    }

    @Override
    public void addComment() {
        super.addComment();
    }

    @Override
    public void closeOpenVip() {
        super.closeOpenVip();
        finish();
    }

    @Override
    public void pay() {
        Logger.i("打开支付--->");
    }

    @Override
    public void payClose() {
        Logger.i("支付界面关闭--->");
        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
