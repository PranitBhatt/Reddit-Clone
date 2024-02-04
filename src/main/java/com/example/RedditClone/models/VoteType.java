package com.example.RedditClone.models;

import com.example.RedditClone.exceptions.VoteNotFound;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1);

    private int direction;
    VoteType(int direction) {
    }
    public static VoteType lookup(Integer direction) throws VoteNotFound {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new VoteNotFound());
    }

    public Integer getDirection() {
        return direction;
    }
}
