package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.protobuf.ByteString;
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


    private String hashedPassword;


    private String salt;

    public UserAccount() {
        // Needed for Jackson deserialisation
    }

    public UserAccount(int userID, String userName, String email, String password, String hashedPassword, String salt) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public UserAccount(int userID, String userName, String email) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
    }

    public UserAccount(int userID, String email) {
        this.userID = userID;
        this.email = email;
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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
