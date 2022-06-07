package com.bookstore.controller;

import com.bookstore.domain.*;
import com.bookstore.repository.*;
import com.bookstore.service.BookService;
import com.bookstore.service.UserService;
import com.bookstore.service.impl.*;
import com.bookstore.utility.MailConstructor;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
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
    @Mock
    BookService bookService;

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

    @Test
    public void testBookshelf() {
        String view = homeController.bookshelf(model, mock(Principal.class));
        then(this.bookService).should().findAll();
        assertThat("bookshelf").isEqualTo(view);
    }

    @Test
    void testBookDetail() throws IOException {
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
        when(userRepository.findByUsername(any())).thenReturn(user);
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
        when(bookRepository.findById((Long) any())).thenReturn(Optional.of(book));
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        StandardReactiveWebEnvironment env = new StandardReactiveWebEnvironment();
        MailConstructor mailConstructor = new MailConstructor(env, new TemplateEngine());

        UserSecurityService userSecurityService = new UserSecurityService(mock(UserRepository.class));
        UserPaymentServiceImpl userPaymentService = new UserPaymentServiceImpl(mock(UserPaymentRepository.class));
        UserShippingServiceImpl userShippingService = new UserShippingServiceImpl(mock(UserShippingRepository.class));
        CartItemServiceImpl cartItemService = new CartItemServiceImpl(mock(CartItemRepository.class),
                mock(BookToCartItemRepository.class));

        HomeController homeController = new HomeController(mailSender, mailConstructor, userService, userSecurityService,
                bookService, userPaymentService, userShippingService, cartItemService,
                new OrderServiceImpl(
                        new CartItemServiceImpl(mock(CartItemRepository.class), mock(BookToCartItemRepository.class)),
                        mock(OrderRepository.class)));
        ConcurrentModel model = new ConcurrentModel();
        assertEquals("bookDetail", homeController.bookDetail(123L, model, new UserPrincipal("principal")));
        verify(userRepository).findByUsername(any());
        verify(bookRepository).findById(any());
    }

    @Test
    void testMyProfile() {
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
        when(userRepository.findByUsername(any())).thenReturn(user);
        UserServiceImpl userService = new UserServiceImpl(mock(UserPaymentRepository.class), userRepository,
                mock(RoleRepository.class), mock(PasswordResetTokenRepository.class), mock(UserShippingRepository.class));

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        StandardReactiveWebEnvironment env = new StandardReactiveWebEnvironment();
        MailConstructor mailConstructor = new MailConstructor(env, new TemplateEngine());

        UserSecurityService userSecurityService = new UserSecurityService(mock(UserRepository.class));
        BookServiceImpl bookService = new BookServiceImpl(mock(BookRepository.class));
        UserPaymentServiceImpl userPaymentService = new UserPaymentServiceImpl(mock(UserPaymentRepository.class));
        UserShippingServiceImpl userShippingService = new UserShippingServiceImpl(mock(UserShippingRepository.class));
        CartItemServiceImpl cartItemService = new CartItemServiceImpl(mock(CartItemRepository.class),
                mock(BookToCartItemRepository.class));

        HomeController homeController = new HomeController(mailSender, mailConstructor, userService, userSecurityService,
                bookService, userPaymentService, userShippingService, cartItemService,
                new OrderServiceImpl(
                        new CartItemServiceImpl(mock(CartItemRepository.class), mock(BookToCartItemRepository.class)),
                        mock(OrderRepository.class)));
        ConcurrentModel concurrentModel = new ConcurrentModel();
        assertEquals("myProfile", homeController.myProfile(concurrentModel, new UserPrincipal("principal")));
        verify(userRepository).findByUsername(any());
        Object getResult = concurrentModel.get("stateList");
        assertEquals(4, ((Collection<String>) getResult).size());
        assertEquals("BiH", ((List<String>) getResult).get(0));
        assertEquals("CG", ((List<String>) getResult).get(1));
        assertEquals("MK", ((List<String>) getResult).get(2));
        assertEquals("RS", ((List<String>) getResult).get(3));
    }

    @Test
    void testUpdateCreditCard() {
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
        when(userRepository.findByUsername(any())).thenReturn(user);
        UserServiceImpl userService = new UserServiceImpl(mock(UserPaymentRepository.class), userRepository,
                mock(RoleRepository.class), mock(PasswordResetTokenRepository.class), mock(UserShippingRepository.class));

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
        userPayment.setUser(user);
        userPayment.setUserBilling(userBilling);

        UserPaymentRepository userPaymentRepository = mock(UserPaymentRepository.class);
        when(userPaymentRepository.findById(any())).thenReturn(Optional.of(userPayment));
        UserPaymentServiceImpl userPaymentService = new UserPaymentServiceImpl(userPaymentRepository);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        StandardReactiveWebEnvironment env = new StandardReactiveWebEnvironment();
        MailConstructor mailConstructor = new MailConstructor(env, new TemplateEngine());

        UserSecurityService userSecurityService = new UserSecurityService(mock(UserRepository.class));
        BookServiceImpl bookService = new BookServiceImpl(mock(BookRepository.class));
        UserShippingServiceImpl userShippingService = new UserShippingServiceImpl(mock(UserShippingRepository.class));
        CartItemServiceImpl cartItemService = new CartItemServiceImpl(mock(CartItemRepository.class),
                mock(BookToCartItemRepository.class));

        HomeController homeController = new HomeController(mailSender, mailConstructor, userService, userSecurityService,
                bookService, userPaymentService, userShippingService, cartItemService,
                new OrderServiceImpl(
                        new CartItemServiceImpl(mock(CartItemRepository.class), mock(BookToCartItemRepository.class)),
                        mock(OrderRepository.class)));
        UserPrincipal principal = new UserPrincipal("principal");
        ConcurrentModel concurrentModel = new ConcurrentModel();
        assertEquals("myProfile", homeController.updateCreditCard(123L, principal, concurrentModel));
        verify(userRepository).findByUsername(any());
        verify(userPaymentRepository).findById(any());
        Object getResult = concurrentModel.get("stateList");
        assertEquals(4, ((Collection<String>) getResult).size());
        assertEquals("BiH", ((List<String>) getResult).get(0));
        assertEquals("CG", ((List<String>) getResult).get(1));
        assertEquals("MK", ((List<String>) getResult).get(2));
        assertEquals("RS", ((List<String>) getResult).get(3));
    }

    @Test
    void testUpdateUserShipping() {
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
        when(userRepository.findByUsername(any())).thenReturn(user);
        UserServiceImpl userService = new UserServiceImpl(mock(UserPaymentRepository.class), userRepository,
                mock(RoleRepository.class), mock(PasswordResetTokenRepository.class), mock(UserShippingRepository.class));

        UserShipping userShipping = new UserShipping();
        userShipping.setId(123L);
        userShipping.setUser(user);
        userShipping.setUserShippingCity("User Shipping City");
        userShipping.setUserShippingCountry("GB");
        userShipping.setUserShippingDefault(true);
        userShipping.setUserShippingName("User Shipping Name");
        userShipping.setUserShippingState("User Shipping State");
        userShipping.setUserShippingStreet1("User Shipping Street1");
        userShipping.setUserShippingStreet2("User Shipping Street2");
        userShipping.setUserShippingZipcode("User Shipping Zipcode");
        UserShippingRepository userShippingRepository = mock(UserShippingRepository.class);
        when(userShippingRepository.findById(any())).thenReturn(Optional.of(userShipping));
        UserShippingServiceImpl userShippingService = new UserShippingServiceImpl(userShippingRepository);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        StandardReactiveWebEnvironment env = new StandardReactiveWebEnvironment();
        MailConstructor mailConstructor = new MailConstructor(env, new TemplateEngine());

        UserSecurityService userSecurityService = new UserSecurityService(mock(UserRepository.class));
        BookServiceImpl bookService = new BookServiceImpl(mock(BookRepository.class));
        UserPaymentServiceImpl userPaymentService = new UserPaymentServiceImpl(mock(UserPaymentRepository.class));
        CartItemServiceImpl cartItemService = new CartItemServiceImpl(mock(CartItemRepository.class),
                mock(BookToCartItemRepository.class));

        HomeController homeController = new HomeController(mailSender, mailConstructor, userService, userSecurityService,
                bookService, userPaymentService, userShippingService, cartItemService,
                new OrderServiceImpl(
                        new CartItemServiceImpl(mock(CartItemRepository.class), mock(BookToCartItemRepository.class)),
                        mock(OrderRepository.class)));
        UserPrincipal principal = new UserPrincipal("principal");
        ConcurrentModel concurrentModel = new ConcurrentModel();
        assertEquals("myProfile", homeController.updateUserShipping(123L, principal, concurrentModel));
        verify(userRepository).findByUsername(any());
        verify(userShippingRepository).findById(any());
        Object getResult = concurrentModel.get("stateList");
        assertEquals(4, ((Collection<String>) getResult).size());
        assertEquals("BiH", ((List<String>) getResult).get(0));
        assertEquals("CG", ((List<String>) getResult).get(1));
        assertEquals("MK", ((List<String>) getResult).get(2));
        assertEquals("RS", ((List<String>) getResult).get(3));
    }

    @Test
    void testRemoveCreditCard() {
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
        when(userRepository.findByUsername(any())).thenReturn(user);
        UserServiceImpl userService = new UserServiceImpl(mock(UserPaymentRepository.class), userRepository,
                mock(RoleRepository.class), mock(PasswordResetTokenRepository.class), mock(UserShippingRepository.class));

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
        userPayment.setUser(user);
        userPayment.setUserBilling(userBilling);

        Optional<UserPayment> ofResult = Optional.of(userPayment);
        UserPaymentRepository userPaymentRepository = mock(UserPaymentRepository.class);
        doNothing().when(userPaymentRepository).deleteById(any());
        when(userPaymentRepository.findById(any())).thenReturn(ofResult);
        UserPaymentServiceImpl userPaymentService = new UserPaymentServiceImpl(userPaymentRepository);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        StandardReactiveWebEnvironment env = new StandardReactiveWebEnvironment();
        MailConstructor mailConstructor = new MailConstructor(env, new TemplateEngine());

        UserSecurityService userSecurityService = new UserSecurityService(mock(UserRepository.class));
        BookServiceImpl bookService = new BookServiceImpl(mock(BookRepository.class));
        UserShippingServiceImpl userShippingService = new UserShippingServiceImpl(mock(UserShippingRepository.class));
        CartItemServiceImpl cartItemService = new CartItemServiceImpl(mock(CartItemRepository.class),
                mock(BookToCartItemRepository.class));

        HomeController homeController = new HomeController(mailSender, mailConstructor, userService, userSecurityService,
                bookService, userPaymentService, userShippingService, cartItemService,
                new OrderServiceImpl(
                        new CartItemServiceImpl(mock(CartItemRepository.class), mock(BookToCartItemRepository.class)),
                        mock(OrderRepository.class)));
        UserPrincipal principal = new UserPrincipal("principal");
        assertEquals("myProfile", homeController.removeCreditCard(123L, principal, new ConcurrentModel()));
        verify(userRepository).findByUsername(any());
        verify(userPaymentRepository).deleteById(any());
        verify(userPaymentRepository).findById(any());
    }

    @Test
    void testRemoveUserShipping() {
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
        when(userRepository.findByUsername(any())).thenReturn(user);
        UserServiceImpl userService = new UserServiceImpl(mock(UserPaymentRepository.class), userRepository,
                mock(RoleRepository.class), mock(PasswordResetTokenRepository.class), mock(UserShippingRepository.class));

        UserShipping userShipping = new UserShipping();
        userShipping.setId(123L);
        userShipping.setUser(user);
        userShipping.setUserShippingCity("User Shipping City");
        userShipping.setUserShippingCountry("GB");
        userShipping.setUserShippingDefault(true);
        userShipping.setUserShippingName("User Shipping Name");
        userShipping.setUserShippingState("User Shipping State");
        userShipping.setUserShippingStreet1("User Shipping Street1");
        userShipping.setUserShippingStreet2("User Shipping Street2");
        userShipping.setUserShippingZipcode("User Shipping Zipcode");
        Optional<UserShipping> ofResult = Optional.of(userShipping);
        UserShippingRepository userShippingRepository = mock(UserShippingRepository.class);
        doNothing().when(userShippingRepository).deleteById(any());
        when(userShippingRepository.findById(any())).thenReturn(ofResult);
        UserShippingServiceImpl userShippingService = new UserShippingServiceImpl(userShippingRepository);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        StandardReactiveWebEnvironment env = new StandardReactiveWebEnvironment();
        MailConstructor mailConstructor = new MailConstructor(env, new TemplateEngine());

        UserSecurityService userSecurityService = new UserSecurityService(mock(UserRepository.class));
        BookServiceImpl bookService = new BookServiceImpl(mock(BookRepository.class));
        UserPaymentServiceImpl userPaymentService = new UserPaymentServiceImpl(mock(UserPaymentRepository.class));
        CartItemServiceImpl cartItemService = new CartItemServiceImpl(mock(CartItemRepository.class),
                mock(BookToCartItemRepository.class));

        HomeController homeController = new HomeController(mailSender, mailConstructor, userService, userSecurityService,
                bookService, userPaymentService, userShippingService, cartItemService,
                new OrderServiceImpl(
                        new CartItemServiceImpl(mock(CartItemRepository.class), mock(BookToCartItemRepository.class)),
                        mock(OrderRepository.class)));
        UserPrincipal principal = new UserPrincipal("principal");
        assertEquals("myProfile", homeController.removeUserShipping(123L, principal, new ConcurrentModel()));
        verify(userRepository).findByUsername(any());
        verify(userShippingRepository).deleteById(any());
        verify(userShippingRepository).findById(any());
    }
}