package ca.hicham.test.forms;

public class Alert {
	 public String type;
	 public String message;
	
	public Alert(String type, String message) {
		this.type = type;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "Alert [type=" + type + ", message=" + message + "]";
	}

}
