/**
 * 
 */
package com.datatalking.structure;

/**
 * @author tang
 *
 */
public class ForthObj extends TripleObj {
	private static final long serialVersionUID = 7563560641256650373L;
	public Object d;
	
	public int  Capacity(){
		return 4;
	}
	@Override
	public String toString(){
		return String.format("Compare:%s,a:%s,b:%s,c:%s,d:%s",this.allCompare?"true":"false",this.a==null?"null":a.toString(),this.b==null?"null":this.b.toString()
				,this.c==null?"null":this.c.toString()
				,this.d==null?"null":this.d.toString()		);
	}
	public boolean set(String _char,Object value){
		if(super.set(_char, value))
			return true;
		switch(_char){
		case	"d":
			d= value;
			return true;
		}
		return false;
	}
	public Object get(String _char){
		switch(_char){
		case	"d":
			return (Object) d;
		default:
			return super.get(_char);
		}
	}
	public ForthObj(){
		super();
	}
	public ForthObj(Object one,Object two){
		super(one,two);
	}
	public ForthObj(Object one,Object two,Object three){
		super(one,two);
		c=three;
	}
	public ForthObj(Object one,Object two,Object three,Object forth){
		super(one,two,three);
		d=forth;
	}
	public ForthObj(Object one,Object two,boolean allcompare){
		super(one,two,allcompare);
	}
	public ForthObj(Object one,Object two,Object three,boolean allcompare){
		super(one,two,allcompare);
		c=three;
	}
	public ForthObj(Object one,Object two,Object three,Object forth,boolean allcompare){
		super(one,two,three,allcompare);
		d=forth;
	}
	public ForthObj copy(){
		return new ForthObj(a,b,c,d);
	}
	public int hashCode() {
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
		if (!(obj instanceof ForthObj)) {
		    return false;
		}
		
		ForthObj tmpObj = (ForthObj) obj;
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
	public int compareTo(ForthObj arg0) {
		if(equals(arg0))
			return 0;
		if(a instanceof Comparable){
			if(!allCompare)
				return ((Comparable<Object>) this.a).compareTo(arg0.a);
			else{
				int i=((Comparable<Object>) this.a).compareTo(arg0.a);				
				if(i==0 && b instanceof Comparable){
					int j=((Comparable<Object>) this.b).compareTo(arg0.b);
					if(j==0 && c instanceof Comparable){
						int k=((Comparable<Object>) this.c).compareTo(arg0.c);
						if(k==0 && d instanceof Comparable){
							int h=((Comparable<Object>) this.d).compareTo(arg0.d);
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
