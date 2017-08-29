package controllers;

import models.User;
import play.data.validation.Valid;
import play.mvc.Before;
import play.mvc.Controller;

import java.util.LinkedHashMap;
import java.util.Map;

public class Application extends Controller {

    @Before
    static void addUser() {
        User user = connected();
        if (user != null) {
            renderArgs.put("user", user);
        }
    }

    static User connected() {
        if (renderArgs.get("user") != null) {
            return renderArgs.get("user", User.class);
        }
        String login = session.get("login");
        String password = session.get("password");
        if (login != null) {
            return User.connect(login, password);
        }
        return null;
    }

    public static void index() {
        User user = connected();
        render(user);
    }

    public static void register() {
        Map<String, String> types = new LinkedHashMap<String, String>();
        types.put("administrator", "Администратор");
        types.put("client", "Клиент");
        types.put("trainer", "Тренер");
        render();
    }

    public static void login(String login, String password) {
        User user = User.find("byLoginAndPassword", login, password).first();
        if (user != null) {
            session.put("login", user.login);
            session.put("password", user.password);
            flash.success("Welcome, " + user.firstName);
            index();
        }
        session.put("login", login);
        flash.error("Неверный пароль");
        index();
    }

    public static void logout() {
        session.clear();
        index();
    }

    public static void saveUser(@Valid User user, String verifyPassword) {
        validation.required(verifyPassword);
        validation.equals(verifyPassword, user.password).message("Пароли не совпадают");
        if (validation.hasErrors()) {
            render("@register", user, verifyPassword);
        }
        user.create();
        session.put("login", user.login);
        session.put("password", user.password);
        flash.success("Welcome, " + user.firstName);
        index();
    }

    public static void changePassword() {
        render();
    }

    public static void updatePassword(String oldPassword, String newPassword, String verifyPassword) {
        validation.required(oldPassword);
        validation.required(newPassword);
        validation.required(verifyPassword);
        User user = connected();
        validation.equals(oldPassword, user.password).message("Неверный пароль");
        validation.equals(verifyPassword, newPassword).message("Пароли не совпадают");
        if (validation.hasErrors()) {
            render("@changePassword", user, verifyPassword, oldPassword, newPassword);
        }
        user.password = newPassword;
        session.put("password", user.password);
        user.save();
        index();
    }
}