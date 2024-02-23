package ru.teamscore.java23.enums;

import lombok.Getter;

@Getter
public enum Direction {
    UP(1),
    DOWN(-1),
    NONE(0);

    private final int num;
    Direction(int num) {this.num = num;}
}
