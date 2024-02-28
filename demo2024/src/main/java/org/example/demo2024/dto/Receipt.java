package org.example.demo2024.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.java_websocket.WebSocket;

/**
 * @program: demo2024
 * @description: Receipt
 * @author: 作者名
 * @create: 2024/02/06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {

    private String topic;

    private String msg;

    private WebSocket conn;

}
