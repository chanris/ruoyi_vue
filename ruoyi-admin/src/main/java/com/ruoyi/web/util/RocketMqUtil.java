package com.ruoyi.web.util;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.EmailLoginVo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.web.conf.mail.MailService;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import javax.annotation.Resource;

/**
 * @author chenyue7@foxmail.com
 * @date 2022/7/29
 * @description:
 */
@Component
public class RocketMqUtil {

    private static final Logger logger = LoggerFactory.getLogger(RocketMqUtil.class);

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private RedisCache redisCache;

    @Value("${rocketmq.consumer.topic}")
    private String emailTopic;

    public AjaxResult syncSendEmail(EmailLoginVo emailLoginVo){
        logger.info("发送邮箱消息 email: {}", emailLoginVo.getEmail());
        AjaxResult ajax = AjaxResult.success();
        String uuid = emailLoginVo.getUuid();
        emailLoginVo.setUuid(uuid);
        SendResult sendResult = rocketMQTemplate.syncSend(emailTopic, emailLoginVo);
        if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
            ajax.put("uuid", uuid);
            return ajax;
        }
        return AjaxResult.error("发送邮件失败");
    }
}
