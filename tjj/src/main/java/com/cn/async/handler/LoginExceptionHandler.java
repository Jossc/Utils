package com.cn.async.handler;

import com.cn.async.EventHandler;
import com.cn.async.EventModel;
import com.cn.async.EventType;
import com.cn.model.Message;
import com.cn.service.MessageService;
import com.cn.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 安全控制
 */
@Component
public class LoginExceptionHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    MailSender mailSender;


    @Override
    public void doHandle(EventModel model) {
        // 判断是否有异常登陆
        Message message = new Message();
        message.setToId(model.getActorId());
        message.setContent("你上次的登陆ip异常");
        message.setFromId(3);
        message.setCreatedDate(new Date());
        messageService.addMessage(message);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", model.getExt("username"));
        mailSender.sendWithHTMLTemplate(model.getExt("email"), "登陆异常", "mails/welcome.html",
                map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
