package ru.netology.rest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebUiTest {

    long PLUS_DAYS = 3;
    String APP_ADDRESS = "http://localhost:9999";

    private String getMinDate(long plusDays) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void shouldSuccessfullySendRequest() {
        Selenide.open(APP_ADDRESS);
        $("[data-test-id='city'] input").setValue("Смоленск");
        String inputDate = getMinDate(PLUS_DAYS);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Лягушеслав Болотин");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + inputDate));
    }

    @Test
    public void shouldFailIfCityNotValid() {
        Selenide.open(APP_ADDRESS);
        $("[data-test-id='city'] input").setValue("СтранныйГород");
        String inputDate = getMinDate(PLUS_DAYS);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Лягушеслав Болотин");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='city']").$(By.className("input__sub"))
                .shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldFailIfCityIsEmpty() {
        Selenide.open(APP_ADDRESS);
        String inputDate = getMinDate(PLUS_DAYS);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Лягушеслав Болотин");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='city']").$(By.className("input__sub"))
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldFailIfNameContainIllegalSymbols() {
        Selenide.open(APP_ADDRESS);
        $("[data-test-id='city'] input").setValue("Смоленск");
        String inputDate = getMinDate(PLUS_DAYS);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Froggy Swampin");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='name']").$(By.className("input__sub"))
                .shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldFailIfNameIsEmpty() {
        Selenide.open(APP_ADDRESS);
        $("[data-test-id='city'] input").setValue("Смоленск");
        String inputDate = getMinDate(PLUS_DAYS);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(inputDate);
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='name']").$(By.className("input__sub"))
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldFailIfPhoneTooShort() {
        Selenide.open(APP_ADDRESS);
        $("[data-test-id='city'] input").setValue("Смоленск");
        String inputDate = getMinDate(PLUS_DAYS);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Лягушеслав Болотин");
        $("[data-test-id='phone'] input").setValue("+79");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone']").$(By.className("input__sub"))
                .shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldFailIfPhoneIsEmpty() {
        Selenide.open(APP_ADDRESS);
        $("[data-test-id='city'] input").setValue("Смоленск");
        String inputDate = getMinDate(PLUS_DAYS);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Лягушеслав Болотин");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone']").$(By.className("input__sub"))
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldFailIfDateIsTooEarly() {
        Selenide.open(APP_ADDRESS);
        $("[data-test-id='city'] input").setValue("Смоленск");
        String inputDate = getMinDate(- 1);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Лягушеслав Болотин");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='date']").$(By.className("input__sub"))
                .shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldFailIfCheckboxIsUnchecked() {
        Selenide.open(APP_ADDRESS);
        $("[data-test-id='city'] input").setValue("Смоленск");
        String inputDate = getMinDate(PLUS_DAYS);
        $("[data-test-id='date'] input").setValue(inputDate);
        $("[data-test-id='name'] input").setValue("Лягушеслав Болотин");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("button.button").click();
        assertTrue($("[data-test-id='agreement'].input_invalid").isDisplayed());
    }


}
