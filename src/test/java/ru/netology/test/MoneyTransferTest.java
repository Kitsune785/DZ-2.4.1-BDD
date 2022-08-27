package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import ru.netology.date.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage1;
import ru.netology.page.LoginPage2;
import ru.netology.page.LoginPage3;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen=true;
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferOneForTwo() {
        var loginPage = new LoginPage1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var currentBalance2Card = DashboardPage.getCardBalance(DashboardPage.getIdTwo());
        var currentBalance1Card = DashboardPage.getCardBalance(DashboardPage.getIdOne());
        var sumTranslation = DashboardPage.getAmount(DashboardPage.getIdTwo());
        var choosingACard = dashboardPage.choosingCard(DashboardPage.getIdOne());
        var transfer = choosingACard.transfer(sumTranslation, 1);
        int balanceAfterTransfer2Card = currentBalance2Card - Integer.parseInt(sumTranslation);
        int balanceAfterTransfer1Card = currentBalance1Card + Integer.parseInt(sumTranslation);
        transfer.chekBalance(DashboardPage.getIdTwo(), balanceAfterTransfer2Card);
        transfer.chekBalance(DashboardPage.getIdOne(), balanceAfterTransfer1Card);
    }

    @Test
    void shouldTransferTwoForOne() {
        var loginPage = new LoginPage2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var currentBalance2Card = DashboardPage.getCardBalance(DashboardPage.getIdTwo());
        var currentBalance1Card = DashboardPage.getCardBalance(DashboardPage.getIdOne());
        var sumTranslation = DashboardPage.getAmount(DashboardPage.getIdOne());
        var choosingACard = dashboardPage.choosingCard(DashboardPage.getIdTwo());
        var transfer = choosingACard.transfer(sumTranslation, 0);
        int balanceAfterTransfer2Card = currentBalance2Card + Integer.parseInt(sumTranslation);
        int balanceAfterTransfer1Card = currentBalance1Card - Integer.parseInt(sumTranslation);
        transfer.chekBalance(DashboardPage.getIdTwo(), balanceAfterTransfer2Card);
        transfer.chekBalance(DashboardPage.getIdOne(), balanceAfterTransfer1Card);
    }

    @Test
    void shouldTransferOneForOne() {
        var loginPage = new LoginPage2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var currentBalance2Card = DashboardPage.getCardBalance(DashboardPage.getIdTwo());
        var currentBalance1Card = DashboardPage.getCardBalance(DashboardPage.getIdOne());
        var sumTranslation = DashboardPage.getAmount(DashboardPage.getIdOne());
        var choosingACard = dashboardPage.choosingCard(DashboardPage.getIdOne());
        var transfer = choosingACard.transfer(sumTranslation, 0);
        int balanceAfterTransfer2Card = currentBalance2Card;
        int balanceAfterTransfer1Card = currentBalance1Card;
        transfer.chekBalance(DashboardPage.getIdTwo(), balanceAfterTransfer2Card);
        transfer.chekBalance(DashboardPage.getIdOne(), balanceAfterTransfer1Card);
    }

    @Test
    void shouldTransferTwoForTwo() {
        var loginPage = new LoginPage2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var currentBalance2Card = DashboardPage.getCardBalance(DashboardPage.getIdTwo());
        var currentBalance1Card = DashboardPage.getCardBalance(DashboardPage.getIdOne());
        var sumTranslation = DashboardPage.getAmount(DashboardPage.getIdTwo());
        var choosingACard = dashboardPage.choosingCard(DashboardPage.getIdTwo());
        var transfer = choosingACard.transfer(sumTranslation, 1);
        int balanceAfterTransfer2Card = currentBalance2Card;
        int balanceAfterTransfer1Card = currentBalance1Card;
        transfer.chekBalance(DashboardPage.getIdTwo(), balanceAfterTransfer2Card);
        transfer.chekBalance(DashboardPage.getIdOne(), balanceAfterTransfer1Card);
    }

    @Test
    void shouldTransferNegativeTwoForOne() {
        var loginPage = new LoginPage2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var currentBalance2Card = DashboardPage.getCardBalance(DashboardPage.getIdTwo());
        var currentBalance1Card = DashboardPage.getCardBalance(DashboardPage.getIdOne());
        var sumTranslation = "-" + DashboardPage.getAmount(DashboardPage.getIdTwo());
        var choosingACard = dashboardPage.choosingCard(DashboardPage.getIdOne());
        var transfer = choosingACard.transfer(sumTranslation, 1);
        int balanceAfterTransfer2Card = currentBalance2Card - Integer.parseInt(sumTranslation.substring(1));
        int balanceAfterTransfer1Card = currentBalance1Card + Integer.parseInt(sumTranslation.substring(1));
        transfer.chekBalance(DashboardPage.getIdTwo(), balanceAfterTransfer2Card);
        transfer.chekBalance(DashboardPage.getIdOne(), balanceAfterTransfer1Card);
    }

    @Test
    void shouldTransferOneForTwoOverlimit() {
        var loginPage = open("http://localhost:9999", LoginPage3.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var currentBalance2Card = DashboardPage.getCardBalance(DashboardPage.getIdTwo());
        var currentBalance1Card = DashboardPage.getCardBalance(DashboardPage.getIdOne());
        var sumTranslation = DashboardPage.getAmount(DashboardPage.getIdOne()) + "00";
        var choosingACard = dashboardPage.choosingCard(DashboardPage.getIdTwo());
        var transfer = choosingACard.transfer(sumTranslation, 0);
        int balanceAfterTransfer2Card =
                currentBalance2Card + Integer.parseInt(sumTranslation.substring(0, (sumTranslation.length() - 2)));
        int balanceAfterTransfer1Card =
                currentBalance1Card - Integer.parseInt(sumTranslation.substring(0, (sumTranslation.length() - 2)));
        transfer.chekBalance(DashboardPage.getIdTwo(), balanceAfterTransfer2Card);
        transfer.chekBalance(DashboardPage.getIdOne(), balanceAfterTransfer1Card);
    }
}

