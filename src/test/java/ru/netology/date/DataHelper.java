package ru.netology.date;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static String getNumberCard(int index) {
        String[] number = {"5559000000000001", "5559000000000002"};
        return number[index];
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }
}