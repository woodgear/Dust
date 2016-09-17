package com.misakimei.stone.type;

import com.misakimei.stone.Environment;
import com.misakimei.stone.Native;
import com.misakimei.stone.ResizableArrayEnv;

/**
 * Created by 18754 on 2016/9/14.
 */
public class TypedNatives extends Native {
    protected TypeEnv typeEnv;
    public TypedNatives(TypeEnv te) {
        typeEnv=te;
    }

    protected void append(Environment env,String name,Class<?>classz,String methodname,TypeInfo type,Class<?> ...params){
        append(env,name,classz,methodname,params);
        int inndex=env.symbol().find(name);
        typeEnv.put(0,inndex,type);
    }
    protected void appendNatives(Environment env){
        append(env,"print",print.class,"m",TypeInfo.function(TypeInfo.INT,TypeInfo.ANY),Object.class);
        append(env,"read",read.class,"m",TypeInfo.function(TypeInfo.STRING),Object.class);
        append(env,"print",length.class,"m",TypeInfo.function(TypeInfo.INT,TypeInfo.STRING),Object.class);
        append(env,"print",toInt.class,"m",TypeInfo.function(TypeInfo.INT,TypeInfo.ANY),Object.class);
        append(env,"print",currenttime.class,"m",TypeInfo.function(TypeInfo.INT),Object.class);


    }
}
