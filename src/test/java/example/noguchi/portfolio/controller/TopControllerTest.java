package example.noguchi.portfolio.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class TopControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("ログイン画面へのアクセス結果")
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"));
    }

    @Test
    @WithMockUser
    @DisplayName("登録済みのユーザーがログインしようとした場合")
    public void testTop01() throws Exception {
        mockMvc.perform(get("/"))
        .andExpect(redirectedUrl("/gamanbanking/home"));
    }

    @Test
    @DisplayName("未登録のユーザーがログインしようとした場合")
    public void testTop02() throws Exception {
        mockMvc.perform(get("/"))
        .andExpect(redirectedUrl("http://localhost/login"));
    }

}
