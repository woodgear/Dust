package com.misakimei.stone;

/**
 * Created by 18754 on 2016/8/2.
 */
public class OptFunction extends Function {
    protected int size;

    public OptFunction(ParamterList paramters, BlockStment body, Environment env,int size) {
        super(paramters, body, env);
        this.size=size;
    }

    @Override
    public Environment makeEnv() {
        return new ArrayEnv(size,env);
    }
}
