package com.bookstore.controller;

import com.bookstore.service.*;
import com.bookstore.utility.MailConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CheckoutController.class})
@ExtendWith(MockitoExtension.class)
class CheckoutControllerTest {

    @InjectMocks
    private CheckoutController checkoutController;

    @Mock
    private BillingAddressService billingAddressService;
    @Mock
    private CartItemService cartItemService;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MailConstructor mailConstructor;
    @Mock
    private OrderService orderService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private ShippingAddressService shippingAddressService;
    @Mock
    private ShoppingCartService shoppingCartService;
    @Mock
    private UserPaymentService userPaymentService;
    @Mock
    private UserService userService;
    @Mock
    private UserShippingService userShippingService;

    @Test
    void testCheckout() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/checkout");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("cartId", String.valueOf(1L));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.checkoutController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testSetPaymentMethod() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/setPaymentMethod")
                .param("userPaymentId", "https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.checkoutController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testSetShippingAddress() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/setShippingAddress")
                .param("userShippingId", "https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.checkoutController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

