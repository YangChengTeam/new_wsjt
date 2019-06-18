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

import java.util.ArrayList;
import java.util.List;


public class VideoTimeDialog extends Dialog {

    private Context mContext;

    WheelView mMinuteWheelView;

    WheelView mSecondWheelView;

    LinearLayout mConfigLayout;

    LinearLayout mCancelLayout;

    private int maxMinute;

    public DateSelectListener dateSelectListener;

    public interface DateSelectListener {
        void configDate(String selectDate);
    }

    public void setDateSelectListener(DateSelectListener dateSelectListener) {
        this.dateSelectListener = dateSelectListener;
    }

    public VideoTimeDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public VideoTimeDialog(Context context, int themeResId,int maxMinute) {
        super(context, themeResId);
        this.mContext = context;
        this.maxMinute = maxMinute;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_time_dialog_view);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        mConfigLayout = findViewById(R.id.layout_config);
        mCancelLayout = findViewById(R.id.layout_cancel);

        WheelView.WheelViewStyle wheelViewStyle = new WheelView.WheelViewStyle();
        wheelViewStyle.textSize = 14;
        wheelViewStyle.selectedTextSize = 14;
        wheelViewStyle.holoBorderColor = ContextCompat.getColor(mContext, R.color.line_color);
        wheelViewStyle.selectedTextColor = ContextCompat.getColor(mContext, R.color.color_blue);

        //分钟
        List<String> hourList = new ArrayList<>();
        for (int i = 0; i < maxMinute; i++) {
            hourList.add(i + "");
        }
        mMinuteWheelView = findViewById(R.id.wheel_view_minute);
        mMinuteWheelView.setLoop(true);
        mMinuteWheelView.setStyle(wheelViewStyle);
        mMinuteWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mMinuteWheelView.setSkin(WheelView.Skin.Holo);
        mMinuteWheelView.setWheelData(hourList);

        //秒
        List<String> minuteList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            minuteList.add(String.format("%02d", i));
        }
        mSecondWheelView = findViewById(R.id.wheel_view_second);
        mSecondWheelView.setLoop(true);
        mSecondWheelView.setStyle(wheelViewStyle);
        mSecondWheelView.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mSecondWheelView.setSkin(WheelView.Skin.Holo);
        mSecondWheelView.setWheelData(minuteList);


        mConfigLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String minute = mMinuteWheelView.getSelectionItem().toString();
                String second = mSecondWheelView.getSelectionItem().toString();

                dateSelectListener.configDate(minute + ":" + second);
                dismiss();
            }
        });

        mCancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}