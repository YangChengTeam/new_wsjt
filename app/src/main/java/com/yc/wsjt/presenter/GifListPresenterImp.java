package com.yc.wsjt.presenter;

import android.content.Context;

import com.yc.wsjt.base.BasePresenterImp;
import com.yc.wsjt.bean.GifListRet;
import com.yc.wsjt.model.GifListModelImp;
import com.yc.wsjt.view.GifListView;

/**
 * Created by admin on 2017/4/7.
 */

public class GifListPresenterImp extends BasePresenterImp<GifListView, GifListRet> implements GifListPresenter {

    private Context context = null;
    private GifListModelImp movieModelImp = null;

    public GifListPresenterImp(GifListView view, Context context) {
        super(view);
        this.context = context;
        this.movieModelImp = new GifListModelImp(context);
    }

    @Override
    public void getGifList(int index) {
        movieModelImp.getGifList(index,this);
    }
}
