package API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
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
    public User updateUser(String newEmail, String newPassword, Integer userId)
    {
        User userFromDB = userRepository.findById(userId).get();
        userFromDB.setEmail(newEmail);
        userFromDB.setPassword(newPassword);
        User updatedUser = userRepository.save(userFromDB);
        return updatedUser;
    }
    public List<User> getUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }
}
