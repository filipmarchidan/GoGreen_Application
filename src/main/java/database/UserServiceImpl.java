package database;

import database.entities.User;

import java.util.List;

public interface UserServiceImpl {
    User createUser(User user);
    User getUserById(Integer userId);
    void deleteUser(Integer userId);
    User updateUser(String newEmail, String newPassword, Integer userId);
    List<User> getUserByEmail(String email);
}
