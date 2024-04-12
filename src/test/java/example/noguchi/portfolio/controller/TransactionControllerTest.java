package example.noguchi.portfolio.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import example.noguchi.portfolio.entity.User;
import example.noguchi.portfolio.entity.User.Role;
import example.noguchi.portfolio.service.CategoryService;
import example.noguchi.portfolio.service.TransactionService;
import example.noguchi.portfolio.service.UserDetail;
import example.noguchi.portfolio.service.UserService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetail userDetail;

    @MockBean
    private User user;

    @BeforeEach
    void beforeEach() {
        // Spring Securityを有効にする
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    private final WebApplicationContext webApplicationContext;
    
    TransactionControllerTest(WebApplicationContext context) {
        this.webApplicationContext = context;
    }

    @MockBean
    TransactionService transactionService;
    @MockBean
    CategoryService categoryService;
    @MockBean
    UserService userService;

    @Test
    @WithMockUser
    void testTransactionController() throws Exception {
        
        User user = new User();
        when(userDetail.getEmployee()).thenReturn(user);
        
        mockMvc.perform(get("/gamanbanking/home"))
        .andExpect(status().isOk());
    }

//    @Test
//    void testHome() {
//        fail("まだ実装されていません");
//    }
//
//    @Test
//    void testSetting() {
//        fail("まだ実装されていません");
//    }
//
//    @Test
//    void testDeposit() {
//        fail("まだ実装されていません");
//    }
//
//    @Test
//    void testWithdrawView() {
//        fail("まだ実装されていません");
//    }
//
//    @Test
//    void testWithdraw() {
//        fail("まだ実装されていません");
//    }
//
//    @Test
//    void testList() {
//        fail("まだ実装されていません");
//    }
//
//    @Test
//    void testDetail() {
//        fail("まだ実装されていません");
//    }
//
//    @Test
//    void testUpdateIntegerModelTransactionBindingResultUserDetailRedirectAttributes() {
//        fail("まだ実装されていません");
//    }
//
//    @Test
//    void testUpdateIntegerRedirectAttributes() {
//        fail("まだ実装されていません");
//    }
//
//    @Test
//    void testStatistics() {
//        fail("まだ実装されていません");
//    }

}
