package com.hoo.universe.adapter.out.persistence.repository;

import com.hoo.universe.adapter.out.persistence.entity.TagJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagJpaRepository extends JpaRepository<TagJpaEntity, Long> {

    @Query("select t.tag from TagJpaEntity t where t.tag in :tags")
    List<String> findExistTags(List<String> tags);
}
