# Homework: Transactions and Concurrency Control in Distributed Systems  


---

## Part A: Theoretical Questions

### ACID Properties  

In distributed transactions, atomicity ensures that a transaction either completes fully or not at all, even when it spans multiple systems. For instance, during a cross-bank fund transfer, if the debit operation is successful but the credit fails, atomicity guarantees that the entire transaction is rolled back to prevent inconsistencies.

Consistency ensures that a distributed system transitions from one valid state to another. This means that after a transaction is completed, all data must remain valid according to defined rules or constraints. For example, when booking a flight ticket, even if multiple services interact with the same seat inventory, consistency ensures no overbooking occurs.

Isolation maintains transaction independence, even when multiple transactions run concurrently. In a distributed context, isolation becomes challenging due to communication delays and potential replication issues. A practical example would be two users modifying the same item in a shopping cart from different devices—proper isolation ensures these actions don’t conflict.

Durability guarantees that once a transaction is committed, its results persist permanently, regardless of system failures. This is particularly important in distributed environments, where failures can occur across various nodes. An example is an online purchase confirmation that remains valid even if a related microservice crashes immediately afterward.

### Concurrency Issues  

The lost update problem occurs when two transactions read the same data and make updates without knowledge of each other’s changes, causing one update to overwrite the other. For instance, if two users simultaneously withdraw money from the same account, each reading the same initial balance, the resulting balance may not reflect both withdrawals.

A dirty read happens when a transaction accesses uncommitted data from another transaction. If the other transaction is later rolled back, the data read was invalid. This could happen when a service reads an updated inventory count before the update has been finalized, leading to incorrect decisions.

Deadlocks arise when two or more transactions wait indefinitely for each other to release locks. This can happen if each holds a resource that the other needs. In a distributed system, one transaction might lock a product table while another locks a customer table, and each waits on the other, creating a circular wait that halts progress.

---

## Part B: Practical Scenario

### Concurrency Control Mechanisms  

In a distributed banking system, if two clients attempt to withdraw money from the same account simultaneously from different nodes, the absence of concurrency control can result in inconsistent data. Both may read the same balance—say $1000—and independently deduct $500. Without coordination, the system might save $500 as the final balance instead of $0, ignoring one of the withdrawals.

Locking helps maintain consistency by ensuring that only one transaction can modify a resource at a time. In this scenario, locking the account record during the transaction prevents concurrent access, avoiding conflicting updates. However, excessive or poorly managed locking can lead to deadlocks, where multiple transactions wait indefinitely for each other’s resources. Techniques like timeout-based rollback, deadlock detection algorithms, or acquiring locks in a globally consistent order can help mitigate these issues.

### Distributed Transaction Coordination  

In an e-commerce platform, placing an order might involve reducing inventory, processing payment, and recording the order. To ensure atomicity across these distributed components, a coordination mechanism is essential. If one service fails, the entire transaction must be rolled back to avoid data inconsistencies.

The two-phase commit protocol (2PC) is commonly used to coordinate such distributed transactions. In the first phase, each service prepares to commit and responds with a success or failure signal. In the second phase, if all participants agree, the coordinator instructs them to commit; otherwise, it aborts the transaction. Although reliable, 2PC can be slow and blocking in nature.

In the event of partial failure—such as successful payment but failure in order creation—a compensating transaction may be triggered to reverse the payment. This pattern, often used in the saga approach, defines a compensating action for each transaction step, enabling the system to recover gracefully and maintain consistency.

---

## Part C: Research and Critical Thinking

### Real-World Systems: Google Spanner  

Google Spanner is a globally distributed relational database that supports strong consistency and full ACID transactions. It achieves this by combining the Paxos consensus algorithm with Google’s TrueTime API, which provides globally synchronized clocks with bounded uncertainty. This architecture enables Spanner to maintain strict external consistency, ensuring that transactions reflect real-world time ordering.

To manage concurrency, Spanner employs two-phase locking for read-write transactions, ensuring isolation, while using multiversion concurrency control (MVCC) for read-only transactions to enhance performance. This design allows Spanner to support high throughput while preserving transactional integrity.

Spanner’s emphasis is on consistency and availability, even if that means slightly higher latency due to global coordination. It is ideal for applications that require consistent, up-to-date data at a global scale—such as financial services or international inventory systems—where transactional accuracy and reliability are critical across multiple data centers.

---
