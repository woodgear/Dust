package com.misakimei.stone.Interpreter;

import com.misakimei.stone.*;

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
        run(new BasicParser(),new BasicEnv(),file);
    }

    public static void run(BasicParser basicParser, Environment basicEnv, String path) {
        Log.d("开始");
        try {
            Lexer lex=new Lexer(new FileReader(new File(path)));
            while (lex.peek(0)!= Token.EOF){
                Log.d("开始生成语法树");
                ASTree ast=basicParser.parse(lex);
                if (ast instanceof BinaryExpr){
                    Log.d("bin");
                }else if (ast instanceof PrimaryExpr){
                    Log.d("prim");
                }
                Log.d("生成语法树完成");

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
