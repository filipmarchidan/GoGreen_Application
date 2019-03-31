package API;

import database.UserRepository;
import database.UserServiceImpl;
import database.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService implements UserServiceImpl {
    @Autowired
    private UserRepository userRepository;
    
    public User createUser(User user)
    {
        return userRepository.save(user);
    }
    
    public User getUserById(Integer userId)
    {
        return userRepository.findById(userId).get();
    }
    public Iterable<User> getAllUsers()
    {
        return userRepository.findAll();
    }
    public void deleteUser(Integer userId)
    {
        userRepository.deleteById(userId);
    }
    public User updateUser(User user, String newEmail, String newPassword)
    {
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        User updatedUser = userRepository.save(user);
        return updatedUser;
        
    }
    public User getUserByEmail(String email) { return userRepository.findByEmail(email); }

}
