package com.datatalking.exception;


public class AppException extends Exception {
	private static final long serialVersionUID = 1L;
	private String sErrCode;
	private String sExtMessage;
	private IFExceptionMesgTranslator msg=null;
	public String getsErrCode() {
		return sErrCode;
	}
	public void setsErrCode(String sErrCode) {
		this.sErrCode = sErrCode;
	}

	public IFExceptionMesgTranslator getTranslator(){
		return msg;
	}
	public void setTranslator(IFExceptionMesgTranslator translator){
		this.msg=translator;
	}

	public String getsExtMessage() {
		return sExtMessage;
	}
	public void setsExtMessage(String sExtMessage) {
		this.sExtMessage = sExtMessage;
	}	
	
	public String getErrMessage(){
		return getErrMessage(zh_cn);
	}
	public String getErrMessage(String region){
//		switch(region.toLowerCase()){
//		case	"zh_cn":
//		case	"zh_tw":
//		case	"zh":
//		case	"cn":
//		case	"tw":
//			return zh_UnsupportMsg;
//		default:
//			return en_UnsupportMsg;
//		}
		if(msg!=null)
			return msg.getString(region, sErrCode);
		return sErrCode;
	}

	public void setErrMessage(String sErrCode, String sExtMessage)
	{
		this.setsErrCode(sErrCode);
		this.setsExtMessage(sExtMessage);
	}
	
	public AppException(){
		this.setsErrCode(Success);
	}
	public AppException(String sErrCode){
		this.setsErrCode(sErrCode);
	}
	public AppException(String sErrCode,String sExtMessage)
	{
		this.setErrMessage(sErrCode,sExtMessage);
	}
	public AppException(AppException e){
		this.setErrMessage(e.getsErrCode(), e.getsExtMessage());
	}
	public AppException(AppException e,String ext){
		this.setErrMessage(e.getsErrCode(), ext);
	}
	public AppException(Exception e){
		this.setErrMessage(ExternalError, e.getMessage());
	}
	public AppException(Exception e,String ext){
		this.setErrMessage(ExternalError, ext);
	}
	public static AppException getDefault(){
		return new AppException(Success);
	}
	@Override
	public String toString(){
		return String.format("%s,%s,%s", this.getsErrCode(),this.getErrMessage(),this.getsExtMessage());
	}
	
	//--------------------------------------------
	public static final String zh_UnsupportMsg="不支持getErrMessage(。。。)函数实现";
	public static final String en_UnsupportMsg="Get Error Message not supported";
	public static final String zh_cn="zh_cn";
	//-------------
	public static final String Success="0000";
	public static final String SysError="0001";
	public static final String ExternalError="0002";
	public static final String UnsupportError="0003";
}
