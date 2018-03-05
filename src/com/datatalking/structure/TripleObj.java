/**
 * 
 */
package com.datatalking.structure;

/**
 * @author tang
 *
 */
public class TripleObj extends PairObj {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2814541525243957933L;

	public Object c;
	
	public int  Capacity(){
		return 3;
	}
	@Override
	public String toString(){
		return String.format("Compare:%s,a:%s,b:%s,c:%s",this.allCompare?"true":"false",this.a==null?"null":a.toString(),this.b==null?"null":this.b.toString(),this.c==null?"null":this.c.toString());
	}
	public boolean set(String _char,Object value){
		if(super.set(_char, value))
			return true;
		switch(_char){
		case	"c":
			c= value;
			return true;
		}
		return false;
	}
	public Object get(String _char){
		switch(_char){
		case	"c":
			return (Object) c;
		default:
			return super.get(_char);
		}
	}
	public TripleObj(){
		super();
	}
	public TripleObj(Object one,Object two){
		super(one,two);
	}
	public TripleObj(Object one,Object two,Object three){
		super(one,two);
		c=three;
	}
	public TripleObj(Object one,Object two,boolean allcompare){
		super(one,two,allcompare);
	}
	public TripleObj(Object one,Object two,Object three,boolean allcompare){
		super(one,two,allcompare);
		c=three;
	}
	
	public TripleObj copy(){
		return new TripleObj(a,b,c);
	}
	public int hashCode() {
		//int ret = a.hashCode();//a.hashCode() ^ b.hashCode();  	
		int hasha=((a==null)?0:a.hashCode());
		return (allCompare==false?hasha:(
				String.format("%d,%d,%d",hasha,(b==null?0:b.hashCode()),(c==null?0:c.hashCode())).hashCode()
				//a.hashCode() ^ b.hashCode()
				));
	}
	public boolean equals(Object obj) {
		if (null == obj) {
			if(a instanceof Null)
				return true;
			return false;
		}
		if (!(obj instanceof TripleObj)) {
		    return false;
		}
		
		TripleObj tmpObj = (TripleObj) obj;
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
	public int compareTo(TripleObj arg0) {
		if(equals(arg0))
			return 0;
		if(a instanceof Comparable){
			if(!allCompare)
				return ((Comparable<Object>) this.a).compareTo(arg0.a);
			else{
				int i=((Comparable<Object>) this.a).compareTo(arg0.a);				
				if(b instanceof Comparable){
					int j=((Comparable<Object>) this.b).compareTo(arg0.b);
					if(i==0){
						if(c instanceof Comparable){
							int k=((Comparable<Object>) this.c).compareTo(arg0.c);
							if(j==0)
								return k;
							else
								return j;
						}else{
							return j;
						}						
					}
					else
						return i;
				}
				else
					return i;
			}
		}
		return this.hashCode()>arg0.hashCode()?1:-1;
	}
}
