package com.misakimei.stone;

import com.misakimei.stone.tool.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by 18754 on 2016/7/27.
 */
public class LexerRunner {
    public static void main(String[] args) {
        File code=new File("data/lex.stone");
        Lexer lexer;
        try {
            lexer=new Lexer(new FileReader(code));
            for (Token t;(t=lexer.read())!=Token.EOF;){
                Log.d("==>"+t.getText());
              }

        } catch (FileNotFoundException e) {
            Log.d("找不到文件 "+code.getAbsolutePath());
            Log.d("not find");
        }
    }
}
