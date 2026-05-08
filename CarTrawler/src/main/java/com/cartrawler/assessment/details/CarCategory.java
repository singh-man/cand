package com.cartrawler.assessment.details;

public enum CarCategory {
    // Order is important for Comparator to work
    MINI('M'),
    ECONOMY('E'),
    COMPACT('C'),
    OTHER('O');

    private char shortName;

    CarCategory(char shortName) {
        this.shortName = shortName;
    }

    public static CarCategory getCarCategory(char code) {
        return fromSippCode(code);
    }

    private static CarCategory fromSippCode(char code) {

        if (Character.isWhitespace(code)) {
            return OTHER;
        }
        CarCategory cat = switch (code) {
            case 'M' -> MINI;
            case 'E' -> ECONOMY;
            case 'C' -> COMPACT;
            default -> OTHER;
        };
        return cat;
    }

}
