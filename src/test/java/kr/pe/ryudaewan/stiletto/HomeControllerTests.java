package kr.pe.ryudaewan.stiletto;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class) // 테스트할 controller 클래스를 제한
@AutoConfigureMockMvc
public class HomeControllerTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHome() throws Exception {

        this.mockMvc.perform(get("/").characterEncoding("UTF-8").header("Accept-Language", "ko"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
