package API.security;

import database.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserPrincipalTest {
    
    @Test
    void getAuthorities() {
    }
    
    @Test
    void getPassword() {
    }
    
    @Test
    void getUsername() {
    }
    
    @Test
    void isAccountNonExpired() {
    }
    
    @Test
    void isAccountNonLocked() {
    }
    
    @Test
    void isCredentialsNonExpired() {
    }
    
    @Test
    void isEnabled() {
    
        User user = new User();
        
        assertTrue(new UserPrincipal(user).isEnabled());
        
    }
    
}