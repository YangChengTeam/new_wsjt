package com.yc.wsjt.presenter;

import android.content.Context;

import com.yc.wsjt.base.BasePresenterImp;
import com.yc.wsjt.base.IBaseView;
import com.yc.wsjt.bean.QuestionInfo;
import com.yc.wsjt.bean.ResultInfo;
import com.yc.wsjt.model.QuestionInfoModelImp;

import java.io.File;

/**
 * Created by admin on 2017/4/7.
 */

public class QuestionInfoPresenterImp extends BasePresenterImp<IBaseView, ResultInfo> implements QuestionInfoPresenter {

    private Context context = null;
    private QuestionInfoModelImp questionInfoModelImp = null;

    public QuestionInfoPresenterImp(IBaseView view, Context context) {
        super(view);
        this.context = context;
        this.questionInfoModelImp = new QuestionInfoModelImp(context);
    }

    @Override
    public void addQuestion(QuestionInfo questionInfo, File file) {
        questionInfoModelImp.addQuestion(questionInfo, file,this);
    }
}
