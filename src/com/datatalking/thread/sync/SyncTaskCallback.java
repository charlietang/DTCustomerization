package com.datatalking.thread.sync;

import com.datatalking.thread.IFThreadCallback;

public class SyncTaskCallback implements IFThreadCallback {

	@Override
	public boolean OnPrepared() {
		System.out.println("OnPrepared");
		return true;
	}

	@Override
	public boolean OnStarted() {
		System.out.println("OnStarted");
		return true;
	}

	@Override
	public boolean OnProcessing(long row) {
		System.out.printf("OnProcessing %d\n",row);
		return true;
	}

	@Override
	public Object OnProcessing() {
		System.out.println("OnProcessing");
		return new Long(19);
	}
	private long l=0;
	@Override
	public boolean OnCompleted(Object obj) {
		l++;
		if(obj==null)
			return false;
		if(obj instanceof Long){
			if(((Long)obj).longValue()==l){
				System.out.println("OnCompleted == 19");
				return true;
			}
		}
		return false;
	}

}
