Functional Requirements
Topics:

The system should support different topic creation, which act as logical message categories.
Publishers publish messages to specific topics, and subscribers subscribe to one or more topics to consume messages.
Partitioning:

Topics should be divided into partitions for scalability, and each partition should handle concurrent reads and writes.
Publishers:

Publishers can produce messages concurrently without causing race conditions or data corruption.
The system should support multiple publishers that can send messages to a topic.
Subscribers:

Subscribers receive the messages intended for them, ensuring that no messages are lost.
The system must allow multiple subscribers to consume messages from one or more topics.
Message Delivery Semantics:

Exactly-Once Delivery: Ensure that each message is delivered exactly once to each subscriber.
Concurrency:

The system should support multiple concurrent publishers and subscribers.
It must ensure thread-safe production and consumption of messages.
Ordering:

Messages within a partition must be delivered in the order they were produced.
Subscribers should consume messages in the same order, particularly within a partition of a topic.
Non-functional Requirements
Design Principles:

The implementation should follow standard design principles, such as Separation of Concerns, Modularity, Loose Coupling, and Concurrency Control, to ensure maintainability, extensibility, and performance.
The use of appropriate design patterns is highly recommended to address common challenges and promote a clean and scalable architecture.
High Throughput:

The system should handle a high volume of message traffic with minimal latency.
It should be optimised for fast write and read operations on messages.
Low Latency:

Ensure that messages are delivered to subscribers with minimal delay after being produced by publishers.
Examples
Scenario 1: Multiple Publishers and Subscribers

Publishers A and B are producing messages to Topic X. Subscribers C and D are subscribed to Topic X.
Messages produced by both publishers should be distributed to both subscribers, ensuring each message is delivered exactly once to each subscriber.
Scenario 2: Concurrency Handling

Publishers A, B, and C are producing messages concurrently to the same topic. The system should ensure that there are no race conditions, and each message is safely enqueued in the correct order.