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
	private String code;
	private boolean success;
	private String msg;
	private T data;
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
		jsonResult.setCode("0");
		jsonResult.setSuccess(true);
		jsonResult.setData(data);
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
		jsonResult.setCode("0");
		jsonResult.setSuccess(true);
		jsonResult.setData(data);
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
		jsonResult.setCode(code);
		jsonResult.setSuccess(false);
		jsonResult.setMsg(msg);
		jsonResult.setData(null);
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
		jsonResult.setCode(code);
		jsonResult.setSuccess(false);
		jsonResult.setMsg(msg);
		jsonResult.setData(null);
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
		jsonResult.setCode(resultCode);
		jsonResult.setSuccess(false);
		jsonResult.setMsg(msg);
		jsonResult.setData(data);
		return jsonResult;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
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
