/*
 * Filename UserService.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 通过配置的ip判断是否网关类型，外网和内网
 *
 * @author kongweixiang
 * @date 2018/9/13
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "iplist")
@Setter
@Getter
public class GatewayType {
    private List<String> list;
    private Boolean inner = Boolean.FALSE;

//    private Boolean isinner = Boolean.FALSE;


//    private static List<String> getLocalIPList() {
//        List<String> ipList = new ArrayList<String>();
//        try {
//            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//            NetworkInterface networkInterface;
//            Enumeration<InetAddress> inetAddresses;
//            InetAddress inetAddress;
//            String ip;
//            while (networkInterfaces.hasMoreElements()) {
//                networkInterface = networkInterfaces.nextElement();
//                inetAddresses = networkInterface.getInetAddresses();
//                while (inetAddresses.hasMoreElements()) {
//                    inetAddress = inetAddresses.nextElement();
//                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
//                        ip = inetAddress.getHostAddress();
//                        ipList.add(ip);
//                    }
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//        return ipList;
//    }

    /**
     * 初始化判断是否为内部网关，并初始化内部网关标识
     *
     * @return
     */
    @PostConstruct
    public void innerValid() {
//        System.err.println("==========================>>>>>>>" + inner);
//
//        List<String> ipLocal = this.getLocalIPList();
//        this.inner = false;
//        if (this.list != null && !this.list.isEmpty()) {
//            for (String ip : ipLocal) {
//                if (this.list.contains(ip)) {
//                    this.inner = true;
//                    break;
//                }
//            }
//        }
    }
}
