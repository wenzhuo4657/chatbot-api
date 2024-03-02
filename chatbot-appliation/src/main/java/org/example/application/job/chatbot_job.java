package org.example.application.job;


import org.example.domain.zsxq.ZsxqApi;
import org.example.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import org.example.domain.zsxq.model.vo.Talk;
import org.example.domain.zsxq.model.vo.Topics;
import org.example.domain.zsxq.utils.chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@EnableScheduling
@Configuration
public class chatbot_job {

    private Logger logger= LoggerFactory.getLogger(chatbot_job.class);


    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;


    @Value("${chatbot-api.username}")
    private String userName;
    @Resource
    private ZsxqApi zsxqApi;


    private final static HashSet<String> map=new HashSet<>();


    @Scheduled(cron = "0/5 * * * * ?")
    public void run() {
//        随机打烊
        if (new Random().nextBoolean()){
            logger.info("随机打烊------");
        }
//        1，获取主题列表
        UnAnsweredQuestionsAggregates aggregates=
                null;
        try {
            aggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId,cookie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("测试结果：{}",aggregates);

//        检索指定用户发表的主题
        List<Topics> list=aggregates.getResp_data().getTopics();
        Topics topic=new Topics();
        String test="null";
        for (Topics topics:list){
            if (!Objects.isNull(topics)){
                String topicsId= String.valueOf(topics.getTopic_id());
                Talk talk= topics.getTalk();
                test=talk.getText();
                if (test!="null"&&talk.getOwner().getName().equals(userName)&&map.add(test)){
                    logger.info("topicId：{} text：{}", topicsId, test);
                    topic=topics;
                    break;
                }
            }
        }


//        3，，获取新问题的回复
           String  data=chat.chatAi(test);

//       4， 发送
        try {
            zsxqApi.answer(groupId,cookie,topic.getTopic_id(),data,true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }






}
