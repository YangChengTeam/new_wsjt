/**
 *
 */
package com.yc.wsjt.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.TextView;

import com.yc.wsjt.ui.custom.CenterAlignImageSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : zejian
 * @time : 2016年1月5日 上午11:30:39
 * @email : shinezejian@163.com
 * @description :文本中的emojb字符处理为表情图片
 */
public class SpanStringUtils {

    public static SpannableString getEmotionContent(int emotion_map_type, final Context context, final TextView tv, String source) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();

        String regexEmotion = emotion_map_type == EmotionUtils.EMOTION_CLASSIC_TYPE ? "\\[([\u4e00-\u9fa5\\w])+\\]" : "\\[([E\\d\\w])+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);

        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = EmotionUtils.getImgByName(emotion_map_type, key);
            if (imgRes != null) {
                // 压缩表情图片
                int size = (int) tv.getTextSize() * 13 / 10;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                if (bitmap != null) {
                    Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                    //ImageSpan span = new ImageSpan(context, bitmap);
                    CenterAlignImageSpan centerAlignImageSpan = new CenterAlignImageSpan(scaleBitmap);
                    spannableString.setSpan(centerAlignImageSpan, start, start + key.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return spannableString;
    }

}
