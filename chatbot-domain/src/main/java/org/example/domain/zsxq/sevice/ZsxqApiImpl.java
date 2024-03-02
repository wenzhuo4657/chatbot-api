package org.example.domain.zsxq.sevice;



import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.example.domain.zsxq.ZsxqApi;
import org.example.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import org.example.domain.zsxq.model.req.AnswerReq;
import org.example.domain.zsxq.model.req.ReqData;
import org.example.domain.zsxq.model.res.AnswerRes;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ZsxqApiImpl implements ZsxqApi {




    private Logger logger= LoggerFactory.getLogger(ZsxqApiImpl.class);
    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {
        CloseableHttpClient client= HttpClientBuilder.create().build();

        HttpGet get=new HttpGet("https://api.zsxq.com/v2/groups/"+groupId+"/topics?scope=all&count=20");
        get.addHeader("cookie",cookie);
        get.addHeader("Content-Type","application/json, text/plain, */*");


        CloseableHttpResponse response=client.execute(get);
        System.out.println("======start");

        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String res= EntityUtils.toString(response.getEntity());
            GsonBuilder gsonBuilder = new GsonBuilder();
            // 设置日期转换格式
            gsonBuilder.setDateFormat("yyyy-MM--dd");
            Gson gson = gsonBuilder.create();

            return gson.fromJson(res, UnAnsweredQuestionsAggregates.class);

        }else {
            System.out.println(response.getStatusLine().getStatusCode());
            return null;
        }


    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {
        if (text.isEmpty()){
            return false;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        // 设置日期转换格式
        gsonBuilder.setDateFormat("yyyy-MM--dd");
        Gson gson = gsonBuilder.create();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(
                "https://api.zsxq.com/v2/topics/"+topicId+"/comments");
        post.addHeader("cookie", cookie);
        post.addHeader("Content-Type", "application/json; charset=UTF-8");
        post.addHeader("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 Edg/123.0.0.0");

        /* 测试数据
          String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"自己去百度！\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";
         */

        AnswerReq answerReq = new AnswerReq(new ReqData(text, silenced));
        String paramJson = gson.toJson(answerReq);

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答问题结果。groupId：{} topicId：{} jsonStr：{}", groupId, topicId, jsonStr);
            AnswerRes answerRes =gson.fromJson(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();
        } else {
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }

    }
}
