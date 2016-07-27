package com.misakimei.stone;

import sun.dc.pr.PRError;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 18754 on 2016/7/26.
 * 精彩的代码
 */
public class Lexer {
    /*
    \s*((//.*)|([0-9]+)|("(\n|\\\\|\\"|[^"])*")|([A-Z_a-z][A-Z_a-z0-9]*)|==|<=|>=|&&|\|\||\p{Punct})
     \s*                        前导空白符
     (//.*)                     //注释符
     ([0-9]+)                   整形
     ("(\n|\\\\|\\"|[^"])*")    字符串 里面可以有// \"
     ([A-Z_a-z][A-Z_a-z0-9]*)   标识符
     ==
     <=
     >=
     &&
     \|\|                       短路符
     p{Punct}                   POSIX 字符类 表示标点符号
     */

    public static String regexpat =
            "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\n|[^\"])*\")|([A-Z_a-z][A-Z_a-z0-9]*)|==|<=|>=|&&|\\|\\||\\p{Punct})";

    private Pattern pattern = Pattern.compile(regexpat);
    private ArrayList<Token> queue = new ArrayList<>();
    private boolean hasMore;
    private LineNumberReader reader;

    public Lexer(Reader reader) {
        hasMore = true;
        this.reader = new LineNumberReader(reader);
    }

    public Token read(){
        if (fillQueue(0)){//如果true 意味着queue中还有
            return queue.remove(0);//拿出一个
        }else {
            return Token.EOF;
        }
    }
    public Token peek(int i){
        if (fillQueue(i)){
            return queue.get(i);
        }
        return Token.EOF;
    }

    /**
     * 向queue中填充i个token 如果已经没有可读的了 false
     */
    //很精妙
    private boolean fillQueue(int i) {
        while (i>=queue.size()){
            if (hasMore){
                readLine();
            }else {
                return false;
            }
        }
        return true;
    }


    private void readLine() throws ParseException {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new ParseException(e);
        }
        if (line == null) {
            hasMore = false;
            return;
        }

        int lineNo = reader.getLineNumber();
        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);

        int pos = 0;
        int endPos = line.length();
//关键逻辑
        while (pos < endPos) {
            matcher.region(pos, endPos);//设定匹配范围
            if (matcher.lookingAt()){//匹配到
                addToken(lineNo, matcher);
                pos = matcher.end();//设定新的起始点
            } else {//有无法匹配的就抛出异常

                throw new ParseException("无法解析此行 " + lineNo);
            }
        }
        queue.add(new IdToken(lineNo, Token.EOL));//解析完一行 增加一个EOL
    }

    private void addToken(int lineNo, Matcher matcher) {
        String m=matcher.group(1);
        if (m!=null)//整个匹配有命中
        {
            if (matcher.group(2)==null)//不是注释
            {
                Token token;
                if (matcher.group(3)!=null){//是数字
                    token=new NumToken(lineNo,Integer.parseInt(m));
                }else if (matcher.group(4)!=null){//是字符串
                    token=new StrToken(lineNo,toStringliteral(m));
                }else {
                    token=new IdToken(lineNo,m);
                }
                queue.add(token);
            }
        }

    }

    //正则捕获的是 "wwea"的形式 去掉 "" 同时处理一些转义字符
    private String toStringliteral(String m) {
        StringBuilder sb=new StringBuilder();
        int len=m.length()-2;//去掉""后的长度
        for (int i=1;i<=len;i++){
            char c=m.charAt(i);
            if (c=='\\'&&(i+1)<=len){//有转义字符
                char c2=m.charAt(i+1);
                if (c2=='\\'||c2=='"')//去掉转义的 \\=>\ \"=>"
                {
                    i++;
                    c=m.charAt(i);
                }else  if (c2=='n'){//将字符串\n 变成真正的换行符
                    i++;
                    c='\n';

                }
            }
            sb.append(c);
        }
        return sb.toString();
    }


}
