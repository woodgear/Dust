package com.misakimei.stone.vm;

import com.misakimei.stone.BlockStment;
import com.misakimei.stone.Environment;
import com.misakimei.stone.Function;
import com.misakimei.stone.ParamterList;

/**
 * Created by 18754 on 2016/9/6.
 */
public class VMFunction extends Function{

    protected int entry;
    public VMFunction(ParamterList paramters, BlockStment body, Environment env,int entry){
        super(paramters,body,env);
        this.entry=entry;
    }

    public int entry() {
        return entry;
    }



}
