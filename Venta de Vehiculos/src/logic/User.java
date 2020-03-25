package logic;

public class User {

	private String tipo;
	private String userName;
	private String pass;
	
	public User(String tipo, String userName, String pass) {
		super();
		this.tipo = tipo;
		this.userName = userName;
		this.pass = pass;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
