package API.security;

import database.entities.User;
import database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailServices implements UserDetailsService {
    
    
    @Autowired
    UserRepository userRepository;
    
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        User user = userRepository.findByEmail(email);
        
        if(email == null) {
            throw new UsernameNotFoundException("invalid credentials");
        }
        
        return new UserPrincipal(user);
    }
}
