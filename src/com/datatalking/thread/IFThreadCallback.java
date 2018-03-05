package com.datatalking.thread;

public interface IFThreadCallback {
	public boolean OnPrepared();
	public boolean OnStarted();
	public boolean OnProcessing(long row);
	public Object OnProcessing();
	public boolean OnCompleted(Object obj);
}
