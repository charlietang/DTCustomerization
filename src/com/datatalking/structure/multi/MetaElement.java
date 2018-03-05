/**
 * 
 */
package com.datatalking.structure.multi;


public class MetaElement {
	public static final int	REPLACE=0;
	public static final int	ADD=1;
	public static final int	MIN=2;
	public static final int	MAX=3;
	public String ColName;
	public int Type;
	public String TypeName;
	public int Ops=REPLACE;	//0-replace;1-add;2-min;3-max
	public MetaElement(String n,int t,String tn){
		this.ColName=n;
		this.Type=t;
		this.TypeName=tn;
	}
	public MetaElement(String n,int t,String tn,int ops){
		this.ColName=n;
		this.Type=t;
		this.TypeName=tn;
		this.Ops=ops;
	}
}
