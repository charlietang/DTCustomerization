/**
 * 
 */
package com.datatalking.structure.multi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.datatalking.structure.Null;
import com.datatalking.structure.TripleObj;
import com.datatalking.utility.DataConvert;

/**
 * @author tang
 *
 */
public class AnyObj extends TripleObj{
	private static final long serialVersionUID = 5127423538605273791L;
	public List<LinkedHashMap<String,Object>> datas=new ArrayList<LinkedHashMap<String,Object>>();
	public Meta metas=null;
	
	private List<String> keys=null;
	public AnyObj(){
		super();
	}
	public AnyObj(Meta m){
		super();
		this.set(m);
	}
	public AnyObj(Meta m,String keycolumns){
		super();
		this.set(m);
		this.set(keycolumns);
	}
	public void set(Meta m){
		this.metas=m;
	}
	//"0|1|3|4|5"
	public void set(String keycolumns){
		if(keycolumns==null||keycolumns.length()==0)
			keys=Arrays.asList(new String[]{"a"});
		else
			keys=Arrays.asList(keycolumns.split("\\|"));
	}
	public int  Capacity(){
		return this.metas==null?0:this.metas.size();
	}
	public int size(){
		return datas.size();
	}
	public boolean set(String _char,Object value){
		if(super.set(_char, value))
			return true;
		switch(_char){
		case	"c":
			c= value;
			return true;
		}
		if(this.metas==null)
			return false;
		if(this.metas.get(_char)==null)
			return false;
		if(this.datas.size()==0){
			LinkedHashMap<String,Object> _tmp=new LinkedHashMap<String,Object>();
			_tmp.put(_char, value);
			datas.add(_tmp);
		}else{
			LinkedHashMap<String,Object> _tmp=this.datas.get(this.datas.size()-1);
			_tmp.put(_char, value);
		}
		return true;
	}
	public Object get(String _char){
		Object ret=null;
		switch(_char){
		case	"c":
			return (Object) c;
		default:
			ret= super.get(_char);
		}
		if(ret!=null)
			return ret;
		if(this.datas.size()==0)
			return null;
		LinkedHashMap<String,Object> _tmp=this.datas.get(this.datas.size()-1);
		if(_tmp==null)
			return null;
		return _tmp.get(_char);
	}
	public int hashCode() {
		return this.datas==null?0:this.datas.hashCode();
	}
	protected String getkey(){
		if(datas==null)
			return null;
		StringBuilder sb=new StringBuilder();
		for(LinkedHashMap<String,Object> m:datas){
			Object o=null;
			if(keys==null|keys.size()==0){
				o=m.get("a");
				sb.append(o==null?"":o.toString());//.append(";");
			}
			for(String k:keys){
				o=m.get(k);
				sb.append(o==null?"":o.toString()).append(",");
			}
			sb.append(";");
		}
		return sb.toString();
	}
	protected String getkey(int row){
		Map<String,Object> m=this.get(row);
		if(m==null)
			return null;
		StringBuilder sb=new StringBuilder();
		Object o=null;
		if(keys==null||keys.size()==0){
			o=m.get("a");
			sb.append(o==null?"":o.toString());//.append(";");
		}else{
			for(String k:keys){
				o=m.get(k);
				sb.append(o==null?"":o.toString()).append(",");
			}
		}
		sb.append(";");
		return sb.toString();
	}
	public boolean equals(Object obj) {
		if (null == obj) {
			if(a instanceof Null)
				return true;
			return false;
		}
		if (!(obj instanceof AnyObj)) {
		    return false;
		}
		
		AnyObj tmpObj = (AnyObj) obj;
		String mykey=this.getkey();
		String otherkey=tmpObj.getkey();
		if(mykey==null&&otherkey==null)
			return true;
		else if(mykey==null||otherkey==null)
			return false;
		else{
			return mykey.compareTo(otherkey)==0?true:false;
		}
	}
	public int compareTo(AnyObj arg0) {
		String mykey=this.getkey();
		String otherkey=arg0.getkey();
		if(mykey==null&&otherkey==null)
			return 0;
		else if(mykey==null||otherkey==null)
			return 1;
		else{
			return mykey.compareTo(otherkey);
		}
	}
	//======================================================
	public int put(Object ...objects){
		LinkedHashMap<String,Object> ret=new LinkedHashMap<String,Object>();
		int i=0;
		for(Object o:objects){
			MetaElement me=metas==null?null:metas.get(i++);
			ret.put(me==null?String.valueOf(i-1):me.ColName,o);
		}
		datas.add(ret);
		return datas.size()-1;
	}
	
	public Map<String,Object> get(int row){
		return datas.get(row);
	}
	public Object get(int row,int col){
		MetaElement m=metas.get(col);
		if(m==null)
			return null;
		return get(row,m.ColName);
	}
	public Object get(int row,String col){
		MetaElement m=metas.get(col);
		if(m==null)
			return null;
		LinkedHashMap<String,Object> _tmp=this.datas.get(row);
		if(_tmp==null)
			return null;
		return _tmp.get(col);
	}

	
	private Object ops(Object a,Object b,int ops){
		switch(ops){
		case	0:
			return b;
		case	1:
			if(a==null&&b==null)
				return null;
			if(a==null||b==null)
				return a==null?b:a;
			if(a instanceof Integer){
				if(b instanceof Integer)
					return ((Integer)a)+(Integer)b;
				else if(b instanceof Long)
					return ((Long)a)+(Long)b;
				else if(b instanceof Float)
					return ((Float)a)+(Float)b;
				else if(b instanceof Double)
					return ((Double)a)+(Double)b;
				else
					return a.toString()+b.toString();
			}else if(a instanceof Long){
				if(b instanceof Integer)
					return ((Long)a)+(Long)b;
				else if(b instanceof Long)
					return ((Long)a)+(Long)b;
				else if(b instanceof Float)
					return ((Float)a)+(Float)b;
				else if(b instanceof Double)
					return ((Double)a)+(Double)b;
				else
					return a.toString()+b.toString();
			}else if(a instanceof Float){
				if(b instanceof Integer)
					return ((Float)a)+(Float)b;
				else if(b instanceof Long)
					return ((Float)a)+(Float)b;
				else if(b instanceof Float)
					return ((Float)a)+(Float)b;
				else if(b instanceof Double)
					return ((Double)a)+(Double)b;
				else
					return a.toString()+b.toString();
			}else if(a instanceof Double){
				if(b instanceof Integer)
					return ((Double)a)+(Double)b;
				else if(b instanceof Long)
					return ((Double)a)+(Double)b;
				else if(b instanceof Float)
					return ((Double)a)+(Double)b;
				else if(b instanceof Double)
					return ((Double)a)+(Double)b;
				else
					return a.toString()+b.toString();
			}else{
				return a.toString()+b.toString();
			}
		case	2:
			if(a==null||b==null)
				return null;
			if(a instanceof Integer){
				if(b instanceof Integer)
					return ((Integer)a)>(Integer)b?b:a;
				else if(b instanceof Long)
					return ((Long)a)>(Long)b?b:a;
				else if(b instanceof Float)
					return ((Float)a)>(Float)b?b:a;
				else if(b instanceof Double)
					return ((Double)a)>(Double)b?b:a;
				else
					return a.toString().compareToIgnoreCase(b.toString())>0?b:a;
			}else if(a instanceof Long){
				if(b instanceof Integer)
					return ((Long)a)>(Long)b?b:a;
				else if(b instanceof Long)
					return ((Long)a)>(Long)b?b:a;
				else if(b instanceof Float)
					return ((Float)a)>(Float)b?b:a;
				else if(b instanceof Double)
					return ((Double)a)>(Double)b?b:a;
				else
					return a.toString().compareToIgnoreCase(b.toString())>0?b:a;
			}else if(a instanceof Float){
				if(b instanceof Integer)
					return ((Float)a)>(Float)b?b:a;
				else if(b instanceof Long)
					return ((Float)a)>(Float)b?b:a;
				else if(b instanceof Float)
					return ((Float)a)>(Float)b?b:a;
				else if(b instanceof Double)
					return ((Double)a)>(Double)b?b:a;
				else
					return a.toString().compareToIgnoreCase(b.toString())>0?b:a;
			}else if(a instanceof Double){
				if(b instanceof Integer)
					return ((Double)a)>(Double)b?b:a;
				else if(b instanceof Long)
					return ((Double)a)>(Double)b?b:a;
				else if(b instanceof Float)
					return ((Double)a)>(Double)b?b:a;
				else if(b instanceof Double)
					return ((Double)a)>(Double)b?b:a;
				else
					return a.toString().compareToIgnoreCase(b.toString())>0?b:a;
			}else{
				return a.toString().compareToIgnoreCase(b.toString())>0?b:a;
			}
		default:
			if(a==null||b==null)
				return null;
			if(a instanceof Integer){
				if(b instanceof Integer)
					return ((Integer)a)>(Integer)b?a:b;
				else if(b instanceof Long)
					return ((Long)a)>(Long)b?a:b;
				else if(b instanceof Float)
					return ((Float)a)>(Float)b?a:b;
				else if(b instanceof Double)
					return ((Double)a)>(Double)b?a:b;
				else
					return a.toString().compareToIgnoreCase(b.toString())>0?a:b;
			}else if(a instanceof Long){
				if(b instanceof Integer)
					return ((Long)a)>(Long)b?a:b;
				else if(b instanceof Long)
					return ((Long)a)>(Long)b?a:b;
				else if(b instanceof Float)
					return ((Float)a)>(Float)b?a:b;
				else if(b instanceof Double)
					return ((Double)a)>(Double)b?a:b;
				else
					return a.toString().compareToIgnoreCase(b.toString())>0?a:b;
			}else if(a instanceof Float){
				if(b instanceof Integer)
					return ((Float)a)>(Float)b?a:b;
				else if(b instanceof Long)
					return ((Float)a)>(Float)b?a:b;
				else if(b instanceof Float)
					return ((Float)a)>(Float)b?a:b;
				else if(b instanceof Double)
					return ((Double)a)>(Double)b?a:b;
				else
					return a.toString().compareToIgnoreCase(b.toString())>0?a:b;
			}else if(a instanceof Double){
				if(b instanceof Integer)
					return ((Double)a)>(Double)b?a:b;
				else if(b instanceof Long)
					return ((Double)a)>(Double)b?a:b;
				else if(b instanceof Float)
					return ((Double)a)>(Double)b?a:b;
				else if(b instanceof Double)
					return ((Double)a)>(Double)b?a:b;
				else
					return a.toString().compareToIgnoreCase(b.toString())>0?a:b;
			}else{
				return a.toString().compareToIgnoreCase(b.toString())>0?a:b;
			}
		}
	}
	private boolean isSame(Map<String,Object> curr,Map<String,Object> objs,Meta othermeta,List<String> mergeCols){
		Iterator<Entry<String,Object>> iter=objs.entrySet().iterator();
		boolean b=false;
		while(iter.hasNext()){
			Entry<String,Object> e=iter.next();
			if(!mergeCols.contains(e.getKey()))
				continue;
			if(curr.containsKey(e.getKey())){
				int ops=othermeta.get(e.getKey()).Ops;
				curr.put(e.getKey(), ops(curr.get(e.getKey()),e.getValue(),ops));
				b=true;
				//break;
			}			
		}
		return b;
	}
	public AnyObj merge(AnyObj datas,String mergeCol){ 
		List<String> mergeCols=Arrays.asList(mergeCol.split("\\|"));		
		for(int j=0;j<datas.datas.size();j++){
			boolean b=false;
			for(int i=0;i<this.datas.size();i++){
				String mykey=this.getkey(i);
				String otherkey=datas.getkey(j);
				if(!mykey.equalsIgnoreCase(otherkey))
					continue;
				if(isSame(this.datas.get(i),datas.get(j),datas.metas,mergeCols)){
					b=true;
				}			
			}
			if(!b){
				Object objs[]=new Object[this.metas.size()];
				for(int h=0;h<objs.length;h++){
					Map<String,Object> _m=datas.get(j);
					MetaElement _meta=this.metas.get(h);
					objs[h]=_m.get(_meta.ColName);
				}
				this.put(objs);
			}
		}
		return this;
	}
	
	private boolean isSame(Object objs[]){
		Object temp=objs[0];
		for(Object obj:objs){
			if(temp==null&&obj==null) {
				continue;
				//temp=obj;
			}else if(temp!=null&&obj==null)
				return false;
			else if(temp==null&&obj!=null)
				return false;
			else{
				if( temp.toString().equals(obj.toString()))
					continue;//temp=obj;
				else
					return false;
			}
		}
		return true;
	}
	public static final String CHECKS="CHECKS";
	public static final String REASON="REASON";
	
	public static final String AUTO_CHECKED="AUTO CHECKED";
	public static final String DIFFERENT="DIFFERENT";
	private int min(Object objs[]){
		int temp=Integer.MAX_VALUE;
		for(Object o:objs){
			int i=(o==null?0:DataConvert.atoi(o.toString(), -1));
			if(i<temp)
				temp=i;
		}
		return temp;
	}
	private int max(Object objs[]){
		int temp=0;
		for(Object o:objs){
			int i=(o==null?0:DataConvert.atoi(o.toString(), -1));
			if(i>temp)
				temp=i;
		}
		return temp;
	}
	private int diff(Object objs[]){
		return max(objs)-min(objs);
	}
	private int toInt(String s){
		if(s==null)
			return 0;
		try{
			return Integer.valueOf(s);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		return 0;
	}
	private void check(int row,Object objs[]){
		//int i=this.metas.index("CHECKS");
		//int j=this.metas.index("REASON");
		LinkedHashMap<String,Object> _origs=this.datas.get(row);
		if(_origs!=null&&_origs.containsKey(REASON)){//j!=-1){
			Object o=this.datas.get(row).get(REASON);
			if(o!=null){
				int sum=0;
				String ss[]=o.toString().split(",");
				if(ss.length>0&&ss[0].contains(":")){
					for(String s:ss){
						String _ss[]=s.split(":");
						if(_ss.length==2){
							int p=toInt(_ss[1]);
							sum+=(p>1)?p-1:p;
							//System.out.printf("%d\t  _ss[1]=%s\n",sum,_ss[1]);
						}
					}
				}
				if(diff(objs)==sum){
					//LinkedHashMap<String,Object> _origs=this.datas.get(row);
					//if(_origs==null)//||_origs.containsKey(CHECKS))
					//	return;
					_origs.put(CHECKS, AUTO_CHECKED);
					this.datas.set(row, _origs);
				}else{
					//LinkedHashMap<String,Object> _origs=this.datas.get(row);
					//if(_origs==null)//||_origs.size()<=i)
					//	return;
					_origs.put(CHECKS,DIFFERENT);
					this.datas.set(row, _origs);
				}
					
			}else{
				//LinkedHashMap<String,Object> _origs=this.datas.get(row);
				//if(_origs==null)//||_origs.size()<=i)
				//	return;
				_origs.put(CHECKS, DIFFERENT);
				this.datas.set(row, _origs);
			}
		}
	}
	//11,14,//17
	public AnyObj check(String checkOps){
		String ss[]=checkOps.split("\\|");
		Object objs[]=new Object[ss.length];
		for(int i=0;i<this.size();i++){
			int j=0;
			for(String s:ss)
				objs[j++]=this.get(i, s);
			if(!isSame(objs)){
				check(i,objs);
			}else
				this.datas.get(i).put("CHECKS", null);
		}
		return this;
	}
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<metas.size();i++)
			sb.append(metas.get(i).ColName).append("\t");
		sb.append("\n---------------------------------------------------------------------------------\n");
		for(LinkedHashMap<String,Object> objs:datas){
			Iterator<Object> iter=objs.values().iterator();
			while(iter.hasNext()){
				Object obj=iter.next();
				sb.append(obj==null?"null":obj.toString()).append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	
	public static void main(String args[]){
		AnyObj any=new AnyObj();
		Meta meta=new Meta();
		meta.put("SENDER",1, "String",MetaElement.REPLACE);
		meta.put("RECEIVER",1, "String",MetaElement.REPLACE);
		meta.put("MSG_CLASS",1, "String",MetaElement.REPLACE);
		meta.put("RCV_CNT",2, "String",MetaElement.ADD);
		meta.put("PROC_CNT",2, "String",MetaElement.REPLACE);
		meta.put("SND_CNT",2, "String",MetaElement.REPLACE);
		meta.put(CHECKS,1, "String",MetaElement.REPLACE);
		meta.put(REASON,1, "String",MetaElement.REPLACE);
		any.set(meta);
		any.set("SENDER|RECEIVER|MSG_CLASS");
		any.put("SenderA","ReceiverA","322",1,1,1,null,null);
		any.put("SenderB","ReceiverA","322",null,null,null,null,null);
		any.put("SenderC","ReceiverA","322",null,null,1,null,null);
		any.put("SenderD","ReceiverA","322",null,1,1,null,null);
		
		
		//System.out.println(any.toString());
		AnyObj any1=new AnyObj(meta);
		any1.set("SENDER|RECEIVER|MSG_CLASS");
		any1.put("SenderB","ReceiverA","322",2,null,null,null,null);
		AnyObj any2=new AnyObj(meta);
		any2.set("SENDER|RECEIVER|MSG_CLASS");
		any2.put("SenderB","ReceiverA","322",1,1,null,null,null);
		any2.put("SenderE","ReceiverE","322",5,1,5,5,5);
		System.out.println(any.merge(any1,"RCV_CNT|PROC_CNT|SND_CNT")
				.merge(any2, "RCV_CNT|PROC_CNT")
				.check("RCV_CNT|PROC_CNT|SND_CNT"));
	}
}
