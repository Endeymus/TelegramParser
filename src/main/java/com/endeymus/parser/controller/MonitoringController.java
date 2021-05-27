package com.endeymus.parser.controller;

import com.endeymus.parser.entity.Channel;
import com.endeymus.parser.entity.Monitoring;
import com.endeymus.parser.entity.MonitoringSettings;
import com.endeymus.parser.entity.User;
import com.endeymus.parser.model.TelegramAPI;
import com.endeymus.parser.service.ChannelService;
import com.endeymus.parser.service.MonitoringService;
import com.endeymus.parser.service.UserService;
import com.endeymus.parser.util.SaveHelper;
import it.tdlight.jni.TdApi;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mark Shamray
 */
@Controller
@RequestMapping("/monitoring")
public class MonitoringController {

    @Autowired
    private SaveHelper saveHelper;
    @Autowired
    private MonitoringService monitoringService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private UserService userService;

    @Autowired
    private TelegramAPI telegramAPI;


    @GetMapping({"/", ""})
    public String controllers(@ModelAttribute("chats") ArrayList<Monitoring> chat, Model model) {
        model.addAttribute("title", "Страница списка мониторинга");
        model.addAttribute("chats", chat == null ? new ArrayList<Monitoring>() : chat);
        return "user/monitoring";
    }

    @SneakyThrows
    @PostMapping("/")
    public String upload(@RequestParam("chat") List<String> chats, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        for (String chat : chats) {
            TdApi.Chat tdChat = joinAndGet(chat);
            Channel channel = channelService.findByIdInternal(tdChat.id);
            if (channel == null) {
                channel = saveHelper.saveMono(tdChat);
                channelService.save(channel);
            }
            channel = channelService.findByIdInternal(tdChat.id);

            Monitoring monitoring = monitoringService.findByUserAndChannel(channel.getId(), user.getId());
            if (monitoring == null) {
                monitoring = new Monitoring();
                monitoring.setIdUser(user);
                monitoring.setIdChannel(channel);

                MonitoringSettings settings = new MonitoringSettings();
                settings.setNeedAnalytic(false);
                settings.setIdMonitoring(monitoring);
                monitoring.setSettings(settings);

                monitoringService.save(monitoring);
            }
        }
        return "user/monitoring";
    }
    @PostMapping("/upload/")
    public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        List<String> lines = reader.lines().collect(Collectors.toUnmodifiableList());

        List<Monitoring> monitoringList = new ArrayList<>();
        lines.forEach(x->{
            Monitoring monitoring = new Monitoring();

            Channel channel = new Channel();
            channel.setName(x);

            monitoring.setIdChannel(channel);

            monitoringList.add(monitoring);
        });

        redirectAttributes.addFlashAttribute("chats", monitoringList);
        reader.close();
        return "redirect:../";
    }

    @SneakyThrows
    private TdApi.Chat joinAndGet(String name) {
        Long idChat = telegramAPI.getChatIdByName(name);
        telegramAPI.joinChat(idChat);
        return telegramAPI.getChat(telegramAPI.getChatIdByName(name));
    }
}
