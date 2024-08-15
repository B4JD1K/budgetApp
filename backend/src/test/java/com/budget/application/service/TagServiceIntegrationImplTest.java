package com.budget.application.service;

import com.budget.application.model.Tag;
import com.budget.application.repository.TagRepository;
import com.budget.application.utils.TestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class TagServiceIntegrationImplTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    private String tagName;

    private TestUtils testUtils;

    @BeforeEach
    void setUp() {
        testUtils = new TestUtils();
        tagName = testUtils.getRandomTextFromUUID();
        for (int i = 0; i < 10; i++) {
            tagService.createTag(testUtils.getRandomTextFromUUID());
        }
    }

    @Test
    void createTag() {
        Tag createdTag = tagService.createTag(tagName);
        System.out.println("tagName = " + tagName);
        System.out.println("createdTag.getName() = " + createdTag.getName());
        assertEquals(createdTag.getName(), tagName);
    }

    @Test
    void getAllTags() {
        assertTrue(tagService.getAllTags().get().size() > 0);
        assertTrue(tagService.getAllTags().get().size() >= 10);
        System.out.println("Ilość tagów >= 10 wynosi " + tagService.getAllTags().get().size());
    }

    @Test
    void deleteTag() {
        Long retrievedTagId = tagService.getAllTags().get().get(0).getId();
        tagService.deleteTag(retrievedTagId);
        Optional<Tag> foundById = tagRepository.findById(retrievedTagId);
        System.out.println("foundById.isPresent() = " + foundById.isPresent());
        assertFalse(foundById.isPresent());
    }

    @Test
    void getTagByName() {
        String retrievedTagName = tagService.getAllTags().get().get(0).getName();
        Optional<Tag> retrievedTagByName = tagService.getTagByName(retrievedTagName);
        assertTrue(retrievedTagByName.isPresent());
        System.out.println("retrievedTagByName.get().getName() = " + retrievedTagByName.get().getName());
        System.out.println("retrievedTagName = " + retrievedTagName);
        assertEquals(retrievedTagByName.get().getName(), retrievedTagName);
    }
}