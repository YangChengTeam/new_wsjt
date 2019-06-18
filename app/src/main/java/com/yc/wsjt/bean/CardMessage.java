package com.yc.wsjt.bean;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
public class CardMessage extends MessageContent {

    private String personName;

    private String personWeChatNumber;

    private int personCardHead;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonWeChatNumber() {
        return personWeChatNumber;
    }

    public void setPersonWeChatNumber(String personWeChatNumber) {
        this.personWeChatNumber = personWeChatNumber;
    }

    public int getPersonCardHead() {
        return personCardHead;
    }

    public void setPersonCardHead(int personCardHead) {
        this.personCardHead = personCardHead;
    }
}
