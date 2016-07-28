package com.misakimei.stone;

import java.util.HashMap;

/**
 * Created by 18754 on 2016/7/28.
 */
public class BasicEnv implements Environment {

    protected HashMap<String,Object>values;
    public BasicEnv(){
        values=new HashMap<>();
    }
    @Override
    public void put(String name, Object value) {
        values.put(name,value);
    }

    @Override
    public Object get(String name) {
        return values.get(name);
    }
}
