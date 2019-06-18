package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "wx_transfer_info",inheritSuperIndices = true)
public class TransferMessage extends MessageContent {

    private int transferType;//1转账，2收钱

    private String transferNum; //转账金额

    private String transferDesc;//转账备注说明

    private boolean isReceive; //转账是否接收

    private String sendTime;

    private String receiveTime;

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public String getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(String transferNum) {
        this.transferNum = transferNum;
    }

    public String getTransferDesc() {
        return transferDesc;
    }

    public void setTransferDesc(String transferDesc) {
        this.transferDesc = transferDesc;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }
}
