/**
 * 
 */
package com.datatalking.structure;

import java.io.Serializable;

/**
 * @author tang
 *
 */
public class Single<T> implements Comparable<Single<T>>,Serializable,IFOperation{
	private static final long serialVersionUID = -3378230651291870260L;
	public T a;
	
	public Single(){}
	public Single(T one){
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
	public int compareTo(Single<T> arg0) {
		if(equals(arg0))
			return 0;
		if(a instanceof Comparable){
				return ((Comparable<T>) this.a).compareTo(arg0.a);
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
	
	
	@SuppressWarnings("unchecked")
	public boolean set(String _char,Object value){
		switch(_char){
		case	"a":
			a=(T) value;
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
