package com.misakimei.stone;

/**
 * Created by 18754 on 2016/7/31.
 */
public class StoneObject {

    public static  class AccessException extends Exception{}

    private Environment env;


    public StoneObject(NestEnv nestEnv) {
        env=nestEnv;

    }

    public Object read(String name) throws AccessException {
        return getEnv(name).get(name);
    }

    public void write(String name, Object value) throws AccessException {
        getEnv(name).putNew(name,value);
    }
    /*
    Object的env可能有外部的env 此时是不允许访问的
    例如 语句是

    a=100
    class t{

    }
    c=t.new
    c.a=101

    此时使用c.a 如何不检查的话 在全局变量中的a 就会被改变

     */



    private Environment getEnv(String member) throws AccessException {
        Environment e=env.where(member);
        if (e!=null&&e==env){
            return e;
        }
        throw new AccessException();

    }
}
