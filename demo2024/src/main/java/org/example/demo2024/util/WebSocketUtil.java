package org.example.demo2024.util;

/**
 * @program: demo2024
 * @description: ws
 * @author: 作者名
 * @create: 2024/02/06
 */

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.setting.dialect.Props;
import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.biz.ITopicHandleStrategy;
import org.example.demo2024.biz.ReceiptHandleStrategyFactory;
import org.example.demo2024.biz.ReceiptStrategyContext;
import org.example.demo2024.dto.Receipt;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class WebSocketUtil extends WebSocketServer {


    private static List<WebSocket> clients = new CopyOnWriteArrayList<>();


    private static  InetSocketAddress inet = new InetSocketAddress(Integer.valueOf(SpringUtil.getProperty("wskt.port")));
    private WebSocketUtil() {
        super(inet);
    }

    private static final WebSocketUtil wskt = new WebSocketUtil();


    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        if (conn != null && conn.isOpen()) {
            log.info("一个新客户端打开连接...");
            conn.send("Welcome to link wskt server!");


            //客户端ip
            String ip = conn.getRemoteSocketAddress().getAddress().getHostAddress();
            log.info("客户端请求的ip:{}", ip);
            int port = conn.getRemoteSocketAddress().getPort();
            log.info("客户端的port:{}", port);
            //客户端请求的 websocket path
            String resourceDescriptor = handshake.getResourceDescriptor();
            log.info("客户端请求的  path:{}", resourceDescriptor);
            clients.add(conn);
        }


    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        log.warn("一个客户端断开websocket连接...");
        String ip = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        log.info("客户端请求的ip:{}", ip);
        int port = conn.getRemoteSocketAddress().getPort();
        log.info("客户端的port:{}", port);
        String resourceDescriptor = conn.getResourceDescriptor();
        log.info("客户端请求的 path:{}", resourceDescriptor);
        log.info("code:{}", code);
        log.info("reason:{}", reason);
        log.info("remote:{}", remote);
        clients.remove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        log.info("一个客户端发送了消息....");
        String ip = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        log.info("客户端请求的ip:{}", ip);
        int port = conn.getRemoteSocketAddress().getPort();
        log.info("客户端请求的port:{}", port);
        String resourceDescriptor = conn.getResourceDescriptor();
        log.info("客户端请求的path:{}", resourceDescriptor);
        log.info("客户端发送的msg:{}", message);

        handleClientReqMsg(conn, message);

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        log.error("ws客户端异常", ex);
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific
            // websocket
            if (conn.getRemoteSocketAddress() == null) {
                log.info("ws客户端异常,无法解析ip端口等信息");
                conn.close();
            } else {
                String hostName = conn.getRemoteSocketAddress().getAddress().getHostName();
                log.info("异常客户端的 hostName:{}", hostName);
//                String ip = conn.getRemoteSocketAddress().getAddress().getHostAddress();
//                log.info("异常客户端的ip:{}", ip);
                int port = conn.getRemoteSocketAddress().getPort();
                log.info("异常客户端的port:{}", port);
                String resourceDescriptor = conn.getResourceDescriptor();
                log.info("异常客户端的path:{}", resourceDescriptor);
                conn.close();

            }
        }
    }

    @Override
    public void onStart() {
        setConnectionLostTimeout(100); //连接丢失超时时间100s

        //启动后定时打印 客户端连接信息
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                printCurrentConns();
            }
        }, 3000, 10000); //延迟1s后,每8秒打印一次
    }

    private static void printCurrentConns() {
        int size = clients.size();
        if (size > 0) {
            log.info("--当前共有{}个websocket连接---", size);
            for (int i = 0; i < clients.size(); i++) {
                String ip = clients.get(i).getRemoteSocketAddress().getAddress().getHostAddress();
                int port = clients.get(i).getRemoteSocketAddress().getPort();
                String resourceDescriptor = clients.get(i).getResourceDescriptor();
                log.info("第{}个客户端的ip:{},port:{},path:{}", i + 1, ip, port, resourceDescriptor);
            }
            log.info("--------------------------");
        }
    }


    public static void startServer() throws UnknownHostException {
        wskt.start();
        log.info("wsktServer started on port: {}", wskt.getPort());
        String localServerIp = IpUtil.getLocalServerIp();
        if (StrUtil.isBlank(localServerIp)){
            localServerIp = "127.0.0.1";
        }
        log.info("系统websocket服务地址：ws://{}:{}", localServerIp,wskt.getPort());
    }


    public static void closeServer() throws InterruptedException {
        log.info("wsktServer closed on port: {}", wskt.getPort());
        wskt.stop(200);
    }


    private static void handleClientReqMsg(WebSocket conn, String reqStr) {
        String resourceDescriptor = conn.getResourceDescriptor();

        // 可以在这里处理req/resp形式的ws请求
//        if (conn.getResourceDescriptor().equals("/123")) {
//            if (reqStr.equals("aaa")) {
//                publishMsgToClient("bbb", conn);
//            }
//        }
//        if (conn.getResourceDescriptor().equals("/234")) {
//            if (reqStr.equals("ccc")) {
//                //w12
//                publishMsgToClient("ddd", conn);
//            }
//        }

        log.warn("========start====================");

        ReceiptStrategyContext receiptStrategyContext = new ReceiptStrategyContext();
        ITopicHandleStrategy topicHandleStrategy =
                ReceiptHandleStrategyFactory.getReceiptHandleStrategy(
                        resourceDescriptor);
        if (topicHandleStrategy != null) {
            receiptStrategyContext.setTopicHandleStrategy(topicHandleStrategy);
            receiptStrategyContext.handleReceipt(Receipt.builder()
                    .msg(reqStr)
                    .conn(conn)
                    .topic(resourceDescriptor)
                    .build());
        }else {
            conn.close();
        }
        log.warn("===========end=====================" + "\n\n\n");
    }


    public static void publishMsgToClient(String msg, WebSocket targetClient) {
        if (targetClient == null) {
            return;
        }
        String ip = targetClient.getRemoteSocketAddress().getAddress().getHostAddress();
        int port = targetClient.getRemoteSocketAddress().getPort();
        String resourceDescriptor = targetClient.getResourceDescriptor();
        wskt.broadcast(msg, ListUtil.toList(targetClient));
        log.info("server 发布消息:{}给客户端ip:{},port:{},path:{}", msg, ip, port, resourceDescriptor);
    }

    public static void publishMsgToSomeClients(String msg, Collection<WebSocket> clients) {
        if (clients == null || clients.size() == 0) {
            return;
        }
        wskt.broadcast(msg, clients);
        log.info("server 广播消息:{}", msg);
    }


    /**
     * ws://127.0.0.1:8843/123
     *
     * @param ip   127.0.0.1
     * @param port 8843
     * @param path /123
     * @return WebSocket
     */
    public static WebSocket getOneClient(String ip, int port, String path) {
        if (clients.size() > 0) {
            for (WebSocket client : clients) {
                if (client != null) {
                    int cPort = client.getRemoteSocketAddress().getPort();
                    String cip = client.getRemoteSocketAddress().getAddress().getHostAddress();
                    String cPath = client.getResourceDescriptor();
                    if (ip.equals(cip) && port == cPort && path.equals(cPath)) {
                        return client;
                    }
                }
            }
        }
        return null;
    }


    public static List<WebSocket> getClientsByPath(String path){
        List<WebSocket>  resultList = new ArrayList<>();
        if (clients.size() > 0) {
            for (WebSocket client : clients) {
                if (client != null) {
                    String cPath = client.getResourceDescriptor();
                    if (path.equals(cPath)) {
                        resultList.add(client);
                    }
                }
            }
            return resultList;
        }else {
            return null;
        }
    }



    public static void serverPushClientTest(String msg) {
        if (clients.size() > 0) {
            for (WebSocket client : clients) {
                if (client != null) {
                    String ip = client.getRemoteSocketAddress().getAddress().getHostAddress();
                    int port = client.getRemoteSocketAddress().getPort();
                    String resourceDescriptor = client.getResourceDescriptor();

                    wskt.broadcast(msg, ListUtil.toList(client));
                    log.info("server 推送广播消息:{}给客户端ip:{},port:{},path:{}", msg, ip, port, resourceDescriptor);
                }
            }
        }
    }




}

