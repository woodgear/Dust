package com.misakimei.stone;

/**
 * Created by 18754 on 2016/7/28.
 */
public class StoneExcetion extends RuntimeException {
    public StoneExcetion(String s) {
        super(s);
    }
    public StoneExcetion(String msg,ASTree t){
        super(msg+" "+t.location());
    }

}
