package com.bee.client.base;

import java.util.logging.Logger;

public class CategorySelectedItemEvent {
    private static final Logger logger = Logger.getLogger(CategorySelectedItemEvent.class.getName());

    private final int categoryId;

    public CategorySelectedItemEvent(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
