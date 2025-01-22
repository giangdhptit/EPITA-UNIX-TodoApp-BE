package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.TodoEntity;
import org.example.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Value("${email.pre-notify-duration}")
    private Long preNotifyDuration;

    @Value("${email.destination}")
    private String emailDestination;

    @Scheduled(fixedRate = 30000)  // 30 secs
    public void sendSimpleEmail() {
        // Retrieve tasks to notify
        List<TodoEntity> list = todoRepository.getListToNotify(preNotifyDuration);

        // Mail configuration
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.zoho.eu");
        mailSender.setPort(465);  // Use 587 if you enable TLS
        mailSender.setUsername("ag-epita@zohomail.eu");
        mailSender.setPassword("rcm6Xzfdmh9V"); // Ensure no extra spaces

        // Mail properties
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");  // Use SSL
//         props.put("mail.smtp.starttls.enable", "true");  // Uncomment if using TLS
        props.put("mail.smtp.connectiontimeout", "6000000");
        props.put("mail.smtp.timeout", "6000000");
        props.put("mail.smtp.writetimeout", "600000");
        props.put("mail.smtp.ssl.trust", "smtp.zoho.eu");
        props.put("mail.debug", "true");

        // Send email notifications
        list.forEach(t -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ag-epita@zohomail.eu"); // Use your Zoho email address
            message.setTo(emailDestination);
            message.setSubject("[Todo by AG] Task Notification");
            message.setText("You have an uncompleted task: " + t.getTitle() + "\n" +
                    "Task Description: " + t.getDescription() + "\n" +
                    "The deadline is: " + t.getDeadline() + "\n" +
                    "Don't miss it!");
            mailSender.send(message);
        });
    }
}
