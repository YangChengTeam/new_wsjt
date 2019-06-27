package com.yc.wsjt.presenter;

import android.content.Context;

import com.yc.wsjt.base.BasePresenterImp;
import com.yc.wsjt.bean.EmojiInfoRet;
import com.yc.wsjt.model.EmojiInfoModelImp;
import com.yc.wsjt.view.EmojiInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class EmojiInfoPresenterImp extends BasePresenterImp<EmojiInfoView, EmojiInfoRet> implements EmojiInfoPresenter {

    private Context context = null;
    private EmojiInfoModelImp emojiInfoModelImp = null;

    public EmojiInfoPresenterImp(EmojiInfoView view, Context context) {
        super(view);
        this.context = context;
        this.emojiInfoModelImp = new EmojiInfoModelImp(context);
    }

    @Override
    public void getEmojiList() {
        emojiInfoModelImp.getEmojiList(this);
    }
}
