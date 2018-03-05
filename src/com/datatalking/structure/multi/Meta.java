/**
 * 
 */
package com.datatalking.structure.multi;

import java.util.ArrayList;
import java.util.List;

public class Meta {
public List<MetaElement> metas=new ArrayList<MetaElement>();
	
	public int put(MetaElement m){
		metas.add(m);
		return metas.size()-1;
	}
	public int put(String col,int type,String typename,int ops){
		return put(new MetaElement(col,type,typename,ops));
	}
	public int index(MetaElement meta){
		if(meta==null)
			return -1;
		if(meta.ColName==null)
			return -1;
		return index(meta.ColName);
	}
	public int index(String colname){
		for(int i=0;i<metas.size();i++){
			MetaElement m=metas.get(i);
			if(m==null)
				continue;
			if(m.ColName==null)
				continue;
			if(m.ColName.equalsIgnoreCase(colname))
				return i;
		}
		return -1;
	}
	public MetaElement get(int pos){
		return metas.get(pos);
	}
	public MetaElement get(String colname){
		for(MetaElement m:metas){
			if(m.ColName.equalsIgnoreCase(colname))
				return m;
		}
		return null;
	}
	
	public int size(){
		return metas.size();
	}
}
