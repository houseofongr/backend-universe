package com.hoo.admin.domain.universe;

import lombok.Getter;

import java.util.List;
import java.util.regex.Pattern;

@Getter
public class SocialInfo {

    private static final Pattern VALID_HASHTAG_PATTERN = Pattern.compile("^[a-zA-Z0-9_가-힣]+$");

    private final Integer likeCount;
    private final Long viewCount;
    private List<String> hashtags;


    public SocialInfo(Integer likeCount, Long viewCount, List<String> hashtags) {
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.hashtags = validationAndReplace(hashtags);
    }

    private List<String> validationAndReplace(List<String> hashtags) {
        return hashtags.stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(tag -> VALID_HASHTAG_PATTERN.matcher(tag).matches())
                .distinct()
                .toList();
    }

    public void updateHashtag(List<String> tags) {
        this.hashtags = tags;
    }
}
