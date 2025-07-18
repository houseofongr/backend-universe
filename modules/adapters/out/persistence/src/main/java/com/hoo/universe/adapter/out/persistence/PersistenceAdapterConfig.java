package com.hoo.universe.adapter.out.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hoo.universe.adapter.out.persistence.command.*;
import com.hoo.universe.adapter.out.persistence.config.PointDeserializer;
import com.hoo.universe.adapter.out.persistence.config.PointSerializer;
import com.hoo.universe.adapter.out.persistence.query.UniverseLoadAdapter;
import com.hoo.universe.adapter.out.persistence.query.UniverseQueryAdapter;
import com.hoo.universe.adapter.out.persistence.repository.*;
import com.hoo.universe.domain.vo.Point;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EntityScan
public class PersistenceAdapterConfig {

    @Bean
    UniverseQueryAdapter universePersistenceAdapter(
            UniverseJpaRepository universeJpaRepository,
            PieceJpaRepository pieceJpaRepository,
            CategoryJpaRepository categoryJpaRepository,
            UniverseMapper universeMapper
    ) {
        return new UniverseQueryAdapter(
                universeJpaRepository,
                pieceJpaRepository,
                categoryJpaRepository,
                universeMapper
        );
    }

    @Bean
    UniverseEventHandlerAdapter universeEventHandlerAdapter(
            UniverseJpaRepository universeJpaRepository,
            CategoryJpaRepository categoryJpaRepository,
            TagJpaRepository tagJpaRepository
    ) {
        return new UniverseEventHandlerAdapter(
                universeJpaRepository,
                categoryJpaRepository,
                tagJpaRepository
        );

    }

    @Bean
    UniverseLoadAdapter universeLoadAdapter(
            UniverseJpaRepository universeJpaRepository,
            UniverseMapper universeMapper,
            OutlineSerializer outlineSerializer
    ) {
        return new UniverseLoadAdapter(
                universeJpaRepository,
                universeMapper,
                outlineSerializer
        );
    }

    @Bean
    UniverseCommandCategoryAdapter universeCommandAdapter(
            CategoryJpaRepository categoryJpaRepository
    ) {
        return new UniverseCommandCategoryAdapter(categoryJpaRepository);
    }

    @Bean
    SpaceEventHandleAdapter spaceEventHandleAdapter(
            UniverseJpaRepository universeJpaRepository,
            SpaceJpaRepository spaceJpaRepository,
            PieceJpaRepository pieceJpaRepository,
            SoundJpaRepository soundJpaRepository,
            OutlineSerializer outlineSerializer
    ) {
        return new SpaceEventHandleAdapter(
                universeJpaRepository,
                spaceJpaRepository,
                pieceJpaRepository,
                soundJpaRepository,
                outlineSerializer
        );
    }

    @Bean
    PieceEventHandleAdapter pieceEventHandleAdapter(
            UniverseJpaRepository universeJpaRepository,
            PieceJpaRepository pieceJpaRepository,
            SoundJpaRepository soundJpaRepository,
            OutlineSerializer outlineSerializer

    ) {
        return new PieceEventHandleAdapter(
                universeJpaRepository,
                pieceJpaRepository,
                soundJpaRepository,
                outlineSerializer
        );
    }

    @Bean
    SoundEventHandleAdapter soundEventHandleAdapter(
            PieceJpaRepository pieceJpaRepository,
            SoundJpaRepository soundJpaRepository
    ) {
        return new SoundEventHandleAdapter(
                pieceJpaRepository,
                soundJpaRepository
        );
    }

    @Bean
    UniverseMapper universeMapper(OutlineSerializer outlineSerializer) {
        return new UniverseMapper(outlineSerializer);
    }

    @Bean
    OutlineSerializer outlineSerializer(ObjectMapper persistenceObjectMapper) {
        return new OutlineSerializer(persistenceObjectMapper);
    }

    @Bean("persistenceObjectMapper")
    ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(Point.class, new PointDeserializer());
        module.addSerializer(Point.class, new PointSerializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
