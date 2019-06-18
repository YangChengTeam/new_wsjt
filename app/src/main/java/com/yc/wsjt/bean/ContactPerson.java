package com.yc.wsjt.bean;

import android.text.TextUtils;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.orhanobut.logger.Logger;

/**
 * Created by fighting on 2017/5/17.
 */

public class ContactPerson implements Comparable<ContactPerson> {

    public String mName;
    public String mHead;
    public String mPinyin;
    public String firstLetter;

    public String getPinyin() {
        return mPinyin;
    }

    public ContactPerson(String name) {
        this.mName = name;
        if (!TextUtils.isEmpty(name)) {
            String firstChar = name.charAt(0) + "";
            if (firstChar.matches("^[0-9]+") || firstChar.matches("#")) {
                /**以数字和#开头的name都归于#*/
                mPinyin = "zzzzzzzzzzzzzzzzzzzz";
                firstLetter = "#";
            } else {
                try {
                    this.mPinyin = PinyinHelper.convertToPinyinString(name.trim(), "", PinyinFormat.WITHOUT_TONE);
                } catch (PinyinException e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(mPinyin)) {
                    char firstLetterChar = this.mPinyin.toUpperCase().charAt(0);
                    firstLetter = firstLetterChar + "";

                    Logger.i("firstLetter--->" + firstLetter);

                    if (firstLetterChar < 'A' || firstLetterChar > 'Z') {
                        mPinyin = "zzzzzzzzzzzzzzzzzzzz";
                        firstLetter = "#";
                    }
                }
            }

        } else {
            mPinyin = "zzzzzzzzzzzzzzzzzzzz";
            firstLetter = "#";
        }
    }

    public String getmHead() {
        return mHead;
    }

    public void setmHead(String mHead) {
        this.mHead = mHead;
    }

    @Override
    public int compareTo(ContactPerson o) {
        //此处为了兼容汉字的拼音与英文的字母首字母一致，统一转换为大写的字母比较来分组
        return this.mPinyin.toUpperCase().compareTo(o.getPinyin().toUpperCase());
    }
}
