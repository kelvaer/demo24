package org.example.demo2024.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: demo2024
 * @description: InMessage
 * @author: 作者名
 * @create: 2024/02/06
 */
@Data
public class InMessage {
    private String fromName;


    private String toName;

    private String content;

    private Date time;
}
