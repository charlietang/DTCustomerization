/**
 * 
 */
package com.datatalking.debug;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * print classinstance method runtime detail <br>
 * when your class impletments IFDynProxyCallback, init without  IFDynProxyCallback instance:<br>
 * | IFLogPrint f=(IFLogPrint)new com.datatalking.debug.DynProxy(new Test()).bind();<br>
 * otherwise use below:<br>
 * | IFLogPrint f=(IFLogPrint)new com.datatalking.debug.DynProxy(new Test()<br>
 * |                                ,new IFDynProxyCallback(){<br>
 * |                                    //call when ClassInstance.MethodInstance begin<br>
 * |                                    public void CallbackStart(Object classinstance, Method method, Object[] args){<br>
 * |                                        System.out.printf("%s\n",debug);<br>
 * |                                    }	<br>
 * |                                    //call when ClassInstance.MethodInstance end<br>
 * |                                    public void CallbackComplete(float Seconds, Object classinstance,Method method, Object[] args, Object ret) {<br>
 * |                                        System.out.printf("%.3f\t%s\n",Seconds,debug);		 <br>
 * |                                    }<br>
 * |                                }<br>
 * |                                ).bind();<br>
 * | f.print();<br>
 * @author tang dalong
 *
 */
public class DynProxy implements InvocationHandler {
	private Object origObj;
	private IFDynProxyCallback _callback=null;
	public DynProxy(Object obj){
		this.origObj=obj;
	}
	public DynProxy(Object obj,IFDynProxyCallback callback){
		this.origObj=obj;
		this._callback=callback;
	}
	public synchronized Object bind(Object origObj){
		this.origObj=origObj;
		return Proxy.newProxyInstance(origObj.getClass().getClassLoader(), origObj.getClass().getInterfaces(), this);
	}
	public synchronized Object bind(){
		return Proxy.newProxyInstance(origObj.getClass().getClassLoader(), origObj.getClass().getInterfaces(), this);
	}
	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		IFDynProxyCallback callback=null;
		if(origObj instanceof IFDynProxyCallback)
			callback=(IFDynProxyCallback)origObj;
		long begin=System.currentTimeMillis();
		if(_callback!=null)
			_callback.CallbackStart(origObj,arg1,arg2);
		if(callback!=null)
			callback.CallbackStart(origObj,arg1,arg2);
		Object ret=arg1.invoke(origObj, arg2);
		long end=System.currentTimeMillis();
		if(_callback!=null)
			_callback.CallbackComplete(timeused(end,begin),origObj,arg1,arg2,ret);
		if(callback!=null)
			callback.CallbackComplete(timeused(end,begin),origObj,arg1,arg2,ret);
		return ret;
	}
	private float timeused(long end,long begin){
		return  (System.currentTimeMillis()-begin)/1000.0f;
	}
}
