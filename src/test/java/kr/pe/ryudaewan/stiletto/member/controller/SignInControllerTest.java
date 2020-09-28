package kr.pe.ryudaewan.stiletto.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.equalTo;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RestClientTest
@SpringBootTest
public class SignInControllerTest {
    private final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testSignIn() throws Exception {
        String email = "ha.juhee@kof14.co.kr";
        String password = "1111";
        SignInRequest request = new SignInRequest(email, password);
        ObjectMapper om = new ObjectMapper();

        this.mockMvc.perform(post("/signIn")
            .contentType(this.APPLICATION_JSON_UTF8)
            .accept(this.APPLICATION_JSON_UTF8)
            .content(om.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(this.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.memberId", notNullValue()))
            .andExpect(jsonPath("$.screenName", notNullValue()))
            .andExpect(jsonPath("$.authorities[*].authority", hasItem("ROLE_MEMBER")))
            .andExpect(jsonPath("$.email", equalTo(email)))
        ;
    }

    @Test
    public void testSignInFail() throws Exception {
        String email = "xxx@test.org";
        String password = "3984fkdajrker;";
        SignInRequest signInRequest = new SignInRequest(email, password);
        ObjectMapper om = new ObjectMapper();

        this.mockMvc.perform(post("/signIn")
                .contentType(this.APPLICATION_JSON_UTF8)
                .accept(this.APPLICATION_JSON_UTF8)
                .content(om.writeValueAsString(signInRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                //.andExpect(content().contentType(this.APPLICATION_JSON_UTF8))
        //.andExpect(jsonPath("$.username", is(request.getUsername().toUpperCase())))
        //.andExpect(jsonPath("$.authorities[*].authority", hasItem("USER")))
        ;
    }
}

