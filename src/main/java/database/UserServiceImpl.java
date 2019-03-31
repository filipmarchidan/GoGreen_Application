package database;

import database.entities.User;

public interface UserServiceImpl {
    
    User createUser(User user);
    
    User getUserById(Integer userId);
    
    void deleteUser(Integer userId);
    
    User updateUser(User user, String newEmail, String newPassword);
    
    User getUserByEmail(String email);
}
