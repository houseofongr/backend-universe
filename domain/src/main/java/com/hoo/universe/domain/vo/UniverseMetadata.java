package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UniverseMetadata {

    private final UUID thumbmusicID;
    private final UUID thumbnailID;
    private final UUID backgroundID;
    private final long viewCount;
    private final long likeCount;
    private final AccessStatus accessStatus;
    private final List<String> tags;

    public static UniverseMetadata create(UUID thumbmusicID, UUID thumbnailID, UUID backgroundID, AccessStatus accessStatus, List<String> tags) {
        return new UniverseMetadata(thumbmusicID, thumbnailID, backgroundID, 0, 0, accessStatus, tags);
    }

    public UniverseMetadata update(AccessStatus accessStatus, List<String> tags) {
        return new UniverseMetadata(thumbmusicID, thumbnailID, backgroundID, viewCount, likeCount, getOrDefault(accessStatus, this.accessStatus), getOrDefault(tags, this.tags));
    }

    public UniverseMetadata overwrite(UUID thumbmusicID, UUID thumbnailID, UUID backgroundID) {
        return new UniverseMetadata(getOrDefault(thumbmusicID, this.thumbmusicID), getOrDefault(thumbnailID, this.thumbnailID), getOrDefault(backgroundID, this.backgroundID), viewCount, likeCount, accessStatus, tags);
    }

    private  <T> T getOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

}
