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
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.training.mjunction.account.aggregates.AccountAggregate;

@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
public class AxonConfig {

	@Autowired
	private EventStore accountsEventStore;

	@Bean
	public EventStorageEngine eventStore(final MongoClient client) {
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

	@Bean
	public Exchange exchange() {
		return ExchangeBuilder.fanoutExchange("Accounts").build();
	}

	@Bean
	public Queue queue() {
		return QueueBuilder.durable("Accounts").build();
	}

	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with("*").noargs();
	}

}
