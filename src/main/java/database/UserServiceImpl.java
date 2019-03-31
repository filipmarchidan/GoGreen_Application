package database;

import database.entities.User;

import java.util.List;

public interface UserServiceImpl {
    
    User createUser(User user);
    
    User getUserById(Integer userId);
    
    void deleteUser(Integer userId);
    
    User updateUser(User user, String newEmail, String newPassword);

    List<User> getAllUsers();

    User getUserByEmail(String email);
}
