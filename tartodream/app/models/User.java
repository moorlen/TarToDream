package models;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class User extends Model {
    static final String uniqueMessage = "validation.user.login.unique";
    static final String emailMessage = "validation.user.email";

    @Email(message = emailMessage)
    public String email;

    @Required
    @Password
    public String password;

    @Required
    public String firstName;

    @Required
    public String lastName;

    public String thirdName;

    @Required
    @Unique(message = uniqueMessage)
    public String login;

    public Date birthDate;

    public static User connect(String login, String password) {
        return find("byLoginAndPassword", login, password).first();
    }
}