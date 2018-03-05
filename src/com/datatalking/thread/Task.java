package com.datatalking.thread;

public abstract class Task implements IFTask {
	public IFThreadCallback callback=null;
	public Task(IFThreadCallback callback){
		this.callback=callback;
	}
//	public abstract Object process();
	@Override
	public void run() {
		if(this.callback==null){
//			process();
			return;
		}
		if(!this.callback.OnPrepared()){
			this.callback.OnCompleted(null);
			return;
		}
		if(!this.callback.OnStarted()){
			this.callback.OnCompleted(null);
			return;
		}
		long i=0;
		do{
			if(!this.callback.OnProcessing(i++)){
				this.callback.OnCompleted(null);
				break;
			}
		}while(!this.callback.OnCompleted(this.callback.OnProcessing()));
	}

	@Override
	public boolean needRunImmde() {
		return false;
	}

}
