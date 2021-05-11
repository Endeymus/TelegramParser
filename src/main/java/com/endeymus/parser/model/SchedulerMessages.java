package com.endeymus.parser.model;

import com.endeymus.parser.entity.Monitoring;
import com.endeymus.parser.entity.Posts;
import com.endeymus.parser.entity.User;
import com.endeymus.parser.model.telegram.tdlib.Example;
import com.endeymus.parser.service.PostsService;
import com.endeymus.parser.service.UserService;
import com.endeymus.parser.util.SaveHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Mark Shamray
 */
@Component
public class SchedulerMessages {

    private static final long SECOND = 1000L;
    private static final long MINUTE = 60000L;
    private static final long HOUR = 3600000L;
    private static final long DAY = 86400000L;


    @Autowired
    private Example example;
    @Autowired
    private UserService userService;
    @Autowired
    private PostsService postsService;


//    @Scheduled(fixedRate = MINUTE)
    public void schedule() {
        System.out.println("Start schedule");
        List<User> userList = userService.findAllWithDetails();
        userList.forEach(user -> {
            List<Monitoring> monitoringList = user.getMonitoringList()
                    .stream()
//                    .filter(monitoring -> monitoring.getSettings().isNeedAnalytic())
                    .collect(Collectors.toUnmodifiableList());
            monitoringList.forEach(monitoring -> {
                System.out.println("Рассписание: " + monitoring.getIdChannel().getName());
                List<Posts> messages;
                try {
                    messages = example.messages(monitoring.getIdChannel().getIdInternal());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    messages = Collections.emptyList();
                }
                messages.forEach(posts -> {
                    if (!notExist(posts.getIdInternal())) {
                        Posts changedPost = postsService.findByIdInternal(posts.getIdInternal());
                        posts.setId(changedPost.getId());
                    }
//                    postsService.save(posts);
                    System.out.println(posts);
                });
            });
        });
    }

    private boolean notExist(Long idInternal) {
        return postsService.findByIdInternal(idInternal) == null;
    }
}
