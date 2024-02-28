package org.example.demo2024.vo;

import lombok.Data;

import java.util.Date;

/**
 * @program: demo2024
 * @description: OutMessage
 * @author: 作者名
 * @create: 2024/02/06
 */
@Data
public class OutMessage {
    private String fromName;

    private String content;

    private Date time;
}
