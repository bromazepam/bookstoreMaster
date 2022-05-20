package com.bookstore.controller;

import com.bookstore.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = HomeController.class)
@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    @InjectMocks
    HomeController homeController;

    @Mock
    Model model;
    @Mock
    UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @AfterEach
    void tearDown() {
        reset(userService);
    }

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

    }

    @Test
    public void testHours() {
        String view = homeController.hours();
        assertThat("hours").isEqualTo(view);
    }

    @Test
    public void testFAQ() {
        String view = homeController.faq();
        assertThat("faq").isEqualTo(view);
    }

    @Test
    public void testLogin() throws Exception {
        model.addAttribute("classActiveLogin");
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("classActiveLogin"))
                .andExpect(view().name("myAccount"));
    }

}