package com.example.demo.model.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "settings.posts") // "settings.post" ez az application.propertiesben van
public class PostProperties {

    private String slugPrefix;

    private String  visibility;

    public String getSlugPrefix() {
        return slugPrefix;
    }

    public void setSlugPrefix(String slugPrefix) {
        this.slugPrefix = slugPrefix;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }


}
