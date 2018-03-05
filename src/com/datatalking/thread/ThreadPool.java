package com.datatalking.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ThreadPool {
	protected static Logger log = Logger.getLogger(ThreadPool.class);
	List<IFTask> tasks = Collections.synchronizedList(new ArrayList<IFTask>());
	 private static ThreadPool instance =null;// ThreadPool.getInstance();
	 private static int poolsize=5;
	 public synchronized static ThreadPool getInstance(int size) {
		 if (ThreadPool.instance == null) {
			 poolsize=size;
			 ThreadPool.instance = new ThreadPool();
		 }
	     return ThreadPool.instance;
	 }
	 public synchronized static ThreadPool getInstance(){
		 if (ThreadPool.instance == null) {
			 ThreadPool.instance = new ThreadPool();
		 }
	     return ThreadPool.instance;
	 }

	 private WorkThread[] threads;
	 private ThreadPool() {
	        threads = new WorkThread[poolsize];
	        for (int i = 0; i < poolsize; i++) {
	            threads[i] = new WorkThread();
	        }
	 }
	 
//	 public class ThreadGroup{
//		 private Map<String,Pair<IFTask,Thread>> _syncs=Collections.synchronizedMap(new HashMap<String,Pair<IFTask,Thread>>());
//		 public Pair<IFTask,Thread> put(IFTask task){
//			 if(task==null)
//				 return null;
//			 Thread t=new Thread(task);
//			 return _syncs.put(task.key(),new Pair<IFTask,Thread>(task,t));
//		 }
//		 public Pair<IFTask,Thread> get(IFTask task){
//			 if(task==null)
//				 return null;
//			 return _syncs.get(task.key());
//		 }
//		 public Pair<IFTask,Thread> del(IFTask task){
//			 if(task==null)
//				 return null;
//			 return _syncs.remove(task.key());
//		 }
//		 
//		 public ThreadGroup start(){
//			 for(Entry<String,Pair<IFTask,Thread>> e:_syncs.entrySet()){
//				 Pair<IFTask,Thread> p=e.getValue();
//				 if( p.b!=null&&p.b.getState()==Thread.State.NEW)
//					 p.b.start();
//			 }
//			 return this;
//		 }
//		 public ThreadGroup join(){
//			 for(Entry<String,Pair<IFTask,Thread>> e:_syncs.entrySet()){
//				 Pair<IFTask,Thread> p=e.getValue();
//				 if( p.b!=null&&p.b.getState()!=Thread.State.TERMINATED){
//					try {
//						p.b.join();
//					} catch (InterruptedException e1) {
//						e1.printStackTrace();
//					}
//				 }
//			 }
//			 return this;
//		 }
//		 public ThreadGroup stop(){
//			 for(Entry<String,Pair<IFTask,Thread>> e:_syncs.entrySet()){
//				 Pair<IFTask,Thread> p=e.getValue();
//				 if( p.b!=null&&p.b.getState()!=Thread.State.TERMINATED){
//					try {
//						p.b.interrupt();
//					} catch (Exception e1) {
//						e1.printStackTrace();
//					}
//				 }
//			 }
//			 return this;
//		 }
//	 }
	 private Map<String,Thread> _syncs=Collections.synchronizedMap(new HashMap<String,Thread>());
	 
	 public synchronized Thread getTask(IFTask task){
		 if(task==null)
			 return null;
		 Thread t=_syncs.remove(task.key());
		 return t;
	 }
	 public synchronized Thread delTask(IFTask task){
		 if(task==null)
			 return null;
		 return  _syncs.remove(task.key());
	 }
	 public synchronized Thread syncTask(IFTask task){
		 if(task==null)
			 return null;
		 Thread t=new Thread(task);
		 _syncs.put(task.key(), t);
		 t.start();
		 return t;
	 }
	 public synchronized Thread syncTask(ThreadGroup group,IFTask task){
		 if(task==null)
			 return null;
		 Thread t=null;
		 if(group==null)
			 t=new Thread(task);
		 else
			 t=new Thread(group,task);
		 _syncs.put(task.key(), t);
		 t.start();
		 return t;
	 }
	 public synchronized String newTask(IFTask task){
		 if(task==null)
			 return null;
		 return addTask(task);
	 }
	 public synchronized String addTask(IFTask task) {
		 if(task==null)
			 return null;
		 String ret=task.key();
		 synchronized (tasks) {
	            tasks.add(task);
	            tasks.notifyAll();
		 }
	     return ret;
	 }
	 public synchronized void shutdown(){
		 while(true){
			 //synchronized (tasks) {
             if (tasks.isEmpty()) {
            	 for(WorkThread w:threads){
            		 //System.out.printf("%d:tasks %d set shuddown\n",i,w.getId());
            		 w.shutdown();
            	 }
            	 break;
             }else{
            	 //System.out.printf("tasks have %d\n",tasks.size());
            	 try {
					//tasks.wait(1000);
            		 this.wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             //}
			 }
		 }
	 }
	 class WorkThread extends Thread {
		 private boolean needrun=true;
		 public void shutdown(){
			 needrun=false;
			 try {
				this.join();
				//System.out.printf("thread %d join returned\n",this.getId());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
		 public WorkThread() {
			 start();
	     }
	     @Override
	     public void run() {
	            while (needrun) {
	                synchronized (tasks) {
	                    if (tasks.isEmpty()) {
	                        try {
	                            // When call the method wait or notify of a Object,
	                            // must ensure the Object is synchronized.
	                            // Here let the Thread which use the object wait a
	                            // while.
	                            tasks.wait(500);	                            
	                            //System.out.printf("thread %d continue after wait 20\n",this.getId());
	                            continue;
	                        } catch (InterruptedException e) {
	                        	e.printStackTrace();
	                        }
	                    }
	                    IFTask r = tasks.remove(0);
	                    if (r != null) {
	                        if (r.needRunImmde()) {
	                            new Thread(r).start();
	                        } else {
	                            r.run();
	                        }
	                        //log.info(String.format("+JobThread have %d jobs waiting",tasks.size()));
	                    }
	                }
	            }
	            //System.out.println("shuting down got, in run functions.");
	        }
	    }
}
