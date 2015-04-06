package com.cc.lmsfc.common.test;

import com.cc.lmsfc.common.util.HttpClientUtil;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.util.ParserException;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by tomchen on 15-3-14.
 */
public class HttpClientTest {

    @Test
    public void test1() throws IOException, ParserException {

        byte[] bytes = HttpClientUtil.httpGet("http://www.cnblogs.com/shenliang123/archive/2012/08/28/2660705.html");
        String htmlStr = new String(bytes,"UTF-8");
        System.out.println(htmlStr);

        Parser parser = new Parser();
        parser.setEncoding("UTF-8");
        parser.setResource(htmlStr);

        NodeFilter f1 = new HasAttributeFilter("id","cb_post_title_url");
//        NodeFilter f2 = new RegexFilter("/<a\\s.*?href=\\\"([^\\\"]+)\\\"[^>]*>(.*?)<\\/a>/");
        NodeFilter f2 = new RegexFilter("<a\\s*href=\"?([\\w\\W]*?)\"?[\\s]*?[^>]>([\\s\\S]*?)(?=</a>)");
        NodeFilter f3 = new HasParentFilter(f1);

//        String title = parser.parse(f1).toHtml();
//        System.out.println("title:" + title);
//        parser.setResource(title);
        String titleContent = parser.parse(f3).toHtml();

        System.out.println("title content:" + titleContent);

    }

    @Test
    public void test2() throws IOException, ParserException {

        byte[] bytes = HttpClientUtil.httpGet("http://www.cnblogs.com/shenliang123/archive/2012/08/28/2660705.html");
        String htmlStr = new String(bytes,"UTF-8");
//        System.out.println(htmlStr);

        Parser parser = new Parser();
        parser.setEncoding("UTF-8");
        parser.setResource(htmlStr);

        NodeFilter f1 = new TagNameFilter("div");
        NodeFilter f2 = new HasAttributeFilter("class","entry-content");
        NodeFilter f3 = new AndFilter(f1,f2);
        NodeFilter f4 = new HasParentFilter(f3);

        System.out.println("content:" +parser.parse(f4).toHtml());


    }

    @Test
    public void test3() throws IOException, ParserException {

        byte[] bytes = HttpClientUtil.httpGet("http://www.cnblogs.com/huang0925/p/3556848.html");
        String htmlStr = new String(bytes,"UTF-8");
//        System.out.println(htmlStr);

        Parser parser = new Parser();
        parser.setEncoding("UTF-8");
        parser.setResource(htmlStr);

        NodeFilter f1 = new TagNameFilter("link");
        NodeFilter f2 = new HasAttributeFilter("type","text/css");
        NodeFilter f3 = new AndFilter(f1,f2);

        System.out.println("content:" +parser.parse(f3).toHtml());


    }

    @Test
    public void testCSDN() throws IOException {
        byte[] bytes = HttpClientUtil.httpGet("http://blog.csdn.net/walkerjong/article/details/7210727");
        System.err.println(new String(bytes,"UTF-8"));
    }
}
