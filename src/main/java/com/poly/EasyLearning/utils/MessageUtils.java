package com.poly.EasyLearning.utils;

public class MessageUtils {
    public enum Account {
        CREATE_SUCCESS("Create new account success."),
        ALREADY_EXIST("Account already exist."),
        CREATE_FAIL("Create new account fail."),
        WRONG_PASSWORD("Wrong password."),
        WRONG_USERNAME("Wrong username."),
        NOT_FOUND("Account not found.");
        private String value;

        Account(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum Role {
        NOT_FOUND("Role not found.");
        private String value;

        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum Lesson {
        NOT_FOUND("Lesson not found.");
        private String value;

        Lesson(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum Quiz {
        NOT_FOUND("Quiz not found.");
        private String value;

        Quiz(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum Room {
        NOT_FOUND("Room not found.");
        private String value;

        Room(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum Result {
        NOT_FOUND("Result not found.");
        private String value;

        Result(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
    public enum Question{
        NOT_FOUND("Question not found.");
        private String value;
        Question(String value){
            this.value = value;
        }
        public String getValue(){
            return this.value;
        }
    }
}
