package com.sample;

import io.reactivex.rxjava3.core.Flowable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class Bar implements IBar {

	/*
	 * Field injection works fine.
	 */
//	@Inject
//	private Foo foo;

	/*
	 * Constructor injection doesn't work.
	 */
	@Inject
	public Bar(Foo foo) {
	}

	@Outgoing("test")
	public Publisher<ZonedDateTime> generateTimestamps() {
		return Flowable.interval(2, TimeUnit.SECONDS)
				.map(interval -> ZonedDateTime.now());
	}

	@Incoming("test")
	public void sink(ZonedDateTime timestamp) {
		System.out.println(timestamp + " received");
	}
}