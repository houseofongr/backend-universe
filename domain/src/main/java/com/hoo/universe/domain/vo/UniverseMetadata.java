package com.hoo.universe.domain.vo;

import com.hoo.common.enums.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

import static com.hoo.common.util.OptionalUtil.getOrDefault;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UniverseMetadata {

    private final UUID thumbmusicID;
    private final UUID thumbnailID;
    private final UUID backgroundID;
    private final long viewCount;
    private final long likeCount;
    private final AccessLevel accessLevel;
    private final List<String> tags;

    public static UniverseMetadata create(UUID thumbmusicID, UUID thumbnailID, UUID backgroundID, AccessLevel accessLevel, List<String> tags) {
        return new UniverseMetadata(thumbmusicID, thumbnailID, backgroundID, 0, 0, accessLevel, tags);
    }

    public UniverseMetadata update(AccessLevel accessLevel, List<String> tags) {
        return new UniverseMetadata(thumbmusicID, thumbnailID, backgroundID, viewCount, likeCount, getOrDefault(accessLevel, this.accessLevel), getOrDefault(tags, this.tags));
    }

    public UniverseMetadata overwrite(UUID thumbmusicID, UUID thumbnailID, UUID backgroundID) {
        return new UniverseMetadata(getOrDefault(thumbmusicID, this.thumbmusicID), getOrDefault(thumbnailID, this.thumbnailID), getOrDefault(backgroundID, this.backgroundID), viewCount, likeCount, accessLevel, tags);
    }

}
