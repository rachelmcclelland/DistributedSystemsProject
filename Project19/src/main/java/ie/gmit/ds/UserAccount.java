package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.protobuf.ByteString;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

@XmlRootElement
public class UserAccount {

    @NotNull
    private int userID;

    @NotBlank @Length(min=2, max=255)
    private String userName;

    @Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;

    @NotNull
    private String password;


    private ByteString hashedPassword;


    private ByteString salt;

    public UserAccount() {
        // Needed for Jackson deserialisation
    }

    public UserAccount(int userID, String userName, String email, String password, ByteString hashedPassword, ByteString salt) {
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

    @XmlElement
    @JsonProperty
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @XmlElement
    @JsonProperty
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @XmlElement
    @JsonProperty
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ByteString getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(ByteString hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public ByteString getSalt() {
        return salt;
    }

    public void setSalt(ByteString salt) {
        this.salt = salt;
    }
}
