package com.thisisnoble.javatest.impl;

import com.thisisnoble.javatest.Event;
import com.thisisnoble.javatest.Orchestrator;
import com.thisisnoble.javatest.Processor;
import com.thisisnoble.javatest.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: neurope Date: 01/05/14 Time: 19:09
 */
public class EventOrchestrator implements Orchestrator {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventOrchestrator.class);

	private List<Processor> processors = new ArrayList<Processor>();
	private Publisher publisher;

	@Override
	public void register(Processor processor) {
		processors.add(processor);
	}

	@Override
	public void receive(Event event) throws MyException {

		if (processors == null || processors.isEmpty() || event == null) {
			LOGGER.warn("Processor or event is null {} , {} ", processors, event);
			return;
		}
		CompositeEvent compositeEvent = null;

		if (event.isProcessed()) {
			LOGGER.info("Already markCompletion by processor, so no need to do again {}", event);
			// here the problem is if one processor class did a callback, but
			// not others..then how!!!
			compositeEvent = combineEvents(event.getId(), event, compositeEvent);
		} else {
			for (Processor processor : processors) {

				Event tempEvent = processor.process(event);

				if (tempEvent == null) {
					continue;
				}
				combineEvents(event.getId(), tempEvent, compositeEvent);

			}
		}

		if (compositeEvent != null) {
			publisher.publish(compositeEvent);
		}

		LOGGER.info("End of orchestrator!");

	}

	private CompositeEvent combineEvents(String id, Event inputEvent, CompositeEvent compositeEvent) {
		if (compositeEvent == null) {
			return new CompositeEvent(id, inputEvent);
		} else {
			compositeEvent.addChild(inputEvent);
		}
		return compositeEvent;

	}

	@Override
	public void setup(Publisher publisher) {
		this.publisher = publisher;
	}

}
