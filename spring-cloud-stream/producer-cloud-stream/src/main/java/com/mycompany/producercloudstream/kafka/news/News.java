package com.mycompany.producercloudstream.kafka.news;

import lombok.Value;

@Value
public class News {

    String id;
    String source;
    String title;
}
