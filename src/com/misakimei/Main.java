package com.misakimei;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Main {
    private static Logger log=LogManager.getLogger("Main");
    public static void main(String[] args) {
	// write your code here
        log.debug("show");
        log.error("hello wrold");
    }
}
