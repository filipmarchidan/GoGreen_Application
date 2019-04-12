package API.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.UserRepository;
import database.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AuthenticationTest {
    
    private MockMvc mvc;
    
    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    UserRepository userRepository;
    
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
    
    
//    @Before
//    public void


    @Test
    public void notAuthenticatedTest() throws Exception {
        ResultActions resultActions = mvc.perform(formLogin().user("invalid").password("invalid"))
            .andExpect(unauthenticated());

    }

    @Test
    public void authenticatedTest() throws Exception {

        mvc.perform(formLogin().user("user1@user1.com").password("test"))
                .andExpect(authenticated());

    }

    @Test
    public void logoutTest() throws Exception {

        mvc.perform(formLogin().user("user1@user1.com").password("test"))
                .andExpect(authenticated());

        mvc.perform(logout())
            .andExpect(unauthenticated());

    }
}
