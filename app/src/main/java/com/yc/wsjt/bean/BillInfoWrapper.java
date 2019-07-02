package com.yc.wsjt.bean;

import java.util.List;

public class BillInfoWrapper {
    private String yearMonth;
    private List<BillInfo> billInfoList;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public List<BillInfo> getBillInfoList() {
        return billInfoList;
    }

    public void setBillInfoList(List<BillInfo> billInfoList) {
        this.billInfoList = billInfoList;
    }
}
