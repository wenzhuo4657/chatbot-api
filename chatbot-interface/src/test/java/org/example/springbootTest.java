package org.example;


import org.example.domain.zsxq.ZsxqApi;
import org.example.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import org.example.domain.zsxq.model.vo.Talk;
import org.example.domain.zsxq.model.vo.Topics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class springbootTest {
    private Logger logger= LoggerFactory.getLogger(springbootTest.class);


    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;


    @Value("${chatbot-api.username}")
    private String userName;


    @Resource
    private ZsxqApi zsxqApi;

    @Test
    public void test() throws IOException {
        UnAnsweredQuestionsAggregates aggregates=
            zsxqApi.queryUnAnsweredQuestionsTopicId(groupId,cookie);
        logger.info("测试结果：{}",aggregates);
        List<Topics> list=aggregates.getResp_data().getTopics();
        for (Topics topics:list){
            if (!Objects.isNull(topics)){
                String topicsId= String.valueOf(topics.getTopic_id());
                Talk talk= topics.getTalk();
                String test="null";
                test=talk.getText();
                if (test!="null"&&talk.getOwner().getName().equals(userName)){
                    logger.info("topicId：{} text：{}", topicsId, test);
                    zsxqApi.answer(groupId,cookie,topicsId,test,true);
                    break;
                }
            }
        }
    }
}
