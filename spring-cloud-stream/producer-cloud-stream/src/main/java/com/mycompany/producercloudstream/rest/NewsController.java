package com.mycompany.producercloudstream.rest;

import com.mycompany.producercloudstream.domain.Alert;
import com.mycompany.producercloudstream.domain.News;
import com.mycompany.producercloudstream.kafka.MessageProducer;
import com.mycompany.producercloudstream.rest.dto.CreateAlertDto;
import com.mycompany.producercloudstream.rest.dto.CreateNewsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class NewsController {

    private final MessageProducer messageProducer;

    @PostMapping("/news")
    public String publishNews(@Valid @RequestBody CreateNewsDto createNewsDto) {
        String id = UUID.randomUUID().toString();
        messageProducer.send(new News(id, createNewsDto.getSource(), createNewsDto.getTitle()));
        return id;
    }

    @PostMapping("/alert")
    public String publishAlert(@Valid @RequestBody CreateAlertDto createAlertDto) {
        String id = UUID.randomUUID().toString();
        messageProducer.send(new Alert(id, createAlertDto.getLevel(), createAlertDto.getMessage()));
        return id;
    }

}
