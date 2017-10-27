package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public class BlockingFIFO {

	private final static int MAX_QUEUE_SIZE = 100; // max queue size

	private Task[] queue;                          // FIFO queue of tasks
	private int N;                                 // size of queue
	private int count;                             // the current number of tasks in the queue
	private int nextIn;                            // index to put a task into queue
	private int nextOut;                           // index to take a task from queue

	Object notfull;                                // monitor object to indicate whether the queue is full
	Object notempty;                               // monitor object to indicate whether the queue is empty

	public BlockingFIFO(int size) {
		// limit the queue size
		if (size > MAX_QUEUE_SIZE) {
			this.queue = new Task[MAX_QUEUE_SIZE];
			this.N = MAX_QUEUE_SIZE;
		} else {
			this.queue = new Task[size];
			this.N = size;
		}
		this.count = 0;
		this.nextIn = 0;
		this.nextOut = 0;
		this.notfull = new Object();
		this.notempty = new Object();
	}

	public BlockingFIFO() {
		this.queue = new Task[MAX_QUEUE_SIZE];
		this.N = MAX_QUEUE_SIZE;
		this.count = 0;
		this.nextIn = 0;
		this.nextOut = 0;
		this.notfull = new Object();
		this.notempty = new Object();
	}

	public void put(Task task) {
		while (true) {
			if (count == N) {
				synchronized (notfull) {
					try {
						notfull.wait();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
			synchronized (this) {
				if (count == N)
					continue;
				synchronized (notempty) {
					queue[nextIn] = task;
					nextIn = (nextIn + 1) % N;
					count++;
					notempty.notify();
				}
				break;
			}
		}

	}

	public Task take() {
		while (true) {
			if (count == 0) {
				synchronized (notempty) {
					try {
						notempty.wait();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}

			}
			synchronized (this) {
				if (count == 0)
					continue;
				synchronized (notfull) {
					Task task = queue[nextOut];
					nextOut = (nextOut + 1) % N;
					count--;
					notfull.notify();
					return task;
				}
			}
		}
	}
}
