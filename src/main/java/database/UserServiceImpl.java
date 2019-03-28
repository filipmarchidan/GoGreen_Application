package database;

import database.entities.User;

public interface UserServiceImpl {
    User createUser(User user);
    User getUserById(Integer userId);
    void deleteUser(Integer userId);
    User updateUser(String newEmail, String newPassword, Integer userId);
    User getUserByEmail(String email);
}
