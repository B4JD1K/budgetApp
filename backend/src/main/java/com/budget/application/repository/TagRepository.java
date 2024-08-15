package com.budget.application.repository;

import com.budget.application.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository <Tag, Long> {

    Optional<Tag> findById(Long id);

    Tag save(Tag tag);

    List<Tag> findAll();

    List<Tag> findByName(String name);
}
