package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.taskExecutor.Task;

public class TaskRunner implements Runnable {

	private BlockingFIFO taskQueue;

	public TaskRunner(BlockingFIFO taskQueue) {
		this.taskQueue = taskQueue;
	}

	@Override
	public void run() {
		while (true) {
			Task task = null;
			task = taskQueue.take();
			task.execute();
		}
	}

}
