package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import ru.netology.date.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.String.valueOf;

public class DashboardPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");
    private static ElementsCollection cards = $$(".list__item div");
    private static final String balanceStart = "баланс: ";
    private static final String balanceFinish = " р.";
    private static ElementsCollection ids = $$("li div");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public static int getCardBalance(String id) {
        val text = cards.find(attribute("data-test-id", id)).text();
        return toExtractBalance(text);
    }

    private static int toExtractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public static String getIdOne() {
        String idOne = ids.first().getAttribute("data-test-id");
        return idOne;
    }

    public static String getIdTwo() {
        String idTwo = ids.last().getAttribute("data-test-id");
        return idTwo;
    }

    public DashboardPage choosingCard(String id) {
        $("[data-test-id='" + id + "'] [data-test-id='action-deposit']").click();
        return new DashboardPage();
    }

    public static String getAmount(String id) {
        Faker faker = new Faker();
        int amount = faker.number().numberBetween(0, getCardBalance(id));
        String result = valueOf(amount);
        return result;
    }

    public DashboardPage transfer(String amount, int index) {
        $("[data-test-id='amount'] input").setValue(amount);
        $("[data-test-id='from'] input").setValue(DataHelper.getNumberCard(index));
        $("[data-test-id='action-transfer']").click();
        return new DashboardPage();
    }

    public void chekBalance(String id, int balance) {
        Assertions.assertEquals(getCardBalance(id), balance);
    }
}