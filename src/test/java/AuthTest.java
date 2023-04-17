import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @BeforeEach
    void setUp() {
        open("http://localhost9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfullyLoginIfRegisteredActiveUser() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login' ] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password' ] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe((Condition.visible));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGerErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id='login' ] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password' ] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification' ] .notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe((Condition.visible));
    }
    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGerErrorIfBlockedUser() {
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login' ] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password' ] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification' ] .notification__content").shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован")).shouldBe((Condition.visible));
    }
    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGerErrorIfWrongLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongLogin = DataGenerator.getRandomLogin();
        $("[data-test-id='login' ] input").setValue(wrongLogin);
        $("[data-test-id='password' ] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification' ] .notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe((Condition.visible));
    }
    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGerErrorIfWrongPassword() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongPassword = DataGenerator.getRandomPassword();
        $("[data-test-id='login' ] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password' ] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id='error-notification' ] .notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe((Condition.visible));
    }
}
