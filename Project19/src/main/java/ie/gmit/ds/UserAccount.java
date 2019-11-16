package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UserAccount {

    @NotNull
    private int userID;

    @NotBlank @Length(min=2, max=255)
    private String userName;

    @Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;

    @NotNull
    private String password;

    public UserAccount() {
        // Needed for Jackson deserialisation
    }

    public UserAccount(int userID, String userName, String email, String password) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
       // this.hashedPassword = hashedPassword;
       // this.salt = salt;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
