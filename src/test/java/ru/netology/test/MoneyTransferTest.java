package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.date.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static ru.netology.date.DataHelper.ReplenishmentInfo.getInvalidReplenishmentInfo;
import static ru.netology.date.DataHelper.ReplenishmentInfo.getReplenishmentInfo;

public class MoneyTransferTest {

    private LoginPage loginPage = new LoginPage();
    private DataHelper.AuthInfo authInfo = DataHelper.getUserAuthInfo();
    private VerificationPage verificationPage = loginPage.validLogin(authInfo);
    private DataHelper.VerificationCode verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    private DashboardPage dashboardPage = verificationPage.validVerify(authInfo, verificationCode);
    private long totalAmount = dashboardPage.getFirstCardSum() + dashboardPage.getSecondCardSum();

    @Test
    void cancelTransferMoney() {
        val moneyTransferPage = dashboardPage.refillOneFromTwo();
        val moneyTransferInfo = getReplenishmentInfo(dashboardPage.getSecondCardNumber(),
                dashboardPage.getSecondCardSum() / 10);
        moneyTransferPage.cancelAction(moneyTransferInfo);
        dashboardPage.updateAmounts();

        Assertions.assertEquals(moneyTransferPage.getStartRecipientAmount(), dashboardPage.getFirstCardSum());
        Assertions.assertEquals(moneyTransferPage.getStartSenderAmount(), dashboardPage.getSecondCardSum());
        Assertions.assertEquals(totalAmount, dashboardPage.getFirstCardSum() + dashboardPage.getSecondCardSum());
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        val moneyTransferPage = dashboardPage.refillOneFromTwo();
        val moneyTransferInfo = getReplenishmentInfo(dashboardPage.getSecondCardNumber(),
                dashboardPage.getSecondCardSum() / 10);
        moneyTransferPage.refillAction(moneyTransferInfo);
        dashboardPage.updateAmounts();

        Assertions.assertEquals(moneyTransferPage.getRecipientAmount(), dashboardPage.getFirstCardSum());
        Assertions.assertEquals(moneyTransferPage.getSenderAmount(), dashboardPage.getSecondCardSum());
        Assertions.assertEquals(totalAmount, dashboardPage.getFirstCardSum() + dashboardPage.getSecondCardSum());
    }

    @Test
    void shouldTransferFromSecondToFirst() {
        val moneyTransferPage = dashboardPage.refillTwoFromOne();
        val moneyTransferInfo = getReplenishmentInfo(dashboardPage.getFirstCardNumber(),
                dashboardPage.getFirstCardSum() / 10);
        moneyTransferPage.refillAction(moneyTransferInfo);
        dashboardPage.updateAmounts();

        Assertions.assertEquals(moneyTransferPage.getRecipientAmount(), dashboardPage.getSecondCardSum());
        Assertions.assertEquals(moneyTransferPage.getSenderAmount(), dashboardPage.getFirstCardSum());
        Assertions.assertEquals(totalAmount, dashboardPage.getFirstCardSum() + dashboardPage.getSecondCardSum());
    }

    @Test
    void shouldTransferFromInvalidNumber() {
        val moneyTransferPage = dashboardPage.refillOneFromTwo();
        val moneyTransferInfo = getInvalidReplenishmentInfo(dashboardPage.getSecondCardSum() / 10);
        moneyTransferPage.refillActionError(moneyTransferInfo);
        dashboardPage.isVisible();
    }

    @Test
    void shouldTransferFromFirstToSecondNegativeAmount() {
        val moneyTransferPage = dashboardPage.refillOneFromTwo();
        val moneyTransferInfo = getReplenishmentInfo(dashboardPage.getSecondCardNumber(),
                dashboardPage.getSecondCardSum() + 10);
        moneyTransferPage.refillAction(moneyTransferInfo);
        dashboardPage.updateAmounts();

        boolean testAmounts = dashboardPage.getSecondCardSum() >= 0
                && dashboardPage.getSecondCardSum() <= totalAmount
                && dashboardPage.getFirstCardSum() >= 0
                && dashboardPage.getFirstCardSum() <= totalAmount;

        Assertions.assertTrue(testAmounts);
    }
}

