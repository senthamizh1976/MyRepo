package com.thisisnoble.javatest;

import com.thisisnoble.javatest.impl.MyException;

public interface Orchestrator {

    void register(Processor processor);

    void receive(Event event) throws MyException;

    void setup(Publisher publisher);
}
