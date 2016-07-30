package com.misakimei.stone.run;

import com.misakimei.stone.ClosureParse;
import com.misakimei.stone.Native;
import com.misakimei.stone.NestEnv;

/**
 * Created by 18754 on 2016/7/30.
 * 增加原生函数
 */
public class NativeInterpreter extends BasicInterpreter {

    public static void main(String[] args) {
        run(new ClosureParse(),new Native().environment(new NestEnv()),"./data/nativefun.stone");
    }
}
