package com.atguigu.tingshu.common.util;


import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception工具类
 */
public class ExceptionUtil {

    /**
     * 获取异常信息
     * @param e  异常
     * @return    返回异常信息
     */
    public static String getErrorMessage(Exception e){
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
        }finally {
            try {
                if(pw != null) {
                    pw.close();
                }
                if(sw != null) {
                    sw.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return sw.toString();
    }
}
