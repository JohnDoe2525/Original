package example.noguchi.portfolio.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
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
    @DisplayName("登録済みのユーザーがログインしようとした場合(認証)")
    public void testTop04() throws Exception {
        mockMvc.perform(formLogin().user("admin").password("kirataro"))
                .andExpect(authenticated());
    }

    @Test
    @DisplayName("未登録のユーザーがログインしようとした場合(認証)")
    public void testTop03() throws Exception {
        mockMvc.perform(formLogin().user("admin").password("admin"))
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    @DisplayName("登録済みのユーザーがログインしようとした場合(認可)")
    public void testTop01() throws Exception {
        mockMvc.perform(get("/"))
        .andExpect(redirectedUrl("/gamanbanking/home"));
    }

    @Test
    @DisplayName("未登録のユーザーがログインしようとした場合(認可)")
    public void testTop02() throws Exception {
        mockMvc.perform(get("/"))
        .andExpect(redirectedUrl("http://localhost/login"));
    }

}
