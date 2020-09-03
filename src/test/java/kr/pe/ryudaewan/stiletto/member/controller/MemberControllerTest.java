package kr.pe.ryudaewan.stiletto.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.pe.ryudaewan.stiletto.member.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest // 모든 bean을 테스트에 등록한다.
// @WebMvcTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    private final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    private final Member member = new Member("오또맘", "ohttomom@tekken7.com", "eu59tgdk!f&");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindMember() throws Exception {

        this.mockMvc.perform(get("/member/find/2")
                .contentType(MediaType.APPLICATION_JSON)
                //.accept(MediaTypes.HAL_JSON)
                .accept(this.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id").value(2l))
        ;
    }

    @Test
    public void testGetAllMembers() throws Exception {
        int pageSize = 5;
        String reqUrl = "/member/list?page=0&size=" + pageSize + "&sort=id,ASC";

        ResultActions resp = this.mockMvc.perform(get(reqUrl)
                .contentType(MediaType.APPLICATION_JSON)
                //.accept(MediaTypes.HAL_JSON)
                .accept(this.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.APPLICATION_JSON_UTF8));

        String json = resp.andReturn().getResponse().getContentAsString();
        Member[] members = this.objectMapper.readValue(json, Member[].class);

        assertEquals(pageSize, members.length);

        // ID를 기준으로 정렬되었음을 검증하기 위한 코드
        for (int inx = 0; inx < members.length; inx++) {
            assertEquals(inx + 1, members[inx].getId());
        }
    }

    @Test
    public void testSearchMembers() throws Exception {
        int pageSize = 5;
        String reqUrl = "/member/search/오?page=0&size=" + pageSize + "&sort=id,ASC";

        ResultActions resp = this.mockMvc.perform(get(reqUrl)
                .contentType(MediaType.APPLICATION_JSON)
                //.accept(MediaTypes.HAL_JSON)
                .accept(this.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.APPLICATION_JSON_UTF8));

        String json = resp.andReturn().getResponse().getContentAsString();
        Member[] members = this.objectMapper.readValue(json, Member[].class);
        Assertions.assertNotNull(members);
    }

    @Test
    public void testCreateMember() throws Exception {

        this.mockMvc.perform(post("/member/create")
                .contentType(MediaType.APPLICATION_JSON)
                //.accept(MediaTypes.HAL_JSON)
                .accept(this.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(this.member)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("id").exists())
                .andExpect(content().contentType(this.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("screenName").value(member.getScreenName()))
                .andExpect(jsonPath("email").value(member.getEmail()))
                .andExpect(jsonPath("password").value(member.getPassword()))
        ;
    }

    @Test
    public void testFindNoMembers() throws Exception {
        this.mockMvc.perform(get("/member/find/234834739843")
                .contentType(MediaType.APPLICATION_JSON)
                //.accept(MediaTypes.HAL_JSON)
                .accept(this.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }
}
