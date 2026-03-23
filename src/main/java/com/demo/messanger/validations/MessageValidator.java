package com.demo.messanger.validations;

import com.demo.messanger.models.Message;

public class MessageValidator {

    public boolean checkMessage(Message message){
        if(message.getContent() != null && !message.getContent().isBlank()){
            message.setContent(message.getContent().trim());
            return true;
        } else {
            return false;
        }
    }
}