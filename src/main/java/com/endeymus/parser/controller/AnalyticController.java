package com.endeymus.parser.controller;

import com.endeymus.parser.entity.Channel;
import com.endeymus.parser.entity.Posts;
import com.endeymus.parser.entity.User;
import com.endeymus.parser.model.ChannelPostInfo;
import com.endeymus.parser.model.telegram.tdlib.Example;
import com.endeymus.parser.service.ChannelService;
import com.endeymus.parser.service.MonitoringService;
import com.endeymus.parser.service.PostsService;
import com.endeymus.parser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mark Shamray
 */
@Controller
@RequestMapping("/analytic")
public class AnalyticController {

    @Autowired
    private PostsService postsService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private MonitoringService monitoringService;

    @Autowired
    private Example tgClient;


    @GetMapping("/")
    public String analyze(Model model){
        // TODO: 08.05.2021 Добавить идентификатор пользователя в поиск через Security
        User user = userService.findOne(1L);
        List<Channel> channels = user
                .getMonitoringList()
                .stream()
                .map(x->channelService.findOne(x.getIdChannel().getId()))
                .collect(Collectors.toUnmodifiableList());

        List<ChannelPostInfo> channels1 = user
                .getMonitoringList()
                .stream()
                .map(x->channelService.findOne(x.getIdChannel().getId()))
                .map(channel -> new ChannelPostInfo(channel, postsService.findLastPosts(channel.getIdInternal())))
                .collect(Collectors.toUnmodifiableList());
        model.addAttribute("channels", channels);
        return "user/analytic";
    }

    @ModelAttribute("title")
    public String modelAttribute() {
        return "Станица аналитических данных";
    }
}
