package com.training.mjunction.account.config;

import org.axonframework.boot.autoconfig.AxonAutoConfiguration;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.mongodb.MongoClient;
import com.training.mjunction.account.aggregates.AccountAggregate;

@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
public class AxonConfig {

	@Autowired
	private EventStore accountsEventStore;

	@Bean
	@Primary
	public EventStorageEngine eventStoreEngine(final MongoClient client) {
		return new MongoEventStorageEngine(new DefaultMongoTemplate(client));
	}

	@Bean
	public AggregateFactory<AccountAggregate> aggregateFactory() {
		return new GenericAggregateFactory<>(AccountAggregate.class);
	}

	@Bean
	public Snapshotter snapShotter(final AggregateFactory<AccountAggregate> aggregateFactory) {
		return new AggregateSnapshotter(accountsEventStore, aggregateFactory);
	}

	@Bean
	public SnapshotTriggerDefinition snapshotTriggerDefinition(final Snapshotter snapshotter) {
		return new EventCountSnapshotTriggerDefinition(snapshotter, 5);
	}

	@Bean
	public Repository<AccountAggregate> accountAggregateRepository(
			final SnapshotTriggerDefinition snapshotTriggerDefinition,
			final AggregateFactory<AccountAggregate> aggregateFactory) {
		return new EventSourcingRepository<>(aggregateFactory, accountsEventStore, snapshotTriggerDefinition);
	}

}
