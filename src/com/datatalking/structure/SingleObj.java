/**
 * 
 */
package com.datatalking.structure;

import java.io.Serializable;

/**
 * @author tang
 *
 */
public class SingleObj implements Comparable<SingleObj>,Serializable,IFOperation {
	private static final long serialVersionUID = -1573228015600034526L;
	public Object a;
	
	public SingleObj(){}
	public SingleObj(Object one){
		a=one;
	}
	public int  Capacity(){
		return 1;
	}
	@Override
	public String toString(){
		return String.format("a:%s",this.a==null?"null":a.toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(SingleObj arg0) {
		if(equals(arg0))
			return 0;
		if(a instanceof Comparable){
				return ((Comparable<Object>) this.a).compareTo((Comparable<Object>) arg0.a);
		}
		return this.hashCode()>arg0.hashCode()?1:-1;
	}
	public int hashCode() {	
		return ((a==null)?0:a.hashCode());
	}
	public boolean equals(Object obj) {
		if (null == obj) {
			if(a instanceof Null)
				return true;
			return false;
		}
		if (!(obj instanceof Single)) {
		    return false;
		}
		
		Single<?> tmpObj = (Single<?>) obj;
		return hashCode()==tmpObj.hashCode()?true:false;
	}
	
	
	public boolean set(String _char,Object value){
		switch(_char){
		case	"a":
			a=value;
			return true;
		}
		return false;
	}
	public Object get(String _char){
		switch(_char){
		case	"a":
			return (Object) a;
		}
		return null;
	}
}
