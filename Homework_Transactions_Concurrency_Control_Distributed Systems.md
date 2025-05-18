# Homework: Transactions and Concurrency Control in Distributed Systems  
**Course:** Distributed Systems  
**Professor:** Dardan Iseni  
 

---



---

## Part A: Theoretical Questions

### ACID Properties  

In distributed transactions, atomicity ensures that either all operations across different systems are completed successfully, or none are. For example, in a cross-bank money transfer, if the debit from the sender’s account succeeds but the credit to the receiver’s account fails, the entire transaction should be rolled back to maintain consistency.

Consistency refers to the system being in a valid state before and after the transaction. In a distributed setting, this might involve multiple databases needing to comply with specific rules or constraints. For instance, when booking an airline ticket, the system should not allow overbooking a flight even if multiple booking services are interacting with the same seat inventory.

Isolation guarantees that transactions execute independently, even if they are concurrent. In a distributed environment, this becomes more complex due to latency and replication delays. A real-world example would be two users attempting to update the same item in their cart from different devices; isolation ensures that these actions do not interfere with each other.

Durability ensures that once a transaction is committed, the changes persist even in the event of a system failure. For distributed systems, durability is crucial because data must be reliably stored across nodes. An example would be an online purchase that must remain confirmed even if one of the microservices crashes after the transaction.

### Concurrency Issues  

One common concurrency issue is the lost update problem, which occurs when two transactions read the same data and write back updates without awareness of each other, leading to one of the updates being overwritten. For instance, if two users try to withdraw money from a shared account at the same time, and both read the same initial balance before updating it, the final balance may be incorrect.

Another issue is the dirty read, where a transaction reads data that has been modified by another transaction that has not yet been committed. If the initial transaction is rolled back, the second transaction has used invalid data. For example, if an inventory update is made but not yet committed, and a different service reads this new stock count, it may make decisions based on inaccurate information.

Deadlock is a situation where two or more transactions wait indefinitely for each other to release resources. This can happen if each transaction holds a lock that the other needs. In a distributed database, for example, one transaction might lock a product table while another locks a customer table, and both try to access the other's resource, resulting in a cycle that cannot resolve without external intervention.

---

## Part B: Practical Scenario

### Concurrency Control Mechanisms  

In a scenario where two clients try to withdraw money from the same bank account simultaneously from different nodes in a distributed banking system, a lack of concurrency control can lead to serious inconsistencies. Both transactions may read the same initial balance, say $1000, and decide to deduct $500 each. Without proper coordination, both could update the balance to $500 independently, leading to a final balance that reflects only one withdrawal instead of both.

Locking can help avoid such inconsistencies by ensuring that once one transaction begins modifying the balance, the other must wait until the first completes. The record for the account would be locked during the transaction, preventing concurrent access. This ensures correctness but introduces the risk of other problems, such as deadlocks, where multiple transactions wait on each other in a circular loop. A possible solution is to implement a timeout mechanism or use deadlock detection algorithms. Alternatively, transactions can be structured to acquire locks in a consistent global order to avoid circular waits.

### Distributed Transaction Coordination  

In a distributed e-commerce system, a single order transaction might include decreasing inventory, processing payment, and creating an order record. Ensuring atomicity across these services requires a coordination mechanism that guarantees all-or-nothing behavior. This is crucial because if one service fails, the transaction must be rolled back to avoid data inconsistency.

To achieve this, the two-phase commit protocol can be used. This protocol first prepares all participating services to commit, and if all confirm readiness, it then issues the final commit command. This ensures that either all services commit their part or all abort, maintaining atomicity. However, two-phase commit can be slow and blocking in nature.

If a failure occurs after the payment is processed but before the order record is created, a compensating transaction can be used to reverse the payment. This approach is often used in the saga pattern, where each step of a distributed transaction has a compensating action. This ensures the system can recover to a consistent state even in the face of partial failures.

---

## Part C: Research and Critical Thinking

### Real-World Systems: Google Spanner  

Google Spanner is a globally distributed database that supports full ACID transactions. It uses a combination of the Paxos consensus algorithm and Google's TrueTime API to ensure strong consistency across geographically dispersed nodes. Transactions in Spanner are coordinated with strict external consistency, meaning they reflect a global order that respects real-world time.

To handle concurrency, Spanner uses two-phase locking for read-write transactions and multiversion concurrency control for read-only transactions. This allows it to balance performance with correctness. Spanner prioritizes consistency and availability, often at the cost of latency, due to the need to synchronize clocks and coordinate across wide-area networks.

Spanner is well-suited for applications that require strong consistency and high availability at a global scale. An example would be a multinational financial institution that needs to manage user balances and transactions consistently across continents, ensuring that all operations are synchronized and reliable.

---


