package com.misakimei.stone.run;

import com.misakimei.stone.ClosureParse;
import com.misakimei.stone.NestEnv;

/**
 * Created by 18754 on 2016/7/29.
 * 闭包测试器
 */
public class ClosureInterpretre extends BasicInterpreter{
    public static void main(String[] args) {
        run(new ClosureParse(),new NestEnv(),"./data/closure.stone");
    }
}
