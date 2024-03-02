package org.example.domain.zsxq.utils;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class chat {
    public static  String  chatAi(String text){
        if (text.isEmpty()){
            return "";
        }
        String url = "http://43.153.57.34:9000/openai/foreign/chat/question?prompt="+text+"&temperature=2";
        System.out.println("提出问题："+text);
        Connection connect = Jsoup.connect(url);//向gpt中发送信息
        String data="";
        //内容 在我们的连接中
        try {
            String body = connect.ignoreContentType(true).execute().body();
            JSONObject jsonObject = JSONObject.parseObject(body);
            data = (String)jsonObject.get("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("gpt的回答："+data);

        return data;
    }
}
