package com.thisisnoble.javatest;

import com.thisisnoble.javatest.impl.MyException;

public interface Processor<T extends Event> {

    boolean interestedIn(T event);

	T process(T event) throws MyException;
}
