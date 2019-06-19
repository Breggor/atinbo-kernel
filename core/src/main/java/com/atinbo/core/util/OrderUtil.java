package com.atinbo.core.util;

import com.google.common.base.Strings;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderUtil {

    private static final String FORMATSTRING = "yyMMddHHmmss";
    /**
     * 使用公平锁防止饥饿
     */
    private static final Lock lock = new ReentrantLock(true);
    private static final int TIMEOUTSECODES = 3;
    private volatile static int serialNo = 0;

    /**
     * 生成订单号，生成规则 时间戳+2位随机数+两位自增序列 <br>
     * 采用可重入锁减小锁持有的粒度，提高系统在高并发情况下的性能
     *
     * @return
     */
    public static String generateOrder() {
        StringBuilder builder = new StringBuilder();
        builder.append(getDateTime(FORMATSTRING)).append(getLastNumOfIP());
        builder.append(getRandomNum()).append(getIncrement());
        return builder.toString();
    }


    /**
     * 获取系统当前时间
     *
     * @param formatStr
     * @return
     */
    private static String getDateTime(String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date());
    }

    /**
     * 获取自增序列
     *
     * @return
     */
    private static String getIncrement() {
        int tempSerialNo = 0;
        try {
            if (lock.tryLock(TIMEOUTSECODES, TimeUnit.SECONDS)) {
                if (serialNo >= 99) {
                    serialNo = 0;
                } else {
                    serialNo = serialNo + 1;
                }
                tempSerialNo = serialNo;
            } else {
                // 指定时间内没有获取到锁，存在激烈的锁竞争或者性能问题，直接报错
                System.out.println("can not get lock in:{} seconds!");
                throw new RuntimeException("generateOrder can not get lock!");
            }
        } catch (Exception e) {
            System.out.println("tryLock throws Exception:");
            throw new RuntimeException("tryLock throws Exception!");
        } finally {
            lock.unlock();
        }

        if (tempSerialNo < 10) {
            return "0" + tempSerialNo;
        } else {
            return "" + tempSerialNo;
        }
    }

    /**
     * 返回两位随机整数
     *
     * @return
     */
    private static String getRandomNum() {
        int num = new Random(System.nanoTime()).nextInt(100);
        if (num < 10) {
            return "0" + num;
        } else {
            return num + "";
        }
    }

    /**
     * 获取IP的最后两位数字
     *
     * @return
     */
    private static String getLastNumOfIP() {
        String ip = getCurrentIP();
        if (!Strings.isNullOrEmpty(ip)) {
            ip = ip.replace(".", "");
            ip = ip.substring(ip.length() - 2);
        }
        return ip;
    }

    /**
     * 获取本机IP
     *
     * @return
     */
    private static String getCurrentIP() {
        String ip = "";
        try {
            ip = getIp();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("can not get ip!");
        }
        if (ip == null || ip.equals("")) {
            System.out.println("ip is blank!");
            throw new RuntimeException("ip is blank!");
        }
        return ip;
    }

    private static String getIp() {
        String ip = "";
        // 根据网卡取本机配置的IP
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                System.out.println("DisplayName:" + ni.getDisplayName());
                System.out.println("Name:" + ni.getName());
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    System.out.println("IP:" + ips.nextElement().getHostAddress());
                    ip = ips.nextElement().getHostAddress();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }
}
