package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/31.
 */
public class AddCircleActivity extends BaseActivity {

    public static final int REQUEST_CODE_CHOOSE = 1000;

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

    }

    @Override
    protected void initViews() {

    }

    @OnClick(R.id.iv_add)
    void chooseImage() {
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(9)
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
                    Logger.i(JSONObject.toJSONString(Matisse.obtainPathResult(data)));
                    if (Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                        //Glide.with(context).load(Uri.fromFile(new File(Matisse.obtainPathResult(data).get(0)))).into(mChooseIv);
                        Logger.i("add image --->" + Matisse.obtainPathResult(data).get(0));
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.btn_config)
    void config() {
        finish();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
