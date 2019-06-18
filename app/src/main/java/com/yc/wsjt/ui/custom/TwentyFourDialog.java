package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.orhanobut.logger.Logger;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.yc.wsjt.R;
import com.yc.wsjt.util.MyDateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TwentyFourDialog extends Dialog {

    private Context mContext;

    WheelView mMonthWheelView;

    WheelView mHourWheelView;

    WheelView mMinuteWheelView;

    LinearLayout mConfigLayout;

    public TFDateSelectListener tfDateSelectListener;

    public interface TFDateSelectListener {
        void tfConfigDate(String selectDate);
    }

    public void setTfDateSelectListener(TFDateSelectListener tfDateSelectListener) {
        this.tfDateSelectListener = tfDateSelectListener;
    }

    public TwentyFourDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public TwentyFourDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twenty_four_dialog_view);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        mConfigLayout = findViewById(R.id.layout_config);

        WheelView.WheelViewStyle wheelViewStyle = new WheelView.WheelViewStyle();
        wheelViewStyle.textSize = 14;
        wheelViewStyle.selectedTextSize = 14;
        wheelViewStyle.holoBorderColor = ContextCompat.getColor(mContext, R.color.line_color);
        wheelViewStyle.selectedTextColor = ContextCompat.getColor(mContext, R.color.color_blue);

        //日期
        List<String> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        Calendar c = Calendar.getInstance();
        list.add("今天");
        list.add("昨天");
        for (int i = 2; i < 7; i++) {
            //过去七天
            c.setTime(new Date());
            c.add(Calendar.DATE, -i);
            Date d = c.getTime();
            String day = format.format(d);
            list.add(day);
            Logger.i("过去" + i + "天--->" + day);
        }

        mMonthWheelView = findViewById(R.id.wheel_view_month);
        mMonthWheelView.setLoop(true);
        mMonthWheelView.setStyle(wheelViewStyle);
        mMonthWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mMonthWheelView.setSkin(WheelView.Skin.Holo);
        mMonthWheelView.setWheelData(list);

        //小时
        List<String> hourList = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            hourList.add(i + "");
        }
        mHourWheelView = findViewById(R.id.wheel_view_hour);
        mHourWheelView.setLoop(true);
        mHourWheelView.setStyle(wheelViewStyle);
        mHourWheelView.setSelection(MyDateUtils.getTwentyFourHour() - 1);
        mHourWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mHourWheelView.setSkin(WheelView.Skin.Holo);
        mHourWheelView.setWheelData(hourList);

        //分钟
        List<String> minuteList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            minuteList.add(String.format("%02d", i));
        }
        mMinuteWheelView = findViewById(R.id.wheel_view_minute);
        mMinuteWheelView.setLoop(true);
        mMinuteWheelView.setStyle(wheelViewStyle);
        mMinuteWheelView.setSelection(MyDateUtils.getCurrentMinute());
        mMinuteWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mMinuteWheelView.setSkin(WheelView.Skin.Holo);
        mMinuteWheelView.setWheelData(minuteList);

        mConfigLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String riqi = mMonthWheelView.getSelectedItemPosition() == 0 ? "" : mMonthWheelView.getSelectionItem().toString();
                String hour = mHourWheelView.getSelectionItem().toString();
                String minute = mMinuteWheelView.getSelectionItem().toString();

                tfDateSelectListener.tfConfigDate(riqi + " " + hour + ":" + minute);
                dismiss();
            }
        });
    }

}