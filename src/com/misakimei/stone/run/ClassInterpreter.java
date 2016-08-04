package com.misakimei.stone.run;

import com.misakimei.stone.ClassParse;
import com.misakimei.stone.Native;
import com.misakimei.stone.NestEnv;

/**
 * Created by 18754 on 2016/7/31.
 */
public class ClassInterpreter extends BasicInterpreter {
    public static void main(String[] args) {
        run(new ClassParse(), new Native().environment(new NestEnv()),"./data/classfib.stone");
    }
}
