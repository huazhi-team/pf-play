package com.pf.play.common.utils;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @param <T>
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResult<T> implements Serializable {

	private static final long serialVersionUID = -110632011249276581L;
	// 返回状态码:0代表正常，非零代表错误，-1表示数据为空
	private int errcode;
	private String message;
//	private String content;
	private T content;
	private String sgid;
	private String cgid;


	/**
	 * x
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> JsonResult<T> successResult(T data) {
		JsonResult<T> jsonResult = new JsonResult<T>();
		jsonResult.setErrcode(0);
		jsonResult.setMessage("success");
		jsonResult.setContent(data);
		return jsonResult;
	}

	/**
	 * x
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> JsonResult<T> successResult(T data, String cgid, String sgid) {
		JsonResult<T> jsonResult = new JsonResult<T>();
		jsonResult.setErrcode(0);
		jsonResult.setMessage("success");
		jsonResult.setContent(data);
		jsonResult.setCgid(cgid);
		jsonResult.setSgid(sgid);
		return jsonResult;
	}



	/**
	 * x
	 * @param msg
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static <T> JsonResult<T> failedResult(String msg, String code) {
		JsonResult<T> jsonResult = new JsonResult<T>();
		jsonResult.setErrcode(Integer.parseInt(code));
		jsonResult.setMessage(msg);
//		jsonResult.setContent(msg);
		jsonResult.setContent(null);
		return jsonResult;
	}


	/**
	 * x
	 * @param msg
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static <T> JsonResult<T> failedResult(String msg, String code, String cgid, String sgid) {
		JsonResult<T> jsonResult = new JsonResult<T>();
		jsonResult.setErrcode(Integer.parseInt(code));
		jsonResult.setMessage(msg);
		jsonResult.setContent(null);
//		jsonResult.setData(null);
		jsonResult.setCgid(cgid);
		jsonResult.setSgid(sgid);
		return jsonResult;
	}

	/**
	 * x
	 * @param msg
	 * @param resultCode
	 * @param <T>
	 * @return
	 */
	public static <T> JsonResult<T> failedResult(T data, String msg, String resultCode) {
		JsonResult<T> jsonResult = new JsonResult<T>();
		jsonResult.setErrcode(Integer.parseInt(resultCode));
		jsonResult.setMessage(msg);
		jsonResult.setContent(null);
//		jsonResult.setData(data);
		return jsonResult;
	}


	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public T getContent() {
		return content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public String getSgid() {
		return sgid;
	}

	public void setSgid(String sgid) {
		this.sgid = sgid;
	}

	public String getCgid() {
		return cgid;
	}

	public void setCgid(String cgid) {
		this.cgid = cgid;
	}
}
