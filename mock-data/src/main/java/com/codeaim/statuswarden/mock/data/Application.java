package com.codeaim.statuswarden.mock.data;

import com.codeaim.statuswarden.common.constant.Role;
import com.codeaim.statuswarden.common.model.Monitor;
import com.codeaim.statuswarden.common.model.MonitorEvent;
import com.codeaim.statuswarden.common.model.Status;
import com.codeaim.statuswarden.common.model.User;
import com.codeaim.statuswarden.common.repository.MonitorEventRepository;
import com.codeaim.statuswarden.common.repository.MonitorRepository;
import com.codeaim.statuswarden.common.repository.UserRepository;
import com.github.javafaker.Faker;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = MonitorRepository.class)
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public MonitorRepository monitorRepository;

    @Autowired
    public MonitorEventRepository monitorEventRepository;

    private Random random = new Random();
    private Faker faker = new Faker();

    @Override
    public void run(String... args) throws Exception {
        for(int i = 0; i < 10; i++) {
            User newUser = userRepository.save(User
                    .builder()
                    .email(faker.internet().emailAddress())
                    .name(faker.name().fullName())
                    .accessToken(UUID.randomUUID().toString())
                    .password(UUID.randomUUID().toString())
                    .resetToken(UUID.randomUUID().toString())
                    .emailVerified(true)
                    .roles(Collections.singletonList(Role.USER))
                    .build());

            for(int j = 0; j < 10; j++) {
                Monitor newMonitor = monitorRepository.save(Monitor
                        .builder()
                        .userId(newUser.getId())
                        .name(faker.company().name())
                        .url(faker.internet().url())
                        .interval(getInterval())
                        .build());

                int uptime = random.nextInt(100) + 1;
                MonitorEvent previousMonitorEvent = null;
                for(int k = 0; k < 10080; k++) {
                    Status status = getStatus(uptime);
                    previousMonitorEvent = monitorEventRepository.save(MonitorEvent
                            .builder()
                            .changed(previousMonitorEvent == null || status != previousMonitorEvent.getStatus())
                            .confirmation(previousMonitorEvent != null && previousMonitorEvent.isChanged())
                            .monitorId(newMonitor.getId())
                            .previous(previousMonitorEvent != null ? previousMonitorEvent.getId() : null)
                            .responseTime(random.nextInt(10000) + 1)
                            .scheduler(faker.country().name())
                            .statusCode(status == Status.UP ? 200 : 500)
                            .status(status)
                            .build());
                }
            }
        }
    }

    private Status getStatus(int uptime) {
        int status = random.nextInt(100) + 1;
        if(status <= uptime) {
            return Status.UP;
        } else {
            return Status.DOWN;
        }
    }

    private int getInterval() {
        int distribution = random.nextInt(100) + 1;
        if (distribution <= 30)
            return 1;
        else if (distribution <= 70)
            return 5;
        else
            return random.nextInt(120) + 1;
    }
}
