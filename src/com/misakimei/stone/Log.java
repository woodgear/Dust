package com.misakimei.stone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by 18754 on 2016/7/27.
 */
public class Log {
    private static Logger log= LogManager.getLogger("Stone");

    public static void d(String msg){
        log.debug(msg);
    }
    public static void e(String msg){
        log.error(msg);
    }
    public static void w(String msg){
        log.warn(msg);
    }

}
