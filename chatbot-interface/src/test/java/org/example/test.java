package org.example;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class test {
        @Test
    public  void test() throws IOException {
            CloseableHttpClient client= HttpClientBuilder.create().build();

            HttpGet get=new HttpGet("https://api.zsxq.com/v2/groups/28885518425541/topics?scope=all&count=20");
            get.addHeader("cookie","zsxq_access_token=CE1C5400-A5AB-942E-7A4D-2C59AE124C6A_18A85AEAC6DAF17B; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22212548421584541%22%2C%22first_id%22%3A%2218a8ea08315c35-00852982006be0d-7f5d547e-1327104-18a8ea083168c0%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThhOGVhMDgzMTVjMzUtMDA4NTI5ODIwMDZiZTBkLTdmNWQ1NDdlLTEzMjcxMDQtMThhOGVhMDgzMTY4YzAiLCIkaWRlbnRpdHlfbG9naW5faWQiOiIyMTI1NDg0MjE1ODQ1NDEifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22212548421584541%22%7D%2C%22%24device_id%22%3A%2218a8ea08315c35-00852982006be0d-7f5d547e-1327104-18a8ea083168c0%22%7D; abtest_env=product; zsxqsessionid=d97998ba5947b90369551eb0cc450bec");
            get.addHeader("Content-Type","application/json, text/plain, */*");



            CloseableHttpResponse response=client.execute(get);
            System.out.println("======start");

            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String res= EntityUtils.toString(response.getEntity());
                System.out.println(res);

            }else {
                System.out.println(response.getStatusLine().getStatusCode());
            }
            System.out.println("=======end");
        }


        @Test
        public void answer() throws IOException {
            CloseableHttpClient client= HttpClientBuilder.create().build();

            HttpPost get=new HttpPost("https://api.zsxq.com/v2/topics/811185825524112/comments");
            get.addHeader("cookie","zsxq_access_token=CE1C5400-A5AB-942E-7A4D-2C59AE124C6A_18A85AEAC6DAF17B; abtest_env=product; zsxqsessionid=c54d30206f4db8b90843e0ff93728145; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22212548421584541%22%2C%22first_id%22%3A%2218a8ea08315c35-00852982006be0d-7f5d547e-1327104-18a8ea083168c0%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThhOGVhMDgzMTVjMzUtMDA4NTI5ODIwMDZiZTBkLTdmNWQ1NDdlLTEzMjcxMDQtMThhOGVhMDgzMTY4YzAiLCIkaWRlbnRpdHlfbG9naW5faWQiOiIyMTI1NDg0MjE1ODQ1NDEifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22212548421584541%22%7D%2C%22%24device_id%22%3A%2218a8ea08315c35-00852982006be0d-7f5d547e-1327104-");
            get.addHeader("Content-Type","application/json; charset=UTF-8");
            String n=" jflaskd";

            String res="{\n" +
                    "  \"req_data\": {\n" +
                    "    \"text\": \"这ye是回复"+n+"\\n\",\n" +
                    "    \"image_ids\": [],\n" +
                    "    \"mentioned_user_ids\": []\n" +
                    "  }\n" +
                    "}";

            StringEntity entity=new StringEntity(res, ContentType.create("text/json","UTF-8"));
            get.setEntity(entity);

            CloseableHttpResponse response=client.execute(get);
            System.out.println("======start");

            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String res1= EntityUtils.toString(response.getEntity());
                System.out.println(res);

            }else {
                System.out.println(response.getStatusLine().getStatusCode());
            }
            System.out.println("=======end");
        }






        }

