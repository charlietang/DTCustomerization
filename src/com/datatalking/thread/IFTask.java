package com.datatalking.thread;

public interface IFTask extends Runnable {
	public boolean needRunImmde();
	public String	key();
}
 