package com.poly.EasyLearning.utils;

public class MessageUtils {
    public enum Account{
        CREATE_SUCCESS("Create new account success."),
        ALREADY_EXIST("Account already exist."),
        CREATE_FAIL("Create new account fail.");
        private String value;
        Account(String value){
            this.value = value;
        }
        public String getValue(){
            return this.value;
        }
    }
}
