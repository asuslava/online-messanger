package com.demo.messanger.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    // VALIDATIONS
    // ==========================================
    public static boolean validEmail(String email){
        Pattern p = Pattern.compile("\\b[a-zA-Z0-9._%+-]+@(gmail|yahoo|hotmail)\\.com\\b"); //regex checks the format of email
        Matcher m = p.matcher(email);
        if(m.find()){
            return true;
        }
        return false;
    }

    public static boolean validPassword(String password){
        if(password.length() < 6){ //user cant have less than 6 digits
            return false;
        }
        return true;
    }
    // ==========================================
}
