package com.misakimei.stone;

/**
 * Created by 18754 on 2016/7/29.
 */
public class Function {

    protected ParamterList paramters;
    protected BlockStment body;
    protected Environment env;

    public Function(ParamterList paramters, BlockStment body, Environment env) {
        this.paramters = paramters;
        this.body = body;
        this.env = env;
    }

    public ParamterList getParamters() {
        return paramters;
    }

    public BlockStment getBody() {
        return body;
    }

    public Environment makeEnv(){
        return new NestEnv(env);
    }

    @Override
    public String toString() {
        return "< function: "+hashCode()+" >";
    }
}
