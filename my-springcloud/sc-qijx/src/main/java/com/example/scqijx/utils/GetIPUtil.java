package com.example.scqijx.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;

public class GetIPUtil {

	private static Logger log=LoggerFactory.getLogger(GetIPUtil.class);

    private static final String ERROR_ADDRESS="127.0.0.1/获取服务器IP错误";
    /**
     * 获取服务器地址
     *
     * @return Ip地址
     */
    public static String getServerIp() {
        // 获取操作系统类型
        String sysType = System.getProperties().getProperty("os.name");
        String ip;
        if (sysType.toLowerCase().startsWith("win")) {  // 如果是Windows系统，获取本地IP地址
            String localIP = null;
            try {
                localIP = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
            if (localIP != null) {
                return localIP;
            }
        } else {
            ip = getIpByEthNum("eth0"); // 兼容Linux
            if (ip != null) {
                return ip;
            }
        }
        return ERROR_ADDRESS;
    }
    
    /**
     * 根据网络接口获取IP地址
     * @param ethNum 网络接口名，Linux下是eth0
     * @return
     */
    private static String getIpByEthNum(String ethNum) {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (ethNum.equals(netInterface.getName())) {
                    Enumeration addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            log.error(e.getMessage(), e);
        }
        return ERROR_ADDRESS;
    }
}
