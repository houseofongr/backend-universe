package com.hoo.common.adapter.out.persistence;

import com.hoo.common.application.port.out.IssueIdPort;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdPersistenceAdapter implements IssueIdPort {

    private final EntityManager entityManager;

    @Value("${database.mysql.scheme}")
    private String schema;

    @Override
    public Long issueHouseId() {
        return issue("HOUSE");
    }

    @Override
    public Long issueRoomId() {
        return issue("ROOM");
    }

    @Override
    public Long issueHomeId() {
        return issue("HOME");
    }

    @Override
    public Long issueItemId() {
        return issue("ITEM");
    }

    @Override
    public Long issueSoundSourceId() {
        return issue("SOUND_SOURCE");
    }

    @Override
    public Long issueDeletedUserId() {
        return issue("DELETED_USER");
    }

    @Override
    public Long issueUserId() {
        return issue("AAR_USER");
    }

    @Override
    public Long issueSnsAccountId() {
        return issue("SNS_ACCOUNT");
    }

    @Override
    public Long issueUniverseId() {
        return issue("UNIVERSE");
    }

    @Override
    public Long issueSpaceId() {
        return issue("SPACE");
    }

    @Override
    public Long issuePieceId() {
        return issue("PIECE");
    }

    @Override
    public Long issueSoundId() {
        return issue("SOUND");
    }


    private Long issue(String tableName) {
        Object singleResult = entityManager.createNativeQuery(
                        """
                                SELECT AUTO_INCREMENT
                                FROM information_schema.TABLES
                                WHERE TABLE_SCHEMA = :schema
                                AND TABLE_NAME = :tableName
                                """)
                .setParameter("schema", schema)
                .setParameter("tableName", tableName)
                .getSingleResult();

        return (singleResult == null) ? 1L : (long) singleResult;
    }
}
