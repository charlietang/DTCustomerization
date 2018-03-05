/**
 * 
 */
package com.datatalking.structure;

/**
 * @author tang
 *
 */
public class Forth<T, T1, T2, T3> extends Triple<T, T1, T2> {
	private static final long serialVersionUID = -491488764953365986L;
	public T3 d;
	
	public int  Capacity(){
		return 3;
	}
	@Override
	public String toString(){
		return String.format("Compare:%s,a:%s,b:%s,c:%s,d:%s",this.allCompare?"true":"false",this.a==null?"null":a.toString(),this.b==null?"null":this.b.toString()
				,this.c==null?"null":this.c.toString()
				,this.d==null?"null":this.d.toString()		);
	}
	@SuppressWarnings("unchecked")
	public boolean set(String _char,Object value){
		if(super.set(_char, value))
			return true;
		switch(_char){
		case	"d":
			d=(T3) value;
			return true;
		}
		return false;
	}
	public Object get(String _char){
		switch(_char){
		case	"d":
			return (Object) c;
		default:
			return super.get(_char);
		}
	}
	public Forth(){
		super();
	}
	public Forth(T one,T1 two){
		super(one,two);
	}
	public Forth(T one,T1 two,T2 three){
		super(one,two,three);
	}
	public Forth(T one,T1 two,T2 three,T3 forth){
		super(one,two,three);
		d=forth;
	}
	public Forth(T one,T1 two,boolean allcompare){
		super(one,two,allcompare);
	}
	public Forth(T one,T1 two,T2 three,boolean allcompare){
		super(one,two,allcompare);
		c=three;
	}
	public Forth(T one,T1 two,T2 three,T3 forth,boolean allcompare){
		super(one,two,allcompare);
		d=forth;
	}
	public Forth<T,T1,T2,T3> copy(){
		return new Forth<T, T1,T2,T3>(a,b,c,d);
	}
	public int hashCode() {
		//int ret = a.hashCode();//a.hashCode() ^ b.hashCode();  	
		int hasha=((a==null)?0:a.hashCode());
		return (allCompare==false?hasha:(
				String.format("%d,%d,%d,%d",hasha,(b==null?0:b.hashCode()),(c==null?0:c.hashCode()),(d==null?0:d.hashCode())).hashCode()
				//a.hashCode() ^ b.hashCode()
				));
	}
	public boolean equals(Object obj) {
		if (null == obj) {
			if(a instanceof Null)
				return true;
			return false;
		}
		if (!(obj instanceof Forth)) {
		    return false;
		}
		
		Forth<?, ?,?,?> tmpObj = (Forth<?, ?,?,?>) obj;
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
	public int compareTo(Forth<T, T1, T2,T3> arg0) {
		if(equals(arg0))
			return 0;
		if(a instanceof Comparable){
			if(!allCompare)
				return ((Comparable<T>) this.a).compareTo(arg0.a);
			else{
				int i=((Comparable<T>) this.a).compareTo(arg0.a);				
				if(i==0 && b instanceof Comparable){
					int j=((Comparable<T1>) this.b).compareTo(arg0.b);
					if(j==0 && c instanceof Comparable){
						int k=((Comparable<T2>) this.c).compareTo(arg0.c);
						if(k==0 && d instanceof Comparable){
							int h=((Comparable<T3>) this.d).compareTo(arg0.d);
							return h;
						}else{
							return k;
						}						
					}
					else
						return j;
				}
				else
					return i;
			}
		}
		return this.hashCode()>arg0.hashCode()?1:-1;
	}
}
