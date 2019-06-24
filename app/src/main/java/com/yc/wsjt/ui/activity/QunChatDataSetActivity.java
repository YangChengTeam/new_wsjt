package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.RoleInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.RoleHeadAdapter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.InputDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/9.
 */
public class QunChatDataSetActivity extends BaseActivity implements InputDialog.InputTxtListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.iv_bg_img)
    ImageView mChatBgIv;

    @BindView(R.id.tv_qun_name)
    TextView mQunNameTv;

    @BindView(R.id.tv_qun_liao_number)
    TextView mQunNumberTv;

    @BindView(R.id.sb_disturb)
    SwitchButton sbDisturbBtn;

    @BindView(R.id.sb_open_nick_name)
    SwitchButton sbOpenNickNameBtn;

    private File outputImage;

    private QunChatInfo mQunChatInfo;

    InputDialog inputDialog;

    private int settingType = 1;

    List<RoleInfo> roleInfoList;

    @BindView(R.id.role_list_view)
    RecyclerView mRoleListView;

    RoleHeadAdapter roleHeadAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qun_chat_data_set;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        mTitleTv.setText(getResources().getString(R.string.setting_qunliao));
        mConfigBtn.setVisibility(View.GONE);
    }

    @Override
    protected void initViews() {


        sbDisturbBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAppDatabase.qunChatInfoDao().updateDisturb(isChecked, mQunChatInfo != null ? mQunChatInfo.getId() : 0);
            }
        });

        sbOpenNickNameBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAppDatabase.qunChatInfoDao().updateOpenNickName(isChecked, mQunChatInfo != null ? mQunChatInfo.getId() : 0);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        roleHeadAdapter = new RoleHeadAdapter(this, null, ScreenUtils.getScreenWidth() / 4);
        mRoleListView.setLayoutManager(new GridLayoutManager(this, 4));
        mRoleListView.setAdapter(roleHeadAdapter);

        roleHeadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //最后一个是:"+"号
                if (position == roleHeadAdapter.getData().size() - 1) {
                    Intent intent = new Intent(QunChatDataSetActivity.this, ChooseRoleActivity.class);
                    intent.putExtra("person_type", 2);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (mAppDatabase.qunChatInfoDao().getItemById(PhoneUtils.getDeviceId()) != null) {
                mQunChatInfo = mAppDatabase.qunChatInfoDao().getItemById(PhoneUtils.getDeviceId());

                Glide.with(this).load(mQunChatInfo.getChatInfoBg()).into(mChatBgIv);
                mQunNameTv.setText(mQunChatInfo.getQunLiaoName());
                mQunNumberTv.setText(mQunChatInfo.getChatPersonNumber() + "");
                sbDisturbBtn.setChecked(mQunChatInfo.isOpenDisturb());
                sbOpenNickNameBtn.setChecked(mQunChatInfo.isOpenNickName());

                App.getApp().qunChatInfo = mQunChatInfo;
            } else {
                mQunChatInfo = new QunChatInfo();
                mQunChatInfo.setDeviceId(PhoneUtils.getDeviceId());
                Long qunLiaoId = mAppDatabase.qunChatInfoDao().insert(mQunChatInfo);
                mQunChatInfo.setId(qunLiaoId);

                //初始化群聊角色信息
                RoleInfo roleInfo = new RoleInfo();
                Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.mipmap.c1);
                outputImage = new File(PathUtils.getExternalAppPicturesPath(), TimeUtils.getNowMills() + ".png");
                Logger.i("out path1--->" + outputImage.getAbsolutePath());
                ImageUtils.save(bmp1, outputImage, Bitmap.CompressFormat.PNG);
                roleInfo.setAvatar(outputImage.getAbsolutePath());
                roleInfo.setNickname("王小明");
                roleInfo.setQunLiaoId(qunLiaoId);

                RoleInfo roleInfo1 = new RoleInfo();
                Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.mipmap.c2);
                outputImage = new File(PathUtils.getExternalAppPicturesPath(), TimeUtils.getNowMills() + ".png");
                Logger.i("out path2--->" + outputImage.getAbsolutePath());
                ImageUtils.save(bmp2, outputImage, Bitmap.CompressFormat.PNG);
                roleInfo1.setAvatar(outputImage.getAbsolutePath());
                roleInfo1.setNickname("张晓琳");
                roleInfo1.setQunLiaoId(qunLiaoId);

                RoleInfo roleInfo2 = new RoleInfo();
                Bitmap bmp3 = BitmapFactory.decodeResource(getResources(), R.mipmap.c3);
                outputImage = new File(PathUtils.getExternalAppPicturesPath(), TimeUtils.getNowMills() + ".png");
                Logger.i("out path2--->" + outputImage.getAbsolutePath());
                ImageUtils.save(bmp3, outputImage, Bitmap.CompressFormat.PNG);
                roleInfo2.setAvatar(outputImage.getAbsolutePath());
                roleInfo2.setNickname("李成明");
                roleInfo2.setQunLiaoId(qunLiaoId);

                mAppDatabase.roleInfoDao().insert(roleInfo, roleInfo1, roleInfo2);

                App.getApp().qunChatInfo = mQunChatInfo;
            }

            if (App.getApp().getTempPerson() != null) {
                if (roleHeadAdapter.getData() != null && roleHeadAdapter.getData().size() > 0) {
                    RoleInfo roleInfo = new RoleInfo();
                    roleInfo.setNickname(App.getApp().getTempPerson().mName);
                    roleInfo.setAvatar(App.getApp().getTempPerson().mHead);
                    roleInfo.setQunLiaoId(mQunChatInfo != null ? mQunChatInfo.getId() : 0);
                    mAppDatabase.roleInfoDao().insert(roleInfo);
                }
            }

            if (App.getApp().qunChatInfo != null) {
                List<RoleInfo> roleList = mAppDatabase.roleInfoDao().getListById(App.getApp().qunChatInfo.getId());
                roleHeadAdapter.setNewData(roleList);

                RoleInfo addPlus = new RoleInfo();
                addPlus.setAvatarLocalImg(R.mipmap.add_icon);
                roleHeadAdapter.addData(addPlus);
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.layout_bg_image)
    void chooseImage() {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .showSingleMediaType(true)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    if (Matisse.obtainPathResult(data) != null && Matisse.obtainPathResult(data).size() > 0) {
                        String tempPath = Matisse.obtainPathResult(data).get(0);
                        Logger.i("out path--->" + tempPath);
                        Glide.with(QunChatDataSetActivity.this).load(tempPath).into(mChatBgIv);
                        //更新背景图
                        if (mQunChatInfo != null) {
                            mAppDatabase.qunChatInfoDao().updateBgImage(tempPath, mQunChatInfo.getId());
                        }
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.layout_qunliao_name)
    void qunliaoName() {
        settingType = 1;
        inputDialog = new InputDialog(this, R.style.scale_dialog);
        inputDialog.setTxtListener(this);
        inputDialog.setmTitle("群聊名称");
        inputDialog.show();

        WindowManager.LayoutParams windowParams = inputDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.75);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        inputDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.layout_qunliao_number)
    void qunliaoNumber() {
        settingType = 2;
        inputDialog = new InputDialog(this, R.style.scale_dialog);
        inputDialog.setTxtListener(this);
        inputDialog.setmTitle("群聊人数");
        inputDialog.show();

        WindowManager.LayoutParams windowParams = inputDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.75);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        inputDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void inputTxt(String inputTxt) {
        if (settingType == 1) {
            mQunNameTv.setText(inputTxt);
            mAppDatabase.qunChatInfoDao().updateQunName(inputTxt, mQunChatInfo != null ? mQunChatInfo.getId() : 0);
        } else {
            mQunNumberTv.setText(inputTxt);
            mAppDatabase.qunChatInfoDao().updateQunNumber(inputTxt, mQunChatInfo != null ? mQunChatInfo.getId() : 0);
        }
    }
}
