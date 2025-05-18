# Homework: Transactions and Concurrency Control in Distributed Systems  
**Course**: Distributed Systems  
**Professor**: Dardan Iseni  


---

## Part A: Theoretical Questions 

### 1. ACID Properties
- **Atomicity**: Ensures all operations in a transaction either complete fully or have no effect.  
  *Example*: A distributed fund transfer either succeeds across all nodes or rolls back entirely.  
- **Consistency**: Guarantees the system transitions between valid states.  
  *Example*: An e-commerce order ensures global inventory reductions match purchased quantities.  
- **Isolation**: Prevents concurrent transactions from interfering.  
  *Example*: Two users updating the same account balance see changes as if executed sequentially.  
- **Durability**: Committed changes persist despite failures.  
  *Example*: A confirmed payment remains recorded even if a database node crashes.  

### 2. Concurrency Issues 
1. **Lost Updates**: Two transactions overwrite each other’s changes.  
   *Scenario*: Clients A and B read $1,000, each withdraw $200, resulting in an incorrect $800 balance.  
2. **Dirty Reads**: A transaction reads uncommitted data from another transaction.  
   *Scenario*: A payment service reads an uncommitted inventory update, leading to overselling.  
 

---

## Part B: Practical Scenario

### 6. Concurrency Control Mechanisms 
**a. Risks Without Concurrency Control**  
Both clients read $1,000, withdraw $200 each, and write $800, leaving an incorrect balance of $800 instead of $600.  

**b. Locking Solution**  
Use **pessimistic locking** (e.g., `SELECT FOR UPDATE`). The first client locks the account, updates the balance to $800, and releases the lock. The second client then reads $800 and withdraws $200, resulting in a correct $600 balance.  

**c. Locking Issues and Solutions**  
- **Deadlocks**: Clients may cyclically lock resources.  
- **Solution**: Implement timeouts or wound-wait algorithms to abort older transactions.  

### 7. Distributed Transaction Coordination 
**a. Ensuring Atomicity**  
Use a **two-phase commit (2PC)** protocol. A coordinator ensures all services (inventory, payment, order) agree to commit or roll back.  

**b. Protocol Choice**  
**2PC** is ideal for strong consistency, as it requires unanimous approval before finalizing the transaction.  

**c. Handling Post-Payment Failure**  
Trigger a **compensating transaction** (e.g., refund) to revert the payment and maintain atomicity.  

---

## Part C: Research and Critical Thinking  

### 9. Real-World Systems: Google Spanner  
**Transactions and Concurrency Control**  
- Uses **strict two-phase locking** for read-write transactions and **MVCC** for lock-free snapshot reads.  
- Relies on **TrueTime** and **Paxos** for global timestamping and consensus.  

**Trade-offs**  
- **Consistency**: Strong consistency via synchronized clocks, but higher latency.  
- **Availability**: Survives regional outages but may lag during partitions.  
- **Performance**: Optimized for read-heavy workloads with atomic clock synchronization.  

**Ideal Scenario**  
Global financial systems (e.g., cross-border payments) requiring strict consistency across regions.  

---

