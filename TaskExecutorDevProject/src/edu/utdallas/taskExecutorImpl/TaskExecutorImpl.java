package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor {

	private BlockingFIFO taskQueue;
	public TaskExecutorImpl(int threadSize) {
		taskQueue = new BlockingFIFO();
		
		for (int i = 0; i < threadSize; i++) {
			TaskRunner taskRunner = new TaskRunner(taskQueue);
			Thread thread = new Thread(taskRunner);
			thread.start();
		}
	}

	@Override
	public void addTask(Task task) {
		taskQueue.put(task);
	}

}
