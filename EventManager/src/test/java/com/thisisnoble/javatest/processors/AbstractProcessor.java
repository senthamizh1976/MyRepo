package com.thisisnoble.javatest.processors;

import com.thisisnoble.javatest.Event;
import com.thisisnoble.javatest.Orchestrator;
import com.thisisnoble.javatest.Processor;

import java.util.concurrent.*;

import com.thisisnoble.javatest.impl.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProcessor<T extends Event> implements Processor {

    private static final Logger  LOGGER =  LoggerFactory.getLogger(AbstractProcessor.class);

    private final Orchestrator orchestrator;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    protected AbstractProcessor(Orchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @Override
    public final T process(Event event) throws MyException {

        try {
        if(interestedIn(event)) {
            Future<T> future = threadPool.submit(new EventTask(event));
            if(future == null) {
                return null;
            }
            return future.get();
        } else {
            LOGGER.info("Process class not interested {} ", this.getClass().getSimpleName());
        }
        } catch (InterruptedException ie) {
            LOGGER.info("Interrupted error while processing {}, {}", event, ie);
            throw new MyException("InterruptedException while processing", ie);
        }
        catch (ExecutionException ex) {
            LOGGER.info("Execution error while processing {}, {}", event, ex);
            throw new MyException("ExecutionException while processing ", ex);
        }
        return null;

    }

    protected abstract Event processInternal(Event event);

    private class EventTask implements Callable {
        private final Event input;

        private EventTask(Event input) {
            this.input = input;
        }

        @Override
        public Event call() {
            Event output = processInternal(input);
            output.markCompletion(true);
            try {
                orchestrator.receive(output);
            } catch (MyException e) {
                LOGGER.error("MyException Error ", e);
            }
            return output;
        }
    }
}
