package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.universe.PublicStatus;
import com.hoo.admin.domain.universe.Universe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "UNIVERSE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UniverseJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PublicStatus publicStatus;

    @Column(nullable = false)
    private Long thumbMusicFileId;

    @Column(nullable = false)
    private Long thumbnailFileId;

    @Column(nullable = false)
    private Long innerImageFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserJpaEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private CategoryJpaEntity category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universe", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<UniverseHashtagJpaEntity> universeHashtags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universe", cascade = CascadeType.REMOVE)
    private List<UniverseLikeJpaEntity> universeLikes;

    public static UniverseJpaEntity create(Universe universe, UserJpaEntity author, CategoryJpaEntity category) {
        return new UniverseJpaEntity(
                null,
                universe.getBasicInfo().getTitle(),
                universe.getBasicInfo().getDescription(),
                universe.getSocialInfo().getViewCount(),
                universe.getBasicInfo().getPublicStatus(),
                universe.getFileInfo().getThumbMusicId(),
                universe.getFileInfo().getThumbnailId(),
                universe.getFileInfo().getImageId(),
                author,
                category,
                new ArrayList<>(),
                List.of());
    }

    public void update(Universe universe) {
        this.title = universe.getBasicInfo().getTitle();
        this.description = universe.getBasicInfo().getDescription();
        this.publicStatus = universe.getBasicInfo().getPublicStatus();
        this.thumbMusicFileId = universe.getFileInfo().getThumbMusicId();
        this.thumbnailFileId = universe.getFileInfo().getThumbnailId();
        this.innerImageFileId = universe.getFileInfo().getImageId();
    }

    public void updateAuthor(UserJpaEntity author) {
        this.author = author;
    }

    public void updateCategory(CategoryJpaEntity category) {
        this.category = category;
    }

    public void view() {
        this.viewCount++;
    }
}
