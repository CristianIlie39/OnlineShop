package frontend.controller;

import business.dto.UserDTO;
import business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/registerUser")
    public ResponseEntity insertUser(@RequestBody @Valid UserDTO userDTO) {
        String cryptedPassword = userService.cryptPassword(userDTO.getPassword());
        userDTO.setPassword(cryptedPassword);
        if (userService.getUserByNameAndEmail(userDTO.getName(), userDTO.getEmailAddress()) != null) {
            System.out.println("The user with name " + userDTO.getName() + " and email " + userDTO.getEmailAddress()
            + " already exists in the database.");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("The user with name " + userDTO.getName()
                    + " and email " + userDTO.getEmailAddress() + " already exists in the database.");
        }
        userService.insert(userDTO);
        System.out.println("The user " + userDTO.getName() + " was registered in the database.");
        return ResponseEntity.ok("The user " + userDTO.getName() + " was registered in the database.");
    }

    @PutMapping(path = "/loginUser")
    public ResponseEntity login(@RequestParam String email, @RequestParam String password) {
        UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO == null) {
            System.out.println("The user with email address " + email + " does not exists in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user with email address " + email +
                    " does not exists in the database.");
        }
        String cryptedPassword = userService.cryptPassword(password);
        if (userService.getUserByEmailAndPassword(email, cryptedPassword) == null) {
            System.out.println("Password entered incorrectly!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password entered incorrectly!");
        } else if (userDTO.isActive()) {
            System.out.println("The user with email address " + email + " is already logged in!");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("The user with email address" + email +
                    " is already logged in!");
        } else {
            userService.updateLoginUser(true, email);
            System.out.println("User logged in successfully!");
            return ResponseEntity.ok("User logged in successfully!");
        }
    }

    @PutMapping(path = "/logout")
    public ResponseEntity logout(@RequestParam String email) {
        UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO == null) {
            System.out.println("The user with email address " + email + " does not exists in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user with email address " + email +
                    " does not exists in the database.");
        }
        if (userDTO.isActive()) {
            userService.updateLoginUser(false, email);
            System.out.println("User successfully logged out!");
            return ResponseEntity.ok("User successfully logged out!");
        } else {
            System.out.println("The user with email address " + email + " is not logged in, so you are not authorised "
                    + "to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The user with email address " + email +
                    " is not logged in, so you are not authorised to do this operation!");
        }
    }

    @GetMapping(path = "/editUser")
    public ResponseEntity editUser(@RequestParam String email) {
        UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO == null) {
            System.out.println("The user with email address " + email + " does not exists in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user with email address " + email +
                    " does not exists in the database.");
        }
        if (userDTO.isActive()) {
            userDTO.toString();
            return ResponseEntity.ok(userDTO.toString());
        } else {
            System.out.println("The user must logged in!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The user must logged in!");
        }
    }

    // I am going to develop the endpoint "/uploadUserProfileImage"

    // I am going to develop the endpoint "/getUserProfileImageLocation"

    @PutMapping(path = "/updateUserAddress")
    public ResponseEntity updateUserAddress(@RequestParam String newAddress, @RequestParam String email) {
        UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO == null) {
            System.out.println("The user with email address " + email + " does not exists in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user with email address " + email +
                    " does not exists in the database.");
        }
        if (userDTO.isActive()) {
            userService.updateUserAddress(newAddress, email);
            System.out.println("The user with email address " + email + " has changed his address in: " + newAddress);
            return ResponseEntity.ok("The user with email address " + email + " has changed his address in: " + newAddress);
        } else {
            System.out.println("The user with email address " + email + " must be logged in!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The user with email address " + email +
                    " must be logged in!");
        }
    }

    // only the administrator have access to the endpoint "/deleteUser" when he is logged into the application
    @DeleteMapping(path = "/deleteUser")
    public ResponseEntity deleteUserByEmail(@RequestParam String emailUserForDelete, @RequestParam String adminEmail) {
        UserDTO admin = userService.getUserByEmail(adminEmail);
        if (admin == null) {
            System.out.println("The administrator with email address " + adminEmail + " does not exists in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The administrator with email address " + adminEmail
                    + " does not exists in the database.");
        }
        if (admin.isUserRole() && admin.isActive()) {
            int numberOfUsersDeleted = userService.deleteUserByEmail(emailUserForDelete);
            if (numberOfUsersDeleted == 0) {
                System.out.println("The user with email address " + emailUserForDelete + "does not exists in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email address " + emailUserForDelete
                        + " does not exists in the database.");
            } else {
                System.out.println("The user with email address " + emailUserForDelete + " has been deleted.");
                return ResponseEntity.ok("The user with email address " + emailUserForDelete + " has been deleted.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you don't have access!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    // only the administrator have access to the endpoint "/getUser" when he is logged into the application
    @GetMapping(path = "/getUser")
    public ResponseEntity getUser(@RequestParam String userEmail, @RequestParam String adminEmail) {
        UserDTO admin = userService.getUserByEmail(adminEmail);
        if (admin == null) {
            System.out.println("The administrator with email address " + adminEmail + " does not exists in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The administrator with email address " + adminEmail
                    + " does not exists in the database.");
        }
        if (admin.isUserRole() && admin.isActive()) {
            UserDTO user = userService.getUserByEmail(userEmail);
            if (user == null) {
                System.out.println("The user with email address " + userEmail + " does not exists in "
                        + "the database");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user with email address " + userEmail +
                        " does not exists in the database.");
            } else {
                System.out.println("The user are displayed.");
                return ResponseEntity.ok(user);
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you don't have access!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @GetMapping(path = "/getAllUsers")
    public ResponseEntity getAllUsers() {
        List<UserDTO> userDTOList = userService.getAllUsers();
        if (userDTOList.isEmpty()) {
            System.out.println("There are no users in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no users in the database.");
        }
        System.out.println("The list of users is displayed.");
        return ResponseEntity.ok(userDTOList);
    }

    // only the administrator have access to the endpoint "/getUserByNameAndEmail" when he is logged into the application
    @GetMapping(path = "/getUserByNameAndEmail")
    public ResponseEntity getUserByNameAndEmail(@RequestParam String name, @RequestParam String email, @RequestParam String adminEmail) {
        UserDTO admin = userService.getUserByEmail(adminEmail);
        if (admin == null) {
            System.out.println("The administrator with email address " + adminEmail + " does not exists in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The administrator with email address " + adminEmail
                    + " does not exists in the database.");
        }
        if (admin.isUserRole() && admin.isActive()) {
            UserDTO user = userService.getUserByNameAndEmail(name, email);
            if (user == null) {
                System.out.println("The user with name " + name + " and email address " + email + " does not exists in "
                        + "the database");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user with name " + name +
                        " and email address " + email + " does not exists in " + "the database.");
            } else {
                System.out.println("The user are displayed.");
                return ResponseEntity.ok("The user with name " + name + " and email address " + email + " exists in "
                        + "the database.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you don't have access!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @PutMapping(path = "/changeActiveStatusForAllUsers")
    public ResponseEntity changeActiveStatusForAllUsers(@RequestParam boolean status, @RequestParam String adminEmail) {
        UserDTO admin = userService.getUserByEmail(adminEmail);
        if (admin == null) {
            System.out.println("The administrator with email address " + adminEmail + " does not exists in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The administrator with email address " + adminEmail
                    + " does not exists in the database.");
        }
        if (admin.isUserRole() && admin.isActive()) {
            int numberOfUsersWhithStatusChanged = userService.changeActiveStatusForAllUsers(status);
            if (numberOfUsersWhithStatusChanged == 0) {
                System.out.println("There are no users in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no users in the database.");
            } else {
                System.out.println("All users have changed their active status.");
                return ResponseEntity.ok("All users have changed their status.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you don't have acces in the database.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, so you " +
                    "don't have acces in the database.");
        }
    }



}
