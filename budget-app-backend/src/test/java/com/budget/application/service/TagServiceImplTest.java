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

import java.util.List;

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
        testUtils.generateTestTags(10, true);

        Mockito.when(tagRepository.save(Mockito.any(Tag.class))).thenReturn(null);
    }

    @Test
    void createTag() {
        fail("Not implemented yet");
    }

    @Test
    void getAllTags() {
        fail("Not implemented yet");
    }

    @Test
    void deleteTag() {
        fail("Not implemented yet");
    }

    @Test
    void getTagByName() {
        fail("Not implemented yet");
    }
}