package com.hoo.universe.adapter.out.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hoo.universe.adapter.out.persistence.config.HibernateCustomNamingStrategy;
import com.hoo.universe.adapter.out.persistence.config.PointDeserializer;
import com.hoo.universe.adapter.out.persistence.config.PointSerializer;
import com.hoo.universe.adapter.out.persistence.repository.*;
import com.hoo.universe.domain.vo.Point;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EntityScan
public class PersistenceConfig {

    @Bean
    JpaCommandAdapter jpaCommandAdapter(
            UniverseJpaRepository universeJpaRepository,
            SpaceJpaRepository spaceJpaRepository,
            PieceJpaRepository pieceJpaRepository,
            SoundJpaRepository soundJpaRepository,
            CategoryJpaRepository categoryJpaRepository,
            TagJpaRepository tagJpaRepository,
            OutlineSerializer outlineSerializer
    ) {
        return new JpaCommandAdapter(
                universeJpaRepository,
                spaceJpaRepository,
                pieceJpaRepository,
                soundJpaRepository,
                categoryJpaRepository,
                tagJpaRepository,
                outlineSerializer
        );
    }

    @Bean
    JpaQueryAdapter jpaQueryAdapter(
            UniverseJpaRepository universeJpaRepository,
            PieceJpaRepository pieceJpaRepository,
            CategoryJpaRepository categoryJpaRepository,
            PersistenceMapper persistenceMapper,
            OutlineSerializer outlineSerializer
    ) {
        return new JpaQueryAdapter(
                universeJpaRepository,
                pieceJpaRepository,
                categoryJpaRepository,
                persistenceMapper,
                outlineSerializer
        );
    }

    @Bean
    PersistenceMapper persistenceMapper(OutlineSerializer outlineSerializer) {
        return new PersistenceMapper(outlineSerializer);
    }

    @Bean
    OutlineSerializer outlineSerializer(ObjectMapper persistenceObjectMapper) {
        return new OutlineSerializer(persistenceObjectMapper);
    }

    @Bean
    ObjectMapper persistenceObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(Point.class, new PointDeserializer());
        module.addSerializer(Point.class, new PointSerializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }

    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new HibernateCustomNamingStrategy();
    }
}
