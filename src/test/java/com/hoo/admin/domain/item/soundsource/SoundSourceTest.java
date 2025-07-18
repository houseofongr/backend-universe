package com.hoo.admin.domain.item.soundsource;

import com.hoo.admin.domain.file.FileType;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SoundSourceTest {

    @Test
    @DisplayName("음원 생성 테스트")
    void testCreateSoundSource() {
        // given
        Long fileId = 1L;
        String name = "음원1";
        String description = "음원 설명";

        // when
        SoundSource soundSource = SoundSource.create(1L, 1L, fileId, name, description, null);

        // then
        assertThat(soundSource.getFile().getFileId().getId()).isEqualTo(fileId);
        assertThat(soundSource.getFile().getFileType()).isEqualTo(FileType.AUDIO);
        assertThat(soundSource.getSoundSourceDetail().getName()).isEqualTo(name);
        assertThat(soundSource.getSoundSourceDetail().getDescription()).isEqualTo(description);
        assertThat(soundSource.getActive().isActive()).isTrue();
    }

    @Test
    @DisplayName("음원 수정 테스트")
    void testUpdateSoundSource() throws Exception {
        // given
        String nullName = null;
        String emptyName = " ";
        String newName = "골골골송";

        String nullDescription = null;
        String emptyDescription = " ";
        String newDescription = "2026년 설이가 보내는 골골골송";

        Boolean nullActive = null;
        Boolean active = false;

        SoundSource soundSource = MockEntityFactoryService.getSoundSource();
        String originalName = soundSource.getSoundSourceDetail().getName();
        String originalDesc = soundSource.getSoundSourceDetail().getDescription();
        Boolean originalActive = soundSource.getActive().isActive();

        soundSource.updateDetail(nullName, originalDesc, originalActive);
        assertThat(soundSource.getSoundSourceDetail().getName()).isEqualTo(originalName);

        soundSource.updateDetail(emptyName, originalDesc, originalActive);
        assertThat(soundSource.getSoundSourceDetail().getName()).isEqualTo(originalName);

        soundSource.updateDetail(newName, originalDesc, originalActive);
        assertThat(soundSource.getSoundSourceDetail().getName()).isEqualTo(newName);

        soundSource.updateDetail(originalName, nullDescription, originalActive);
        assertThat(soundSource.getSoundSourceDetail().getDescription()).isEqualTo(originalDesc);

        soundSource.updateDetail(originalName, emptyDescription, originalActive);
        assertThat(soundSource.getSoundSourceDetail().getDescription()).isEqualTo(originalDesc);

        soundSource.updateDetail(originalName, newDescription, originalActive);
        assertThat(soundSource.getSoundSourceDetail().getDescription()).isEqualTo(newDescription);

        soundSource.updateDetail(originalName, originalDesc, nullActive);
        assertThat(soundSource.getActive().isActive()).isEqualTo(originalActive);

        soundSource.updateDetail(newName, newDescription, active);
        assertThat(soundSource.getSoundSourceDetail().getName()).isEqualTo(newName);
        assertThat(soundSource.getSoundSourceDetail().getDescription()).isEqualTo(newDescription);
        assertThat(soundSource.getActive().isActive()).isFalse();
    }
}