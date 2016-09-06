package com.misakimei.stone.vm;

import com.misakimei.stone.Environment;

/**
 * Created by 18754 on 2016/9/6.
 */
public interface EnvEx extends Environment {
    StoneVM stoneVM();
    Code code();
}
