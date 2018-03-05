package com.datatalking.structure;


/*
 *	(1,2,false) equal (1,3,false) = true	*1
 *	(1,2,false) equal (1,2,true) = false	*2
 *	(1,2,true) equal (1,2,false) = true		*3
 * 先实例的pair(T,T1,true)，那么后实例是否false也    会      与先实例比较T,T1,实例3;
 * 先实例的pair(T,T1,false)，那么后实例是否true也   不会   与先实例比较T,T1,实例2;
*/

public class Pair<T,T1> extends Single<T> {
	private static final long serialVersionUID = -5885314510100836511L;
	public T1 b;
	public boolean allCompare=false;
	
	public int  Capacity(){
		return 2;
	}
	@SuppressWarnings("unchecked")
	public boolean set(String _char,Object value){
		if(super.set(_char, value))
			return true;
		switch(_char){
		case	"b":
			b=(T1) value;
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
	public Pair(){}
	public Pair(T one,T1 two){
		a=one;
		b=two;
	}
	public Pair(T one,T1 two,boolean allcompare){
		a=one;
		b=two;
		this.allCompare=allcompare;
	}
	public Pair<T,T1> copy(){
		return new Pair<T, T1>(a,b);
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
	public int compareTo(Pair<T, T1> arg0) {
		if(equals(arg0))
			return 0;
		if(a instanceof Comparable){
			if(!allCompare)
				return ((Comparable<T>) this.a).compareTo(arg0.a);
			else{
				int i=((Comparable<T>) this.a).compareTo(arg0.a);
				if(b instanceof Comparable){
					if(i==0)
						return ((Comparable<T1>) this.b).compareTo(arg0.b);
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
	public static void main(String args[]){
		Pair<Integer,Integer> ip1=new Pair<Integer,Integer>(1,2);
		Pair<Integer,Integer> ip2=new Pair<Integer,Integer>(1,3);
		Pair<Integer,Integer> _ip1=new Pair<Integer,Integer>(1,2,true);
		Pair<Integer,Integer> _ip2=new Pair<Integer,Integer>(1,3,true);
		
		System.out.printf("(1,2,false) equal (1,3,false) = %s\n",ip1.equals(ip2));
		System.out.printf("(1,2,true) equal (1,3,false) = %s\n",_ip1.equals(ip2));
		System.out.printf("(1,3,false) equal (1,2,true)  = %s\n",ip2.equals(_ip1));
		System.out.printf("(1,3,false) equal (1,3,true)  = %s\n",ip2.equals(_ip2));
		
		System.out.printf("(1,2,false) equal (1,2,true) = %s\n",ip1.equals(_ip1));
		System.out.printf("(1,2,true) equal (1,2,false) = %s\n",_ip1.equals(ip1));
	}
}
