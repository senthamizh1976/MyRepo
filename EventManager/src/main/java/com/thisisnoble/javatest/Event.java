package com.thisisnoble.javatest;

//marker interface, add as many interface methods as required
public interface Event {

    String getId();

    boolean isProcessed();

    void markCompletion(boolean b);
}
