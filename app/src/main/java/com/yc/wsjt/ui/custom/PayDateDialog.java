package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.yc.wsjt.R;
import com.yc.wsjt.util.MyDateUtils;

import java.util.ArrayList;
import java.util.List;


public class PayDateDialog extends Dialog {

    private Context mContext;

    WheelView mMonthWheelView;

    WheelView mTimeDayWheelView;

    WheelView mHourWheelView;

    WheelView mMinuteWheelView;

    LinearLayout mConfigLayout;

    public PayDateSelectListener payDateSelectListener;

    public interface PayDateSelectListener {
        void payDate(String payDate);
    }

    public void setPayDateSelectListener(PayDateSelectListener payDateSelectListener) {
        this.payDateSelectListener = payDateSelectListener;
    }

    public PayDateDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public PayDateDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_date_view);
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

        //月份
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.format("%02d", i));
        }
        mMonthWheelView = findViewById(R.id.wheel_view_month);
        mMonthWheelView.setLoop(true);
        mMonthWheelView.setStyle(wheelViewStyle);
        mMonthWheelView.setSelection(MyDateUtils.getCurrentMonth());
        mMonthWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mMonthWheelView.setSkin(WheelView.Skin.Holo);
        mMonthWheelView.setWheelData(list);

        //日,此处暂时不根据月份来确定日期
        List<String> dayList = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            dayList.add(String.format("%02d", i));
        }

        mTimeDayWheelView = findViewById(R.id.wheel_view_day);
        mTimeDayWheelView.setLoop(true);
        mTimeDayWheelView.setStyle(wheelViewStyle);
        mTimeDayWheelView.setSelection(MyDateUtils.getCurrentDay() - 1);
        mTimeDayWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mTimeDayWheelView.setSkin(WheelView.Skin.Holo);
        mTimeDayWheelView.setWheelData(dayList);

        //小时
        List<String> hourList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            hourList.add(i + "");
        }
        mHourWheelView = findViewById(R.id.wheel_view_hour);
        mHourWheelView.setLoop(true);
        mHourWheelView.setStyle(wheelViewStyle);
        mHourWheelView.setSelection(MyDateUtils.getCurrentHour() - 1);
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
        mMinuteWheelView.setSelection(MyDateUtils.getCurrentMinute());
        mMinuteWheelView.setStyle(wheelViewStyle);
        mMinuteWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mMinuteWheelView.setSkin(WheelView.Skin.Holo);
        mMinuteWheelView.setWheelData(minuteList);


        mConfigLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String yue = mMonthWheelView.getSelectionItem().toString() + "月";
                String ri = mTimeDayWheelView.getSelectionItem().toString() + "日  ";

                String hour = mHourWheelView.getSelectionItem().toString();
                String minute = mMinuteWheelView.getSelectionItem().toString();

                payDateSelectListener.payDate(yue + ri + hour + ":" + minute);
                dismiss();
            }
        });
    }

}