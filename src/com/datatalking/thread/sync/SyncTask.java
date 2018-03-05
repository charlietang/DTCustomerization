package com.datatalking.thread.sync;

import java.util.UUID;

import com.datatalking.thread.IFThreadCallback;
import com.datatalking.thread.Task;
import com.datatalking.thread.ThreadPool;

public class SyncTask extends Task {
	public SyncTask(IFThreadCallback callback) {
		super(callback);
	}
	private String key=null;
	@Override
	public String key() {
		return key==null?(key=UUID.randomUUID().toString()):key;
	}
	
	public static void main(String args[]) throws InterruptedException{
		ThreadPool pools=ThreadPool.getInstance();
		SyncTask task=new SyncTask(new SyncTaskCallback());
		SyncTask task1=new SyncTask(new SyncTaskCallback());
		
		ThreadGroup threadGroup = new ThreadGroup("Searcher");  
		pools.syncTask(threadGroup,task);
		pools.syncTask(threadGroup,task1);
		Thread[] threads = new Thread[threadGroup.activeCount()];  
		threadGroup.enumerate(threads);  
        for (int i = 0; i < threadGroup.activeCount(); i++) {  
            System.out.printf("Thread %s: %s\n", threads[i].getName(),  
                    threads[i].getState());  
        }  
		

		System.out.println("sleep ");
//		Thread t1=pools.getTask(task);
//		if(t1!=null)
//			t1.join();
//		Thread.sleep(5000);
		System.out.println("sleep ...");
//		if(t1.isAlive()){
//			System.out.println("interrupt...");
//			t1.interrupt();
//		}
//		t1.join(1000);
//		System.out.printf("getState %s\n",t1.getState());
//		System.out.printf("interrupted %s\n",t1.isInterrupted());
//		System.out.printf("isAlive %s\n",t1.isAlive());
		System.out.println("i am joinning ...");
//		t1.interrupt();
		
		pools.shutdown();
		
	}

	
}
