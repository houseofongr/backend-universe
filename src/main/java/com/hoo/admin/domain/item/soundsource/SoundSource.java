package com.hoo.admin.domain.item.soundsource;

import com.hoo.admin.domain.file.File;
import com.hoo.admin.domain.file.FileId;
import com.hoo.admin.domain.file.FileType;
import com.hoo.admin.domain.item.ItemId;
import com.hoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class SoundSource {
    private final SoundSourceId soundSourceId;
    private final ItemId itemId;
    private final File file;
    private SoundSourceDetail soundSourceDetail;
    private final BaseTime baseTime;
    private Active active;

    private SoundSource(SoundSourceId soundSourceId, ItemId itemId, File file, SoundSourceDetail soundSourceDetail, BaseTime baseTime, Active active) {
        this.soundSourceId = soundSourceId;
        this.itemId = itemId;
        this.file = file;
        this.soundSourceDetail = soundSourceDetail;
        this.baseTime = baseTime;
        this.active = active;
    }

    public static SoundSource create(Long id, Long itemId, Long audioFileId, String name, String description, Boolean isActive) {

        File file = new File(new FileId(audioFileId), FileType.AUDIO);

        return new SoundSource(new SoundSourceId(id), new ItemId(itemId), file, new SoundSourceDetail(name, description), null, new Active(isActive));
    }

    public static SoundSource load(Long id, Long itemId, Long audioFileId, String name, String description, ZonedDateTime createdTime, ZonedDateTime updatedTime, Boolean isActive) {

        File file = new File(new FileId(audioFileId), FileType.AUDIO);

        return new SoundSource(new SoundSourceId(id), new ItemId(itemId), file, new SoundSourceDetail(name, description), new BaseTime(createdTime, updatedTime), new Active(isActive));
    }

    public void updateDetail(String name, String description, Boolean isActive) {

        String newName = name != null && !name.isBlank() ? name : this.soundSourceDetail.getName();
        String newDescription = description != null && !description.isBlank() ? description : this.soundSourceDetail.getDescription();

        this.soundSourceDetail = new SoundSourceDetail(newName, newDescription);

        if (isActive != null) this.active = new Active(isActive);
    }
}
