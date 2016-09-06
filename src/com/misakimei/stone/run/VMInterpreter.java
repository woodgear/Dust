package com.misakimei.stone.run;

import com.misakimei.stone.FuncParse;
import com.misakimei.stone.Native;
import com.misakimei.stone.vm.StoneVMEnv;

/**
 * Created by 18754 on 2016/9/6.
 * fib 33 使用虚拟机优化2988毫秒 和之前的ObjOpInterpreter 比要快 另外调试真是太痛苦啦 最后果然是粗心出错了 2333
 */
public class VMInterpreter extends EnvOptInterpreter{
    public static void main(String[] args) {
        run(new FuncParse(),new Native().environment(new StoneVMEnv(100000,100000,100000)),"./data/fun.stone");
    }
}
