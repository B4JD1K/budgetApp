package com.budget.application.response.provider;

import com.budget.application.model.Tag;

import java.util.List;

public class TagsList {
    private List<Tag> tags;

    public TagsList(){
        super();
    }

    public TagsList(List<Tag> tags) {
        super();
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
