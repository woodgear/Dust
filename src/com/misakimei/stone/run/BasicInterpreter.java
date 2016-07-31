package com.misakimei.stone.run;

import com.misakimei.stone.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by 18754 on 2016/7/28.
 * Stone的解释器
 * 基本解释器运行测试
 */
public class BasicInterpreter {
    private static String file = "./data/interpreter.stone";

    public static void main(String[] args) {
        run(new BasicParser(), new BasicEnv(), file);
    }

    public static void run(BasicParser basicParser, Environment basicEnv, String path) {
        try {
            Lexer lex = new Lexer(new FileReader(new File(path)));
            while (lex.peek(0) != Token.EOF) {
                ASTree ast = basicParser.parse(lex);
                if (!(ast instanceof NULLStmnt)) {
                    Object o = ast.eval(basicEnv);
                    //Log.d("debug==> "+o.toString());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
