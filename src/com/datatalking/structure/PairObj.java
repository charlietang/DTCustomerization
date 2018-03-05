/**
 * 
 */
package com.datatalking.structure;

/**
 * @author tang
 *
 */
public class PairObj extends SingleObj {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7116876640676166109L;
	public Object b;
	public boolean allCompare=false;
	
	public int  Capacity(){
		return 2;
	}
	public boolean set(String _char,Object value){
		if(super.set(_char, value))
			return true;
		switch(_char){
		case	"b":
			b=value;
			return true;
		}
		return false;
	}
	public Object get(String _char){
		switch(_char){
		case	"b":
			return (Object) b;
		default:
			return super.get(_char);
		}
	}
	public PairObj(){}
	public PairObj(Object one,Object two){
		a=one;
		b=two;
	}
	public PairObj(Object one,Object two,boolean allcompare){
		a=one;
		b=two;
		this.allCompare=allcompare;
	}
	public PairObj copy(){
		return new PairObj(a,b);
	}
	public int hashCode() {
		//int ret = a.hashCode();//a.hashCode() ^ b.hashCode();  	
		int hasha=((a==null)?0:a.hashCode());
		return (allCompare==false?hasha:(
				String.format("%d, %d",hasha,(b==null?0:b.hashCode())).hashCode()
				//a.hashCode() ^ b.hashCode()
				));
	}
	public boolean equals(Object obj) {
		if (null == obj) {
			if(a instanceof Null)
				return true;
			return false;
		}
		if (!(obj instanceof Pair)) {
		    return false;
		}
		
		Pair<?, ?> tmpObj = (Pair<?, ?>) obj;
		if(allCompare==false){
			if(tmpObj.allCompare==false)
				return a.hashCode()==tmpObj.hashCode()?true:false;
			return false;
		}else{
			//if(tmpObj.allCompare==true)
				return hashCode()==tmpObj.hashCode()?true:false;//(a.equals(tmpObj.a))?(b.equals(tmpObj.b)):false;
			//return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public int compareTo(PairObj arg0) {
		if(equals(arg0))
			return 0;
		if(a instanceof Comparable){
			if(!allCompare)
				return ((Comparable<Object>) this.a).compareTo(arg0.a);
			else{
				int i=((Comparable<Object>) this.a).compareTo(arg0.a);
				if(b instanceof Comparable){
					if(i==0)
						return ((Comparable<Object>) this.b).compareTo(arg0.b);
					else
						return i;
				}
				else
					return i;
			}
		}
		return this.hashCode()>arg0.hashCode()?1:-1;
	}
	
	@Override
	public String toString(){
		return String.format("Compare:%s,a:%s,b:%s",this.allCompare?"true":"false",this.a==null?"null":a.toString(),this.b==null?"null":this.b.toString());
	}
}
