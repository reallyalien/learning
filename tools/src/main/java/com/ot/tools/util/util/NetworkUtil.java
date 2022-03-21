package com.ot.tools.util.util;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * 常用获取客户端信息的工具
 *
 * @author Xie
 * @date 19-3-19
 */
public final class NetworkUtil {

    private static final String IP_ADDRESS_UNKNOWN = "unknown";
    private static final String IPV4_LOCALHOST = "127.0.0.1";
    private static final String IPV6_LOCALHOST = "0:0:0:0:0:0:0:1";
    private static final int IP_ADDRESS_LENGTH = 15;
    private static final String REGX = ",";
    private static final Pattern IPV4_PATTERN =
            Pattern.compile(
                    "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || IP_ADDRESS_UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || IP_ADDRESS_UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || IP_ADDRESS_UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (IPV4_LOCALHOST.equals(ipAddress) || IPV6_LOCALHOST.equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > IP_ADDRESS_LENGTH) {
            if (ipAddress.indexOf(REGX) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


    public static boolean isValidIpv4Addr(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }
}
