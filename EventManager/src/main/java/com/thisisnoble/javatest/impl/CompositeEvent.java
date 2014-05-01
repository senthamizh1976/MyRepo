package com.thisisnoble.javatest.impl;

import com.thisisnoble.javatest.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CompositeEvent implements Event {

    private static final Logger LOGGER =  LoggerFactory.getLogger(CompositeEvent.class);

    private final String id;
    private final Event parent;
    private final Map<String, Event> children = new HashMap();

    public CompositeEvent(String id, Event parent) {
        this.id = id;
        this.parent = parent;
    }


    @Override
    public void markCompletion(boolean b) {
        LOGGER.error("Composite Event doesn't support markCompletion {} ", this.toString());
        throw new RuntimeException("Composite Event doesn't support markCompletion "  + id);
    }

    @Override
    public boolean isProcessed() {
        return parent == null ? false : parent.isProcessed();
    }

    public String getId() {
        return id;
    }

    public Event getParent() {
        return parent;
    }

    public CompositeEvent addChild(Event child) {
        children.put(child.getId(), child);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <E extends Event> E getChildById(String id) {
        return (E) children.get(id);
    }

    public int size() {
        return children.size();
    }
}
