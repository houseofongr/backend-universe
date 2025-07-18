package com.hoo.common.adapter;

import com.hoo.common.application.port.out.IssueIdPort;

import java.util.Random;
import java.util.random.RandomGenerator;

public class MockIdAdapter implements IssueIdPort {

    @Override
    public Long issueHouseId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }

    @Override
    public Long issueRoomId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }

    @Override
    public Long issueHomeId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }

    @Override
    public Long issueItemId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }

    @Override
    public Long issueSoundSourceId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }

    @Override
    public Long issueDeletedUserId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }

    @Override
    public Long issueUniverseId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }

    @Override
    public Long issueSpaceId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }

    @Override
    public Long issuePieceId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }

    @Override
    public Long issueSoundId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }

    @Override
    public Long issueUserId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }

    @Override
    public Long issueSnsAccountId() {
        return Random.from(RandomGenerator.getDefault()).nextLong(1, 100);
    }
}
