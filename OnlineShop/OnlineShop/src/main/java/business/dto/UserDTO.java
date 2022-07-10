package business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.*;
import java.sql.Date;

public class UserDTO {

    @NotNull(message = "The name field cannot be null!")
    @NotEmpty(message = "The name field cannot be empty!")
    @NotBlank(message = "The name field cannot be blank!")
    @Pattern(regexp = "([a-z A-Z])*", message = "Please enter letters!")
    private String name;

    @NotNull(message = "The password field cannot be null!")
    @NotEmpty(message = "The password field cannot be empty!")
    @NotBlank(message = "The password field cannot be blank!")
    private String password;

    @NotNull(message = "The date of birth field cannot be null!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @NotNull(message = "The phone number field cannot be null!")
    @NotEmpty(message = "The phone number field cannot be empty!")
    @NotBlank(message = "The phone number field cannot be blank!")
    @Pattern(regexp = "([0-9])*", message = "Please enter letters!")
    private String phoneNumber;

    @NotNull(message = "The email address field cannot be null!")
    @NotEmpty(message = "The email address field cannot be empty!")
    @NotBlank(message = "The email address field cannot be blank!")
    @Email
    private String emailAddress;

    @NotNull(message = "The address field cannot be null!")
    @NotEmpty(message = "The address field cannot be empty!")
    @NotBlank(message = "The address field cannot be blank!")
    private String address;

    @NotNull(message = "The user role field cannot be null!")
    private boolean userRole;

    @NotNull(message = "The active field cannot be null!")
    private boolean active;

    public UserDTO(String name, String password, Date dateOfBirth, String phoneNumber, String emailAddress,
                   String address, boolean userRole, boolean active) {
        this.name = name;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.userRole = userRole;
        this.active = active;
    }

    public UserDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isUserRole() {
        return userRole;
    }

    public void setUserRole(boolean userRole) {
        this.userRole = userRole;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String toString() {
        return "name: " + name + "\n" + "date of Birth: " + dateOfBirth + "\n" + "phone number: " + phoneNumber +
                "\n" + "email address: " + emailAddress + "\n" + "address: " + address + "\n" + "user role: " +
                userRole + "\n" + "active: " + active;
    }
}
