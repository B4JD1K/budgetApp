package com.budget.application.service;

import com.budget.application.model.Tag;
import com.budget.application.repository.TagRepository;
import com.budget.application.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;

    private TestUtils testUtils;

    private List<Tag> allGeneratedTestTags;

    private String newTagTame;

    @BeforeEach
    void setUp() {
        testUtils = new TestUtils();
        allGeneratedTestTags = testUtils.generateTestTags(10, true);

        Tag generatedTag = allGeneratedTestTags.get(0);
        newTagTame = generatedTag.getName();
        Mockito.when(tagRepository.save(Mockito.any(Tag.class))).thenReturn(generatedTag);
        Mockito.when(tagRepository.findAll()).thenReturn(allGeneratedTestTags);
        Mockito.when(tagRepository.findByName(newTagTame)).thenReturn(Arrays.asList(generatedTag));
    }

    @Test
    void createTag() {
        Tag createdTag = tagService.createTag(newTagTame);
        assertNotNull(createdTag);
        assertEquals(createdTag.getName(), newTagTame);
    }

    @Test
    void getAllTags() {
        List<Tag> allTags = tagService.getAllTags().get();
        assertEquals(allTags.size(), allGeneratedTestTags.size());
    }

    @Test
    void deleteTag() {
        Tag tag = tagService.getAllTags().get().get(0);
        tagService.deleteTag(tag.getId());
        Optional<Tag> tagWhichShouldNotExist = tagRepository.findById(tag.getId());
        assertFalse(tagWhichShouldNotExist.isPresent());
    }

    @Test
    void getTagByName() {
        Tag retrievedTagByName = tagService.getTagByName(newTagTame).get();
        assertEquals(retrievedTagByName.getName(), newTagTame);
    }
}