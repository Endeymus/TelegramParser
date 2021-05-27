package com.endeymus.parser.controller;

import com.endeymus.parser.entity.*;
import com.endeymus.parser.model.ChannelPostInfo;
import com.endeymus.parser.model.TelegramAPI;
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

import java.security.Principal;
import java.util.List;
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


    @GetMapping("/")
    public String listMonitoring(Model model, Principal principal){
        // TODO: 08.05.2021 Добавить идентификатор пользователя в поиск через Security
        User user = userService.findByLogin(principal.getName());

        List<ChannelPostInfo> channels1 = user
                .getMonitoringList()
                .stream()
                .map(x->channelService.findOne(x.getIdChannel().getId()))
                .map(channel -> {
                    Posts lastPosts = postsService.findLastPosts(channel.getIdInternal());
                    if (lastPosts == null) {
                        lastPosts = new Posts();
                        lastPosts.setCountViews(0);
                        lastPosts.setCountForward(0);
                    }
                    MonitoringSettings monitoringSettings = monitoringService.findByUserAndChannel(channel.getId(), user.getId()).getSettings();
                    return new ChannelPostInfo(channel,
                            lastPosts,
                            monitoringSettings);
                })
                .collect(Collectors.toUnmodifiableList());
        model.addAttribute("channels", channels1);
        return "user/analytic";
    }

    @GetMapping("change/{id}")
    public String analyze(@PathVariable String id){
        Monitoring monitoring = monitoringService.findOne(Long.parseLong(id));
        MonitoringSettings settings = monitoring.getSettings();
        settings.setNeedAnalytic(!settings.isNeedAnalytic());
        monitoring.setSettings(settings);
        monitoringService.save(monitoring);
        return "redirect:../";
    }
    @GetMapping("info/{id}")
    public String info(@PathVariable String id, Model model){
        Channel channel = channelService.findOne(Long.parseLong(id));

        List<Posts> topPosts = postsService.findLastTopPosts(channel.getIdInternal());
        topPosts.forEach(posts -> posts.setShortContent(String.format("%.250s ...", posts.getContent())));
        Integer views = topPosts.stream().map(Posts::getCountViews).reduce(0, Integer::sum);
        Integer forwards = topPosts.stream().map(Posts::getCountForward).reduce(0, Integer::sum);

        model.addAttribute("posts", topPosts);
        model.addAttribute("channel", channel);
        model.addAttribute("views", views);
        model.addAttribute("forwards", forwards);
        return "user/info";
    }

    @ModelAttribute("title")
    public String modelAttribute() {
        return "Станица аналитических данных";
    }
}
