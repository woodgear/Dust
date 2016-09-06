package com.misakimei.stone.run;

import com.misakimei.stone.*;
import com.misakimei.stone.tool.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by 18754 on 2016/7/27.
 * 语法树分割测试
 */
public class ParserRunner {
    public static void main(String[] args) throws FileNotFoundException {
        Lexer lexer=new Lexer(new FileReader(new File("./data/fun.stone")));
        BasicParser bp=new BasicParser();
        while (lexer.peek(0)!= Token.EOF){
            ASTree ast=bp.parse(lexer);
            Log.d(ast.toString());
        }

    }
}
