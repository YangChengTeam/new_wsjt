package com.yc.wsjt.presenter;

import android.content.Context;

import com.yc.wsjt.model.executer.JobExecutor;
import com.yc.wsjt.model.executer.RxJavaExecuter;
import com.yc.wsjt.model.executer.UIThread;


/**
 * Created by Administrator on 2017/2/17.
 */

public abstract class Presenter {

    protected Context context;
    protected RxJavaExecuter rxJavaExecuter;

    public Presenter(Context context){
        this.context = context;
        this.rxJavaExecuter = new RxJavaExecuter(JobExecutor.instance(), UIThread.instance());
    }

    public abstract void destroy();
}
