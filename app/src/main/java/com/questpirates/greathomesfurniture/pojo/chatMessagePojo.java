package com.questpirates.greathomesfurniture.pojo;

public class chatMessagePojo {

    private boolean isMine;
    private String chatContent;

    public chatMessagePojo(boolean isMine, String chatContent) {
        this.isMine = isMine;
        this.chatContent = chatContent;
    }


    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }
}
