package com.endeymus.parser.util;

import com.endeymus.parser.entity.*;
import it.tdlight.jni.TdApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Mark Shamray
 */
@Component("helper")
public class SaveHelper {

    @Autowired
    private ContentParser contentParser;

    public Channel saveMono(TdApi.Chat chat) {
        Channel channel = new Channel();
        channel.setIdInternal(chat.id);
        channel.setName(chat.title);
        return channel;
    }
    public Channel saveMono(TdApi.Chat chat, Channel idParentChannel) {
        Channel channel = new Channel();
        channel.setIdInternal(chat.id);
        channel.setName(chat.title);
        channel.setIdParentChannel(idParentChannel);
        return channel;
    }
    public Client saveMono(TdApi.User user) {
        Client client = new Client();
        client.setIdInternal((long) user.id);
        client.setPhone(user.phoneNumber);
        client.setName(user.username);
        return client;
    }

    public Posts saveMono(TdApi.Message message) {
        Posts posts = new Posts();
        PostType type = contentParser.getType(message.content);
        posts.setIdInternal(message.id);
        posts.setContent(contentParser.getContent(message.content));
        posts.setDate(Date.from(Instant.ofEpochSecond(message.date)));
        posts.setType(type);
        posts.setCountViews(message.interactionInfo == null ? 0 : message.interactionInfo.viewCount);
        posts.setCountForward(message.interactionInfo == null ? 0 : message.interactionInfo.forwardCount);
        return posts;
    }



}
