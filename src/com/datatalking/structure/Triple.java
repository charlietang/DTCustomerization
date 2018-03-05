package com.datatalking.structure;

public class Triple<T,T1,T2> extends Pair<T, T1> {
	private static final long serialVersionUID = 7950869077047712974L;
	public T2 c;
	
	public int  Capacity(){
		return 3;
	}
	@Override
	public String toString(){
		return String.format("Compare:%s,a:%s,b:%s,c:%s",this.allCompare?"true":"false",this.a==null?"null":a.toString(),this.b==null?"null":this.b.toString(),this.c==null?"null":this.c.toString());
	}
	@SuppressWarnings("unchecked")
	public boolean set(String _char,Object value){
		if(super.set(_char, value))
			return true;
		switch(_char){
		case	"c":
			c=(T2) value;
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
	public Triple(){
		super();
	}
	public Triple(T one,T1 two){
		super(one,two);
	}
	public Triple(T one,T1 two,T2 three){
		super(one,two);
		c=three;
	}
	public Triple(T one,T1 two,boolean allcompare){
		super(one,two,allcompare);
	}
	public Triple(T one,T1 two,T2 three,boolean allcompare){
		super(one,two,allcompare);
		c=three;
	}
	
	public Triple<T,T1,T2> copy(){
		return new Triple<T, T1,T2>(a,b,c);
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
		if (!(obj instanceof Triple)) {
		    return false;
		}
		
		Triple<?, ?,?> tmpObj = (Triple<?, ?,?>) obj;
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
	public int compareTo(Triple<T, T1, T2> arg0) {
		if(equals(arg0))
			return 0;
		if(a instanceof Comparable){
			if(!allCompare)
				return ((Comparable<T>) this.a).compareTo(arg0.a);
			else{
				int i=((Comparable<T>) this.a).compareTo(arg0.a);				
				if(b instanceof Comparable){
					int j=((Comparable<T1>) this.b).compareTo(arg0.b);
					if(i==0){
						if(c instanceof Comparable){
							int k=((Comparable<T2>) this.c).compareTo(arg0.c);
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
