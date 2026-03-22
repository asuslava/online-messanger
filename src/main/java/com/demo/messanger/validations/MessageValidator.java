package com.demo.messanger.validations;

import com.demo.messanger.models.Message;

public class MessageValidator {


    public boolean checkMessage(Message message){
        if(!(message.getMessage().isBlank())){
            message.setMessage(message.getMessage().trim());
            return true;
        }else{
            return false;
        }
    }
}
