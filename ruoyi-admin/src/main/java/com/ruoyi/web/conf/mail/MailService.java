package com.ruoyi.web.conf.mail;

import java.io.File;
import java.util.List;

public interface MailService {

    void sendSimpleMail(String mailFrom, String mailFromNick, String mailTo, String cc, String subject, String content);

    void sendMailWithAttachments(String mailFrom, String mailFromNick, String mailTo, String cc, String subject, String content,
                                 List<File> files);

    void sendMailWithImage(String mailFrom, String mailFromNick, String mailTo, String cc, String subject, String content,
                           String[] imagePaths, String[] imageId);

    String sendHtmlMailThymeLeaf(String mailFrom, String mailFromNick, String mailTo, String cc, String subject, String content);

}
