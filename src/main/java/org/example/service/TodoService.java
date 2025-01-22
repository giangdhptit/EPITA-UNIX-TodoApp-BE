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

import java.util.*;


@Service
@RequiredArgsConstructor
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Value("${email.pre-notify-duration}")
    private Long preNotifyDuration;

    @Value("${email.destination}")
    private String emailDestination;

  //  @Scheduled(fixedRate = 30000)
    public void sendSimpleEmail() {
        // retrieve data
        List<TodoEntity> list = todoRepository.getListToNotify(preNotifyDuration);

        //mail configuration
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.zoho.com");
        mailSender.setPort(465);  // or 587 for TLS
        mailSender.setUsername("giang.dinh@lifesup.com.vn");
        mailSender.setPassword("ESjYL2shH66S");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");  // Use this for SSL
        // props.put("mail.smtp.starttls.enable", "true");  // Uncomment for TLS
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.writetimeout", "5000");
//        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "smtp.zoho.com");
        props.put("mail.smtp.starttls.enable", "true");

        list.stream().forEach(t -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("giang.dinh@lifesup.com.vn");
            message.setTo(emailDestination);
            message.setSubject("[Todo by AG] Task notification");
            message.setText("You have an uncompleted task: "+t.getTitle()+"\n" +
                    "Task Description: "+t.getDescription()+"\n" +
                    "Dont miss it!");
            mailSender.send(message);
        });

    }
}
