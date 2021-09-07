package com.mycompany.producercloudstream.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.producercloudstream.kafka.MessageProducer;
import com.mycompany.producercloudstream.kafka.event.Alert;
import com.mycompany.producercloudstream.kafka.event.News;
import com.mycompany.producercloudstream.rest.dto.CreateAlertRequest;
import com.mycompany.producercloudstream.rest.dto.CreateNewsRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The test cases in this file is identical to the ones present in NewsControllerTest of producer-kafka application
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest(NewsController.class)
class NewsControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MessageProducer messageProducer;

    @Test
    void testPublishNews() {
        CreateNewsRequest request = new CreateNewsRequest("source", "title");

        webTestClient.post()
                .uri(BASE_URL + "/news")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateNewsRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(News.class)
                .value(news -> {
                    assertThat(news.getId()).isNotNull();
                    assertThat(news.getSource()).isEqualTo(request.getSource());
                    assertThat(news.getTitle()).isEqualTo(request.getTitle());
                });
    }

    @Test
    void testPublishAlert() {
        CreateAlertRequest request = new CreateAlertRequest(1, "message");

        webTestClient.post()
                .uri(BASE_URL + "/alerts")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateAlertRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Alert.class)
                .value(alert -> {
                    assertThat(alert.getId()).isNotNull();
                    assertThat(alert.getLevel()).isEqualTo(request.getLevel());
                    assertThat(alert.getMessage()).isEqualTo(request.getMessage());
                });
    }

    private static final String BASE_URL = "/api";
}