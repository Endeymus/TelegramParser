package com.endeymus.parser.controller;

import com.endeymus.parser.entity.PostType;
import com.endeymus.parser.entity.Posts;
import com.endeymus.parser.model.telegram.tdlib.Example;
import com.endeymus.parser.util.ContentParser;
import it.tdlight.common.utils.CantLoadLibrary;
import it.tdlight.jni.TdApi;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mark Shamray
 */
@RestController
@RequestMapping("")
public class MainController {

    @Autowired
    private Example example;

    @Autowired
    private ContentParser contentParser;


    @SneakyThrows
    @GetMapping("/chats")
    public NavigableSet<Example.OrderedChat> chats() {
        return example.getMainChatList();
    }

    @SneakyThrows
    @GetMapping("/chatsname")
    public Map<Long, String> chatsWithName() {
        Map<Long, String> map = new ConcurrentHashMap<>();
        example.getMainChatList().forEach(x-> map.put(x.getChatId(), example.getChats().get(x.getChatId()).title));
        return map;
    }

    @SneakyThrows
    @GetMapping("/chat/{id}")
    public TdApi.Chat chat(@PathVariable String id) {
        return example.getChat(Long.parseLong(id));
    }

    @GetMapping("/lastmessage/{id}")
    public TdApi.Message getLastMessage(@PathVariable String id) {
        return example.getLastMessage(Long.parseLong(id));
    }

    @GetMapping("/messages/{id}")
    public List<Posts> messages(@PathVariable String id) {
        try {
            return example.messages(Long.parseLong(id));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @GetMapping("/type/{id}")
    public PostType getType(@PathVariable String id) {
        TdApi.MessageContent content = example.getContentLastMessage(Long.parseLong(id));
        System.out.println(content);
        return contentParser.getType(content);
    }
    @GetMapping("/content/{id}")
    public String content(@PathVariable String id) {
        TdApi.MessageContent content = example.getContentLastMessage(Long.parseLong(id));
        System.out.println(content);
        return contentParser.getContent(content);
    }
}
