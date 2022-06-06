package com.bookstore.controller;

import com.bookstore.domain.*;
import com.bookstore.repository.*;
import com.bookstore.service.BookService;
import com.bookstore.service.CartItemService;
import com.bookstore.service.ShoppingCartService;
import com.bookstore.service.UserService;
import com.bookstore.service.impl.BookServiceImpl;
import com.bookstore.service.impl.CartItemServiceImpl;
import com.bookstore.service.impl.ShoppingCartServiceImpl;
import com.bookstore.service.impl.UserServiceImpl;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ContextConfiguration(classes = {ShoppingCartController.class})
@ExtendWith(MockitoExtension.class)
class ShoppingCartControllerTest {

    @InjectMocks
    ShoppingCartController shoppingCartController;

    @Mock
    UserService userService;
    @Mock
    BookService bookService;
    @Mock
    CartItemService cartItemService;
    @Mock
    ShoppingCartService shoppingCartService;
    @Mock
    ShoppingCartRepository shoppingCartRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();
    }

    @AfterEach
    void tearDown() {
        reset(userService, bookService, cartItemService, shoppingCartService, shoppingCartRepository);
    }

    @Test
    void testShoppingCart() throws Exception {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setOrderList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        user.setShoppingCart(new ShoppingCart());
        user.setUserPaymentList(new ArrayList<>());
        user.setUserRoles(new HashSet<>());
        user.setUserShippingList(new ArrayList<>());
        user.setUsername("janedoe");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItemList(new ArrayList<>());
        shoppingCart.setGrandTotal(BigDecimal.valueOf(1L));
        shoppingCart.setId(123L);
        shoppingCart.setUser(user);

        when(this.userService.findByUsername(org.mockito.Mockito.any())).thenReturn(user);
        when(this.shoppingCartService.updateShoppingCart(org.mockito.Mockito.any())).thenReturn(shoppingCart);
        when(this.cartItemService.findByShoppingCart(org.mockito.Mockito.any())).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/shoppingCart/cart");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(this.shoppingCartController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("cartItemList", "shoppingCart"))
                .andExpect(MockMvcResultMatchers.view().name("shoppingCart"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("shoppingCart"));
    }

    @Test
    void removeItem() throws Exception {
        doNothing().when(cartItemService).removeCartItem(any());

        this.mockMvc.perform(get("/shoppingCart/removeItem")
                        .param("id", String.valueOf(anyLong())))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/shoppingCart/cart"));
    }

    @Test
    void testAddItem() throws IOException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setOrderList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        user.setShoppingCart(new ShoppingCart());
        user.setUserPaymentList(new ArrayList<>());
        user.setUserRoles(new HashSet<>());
        user.setUserShippingList(new ArrayList<>());
        user.setUsername("janedoe");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItemList(new ArrayList<>());
        shoppingCart.setGrandTotal(BigDecimal.valueOf(1L));
        shoppingCart.setId(123L);
        shoppingCart.setUser(user);

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(org.mockito.Mockito.any())).thenReturn(user);
        UserServiceImpl userService = new UserServiceImpl(mock(UserPaymentRepository.class), userRepository,
                mock(RoleRepository.class), mock(PasswordResetTokenRepository.class), mock(UserShippingRepository.class));

        Book book = new Book();
        book.setActive(true);
        book.setAuthor("JaneDoe");
        book.setBookImage(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
        book.setBookToCartItemList(new ArrayList<>());
        book.setCategory("Category");
        book.setDescription("The characteristics of someone or something");
        book.setFormat("Format");
        book.setId(123L);
        book.setInStockNumber(10);
        book.setIsbn(1);
        book.setLanguage("en");
        book.setListPrice(10.0);
        book.setNumberOfPages(10);
        book.setOurPrice(10.0);
        book.setPublicationDate("2020-03-01");
        book.setPublisher("Publisher");
        book.setShippingWeight(10.0);
        book.setTitle("Dr");
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.findById(org.mockito.Mockito.any())).thenReturn(Optional.of(book));
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        CartItemServiceImpl cartItemService = new CartItemServiceImpl(mock(CartItemRepository.class),
                mock(BookToCartItemRepository.class));

        ShoppingCartController shoppingCartController = new ShoppingCartController(userService, cartItemService,
                bookService,
                new ShoppingCartServiceImpl(
                        new CartItemServiceImpl(mock(CartItemRepository.class), mock(BookToCartItemRepository.class)),
                        mock(ShoppingCartRepository.class)));

        ConcurrentModel model = new ConcurrentModel();
        assertEquals("forward:/bookDetail?id=123",
                shoppingCartController.addItem(book, "42", model, new UserPrincipal("principal")));
        then(userRepository).should().findByUsername(org.mockito.Mockito.any());
        then(bookRepository).should().findById(org.mockito.Mockito.any());
    }

    @Test
    void testUpdateShoppingCart() throws IOException {
        Book book = new Book();
        book.setActive(true);
        book.setAuthor("JaneDoe");
        book.setBookImage(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
        book.setBookToCartItemList(new ArrayList<>());
        book.setCategory("Category");
        book.setDescription("The characteristics of someone or something");
        book.setFormat("Format");
        book.setId(123L);
        book.setInStockNumber(10);
        book.setIsbn(1);
        book.setLanguage("en");
        book.setListPrice(10.0);
        book.setNumberOfPages(10);
        book.setOurPrice(10.0);
        book.setPublicationDate("2020-03-01");
        book.setPublisher("Publisher");
        book.setShippingWeight(10.0);
        book.setTitle("Dr");

        Order order = new Order();
        order.setBillingAddress(new BillingAddress());
        order.setCartItemList(new ArrayList<>());
        order.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        order.setOrderDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        order.setOrderStatus("Order Status");
        order.setOrderTotal(null);
        order.setPayment(new Payment());
        order.setShippingAddress(new ShippingAddress());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        order.setShippingDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        order.setShippingMethod("Shipping Method");
        order.setUser(new User());

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setBillingAddressCity("42 Main St");
        billingAddress.setBillingAddressCountry("42 Main St");
        billingAddress.setBillingAddressName("42 Main St");
        billingAddress.setBillingAddressState("42 Main St");
        billingAddress.setBillingAddressStreet1("42 Main St");
        billingAddress.setBillingAddressStreet2("42 Main St");
        billingAddress.setBillingAddressZipcode("42 Main St");
        billingAddress.setId(123L);
        billingAddress.setOrder(order);

        UserBilling userBilling = new UserBilling();
        userBilling.setId(123L);
        userBilling.setUserBillingCity("User Billing City");
        userBilling.setUserBillingCountry("GB");
        userBilling.setUserBillingName("User Billing Name");
        userBilling.setUserBillingState("User Billing State");
        userBilling.setUserBillingStreet1("User Billing Street1");
        userBilling.setUserBillingStreet2("User Billing Street2");
        userBilling.setUserBillingZipcode("User Billing Zipcode");
        userBilling.setUserPayment(new UserPayment());

        Payment payment = new Payment();
        payment.setCardName("Card Name");
        payment.setCardNumber("42");
        payment.setCvc(1);
        payment.setExpiryMonth(1);
        payment.setExpiryYear(1);
        payment.setHolderName("Holder Name");
        payment.setId(123L);
        payment.setOrder(order);
        payment.setType("Type");
        payment.setUserBilling(userBilling);

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(123L);
        shippingAddress.setOrder(order);
        shippingAddress.setShippingAddressCity("42 Main St");
        shippingAddress.setShippingAddressCountry("42 Main St");
        shippingAddress.setShippingAddressName("42 Main St");
        shippingAddress.setShippingAddressState("42 Main St");
        shippingAddress.setShippingAddressStreet1("42 Main St");
        shippingAddress.setShippingAddressStreet2("42 Main St");
        shippingAddress.setShippingAddressZipcode("42 Main St");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItemList(new ArrayList<>());
        shoppingCart.setGrandTotal(null);
        shoppingCart.setId(123L);
        shoppingCart.setUser(new User());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setOrderList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        user.setShoppingCart(shoppingCart);
        user.setUserPaymentList(new ArrayList<>());
        user.setUserRoles(new HashSet<>());
        user.setUserShippingList(new ArrayList<>());
        user.setUsername("janedoe");

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setBookToCartItemList(new ArrayList<>());
        cartItem.setId(123L);
        cartItem.setOrder(order);
        cartItem.setQty(1);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setSubtotal(BigDecimal.valueOf(1L));
        Optional<CartItem> ofResult = Optional.of(cartItem);

        UserPayment userPayment = new UserPayment();
        userPayment.setCardName("Card Name");
        userPayment.setCardNumber("42");
        userPayment.setCvc(1);
        userPayment.setDefaultPayment(true);
        userPayment.setExpiryMonth(1);
        userPayment.setExpiryYear(1);
        userPayment.setHolderName("Holder Name");
        userPayment.setId(123L);
        userPayment.setType("Type");
        userPayment.setUser(new User());
        userPayment.setUserBilling(new UserBilling());

        CartItemRepository cartItemRepository = mock(CartItemRepository.class);
        when(cartItemRepository.save(org.mockito.Mockito.any())).thenReturn(cartItem);
        when(cartItemRepository.findById(org.mockito.Mockito.any())).thenReturn(ofResult);
        CartItemServiceImpl cartItemService = new CartItemServiceImpl(cartItemRepository,
                mock(BookToCartItemRepository.class));

        UserServiceImpl userService = new UserServiceImpl(mock(UserPaymentRepository.class), mock(UserRepository.class),
                mock(RoleRepository.class), mock(PasswordResetTokenRepository.class), mock(UserShippingRepository.class));

        BookServiceImpl bookService = new BookServiceImpl(mock(BookRepository.class));
        assertEquals("forward:/shoppingCart/cart",
                (new ShoppingCartController(userService, cartItemService, bookService,
                        new ShoppingCartServiceImpl(
                                new CartItemServiceImpl(mock(CartItemRepository.class), mock(BookToCartItemRepository.class)),
                                mock(ShoppingCartRepository.class)))).updateShoppingCart(123L, 1));
        then(cartItemRepository).should().findById(org.mockito.Mockito.any());
        then(cartItemRepository).should().save(org.mockito.Mockito.any());
    }

}