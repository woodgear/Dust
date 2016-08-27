package com.misakimei.stone.run;

import com.misakimei.stone.*;
import com.misakimei.stone.run.EnvOptInterpreter;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by 18754 on 2016/8/4.
 * 优化对象操作性能
 * 大写的尴尬 使用斐波那契测试时反而慢了 之前是3661 现在是 4671 增加内联缓存大概是 4300-4560 感觉并没有什么卵用
 */
public class ObjOpInterpreter{
    public static void main(String[] args) throws FileNotFoundException {
        run(new ClassParse(),new Native().environment(new ResizableArrayEnv()),"./data/classfib.stone");
    }
    protected static void run(BasicParser parser, Environment env, String file) throws FileNotFoundException {
        Lexer lexer=new Lexer(new FileReader(file));
        while (lexer.peek(0)!= Token.EOF){
            ASTree t=parser.parse(lexer);
            if (!(t instanceof NULLStmnt)) {
                t.lookup(env.symbol());
                Object r = t.eval(env);
            }
        }
    }
}
