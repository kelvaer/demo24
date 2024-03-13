package org.example.demo2024.util;

/**
 * @program: demo2024
 * @description: ip地址工具类
 * @author: 作者名
 * @create: 2024/02/05
 */
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

@Slf4j
public class IpUtil {

    /**
     * java 获取 本机ip
     * @return String
     */
    public static String getLocalServerIp() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e.toString());
        }
        return "";
    }

    /**
     * 从HttpServletRequest中获取浏览器客户端的请求ip
     * @param request HttpServletRequest
     * @return String ip地址
     */
    public static String getIp(HttpServletRequest request){
        String clientIP = ServletUtil.getClientIP(request);
        String ip = "0:0:0:0:0:0:0:1".equals(clientIP) ? "127.0.0.1" : clientIP;
        ip = ip.trim();
        log.info("请求ip:{}", ip);
        return ip;
    }

    /**
     * 根据ip地址查询IP所属的实际地址信息
     * @param ip String  ip地址,如 223.120.23.106
     * @return String  xx省xx市xx区移动/联通/电信
     * @throws Exception
     */
    public static String getAddr(String ip) throws Exception{
        ClassPathResource resource = new ClassPathResource("\\xdb\\ip2region.xdb");
        byte[] cBuff = FileCopyUtils.copyToByteArray(resource.getInputStream());
        Searcher searcher = Searcher.newWithBuffer(cBuff);
        String region = searcher.searchByStr(ip);
        String addr = region.replace("0|", "").replace("|0", "");
        log.info("请求addr:{}", addr);
        searcher.close();
        return addr;
    }
}
