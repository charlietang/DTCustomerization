/**
 * 
 */
package com.datatalking.debug;

import java.lang.reflect.Method;


/**
 * @author tang dalong
 * @
 *
 */
public interface IFDynProxyCallback {
	public void CallbackStart(Object classinstance, Method method, Object[] args);
	public void CallbackComplete(float Seconds,Object classinstance, Method method, Object[] args,Object ret);
}
