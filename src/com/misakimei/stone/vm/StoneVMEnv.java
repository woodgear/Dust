package com.misakimei.stone.vm;

import com.misakimei.stone.ResizableArrayEnv;

/**
 * Created by 18754 on 2016/9/6.
 */
public class StoneVMEnv extends ResizableArrayEnv implements HeapMemory,EnvEx {

    protected StoneVM svm;
    protected Code code;

    public StoneVMEnv(int codesize,int stacksize,int stringsize) {
        svm=new StoneVM(codesize,stacksize,stringsize,this);
        code=new Code(svm);
    }



    @Override
    public Object read(int index) {
        return values[index];
    }

    @Override
    public void write(int index, Object v) {
        values[index]=v;
    }

    @Override
    public StoneVM stoneVM() {
        return svm;
    }

    @Override
    public Code code() {
        return code;
    }
}
