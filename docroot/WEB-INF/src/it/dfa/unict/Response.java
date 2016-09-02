package it.dfa.unict;

public class Response {
	private int code;
	private Object data;

	public Response(int code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}

	public Response() {
		// TODO Auto-generated constructor stub
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
