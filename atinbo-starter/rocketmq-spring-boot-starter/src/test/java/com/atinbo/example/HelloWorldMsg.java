package com.atinbo.example;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class HelloWorldMsg {
    private String type;
    private String content;
}