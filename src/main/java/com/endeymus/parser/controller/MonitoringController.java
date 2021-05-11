package com.endeymus.parser.controller;

import com.endeymus.parser.entity.Channel;
import com.endeymus.parser.entity.Monitoring;
import com.endeymus.parser.entity.MonitoringSettings;
import com.endeymus.parser.entity.User;
import com.endeymus.parser.model.telegram.tdlib.Example;
import com.endeymus.parser.repository.ChannelRepository;
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
    private Example example;


    @GetMapping("/")
    public String controllers(@ModelAttribute("chats") ArrayList<Monitoring> chat, Model model) {
        model.addAttribute("title", "Страница списка мониторинга");
        model.addAttribute("chats", chat == null ? new ArrayList<Monitoring>() : chat);
        return "user/monitoring";
    }

    @SneakyThrows
    @PostMapping("/")
    public String upload(@RequestParam("chat") List<String> chats, @RequestParam("checkbox") List<String> checkBox) {
        User user = userService.findOne(1L);
        for (int i = 0; i < chats.size(); i++) {
            /*String chat = chats.get(i);
            TdApi.Chat tdChat = joinAndGet(chat);
            Channel channel = channelService.findByIdInternal(tdChat.id);
            if (channel == null) {
                channel = saveHelper.saveMono(tdChat);
                channelService.save(channel);
            }
            channel = channelService.findByIdInternal(tdChat.id);
            Monitoring monitoring = new Monitoring();
            monitoring.setIdUser(user);
            monitoring.setIdChannel(channel);

            MonitoringSettings settings = new MonitoringSettings();
            settings.setNeedAnalytic(checkBox.get(i).equals("1"));
            monitoring.setSettings(settings);

            monitoringService.save(monitoring);*/
            System.out.println(chats.get(i));
        }
        System.out.println("CheckBox:");
        for (String box : checkBox) {
            System.out.println(box);
        }
        return "user/monitoring";
    }
    @PostMapping("/upload/{id}")
    public String fileUpload(@PathVariable String id, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        List<String> lines = reader.lines().collect(Collectors.toUnmodifiableList());

        List<Monitoring> monitoringList = new ArrayList<>();
        lines.forEach(x->{
            Monitoring monitoring = new Monitoring();
            MonitoringSettings settings = new MonitoringSettings();

            String[] split = x.split(";");

            Channel channel = new Channel();
            channel.setName(split[0]);

            monitoring.setIdChannel(channel);
            settings.setNeedAnalytic(split[1].equals("1"));
            monitoring.setSettings(settings);

            monitoringList.add(monitoring);
        });

        redirectAttributes.addFlashAttribute("chats", monitoringList);
        reader.close();
        return "redirect:../"+id;
    }

    @SneakyThrows
    private TdApi.Chat joinAndGet(String name) {
        Long idChat = example.getChatIdByName(name);
        example.joinChat(idChat);
        return example.getChat(example.getChatIdByName(name));
    }
}
