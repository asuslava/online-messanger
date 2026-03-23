package com.demo.messanger.dto;

public class ChatMessageDTO {

    private Long senderId;
    private Long chatRoomId;
    private String content;

    public Long getSenderId() {
        return senderId;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public String getContent() {
        return content;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
