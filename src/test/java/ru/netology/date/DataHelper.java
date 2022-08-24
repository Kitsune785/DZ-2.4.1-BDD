package ru.netology.date;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getUserAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private long balance;
    }

    public static CardInfo getFirstCardInfo(AuthInfo authInfo) {
        return new CardInfo("5559 0000 0000 0001", 10_000);
    }

    public static CardInfo getSecondCardInfo(AuthInfo authInfo) {
        return new CardInfo("5559 0000 0000 0002", 10_000);
    }

    @Value
    public static class ReplenishmentInfo {
        private String refillAmount;
        private String senderCardNumber;

        public static ReplenishmentInfo getReplenishmentInfo(String senderCardNumber, long refillAmount) {
            return new ReplenishmentInfo(Long.toString(refillAmount), senderCardNumber);
        }

        public static ReplenishmentInfo getInvalidReplenishmentInfo(long refillAmount) {
            return new ReplenishmentInfo(Long.toString(refillAmount), "5559 0000 0000 0003");
        }

        @Value
        public static class TransferInfo {
            private int amount;
            private String numberCard;
        }
    }
}