package com.misakimei.stone.run;

import com.misakimei.stone.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by 18754 on 2016/8/1.
 * 使用数组环境 目前只能支持到闭包 不支持 类 数组
 */
public class EnvOptInterpreter {
    public static void main(String[] args) throws FileNotFoundException {
        run(new ClosureParse(),new Native().environment(new ResizableArrayEnv()),"./data/testfun.stone");
    }

    protected static void run(BasicParser parser, Environment env,String file){
        Lexer lexer= null;
        try {
            lexer = new Lexer(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new StoneExcetion("找不到要执行的文件 "+file);
        }
        while (lexer.peek(0)!= Token.EOF){
            ASTree t=parser.parse(lexer);
            if (!(t instanceof NULLStmnt)){
                t.lookup(env.symbol());
                Object r=t.eval(env);
            }
        }
    }
}
