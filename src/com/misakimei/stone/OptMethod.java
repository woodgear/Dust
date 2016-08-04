package com.misakimei.stone;

/**
 * Created by 18754 on 2016/8/2.
 */
public class OptMethod extends OptFunction{
    OptStoneObject self;
    public OptMethod(ParamterList paramters, BlockStment body, Environment enviroment, int size, OptStoneObject self) {
        super(paramters,body,enviroment,size);
        this.self=self;
    }

    @Override
    public Environment makeEnv() {
        ArrayEnv e=new ArrayEnv(size,env);
        e.put(0,0,self);
        return e;
    }
}
