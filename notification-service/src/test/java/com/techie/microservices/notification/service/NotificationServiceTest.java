package com.techie.microservices.notification.service;


import com.techie.microservices.order.event.OrderPlacedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private NotificationService notificationService;
    @Mock
    private MimeMailMessage mimeMailMessage;
    @Mock
    private MimeMessage mimeMessage;
    @Mock
    private MimeMessageHelper mimeMessageHelper;
    private OrderPlacedEvent orderPlacedEvent1;

    @BeforeEach
    void setUp() {
        orderPlacedEvent1 = new OrderPlacedEvent("123", "John.Chandel@gmail.com", "John", "Chandel");
    }

    @Test
    public void testListen_SuccessfullyEmailSending() throws MessagingException {

        notificationService.listen(orderPlacedEvent1);
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));

    }


}
