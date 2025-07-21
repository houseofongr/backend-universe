package com.hoo.universe.adapter.in.web;

import com.hoo.universe.api.in.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = "com.hoo.universe.adapter.in.web")
public class WebMvcTestMockContext {

    @Bean
    public CreateUniverseUseCase createUniverseUseCase() {
        return mock(CreateUniverseUseCase.class);
    }

    @Bean
    public SearchUniverseUseCase searchUniverseUseCase() {
        return mock(SearchUniverseUseCase.class);
    }

    @Bean
    public OpenUniverseUseCase openUniverseUseCase() {
        return mock(OpenUniverseUseCase.class);
    }

    @Bean
    public UpdateUniverseMetadataUseCase updateUniverseMetadataUseCase() {
        return mock(UpdateUniverseMetadataUseCase.class);
    }

    @Bean
    public OverwriteUniverseFileUseCase overwriteUniverseFileUseCase() {
        return mock(OverwriteUniverseFileUseCase.class);
    }

    @Bean
    public DeleteUniverseUseCase deleteUniverseUseCase() {
        return mock(DeleteUniverseUseCase.class);
    }


    @Bean
    public CreateSpaceUseCase createSpaceUseCase() {
        return mock(CreateSpaceUseCase.class);
    }

    @Bean
    public UpdateSpaceMetadataUseCase updateSpaceMetadataUseCase() {
        return mock(UpdateSpaceMetadataUseCase.class);
    }

    @Bean
    public OverwriteSpaceFileUseCase overwriteSpaceFileUseCase() {
        return mock(OverwriteSpaceFileUseCase.class);
    }

    @Bean
    public MoveSpaceUseCase moveSpaceUseCase() {
        return mock(MoveSpaceUseCase.class);
    }

    @Bean
    public DeleteSpaceUseCase deleteSpaceUseCase() {
        return mock(DeleteSpaceUseCase.class);
    }


    @Bean
    public CreatePieceUseCase createPieceUseCase() {
        return mock(CreatePieceUseCase.class);
    }

    @Bean
    public OpenPieceUseCase openPieceUseCase() {
        return mock(OpenPieceUseCase.class);
    }

    @Bean
    public UpdatePieceMetadataUseCase updatePieceMetadataUseCase() {
        return mock(UpdatePieceMetadataUseCase.class);
    }

    @Bean
    public MovePieceUseCase movePieceUseCase() {
        return mock(MovePieceUseCase.class);
    }

    @Bean
    public DeletePieceUseCase deletePieceUseCase() {
        return mock(DeletePieceUseCase.class);
    }

    @Bean
    public CreateSoundUseCase createSoundUseCase() {
        return mock(CreateSoundUseCase.class);
    }

    @Bean
    public UpdateSoundMetadataUseCase updateSoundMetadataUseCase() {
        return mock(UpdateSoundMetadataUseCase.class);
    }

    @Bean
    public OverwriteSoundFileUseCase overwriteSoundFileUseCase() {
        return mock(OverwriteSoundFileUseCase.class);
    }

    @Bean
    public DeleteSoundUseCase deleteSoundUseCase() {
        return mock(DeleteSoundUseCase.class);
    }


    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return mock(CreateCategoryUseCase.class);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return mock(UpdateCategoryUseCase.class);
    }

    @Bean
    public SearchCategoryUseCase searchCategoryUseCase() {
        return mock(SearchCategoryUseCase.class);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return mock(DeleteCategoryUseCase.class);
    }

}
