package com.codeaim.statuswarden.scheduler;

import com.codeaim.statuswarden.common.model.Monitor;
import com.codeaim.statuswarden.common.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories(basePackageClasses = MonitorRepository.class)
public class Application implements CommandLineRunner
{
    @Autowired
    private MonitorRepository monitorRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        monitorRepository.deleteAll();
        monitorRepository.save(Monitor.builder().name("Google").url("http://www.google.com").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Facebook").url("http://www.facebook.com").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Yahoo").url("http://www.yahoo.com").interval(1).build());
        monitorRepository.save(Monitor.builder().name("JSFiddle").url("http://jsfiddle.net/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Pluralsight").url("http://www.pluralsight.com/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("StackOverflow").url("http://stackoverflow.com/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("GitHub").url("https://github.com").interval(1).build());
        monitorRepository.save(Monitor.builder().name("The Morning Paper").url("http://blog.acolyer.org/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Domainsbot Hypermedia Search").url("http://www.domainsbot.com/d/hypermedia").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Netflix").url("http://www.netflix.com/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Songfacts").url("http://www.songfacts.com/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Codeplex").url("http://www.codeplex.com/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("SEOSiteCheckup").url("http://seositecheckup.com/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("RegexHero").url("http://regexhero.net/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("DigitalOcean").url("https://www.digitalocean.com/join/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Visual Studio Blog").url("http://blogs.msdn.com/b/visualstudio/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Jimmy R").url("http://www.jimmyr.com/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Billboard Article").url("http://www.billboard.com/articles/news/6706919/happy-birthday-song-public-domain-warner-chappel").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Imgur").url("http://imgur.com/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("You Betrayed Us Heroku").url("https://www.youbetrayedus.org/heroku/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("GitLab").url("https://about.gitlab.com/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("BBC News").url("http://www.bbc.co.uk/news").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Sky News").url("http://news.sky.com/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("The Mirror").url("http://www.mirror.co.uk/news/").interval(1).build());
        monitorRepository.save(Monitor.builder().name("Telegraph").url("http://www.telegraph.co.uk/news/").interval(1).build());
    }
}
