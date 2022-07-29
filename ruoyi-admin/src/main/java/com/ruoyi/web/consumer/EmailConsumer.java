package com.ruoyi.web.consumer;

import com.google.code.kaptcha.Producer;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.EmailLoginVo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.web.conf.mail.MailService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author chenyue7@foxmail.com
 * @date 2022/7/29
 * @description:
 */
@Service
@RocketMQMessageListener(topic = "${rocketmq.consumer.topic}", consumerGroup = "${rocketmq.consumer.group}")
public class EmailConsumer implements RocketMQListener<EmailLoginVo> {

    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MailService mailService;

    // 注入TemplateEngine
    @Autowired
    TemplateEngine templateEngine;

    @Override
    public void onMessage(EmailLoginVo emailLoginVo) {
        logger.info("消费邮箱信息 email:{}", emailLoginVo.getEmail());
        String verifyKey = CacheConstants.CAPTCHA_EMAIL_CODE_KEY + emailLoginVo.getUuid();  // 缓存key值
        // 生成验证码
        String code = captchaProducer.createText();  // 验证码字符串
        // 发件人要跟yml配置文件里填写的邮箱一致
        String mailFrom = "2511006757@qq.com";
        // 收件人
        String mailTo = emailLoginVo.getEmail();
        // 抄送
        String cc = "";
        Context context = new Context();
        context.setVariable("verificationCode", code);
        context.setVariable("min", "5");
        String content = templateEngine.process("verification.html", context);
        String result = mailService.sendHtmlMailThymeLeaf(mailFrom, "Loki", mailTo, cc, "LFS日志填报系统", content);

        // 缓存验证码字符串，默认5分钟过期
        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EMAIl_EXPIRATION, TimeUnit.MINUTES);
    }
}
