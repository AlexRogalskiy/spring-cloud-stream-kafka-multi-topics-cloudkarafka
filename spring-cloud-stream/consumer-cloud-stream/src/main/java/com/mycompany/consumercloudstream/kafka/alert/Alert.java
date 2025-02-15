package com.mycompany.consumercloudstream.kafka.alert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    private String id;
    private Integer level;
    private String message;
}
