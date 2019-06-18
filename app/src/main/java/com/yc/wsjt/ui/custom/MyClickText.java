package com.yc.wsjt.ui.custom;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.yc.wsjt.R;


public class MyClickText extends ClickableSpan {
    private Context context;

    public MyClickText(Context context) {
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        //设置文本的颜色
        ds.setColor(ContextCompat.getColor(context, R.color.praise_color));
        //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        //Toast.makeText(context, "发生了点击效果", Toast.LENGTH_SHORT).show();
    }
}