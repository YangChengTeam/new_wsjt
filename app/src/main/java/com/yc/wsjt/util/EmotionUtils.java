
package com.yc.wsjt.util;


import android.util.ArrayMap;

import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;

/**
 * @author : zejian
 * @time : 2016年1月5日 上午11:32:33
 * @email : shinezejian@163.com
 * @description :默认表情加载类,可自己添加多种默认表情，分别建立不同的map存放和不同的标志符即可
 */
public class EmotionUtils {

    /**
     * 默认表情类型标志符
     */
    public static final int EMOTION_CLASSIC_TYPE = 0x0001;//经典默认表情

    public static final int EMOJI_TYPE = 0x0002;//e
    /**
     * key-默认表情文字;
     * value-默认表情图片资源
     */
    public static ArrayMap<String, Integer> EMPTY_MAP;
    public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;


    static {
        EMPTY_MAP = new ArrayMap<>();
        EMOTION_CLASSIC_MAP = new ArrayMap<>();

        EMOTION_CLASSIC_MAP.put("[默认表情1]", R.drawable.expression_1);
        EMOTION_CLASSIC_MAP.put("[默认表情2]", R.drawable.expression_2);
        EMOTION_CLASSIC_MAP.put("[默认表情3]", R.drawable.expression_3);
        EMOTION_CLASSIC_MAP.put("[默认表情4]", R.drawable.expression_4);
        EMOTION_CLASSIC_MAP.put("[默认表情5]", R.drawable.expression_5);
        EMOTION_CLASSIC_MAP.put("[默认表情6]", R.drawable.expression_6);
        EMOTION_CLASSIC_MAP.put("[默认表情7]", R.drawable.expression_7);
        EMOTION_CLASSIC_MAP.put("[默认表情8]", R.drawable.expression_8);
        EMOTION_CLASSIC_MAP.put("[默认表情9]", R.drawable.expression_9);
        EMOTION_CLASSIC_MAP.put("[默认表情10]", R.drawable.expression_10);
        EMOTION_CLASSIC_MAP.put("[默认表情11]", R.drawable.expression_11);
        EMOTION_CLASSIC_MAP.put("[默认表情12]", R.drawable.expression_12);
        EMOTION_CLASSIC_MAP.put("[默认表情13]", R.drawable.expression_13);
        EMOTION_CLASSIC_MAP.put("[默认表情14]", R.drawable.expression_14);
        EMOTION_CLASSIC_MAP.put("[默认表情15]", R.drawable.expression_15);
        EMOTION_CLASSIC_MAP.put("[默认表情16]", R.drawable.expression_16);
        EMOTION_CLASSIC_MAP.put("[默认表情17]", R.drawable.expression_17);
        EMOTION_CLASSIC_MAP.put("[默认表情18]", R.drawable.expression_18);
        EMOTION_CLASSIC_MAP.put("[默认表情19]", R.drawable.expression_19);
        EMOTION_CLASSIC_MAP.put("[默认表情20]", R.drawable.expression_20);
        EMOTION_CLASSIC_MAP.put("[默认表情21]", R.drawable.expression_21);
        EMOTION_CLASSIC_MAP.put("[默认表情22]", R.drawable.expression_22);
        EMOTION_CLASSIC_MAP.put("[默认表情23]", R.drawable.expression_23);
        EMOTION_CLASSIC_MAP.put("[默认表情24]", R.drawable.expression_24);
        EMOTION_CLASSIC_MAP.put("[默认表情25]", R.drawable.expression_25);
        EMOTION_CLASSIC_MAP.put("[默认表情26]", R.drawable.expression_26);
        EMOTION_CLASSIC_MAP.put("[默认表情27]", R.drawable.expression_27);
        EMOTION_CLASSIC_MAP.put("[默认表情28]", R.drawable.expression_28);
        EMOTION_CLASSIC_MAP.put("[默认表情29]", R.drawable.expression_29);
        EMOTION_CLASSIC_MAP.put("[默认表情30]", R.drawable.expression_30);
        EMOTION_CLASSIC_MAP.put("[默认表情31]", R.drawable.expression_31);
        EMOTION_CLASSIC_MAP.put("[默认表情32]", R.drawable.expression_32);
        EMOTION_CLASSIC_MAP.put("[默认表情33]", R.drawable.expression_33);
        EMOTION_CLASSIC_MAP.put("[默认表情34]", R.drawable.expression_34);
        EMOTION_CLASSIC_MAP.put("[默认表情35]", R.drawable.expression_35);
        EMOTION_CLASSIC_MAP.put("[默认表情36]", R.drawable.expression_36);
        EMOTION_CLASSIC_MAP.put("[默认表情37]", R.drawable.expression_37);
        EMOTION_CLASSIC_MAP.put("[默认表情38]", R.drawable.expression_38);
        EMOTION_CLASSIC_MAP.put("[默认表情39]", R.drawable.expression_39);
        EMOTION_CLASSIC_MAP.put("[默认表情40]", R.drawable.expression_40);
        EMOTION_CLASSIC_MAP.put("[默认表情41]", R.drawable.expression_41);
        EMOTION_CLASSIC_MAP.put("[默认表情42]", R.drawable.expression_42);
        EMOTION_CLASSIC_MAP.put("[默认表情43]", R.drawable.expression_43);
        EMOTION_CLASSIC_MAP.put("[默认表情44]", R.drawable.expression_44);
        EMOTION_CLASSIC_MAP.put("[默认表情45]", R.drawable.expression_45);
        EMOTION_CLASSIC_MAP.put("[默认表情46]", R.drawable.expression_46);
        EMOTION_CLASSIC_MAP.put("[默认表情47]", R.drawable.expression_47);
        EMOTION_CLASSIC_MAP.put("[默认表情48]", R.drawable.expression_48);
        EMOTION_CLASSIC_MAP.put("[默认表情49]", R.drawable.expression_49);
        EMOTION_CLASSIC_MAP.put("[默认表情50]", R.drawable.expression_50);

        EMOTION_CLASSIC_MAP.put("[E1]", R.drawable.emoji_1f600);
        EMOTION_CLASSIC_MAP.put("[E2]", R.drawable.emoji_1f601);
        EMOTION_CLASSIC_MAP.put("[E3]", R.drawable.emoji_1f602);
        EMOTION_CLASSIC_MAP.put("[E4]", R.drawable.emoji_1f603);
        EMOTION_CLASSIC_MAP.put("[E5]", R.drawable.emoji_1f604);
        EMOTION_CLASSIC_MAP.put("[E6]", R.drawable.emoji_1f605);
        EMOTION_CLASSIC_MAP.put("[E7]", R.drawable.emoji_1f606);
        EMOTION_CLASSIC_MAP.put("[E8]", R.drawable.emoji_1f607);
        EMOTION_CLASSIC_MAP.put("[E9]", R.drawable.emoji_1f608);
        EMOTION_CLASSIC_MAP.put("[E10]", R.drawable.emoji_1f609);
        EMOTION_CLASSIC_MAP.put("[E11]", R.drawable.emoji_1f610);
        EMOTION_CLASSIC_MAP.put("[E12]", R.drawable.emoji_1f611);
        EMOTION_CLASSIC_MAP.put("[E13]", R.drawable.emoji_1f612);
        EMOTION_CLASSIC_MAP.put("[E14]", R.drawable.emoji_1f613);
        EMOTION_CLASSIC_MAP.put("[E15]", R.drawable.emoji_1f614);
        EMOTION_CLASSIC_MAP.put("[E16]", R.drawable.emoji_1f615);
        EMOTION_CLASSIC_MAP.put("[E17]", R.drawable.emoji_1f616);
        EMOTION_CLASSIC_MAP.put("[E18]", R.drawable.emoji_1f617);
        EMOTION_CLASSIC_MAP.put("[E19]", R.drawable.emoji_1f618);
        EMOTION_CLASSIC_MAP.put("[E20]", R.drawable.emoji_1f619);
        EMOTION_CLASSIC_MAP.put("[E21]", R.drawable.emoji_1f620);
        EMOTION_CLASSIC_MAP.put("[E22]", R.drawable.emoji_1f621);
        EMOTION_CLASSIC_MAP.put("[E23]", R.drawable.emoji_1f622);
        EMOTION_CLASSIC_MAP.put("[E24]", R.drawable.emoji_1f623);
        EMOTION_CLASSIC_MAP.put("[E25]", R.drawable.emoji_1f624);
        EMOTION_CLASSIC_MAP.put("[E26]", R.drawable.emoji_1f625);
        EMOTION_CLASSIC_MAP.put("[E27]", R.drawable.emoji_1f626);
        EMOTION_CLASSIC_MAP.put("[E28]", R.drawable.emoji_1f627);
        EMOTION_CLASSIC_MAP.put("[E29]", R.drawable.emoji_1f628);
        EMOTION_CLASSIC_MAP.put("[E30]", R.drawable.emoji_1f629);
        EMOTION_CLASSIC_MAP.put("[E31]", R.drawable.emoji_1f630);
        EMOTION_CLASSIC_MAP.put("[E32]", R.drawable.emoji_1f631);
        EMOTION_CLASSIC_MAP.put("[E33]", R.drawable.emoji_1f632);
        EMOTION_CLASSIC_MAP.put("[E34]", R.drawable.emoji_1f633);
        EMOTION_CLASSIC_MAP.put("[E35]", R.drawable.emoji_1f634);
        EMOTION_CLASSIC_MAP.put("[E36]", R.drawable.emoji_1f635);
        EMOTION_CLASSIC_MAP.put("[E37]", R.drawable.emoji_1f636);
        EMOTION_CLASSIC_MAP.put("[E38]", R.drawable.emoji_1f637);
        EMOTION_CLASSIC_MAP.put("[E39]", R.drawable.emoji_1f638);
        EMOTION_CLASSIC_MAP.put("[E40]", R.drawable.emoji_1f639);
        EMOTION_CLASSIC_MAP.put("[E41]", R.drawable.emoji_1f640);


        EMPTY_MAP.put("[E1]", R.drawable.emoji_1f600);
        EMPTY_MAP.put("[E2]", R.drawable.emoji_1f601);
        EMPTY_MAP.put("[E3]", R.drawable.emoji_1f602);
        EMPTY_MAP.put("[E4]", R.drawable.emoji_1f603);
        EMPTY_MAP.put("[E5]", R.drawable.emoji_1f604);
        EMPTY_MAP.put("[E6]", R.drawable.emoji_1f605);
        EMPTY_MAP.put("[E7]", R.drawable.emoji_1f606);
        EMPTY_MAP.put("[E8]", R.drawable.emoji_1f607);
        EMPTY_MAP.put("[E9]", R.drawable.emoji_1f608);
        EMPTY_MAP.put("[E10]", R.drawable.emoji_1f609);
        EMPTY_MAP.put("[E11]", R.drawable.emoji_1f610);
        EMPTY_MAP.put("[E12]", R.drawable.emoji_1f611);
        EMPTY_MAP.put("[E13]", R.drawable.emoji_1f612);
        EMPTY_MAP.put("[E14]", R.drawable.emoji_1f613);
        EMPTY_MAP.put("[E15]", R.drawable.emoji_1f614);
        EMPTY_MAP.put("[E16]", R.drawable.emoji_1f615);
        EMPTY_MAP.put("[E17]", R.drawable.emoji_1f616);
        EMPTY_MAP.put("[E18]", R.drawable.emoji_1f617);
        EMPTY_MAP.put("[E19]", R.drawable.emoji_1f618);
        EMPTY_MAP.put("[E20]", R.drawable.emoji_1f619);
        EMPTY_MAP.put("[E21]", R.drawable.emoji_1f620);
        EMPTY_MAP.put("[E22]", R.drawable.emoji_1f621);
        EMPTY_MAP.put("[E23]", R.drawable.emoji_1f622);
        EMPTY_MAP.put("[E24]", R.drawable.emoji_1f623);
        EMPTY_MAP.put("[E25]", R.drawable.emoji_1f624);
        EMPTY_MAP.put("[E26]", R.drawable.emoji_1f625);
        EMPTY_MAP.put("[E27]", R.drawable.emoji_1f626);
        EMPTY_MAP.put("[E28]", R.drawable.emoji_1f627);
        EMPTY_MAP.put("[E29]", R.drawable.emoji_1f628);
        EMPTY_MAP.put("[E30]", R.drawable.emoji_1f629);
        EMPTY_MAP.put("[E31]", R.drawable.emoji_1f630);
        EMPTY_MAP.put("[E32]", R.drawable.emoji_1f631);
        EMPTY_MAP.put("[E33]", R.drawable.emoji_1f632);
        EMPTY_MAP.put("[E34]", R.drawable.emoji_1f633);
        EMPTY_MAP.put("[E35]", R.drawable.emoji_1f634);
        EMPTY_MAP.put("[E36]", R.drawable.emoji_1f635);
        EMPTY_MAP.put("[E37]", R.drawable.emoji_1f636);
        EMPTY_MAP.put("[E38]", R.drawable.emoji_1f637);
        EMPTY_MAP.put("[E39]", R.drawable.emoji_1f638);
        EMPTY_MAP.put("[E40]", R.drawable.emoji_1f639);
        EMPTY_MAP.put("[E41]", R.drawable.emoji_1f640);
    }

    /**
     * 根据名称获取当前默认表情图标R值
     *
     * @param EmotionType 默认表情类型标志符
     * @param imgName     名称
     * @return
     */
    public static int getImgByName(int EmotionType, String imgName) {
        Integer integer = null;
        switch (EmotionType) {
            case EMOJI_TYPE:
                integer = EMOTION_CLASSIC_MAP.get(imgName);
                break;
            case EMOTION_CLASSIC_TYPE:
                integer = EMOTION_CLASSIC_MAP.get(imgName);
                break;
            default:
                Logger.i("the emojiMap is null!! Handle Yourself ");
                break;
        }
        return integer == null ? -1 : integer;
    }

    /**
     * 根据类型获取默认表情数据
     *
     * @param EmotionType
     * @return
     */
    public static ArrayMap<String, Integer> getEmojiMap(int EmotionType) {
        ArrayMap EmojiMap = null;
        switch (EmotionType) {
            case EMOTION_CLASSIC_TYPE:
                EmojiMap = EMOTION_CLASSIC_MAP;
                break;
            default:
                EmojiMap = EMPTY_MAP;
                break;
        }
        return EmojiMap;
    }
}
