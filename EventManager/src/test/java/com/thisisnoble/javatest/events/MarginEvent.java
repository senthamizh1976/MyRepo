package com.thisisnoble.javatest.events;

import com.thisisnoble.javatest.Event;

public class MarginEvent implements Event {

    private final String id;
    private final String parentId;
    private final double margin;
    private boolean processed;

    public MarginEvent(String id, String parentId, double margin) {
        this.id = id;
        this.parentId = parentId;
        this.margin = margin;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public double getMargin() {
        return margin;
    }

    @Override
    public boolean isProcessed() {
        return processed;
    }

    @Override
    public void markCompletion(boolean b) {
        this.processed = b;
    }
}
