package com.example.demo.model.properties;

import com.example.demo.model.request.PostVisibility;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

@ConfigurationProperties(prefix = "settings.posts") // "settings.post" ez az application.propertiesben van
public class PostProperties {

    private String slugPrefix;

    private PostVisibility  visibility = PostVisibility.NONE;

    private List<String> topics;

    private PublishingProperties publishing;

    public PublishingProperties getPublishing() {
        return publishing;
    }

    public void setPublishing(PublishingProperties publishing) {
        this.publishing = publishing;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getSlugPrefix() {
        return slugPrefix;
    }

    public void setSlugPrefix(String slugPrefix) {
        this.slugPrefix = slugPrefix;
    }

    public PostVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(PostVisibility visibility) {
        this.visibility = visibility;
    }

    public static class PublishingProperties {
        private Duration delay;

        private String status;

        public Duration getDelay() {
            return delay;
        }

        public void setDelay(Duration delay) {
            this.delay = delay;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
