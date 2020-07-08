package com.selfgrowth.util;

public interface Constant {
    interface ENTITY_STATUS {
        Integer DELETE = 0;
        Integer ACTIVE = 1;
    }

    interface SORT_BY {
        String LOW_TO_HIGH = "low-to-high";
        String HIGH_TO_LOW = "high-to-low";
        String NEW_TO_OLD = "new-to-old";
        String OLD_TO_NEW = "old-to-new";
    }
}
