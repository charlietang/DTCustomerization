package com.datatalking.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	public static <T> byte[] Serialize(T _object){
		if(_object==null)
			return null;
		ByteArrayOutputStream baos=null;
		ObjectOutputStream out=null;
		byte[] byteObject=null;
		baos = new ByteArrayOutputStream();
		try {
			out = new ObjectOutputStream(baos);
			String classname=_object.getClass().getSimpleName().toLowerCase();
			switch(classname){
			case "string":
				byteObject=((String) _object).getBytes("UTF-8");
				out.write(byteObject);
				System.out.printf("original string utf-8 : %s\n",new String(byteObject,"UTF-8"));
				
				break;			
			default:
				out.writeObject(_object);
				byteObject= baos.toByteArray();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e1){
			e1.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(baos!=null){
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return byteObject;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T  Deserialize(byte[] _bytes,Class<?> c) {
		if(_bytes==null||_bytes.length<1)
			return null;
		ByteArrayInputStream bais=null;
		ObjectInputStream in=null;
		T ret=null;
		try {
			String type=c.getSimpleName().toLowerCase();
			if(type.equalsIgnoreCase("string"))
				ret=(T) new String(_bytes,"UTF-8");
			else{
				bais = new ByteArrayInputStream(_bytes);
				in = new ObjectInputStream(bais);
				switch(type){
				case "string":
					ret=(T) new String(_bytes,"UTF-8");
					//ret =(T) in.readUTF();
					break;
				case "double":
					ret=(T)Double.valueOf(in.readDouble());
					break;
				default:
					ret = (T) in.readObject();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bais!=null){
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	@SuppressWarnings({ "unchecked" })
	public static <T> T Deserialize(byte[] _bytes){
		if(_bytes==null||_bytes.length<1)
			return null;
		ByteArrayInputStream bais=null;
		ObjectInputStream in=null;
		T ret=null;
		try {
			bais = new ByteArrayInputStream(_bytes);
			in = new ObjectInputStream(bais);
			ret = (T) in.readObject();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bais!=null){
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

}
