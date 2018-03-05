/**
 * 
 */
package com.datatalking.debug;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;


/**
 * @author tang
 *
 */
public class CommonDynProxyCallback implements IFDynProxyCallback {
	private static final Logger log=Logger.getLogger(CommonDynProxyCallback.class);
	@Override
	public void CallbackStart(Object classinstance, Method method, Object[] args) {
		log.debug(Stringfy(classinstance,method,args));
	}
	@Override
	public void CallbackComplete(float Seconds, Object classinstance,Method method, Object[] args, Object ret) {
		log.debug(String.format("%.3f Seconds,%s\t%s",Seconds,Stringfy(classinstance,method,args),Stringfy(ret)));
	}
	private String Stringfy(Object arg0, Method arg1, Object[] arg2){
		StringBuilder sb=new StringBuilder();
		int i=0;
		sb.append(arg0.getClass().getName()).append("[").append(Integer.toHexString(arg0.hashCode())).append("].")
			.append(arg1.getName()).append("(");
		for(Class<?> t:arg1.getParameterTypes()){
			if(i>0)
				sb.append(" , ");
			sb.append(String.format("%s %s",t.getName(),arg2==null?"void":i<arg2.length?(arg2[i]==null?"null":Stringfy(arg2[i])):"out index"));
			i++;
		}
		if(i==0)
			sb.append("void");
		sb.append(")\t->").append(arg1.getReturnType().getName());
		
		return sb.toString();
	}
	private String Stringfy(Object obj){
		if(obj==null)
			return "null";
		StringBuilder sb=new StringBuilder();
		int i=0;
		if(obj.getClass().isArray()){
			Object objs[]=((Object[])obj);
			sb.append("[");
			for(Object _obj:objs){
				if(i>0)
					sb.append(",");
				sb.append(_obj.toString());
				i++;
			}
			if(i==0)
				sb.append("empty");
			sb.append("]");
		}else{
			sb.append(obj.toString());
		}
		return sb.toString();
	}
}
