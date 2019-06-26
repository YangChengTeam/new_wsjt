package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.CircleInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.CircleImageAdapter;
import com.yc.wsjt.ui.custom.CustomDateDialog;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.InputDialog;
import com.yc.wsjt.ui.custom.SettingRoleDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/31.
 */
public class AddCircleActivity extends BaseActivity implements CustomDateDialog.DateSelectListener, InputDialog.InputTxtListener {

    public static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.et_circle_content)
    EditText mCircleContentEt;

    @BindView(R.id.tv_publish_time)
    TextView mPublishDateTv;

    @BindView(R.id.tv_address)
    TextView mAddressTv;

    @BindView(R.id.circle_image_list_view)
    RecyclerView mCircleImageListView;

    CircleImageAdapter circleImageAdapter;

    SettingRoleDialog settingRoleDialog;

    private boolean isChooseUser;

    CustomDateDialog customDateDialog;

    InputDialog inputDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_circle;
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
        mTitleTv.setText("编辑内容");
        settingRoleDialog = new SettingRoleDialog(this, R.style.custom_dialog);

        customDateDialog = new CustomDateDialog(this, R.style.date_dialog);
        customDateDialog.setDateSelectListener(this);

        inputDialog = new InputDialog(this, R.style.scale_dialog, "所在位置");
        inputDialog.setTxtListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getApp().getTempPerson() != null && isChooseUser) {
            mSendUserNameTv.setText(App.getApp().getTempPerson().mName);
            Glide.with(this).load(App.getApp().getTempPerson().mHead).into(mSendUserHeadIv);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        circleImageAdapter = new CircleImageAdapter(this, null);
        mCircleImageListView.setLayoutManager(new GridLayoutManager(this, 5));
        mCircleImageListView.setAdapter(circleImageAdapter);
        circleImageAdapter.addData(R.mipmap.add_icon);
        circleImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (circleImageAdapter.getData().get(position) instanceof Integer) {
                    Matisse.from(AddCircleActivity.this)
                            .choose(MimeType.ofAll())
                            .countable(true)
                            .maxSelectable(9)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new Glide4Engine())
                            .showSingleMediaType(true)
                            .forResult(REQUEST_CODE_CHOOSE);
                }
            }
        });
    }

    @OnClick(R.id.layout_send_info)
    void chooseRole() {
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            isChooseUser = true;
            settingRoleDialog.setType(2);
            settingRoleDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    if (Matisse.obtainPathResult(data) != null) {
                        if (circleImageAdapter != null && circleImageAdapter.getData() != null && circleImageAdapter.getData().size() > 0) {
                            circleImageAdapter.addData(0, Matisse.obtainPathResult(data));
                        }
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.layout_publish)
    void publishDate() {
        customDateDialog.show();

        //设置Dialog从窗体底部弹出
        customDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = customDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDateDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void configDate(String selectDate) {
        Logger.i("select date --->" + selectDate);
        mPublishDateTv.setText(selectDate);
    }

    @Override
    public void inputTxt(String address) {
        mAddressTv.setText(address);
    }

    @OnClick(R.id.btn_config)
    void config() {
        if (mSendUserNameTv.getText().equals("请选择")) {
            ToastUtils.showLong("请选择发送人");
            return;
        }

        if (StringUtils.isEmpty(mCircleContentEt.getText())) {
            ToastUtils.showLong("请输入消息内容");
            return;
        }

        if (StringUtils.isEmpty(mPublishDateTv.getText())) {
            ToastUtils.showLong("请选择发布时间");
            return;
        }

        StringBuffer imageStr = new StringBuffer();
        if (circleImageAdapter != null && circleImageAdapter.getData().size() > 0) {

            for (int i = 0; i < circleImageAdapter.getData().size(); i++) {
                imageStr.append(circleImageAdapter.getData().get(i)).append("#");
            }
        }
        try {
            CircleInfo circleInfo = new CircleInfo();
            circleInfo.setDeviceId(PhoneUtils.getDeviceId());
            circleInfo.setCircleImages(imageStr.toString());
            circleInfo.setUserName(mSendUserNameTv.getText().toString());
            circleInfo.setUserHead(App.getApp().getTempPerson() != null ? App.getApp().getTempPerson().mHead : "");
            circleInfo.setContent(mCircleContentEt.getText().toString());
            circleInfo.setAddress(mAddressTv.getText().toString());
            circleInfo.setSendDate(mPublishDateTv.getText().toString());

            mAppDatabase.circleInfoDao().insert(circleInfo);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        finish();
    }
}
