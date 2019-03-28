package API.security;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AuthenticationTest {
    
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext appContext;
    
    //create a MockMvc instance
    @Before
    public void setup(){
        
         mvc = MockMvcBuilders
             .webAppContextSetup(appContext)
             .apply(springSecurity())
             .build();
    }
    
    
    @Test
    public void notAuthenticatedTest() throws Exception {
        mvc.perform(formLogin().user("invalid").password("invalid"))
            .andExpect(unauthenticated());
    
    }
    
    @Test
    public void authenticatedTest() throws Exception {
        
        mvc.perform(formLogin().user("test").password("test"))
            .andExpect(authenticated());
        
    }
    
    @Test
    public void logoutTest() throws Exception {
        
        mvc.perform(formLogin().password("test").user("test"));
        
        mvc.perform(logout())
            .andExpect(unauthenticated());
        
    }
}
