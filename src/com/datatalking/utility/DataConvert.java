package com.datatalking.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.datatalking.exception.AppException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class DataConvert {	
	public static boolean ErrorPrint=false;
	public enum datatype{
		BOOLEAN("bool"),SHORT("short"),INT("int"),LONG("long"),FLOAT("float"),DOUBLE("double"),DATE("date"),TIMESTAMP("timestamp"),STRING("string"),DISPLAY("display"),NULL("null"),CLASS("class"),JsonOBJECT("jsonobject");
		private String value=null;
		private datatype(String value){
			this.value=value;
		}
		public boolean isNull(datatype dt){
			if(dt==null||datatype.NULL==dt)
				return true;
			return false;
		}
		public Object is(String value){
			if(value==null||"null".equalsIgnoreCase(value))
				return null;
			switch(this.value){
			case	"bool":
				if("true".equalsIgnoreCase(value))
					return true;
				else if("false".equalsIgnoreCase(value))
					return false;
				else
					return null;//Boolean.parseBoolean(value);
				
			case	"timestamp":
				try{
					long l=Long.parseLong(value);
					if(l>=0)
						return new Timestamp(l);
					return Timestamp.valueOf(value);
				}catch(NumberFormatException  e){
				
				}catch(IllegalArgumentException e){
				}
				return null;
				
			case	"date":
				try{
					long l=Long.parseLong(value);
					if(l>=0)
						return new Date(l);
					return new Date(Timestamp.valueOf(value).getTime());
				}catch(NumberFormatException  e){
				
				}catch(IllegalArgumentException e){
				}
				return null;
				
			case	"short":
				try{
					return Short.parseShort(value);
				}catch(NumberFormatException  e){
				
				}
				return null;
				
			case	"int":
				try{
					return Integer.parseInt(value);
				}catch(NumberFormatException  e){
				
				}
				return null;
			case	"long":
				try{
					return Long.parseLong(value);
				}catch(NumberFormatException  e){
				
				}
				return null;	
			case	"float":
				try{
					return Float.parseFloat(value);
				}catch(NumberFormatException  e){
				
				}
				return null;
				
			case	"double":
				try{
					return Double.parseDouble(value);
				}catch(NumberFormatException  e){
				
				}
				return null;
			case	"class":
				if(value.contains(".")){
					try {
						return Class.forName(value);
					} catch (ClassNotFoundException e) {
						return null;
					}
				}
				return null;
			case	"jsonobject":
				if(value.startsWith("{")&&value.endsWith("}")){
					return value;
				}
				return null;
			default:
				return value;
			}
			
			
		}
		public String getValue(){
			return this.value;
		}
	}	
	public static datatype is(String value){
		@SuppressWarnings("unused")
		Object obj=null;
		if(value==null||value.equalsIgnoreCase("null"))
			return datatype.NULL;
		if((obj=datatype.BOOLEAN.is(value))!=null)
			return datatype.BOOLEAN;
		else if((obj=datatype.TIMESTAMP.is(value))!=null)
			return datatype.TIMESTAMP;
		else if((obj=datatype.DATE.is(value))!=null)
			return datatype.DATE;
		else if((obj=datatype.LONG.is(value))!=null)
			return datatype.LONG;
		else if((obj=datatype.INT.is(value))!=null)
			return datatype.INT;
		else if((obj=datatype.SHORT.is(value))!=null)
			return datatype.SHORT;
		else if((obj=datatype.DOUBLE.is(value))!=null)
			return datatype.DOUBLE;
		else if((obj=datatype.FLOAT.is(value))!=null)
			return datatype.FLOAT;
		else if((obj=datatype.CLASS.is(value))!=null)
			return datatype.CLASS;
		else if((obj=datatype.JsonOBJECT.is(value))!=null)
			return datatype.JsonOBJECT;
		else	
			return datatype.STRING;
	}
	public static Object value(String value){
		Object obj=null;
		if(value==null||value.equalsIgnoreCase("null"))
			return null;
		if((obj=datatype.BOOLEAN.is(value))!=null)
			return obj;
		else if((obj=datatype.TIMESTAMP.is(value))!=null)
			return obj;
		else if((obj=datatype.DATE.is(value))!=null)
			return obj;
		else if((obj=datatype.LONG.is(value))!=null)
			return obj;
		else if((obj=datatype.INT.is(value))!=null)
			return obj;
		else if((obj=datatype.SHORT.is(value))!=null)
			return obj;
		else if((obj=datatype.DOUBLE.is(value))!=null)
			return obj;
		else if((obj=datatype.FLOAT.is(value))!=null)
			return obj;
		else if((obj=datatype.CLASS.is(value))!=null)
			return obj;
		else	
			return value;
	}
	public static Object value(String value,String type){
		if(value==null||value.equalsIgnoreCase("null"))
			return null;
		if(datatype.JsonOBJECT.is(value)!=null&&datatype.CLASS.is(type)!=null){
			try {
				return ObjfromJson(value,(Class<?>) value(type));
			} catch (AppException e) {
				e.printStackTrace();
			}
			return null;
		}
		return value(value);
	}
	
	public static Set<String> StrNull=new HashSet<String>();
	static {
		StrNull.add("未知");
		StrNull.add("任意");
		StrNull.add("null");
		StrNull.add("NULL");
		StrNull.add("Null");
		StrNull.add("NA");
		StrNull.add("N/A");
		StrNull.add("na");
		StrNull.add("n/a");
		StrNull.add("无");
		StrNull.add("");
	}
	public static Object ato(datatype type,String value,String ... ext){
		Object ret=null;
		switch(type){
		case BOOLEAN:
			ret=atoBool(value);
			break;
		case SHORT:
			ret=atoS(value);
			break;
		case INT:
			ret=atoI(value);
			break;
		case LONG:
			ret=atoL(value);
			break;
		case FLOAT:
			ret=atoF(value);
			break;
		case DOUBLE:
			ret=atoD(value);
			break;
		case DATE:
			ret=atoDate(value,((ext==null||ext.length<1)?null:ext[0]));
			break;
		case	TIMESTAMP:
			ret=atoTs(value);
			break;
		case	DISPLAY:
			ret=atoDisplay(value,((ext==null||ext.length<1)?null:ext[0]));
			break;
		case	CLASS:
			ret=atoClass(value);
			break;
		case	JsonOBJECT:
			ret=atoObj(value,((ext==null||ext.length<1)?null:ext[0]));
			break;
		case	STRING:
			ret=value;
		default:
			break;
		}
		return ret;
	}
	public static SimpleDateFormat china_standard_full_dt_format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String	trim(String value){
		if(value==null)
			return null;
		return value.trim();
	}
	
	public static Class<?>	atoClass(String value){
		try {
			return Class.forName(value);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Object	atoObj(String value,String classname){
		if(datatype.JsonOBJECT.is(value)!=null&&datatype.CLASS.is(classname)!=null){
			try {
				return ObjfromJson(value,(Class<?>) value(classname));
			} catch (AppException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static Date		atoDate(String value,SimpleDateFormat formater){ 
		if(value==null||formater==null)
			return null;
		try {
			return formater.parse(value);
		} catch (ParseException e) {
			if(ErrorPrint)	e.printStackTrace();
		}
		return null;
	}
	public static Date		atoDate(String value,String format){ 
		if(value==null||value.trim().length()<8)
			return new Date();
		SimpleDateFormat formater=null;
		try{
			if(format==null||format.trim().length()<2)
				formater=china_standard_full_dt_format;
			else
				formater=new java.text.SimpleDateFormat(format);
		}catch(NullPointerException|IllegalArgumentException e){
			if(ErrorPrint)	e.printStackTrace();
			return null;
		}
		
		try {
			return formater.parse(value);
		} catch (ParseException e) {
			if(ErrorPrint)	e.printStackTrace();
		}
		return null;
	}
	public static Timestamp atoTs(String value){
		try{
			return Timestamp.valueOf(value);
		}catch(IllegalArgumentException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return null;
	}
	public static Boolean	atoBool(String value){
		try{
			return Boolean.parseBoolean(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return null;
	}
	public static Short		atoS(String value){
		try{
			return Short.parseShort(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return null;
	}
	public static Integer	atoI(String value){
		try{
			return Integer.parseInt(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return null;
	}
	public static Long		atoL(String value){
		try{
			return Long.parseLong(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return null;
	}
	public static Float		atoF(String value){
		try{
			return Float.parseFloat(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return null;
	}
	public static Double	atoD(String value){
		try{
			return Double.parseDouble(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return null;
	}
	public static String	atoDisplay(String value){
		if(value==null)
			return "无";
		return value;
	}
	public static String	atoDisplay(String value,String _default){
		if(_default==null)
			return atoDisplay(value);
		else{
			if(value==null)
				return _default;
			return value;
		}
	}
	
	public static boolean	atobool(String value,boolean _default){
		try{
			return Boolean.parseBoolean(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return _default;
	}
	public static short		atos(String value,short _default){
		try{
			return Short.parseShort(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return _default;
	}
	public static int 		atoi(String value,int _default){
		try{
			return Integer.parseInt(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return _default;
	}
	public static long 		atol(String value,long _default){
		try{
			return Long.parseLong(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return _default;
	}
	public static float 	atof(String value,float _default){
		try{
			return Float.parseFloat(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return _default;
	}
	public static double 	atod(String value,double _default){
		try{
			return Double.parseDouble(trim(value));
		}catch(NumberFormatException e){
			if(ErrorPrint)	 e.printStackTrace();
		}catch(NullPointerException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return _default;
	}
	public static String	atoStr(String value){
		if(value==null)
			return null;
		String v=value.trim();
		if(StrNull.contains(v))
			return null;
		return v;
	}
	public static String	atoStr(String value,String _default){
		if(value==null)
			return _default;
		String v=value.trim();
		if(StrNull.contains(v))
			return _default;
		return v;
	}
	
	public static String	toa(Class<?> value){
		if(value==null)
			return null;
		return value.getName();
	}
	public static String	toa(Object value){
		if(value==null)
			return null;
		try {
			return toJson(value);
		} catch (AppException e) {
			return null;
		}
	}
	public static String	toa(Boolean value){
		if(value==null)
			return null;
		return value.toString();
	}
	public static String	toa(Short value){
		if(value==null)
			return null;
		return value.toString();
	}
	public static String	toa(Integer value){
		if(value==null)
			return null;
		return value.toString();
	}
	public static String	toa(Long value){
		if(value==null)
			return null;
		return value.toString();
	}
	public static String	toa(Float value){
		if(value==null)
			return null;
		return value.toString();
	}
	public static String	toa(Double value){
		if(value==null)
			return null;
		return value.toString();
	}
	public static String	toa(Date value,String ... formater){
		if(value==null)
			return null;
		if(formater==null||formater.length<1)
			return china_standard_full_dt_format.format(value);
		try{
			return (new java.text.SimpleDateFormat(formater[0])).format(value);
		}catch(NullPointerException|IllegalArgumentException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return null;
	}
	public static String	toa(Timestamp value,String ... formater){
		if(value==null)
			return null;
		if(formater==null||formater.length<1)
			return china_standard_full_dt_format.format(value);
		try{
			return (new java.text.SimpleDateFormat(formater[0])).format(value);
		}catch(NullPointerException|IllegalArgumentException e){
			if(ErrorPrint)	 e.printStackTrace();
		}
		return null;
	}

	private static final String _COMMON="'";
	public static final String _NULL="null";
	public static String	toSQLa(Boolean value){
		String str=toa(value);
		if(str==null)
			return _NULL;
		return str;				
	}
	public static String	toSQLa(Short value){
		String str=toa(value);
		if(str==null)
			return _NULL;
		return str;
	}
	public static String	toSQLa(Integer value){
		String str=toa(value);
		if(str==null)
			return _NULL;
		return str;
	}
	public static String	toSQLa(Long value){
		String str=toa(value);
		if(str==null)
			return _NULL;
		return str;
	}
	public static String	toSQLa(Float value){
		String str=toa(value);
		if(str==null)
			return _NULL;
		return str;
	}
	public static String	toSQLa(Double value){
		String str=toa(value);
		if(str==null)
			return _NULL;
		return str;
	}
	public static String	toSQLa(Date value){
		String str=toa(value);
		if(str==null)
			return _NULL;
		return _COMMON+str+_COMMON;
	}
	public static String	toSQLa(Timestamp value){
		String str=toa(value);
		if(str==null)
			return _NULL;
		return _COMMON+str+_COMMON;
	}
	
	private static final String _a1="（",_a2="）",_a1_="(",_a2_=")";
	private static final String _b1="【",_b2="】",_b1_="[",_b2_="]";
	private static final String _c1="『",_c2="』",_c1_="{",_c2_="}";
	private static final String _d1="“",_d2="”",_d_="\"";
	public static boolean ContainSpecialChar(String value){
		if(value==null||value.length()<1)
			return false;
		if(value.contains(_a1)||value.contains(_a2)||value.contains(_b1)||value.contains(_b2)
				||value.contains(_c1)||value.contains(_c2)||value.contains(_d1)||value.contains(_d2))
			return true;
		return false;
	}
	public static String ReplaceSpecialChar(String value){
		if(ContainSpecialChar(value)){
			return value.replace(_a1, _a1_).replace(_a2, _a2_).replace(_b1,_b1_).replace(_b2,_b2_)
			.replace(_c1, _c1_).replace(_c2,_c2_).replace(_d1,_d_).replace(_d2,_d_);
		}
		return value;
	}
	public static String	toSQLa(String value){
		if(value==null||value.isEmpty()||value.trim().length()<1)
			return _NULL;
		return _COMMON+ReplaceSpecialChar(value.trim())+_COMMON;
	}
	public static String	toSQLa(String value,String charset){	//"UTF-8"
		if(value==null||value.isEmpty()||value.trim().length()<1)
			return _NULL;
		String encoding=null;
		try {
			encoding = URLEncoder.encode((_COMMON+value.trim()+_COMMON),charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return encoding==null?_NULL:encoding;
	}
	
	public static String gbk2utf8(String gbk) {
        String l_temp = GBK2Unicode(gbk);
        l_temp = unicodeToUtf8(l_temp);
 
        return l_temp;
    }
 
    public static String utf82gbk(String utf) {
        String l_temp = utf8ToUnicode(utf);
        l_temp = Unicode2GBK(l_temp);
 
        return l_temp;
    }
 
    /**
     *
     * @param str
     * @return String
     */
 
    public static String GBK2Unicode(String str) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char chr1 = (char) str.charAt(i);
 
            if (!isNeedConvert(chr1)) {
                result.append(chr1);
                continue;
            }
 
            result.append("\\u" + Integer.toHexString((int) chr1));
        }
 
        return result.toString();
    }
 
    /**
     *
     * @param dataStr
     * @return String
     */
 
    public static String Unicode2GBK(String dataStr) {
        int index = 0;
        StringBuffer buffer = new StringBuffer();
 
        int li_len = dataStr.length();
        while (index < li_len) {
            if (index >= li_len - 1
                    || !"\\u".equals(dataStr.substring(index, index + 2))) {
                buffer.append(dataStr.charAt(index));
 
                index++;
                continue;
            }
 
            String charStr = "";
            charStr = dataStr.substring(index + 2, index + 6);
 
            char letter = (char) Integer.parseInt(charStr, 16);
 
            buffer.append(letter);
            index += 6;
        }
 
        return buffer.toString();
    }
 
    public static boolean isNeedConvert(char para) {
        return ((para & (0x00FF)) != para);
    }
 
    /**
     * utf-8 转unicode
     *
     * @param inStr
     * @return String
     */
    public static String utf8ToUnicode(String inStr) {
        char[] myBuffer = inStr.toCharArray();
 
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < inStr.length(); i++) {
            UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
            if (ub == UnicodeBlock.BASIC_LATIN) {
                sb.append(myBuffer[i]);
            } else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                int j = (int) myBuffer[i] - 65248;
                sb.append((char) j);
            } else {
                short s = (short) myBuffer[i];
                String hexS = Integer.toHexString(s);
                String unicode = "\\u" + hexS;
                sb.append(unicode.toLowerCase());
            }
        }
        return sb.toString();
    }
 
    /**
     *
     * @param theString
     * @return String
     */
    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
    public static int hash37(String s) {
        int h = 0;
        int len = s.length();
        for (int i = 0; i < len; i++)
            h = 37 * h + s.charAt(i);
        return h;
    }
    public static int hash97(String s) {
        int h = 0;
        int len = s.length();
        for (int i = 0; i < len; i++)
            h = 31 * h + s.charAt(i);
        return h;
    }
    //------------------------------------------------
    public static Set<Character> IgnoreEnd=new HashSet<Character>();
	static {
		IgnoreEnd.add('-');
		IgnoreEnd.add('_');
		IgnoreEnd.add('(');
		IgnoreEnd.add('[');
		IgnoreEnd.add('{');
		IgnoreEnd.add('（');
		IgnoreEnd.add('【');
		IgnoreEnd.add('『');
	}
    public static String intersect(String a,String b){
    	if(a==null||b==null)
    		return "";
    	String _a=a.trim();
    	String _b=b.trim();
    	if(_a.length()==0||_b.length()==0)
    		return "";
    	StringBuilder sb=new StringBuilder();
    	for(int i=0;i<_a.length();i++){
    		if(IgnoreEnd.contains(_a.charAt(i)))
    			break;
    		if(i>=_b.length())
    			break;
    		if(_a.charAt(i)!=_b.charAt(i))
    			break;
    		sb.append(_a.charAt(i));
    	}
    	return sb.toString();
    }
    //----------------------------------------
    public static DateFormat nameformat = new SimpleDateFormat("yyyy-MM-dd"); 
    public static String descformat[] = {"%d年%d月%d日","%d年%d月%s半月","%d年%d月","%d年%d季度","%d年%s半年","%d年"}; 
    public enum DayInterval{
		DAY(0),HalfMonth(1),MONTH(2),QUARTER(3),HalfYear(4),YEAR(5);
		private  DayInterval(int value){this.value=value;}
		public int value(){return this.value;}
		private int value=0;
	}	
    public static String DateName(Date d){
    	if(d==null)
    		return null;
    	return nameformat.format(d);
    }
    @SuppressWarnings("deprecation")
	public static String DateDesc(Date d,DayInterval interval){
    	if(d==null)
    		return null;
    	switch(interval){
    	case	HalfMonth:
    		return String.format(descformat[interval.value()], d.getYear()+1900,d.getMonth()+1,d.getDate()/16>0?"下":"上");
    	case	MONTH:
    		return String.format(descformat[interval.value()], d.getYear()+1900,d.getMonth()+1);
    	case	QUARTER:
    		return String.format(descformat[interval.value()], d.getYear()+1900,d.getMonth()/3+1);
    	case	HalfYear:
    		return String.format(descformat[interval.value()], d.getYear()+1900,d.getMonth()/6>0?"下":"上");
    	case	YEAR:
    		return String.format(descformat[interval.value()], d.getYear()+1900);
    	case	DAY:
    		return String.format(descformat[interval.value()], d.getYear()+1900,d.getMonth()+1,d.getDate());
    	default:
    		return String.format(descformat[0], d.getYear()+1900,d.getMonth()+1,d.getDate());
    	}
    }
    public static Date firstDayHalfMonth(Date d){
    	if(d==null)
    		return null;
    	int day=1;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);  
    	day=calendar.get(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DAY_OF_MONTH, day>15?16:1); 
    	return calendar.getTime();
    }
    public static Date LastDayHalfMonth(Date d){
    	if(d==null)
    		return null;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);      	
    	int day=calendar.get(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DAY_OF_MONTH, day>15?calendar.getActualMaximum(Calendar.DATE):15); 
    	return calendar.getTime();
    }
    public static Date firstDayMonth(Date d){
    	if(d==null)
    		return null;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);  
    	calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE)); 
    	return calendar.getTime();
    }
    public static Date lastDayMonth(Date d){
    	if(d==null)
    		return null;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);  
    	calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE)); 
    	return calendar.getTime();
    }
    public static Date firstDayQuarter(Date d){
    	if(d==null)
    		return null;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);
    	int month=(calendar.get(Calendar.MONTH))/3+1;    	
    	calendar.set(Calendar.MONTH, month*3-3);
    	calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));    	
    	return calendar.getTime();
    }
    public static Date lastDayQuarter(Date d){
    	if(d==null)
    		return null;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);  
    	int month=(calendar.get(Calendar.MONTH))/3+1;
    	calendar.set(Calendar.MONTH, month*3-1);
    	calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));     	
    	return calendar.getTime();
    }
    public static Date firstDayHalfYear(Date d){
    	if(d==null)
    		return null;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);
    	int month=(calendar.get(Calendar.MONTH))/6+1;    	
    	calendar.set(Calendar.MONTH, month*6-6);
    	calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));    	
    	return calendar.getTime();
    }
    public static Date lastDayHalfYear(Date d){
    	if(d==null)
    		return null;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);  
    	int month=(calendar.get(Calendar.MONTH))/6+1;
    	calendar.set(Calendar.MONTH, month*6-1);
    	calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));     	
    	return calendar.getTime();
    }
    public static Date firstDayYear(Date d){
    	if(d==null)
    		return null;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);   	
    	calendar.set(Calendar.MONTH, 0);
    	calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));    	
    	return calendar.getTime();
    }
    public static Date lastDayYear(Date d){
    	if(d==null)
    		return null;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);  
    	calendar.set(Calendar.MONTH, 11);
    	calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));     	
    	return calendar.getTime();
    }

    //----------------------------------------------
    private static ObjectMapper objectMapper=new ObjectMapper();
    public static synchronized Object ObjfromJson(String jsonstr,Class<?> toClass) throws AppException{		
		Object ret=null;
		try {
			ret= objectMapper.readValue(jsonstr,toClass);
			//System.out.println("json converto OK:");
		} catch (IOException e) {
			//JsonParseException |JsonMappingException |
			e.printStackTrace();
			throw new AppException("0001",e.getMessage());
		}		
		return ret;
	}
	public static synchronized <T> T fromJson(String jsonstr,Class<T> toClass) throws AppException{		
		T ret=null;
		try {
			ret=(T) objectMapper.readValue(jsonstr,toClass);
			//System.out.println("json converto OK:");
		} catch (IOException e) {
			//JsonParseException |JsonMappingException |
			e.printStackTrace();
			throw new AppException("0001",e.getMessage());
		}		
		return ret;
	}
	public static synchronized <T> String toJson(T obj) throws AppException{
		String ret=null;
		try {
			ret=objectMapper.writeValueAsString( obj);
		} catch (IOException e) {
			e.printStackTrace();
			throw new AppException("0001",e.getMessage());
		}
		return ret;
	}
	public static synchronized <T> T fromXML(String xmlstr,Class<T> toClass) throws AppException{		
		T ret=null;
		try {
			ret=(T) new XmlMapper().readValue(xmlstr,toClass);
		} catch (IOException e) {
			e.printStackTrace();
			throw new AppException("0001",e.getMessage());
		}		
		return ret;
	}
	public static synchronized <T> String toXML(T obj) throws AppException{
		String ret=null;
		try {
			ret=new XmlMapper().writeValueAsString(obj);
		} catch (IOException e) {
			e.printStackTrace();
			throw new AppException("0001",e.getMessage());
		}
		return ret;
	}

	public static String StrTransformerFactory=null;
	public static TransformerFactory  _TransformerFactory=null;
	/**
	 * 
	 * new ByteArrayInputStream(args0.getBytes("UTF-8"))
	 * new ByteArrayOutputStream()
	 * new FileInputStream(xslFilename)
	 * 		out_.toString()
	 * **/
	public static synchronized void convert(InputStream inputStream,OutputStream outputStream,InputStream xsltinputStream) throws AppException{
		
		try {
			if(_TransformerFactory==null){
				String StrTransformerFactory=System.getProperty("javax.xml.transform.TransformerFactory");
				if(StrTransformerFactory==null)
					StrTransformerFactory="net.sf.saxon.TransformerFactoryImpl";
					    //"org.apache.xalan.processor.TransformerFactoryImpl"
				System.setProperty("javax.xml.transform.TransformerFactory",StrTransformerFactory);
				_TransformerFactory=TransformerFactory.newInstance();
			}
			_TransformerFactory.newTemplates(new StreamSource(xsltinputStream))
					.newTransformer()
					.transform(new StreamSource(inputStream), new StreamResult(outputStream));
		}catch(SecurityException e){
			e.printStackTrace();
			throw new AppException("0001",e.getMessage());
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			throw new AppException("0001",e.getMessage());
		}catch(TransformerConfigurationException e){
			e.printStackTrace();
			throw new AppException("0001",e.getMessage());
		}catch (TransformerException e) {
			e.printStackTrace();
			throw new AppException("0001",e.getMessage());
		}catch (NullPointerException e) {
			e.printStackTrace();
			throw new AppException("0001",e.getMessage());
		}
        
		
	}
}
