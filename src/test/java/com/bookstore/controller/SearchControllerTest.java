package com.bookstore.controller;

import com.bookstore.service.BookService;
import com.bookstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {SearchController.class})
@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @InjectMocks
    private SearchController searchController;

    @Mock
    private BookService bookService;
    @Mock
    private UserService userService;

    @Test
    void testSearchBook() throws Exception {
        when(this.bookService.blurrySearch(any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/searchBook");
        MockMvcBuilders.standaloneSetup(this.searchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("emptyList", "keyword"))
                .andExpect(MockMvcResultMatchers.view().name("bookshelf"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("bookshelf"));
    }

    @Test
    void testSearchByCategory() throws Exception {
        when(this.bookService.findByCategory(any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/searchByCategory")
                .param("category", "foo");
        MockMvcBuilders.standaloneSetup(this.searchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("activefoo", "emptyList"))
                .andExpect(MockMvcResultMatchers.view().name("bookshelf"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("bookshelf"));
    }
}

