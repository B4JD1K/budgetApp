package com.budget.application.service;

import com.budget.application.model.Tag;
import com.budget.application.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag createTag(String tagName) {
        return tagRepository.save(new Tag(tagName));
    }

    @Override
    public Optional<List<Tag>> getAllTags() {
        List<Tag> allTags = tagRepository.findAll();
        return Optional.of(allTags);
    }

    @Override
    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }

    @Override
    public Optional<Tag> getTagByName(String tagName) {
        Tag foundTag = null;
        List<Tag> foundByName = tagRepository.findByName(tagName);
//        if (foundByName.size() > 0) {}
        if (!foundByName.isEmpty()) {
            foundTag = foundByName.get(0);
        }
        return Optional.of(foundTag);
    }
}
