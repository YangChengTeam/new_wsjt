package com.yc.wsjt.bean;

public class MessageEvent {

    private int messageType;
    private int imageUrl;
    private String messageContent;
    private QuickInfo addQuickInfo;

    private boolean loginSuccess;
    private int mid;
    private int chatPos;
    private ModuleInfoWrapper moduleInfoWrapper;

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public ModuleInfoWrapper getModuleInfoWrapper() {
        return moduleInfoWrapper;
    }

    public void setModuleInfoWrapper(ModuleInfoWrapper moduleInfoWrapper) {
        this.moduleInfoWrapper = moduleInfoWrapper;
    }

    public QuickInfo getAddQuickInfo() {
        return addQuickInfo;
    }

    public void setAddQuickInfo(QuickInfo addQuickInfo) {
        this.addQuickInfo = addQuickInfo;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getChatPos() {
        return chatPos;
    }

    public void setChatPos(int chatPos) {
        this.chatPos = chatPos;
    }
}
