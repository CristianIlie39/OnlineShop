package persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@NamedQueries({

        @NamedQuery(name = "getUserByNameAndEmail", query = "SELECT user FROM User user WHERE user.name = :name AND user.emailAddress = :email"),
        @NamedQuery(name = "getUserByEmail", query = "SELECT user FROM User user WHERE user.emailAddress = :email"),
        @NamedQuery(name = "deleteUserByEmail", query = "DELETE FROM User user WHERE user.emailAddress = :email"),
        @NamedQuery(name = "updateLoginUser", query = "UPDATE User user SET user.active = :isConnected WHERE user.emailAddress = :email"),
        @NamedQuery(name = "getUserByEmailAndPassword", query = "SELECT user FROM User user WHERE user.emailAddress = :email AND user.password = :password"),
        @NamedQuery(name = "updateUserAddress", query = "UPDATE User user SET user.address = :newAddress WHERE user.emailAddress = :email"),
        @NamedQuery(name = "getAllUsers", query = "SELECT user FROM User user"),
        @NamedQuery(name = "changeActiveStatusForAllUsers", query = "UPDATE User user SET user.active = :status")
})

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "address")
    private String address;

    @Column(name = "user_role")
    private boolean userRole;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Purchase> listOfPurchases;

    public User(String name, String password, Date dateOfBirth, String phoneNumber, String emailAddress, String address,
                boolean userRole, boolean active) {
        this.name = name;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.userRole = userRole;
        this.active = active;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public boolean isUserRole() {
        return userRole;
    }

    public boolean isActive() {
        return active;
    }

    public List<Purchase> getListOfPurchases() {
        return listOfPurchases;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserRole(boolean userRole) {
        this.userRole = userRole;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setListOfPurchases(List<Purchase> listOfPurchases) {
        this.listOfPurchases = listOfPurchases;
    }

    public String toString() {
        return "User: " + name + " , " + dateOfBirth + " , " + phoneNumber + " , " + emailAddress + " , " + address +
                " , " + userRole + " , " + active;
    }

    public boolean equals(Object object) {
        if (object instanceof User) {
            if (this.id == ((User) object).getId() && this.name.equals(((User) object).name)
                    && this.password.equals(((User) object).getPassword())
            && this.dateOfBirth.equals(((User) object).dateOfBirth)
                    && this.phoneNumber.equals(((User) object).getPhoneNumber())
            && this.emailAddress.equals(((User) object).getEmailAddress())
            && this.address.equals(((User) object).getAddress())
            && this.userRole == (((User) object).isUserRole())
            && this.active == (((User) object).isActive())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, name, password, dateOfBirth, phoneNumber, emailAddress, address, userRole, active);
    }
}
