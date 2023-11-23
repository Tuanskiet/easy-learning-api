package com.poly.EasyLearning.utils;

public class MessageUtils {
    public enum Account{
        CREATE_SUCCESS("Create new account success."),
        ALREADY_EXIST("Account already exist."),
        CREATE_FAIL("Create new account fail."),
        WRONG_PASSWORD("Wrong password."),
        WRONG_USERNAME("Wrong username."),
        NOT_FOUND("Account not found.");
        private String value;
        Account(String value){
            this.value = value;
        }
        public String getValue(){
            return this.value;
        }
    }
}
