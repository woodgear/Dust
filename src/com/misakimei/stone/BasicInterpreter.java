package com.misakimei.stone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by 18754 on 2016/7/28.
 * Stone的解释器
 */
public class BasicInterpreter {
    private static String file="./data/interpreter.stone";
    public static void main(String[] args) {
        run(new BasicParser(),new BasicEnv());
    }

    private static void run(BasicParser basicParser, BasicEnv basicEnv) {
        try {
            Lexer lex=new Lexer(new FileReader(new File(file)));
            while (lex.peek(0)!=Token.EOF){
                ASTree ast=basicParser.parse(lex);
                if (!(ast instanceof NULLStmnt)){
                    Object r=ast.eval(basicEnv);
                    Log.d("==>"+r.toString());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
