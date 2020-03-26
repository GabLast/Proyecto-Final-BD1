package logic;

import java.util.ArrayList;

public class Persona {
	
	
	private String cedula;
	private String nombre;
	private String apellido;
	private ArrayList<String> numeros;
	private String provincia;
	private String email;
	
	public Persona(String cedula, String nombre, String apellido, String provincia,
			String email) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.provincia = provincia;
		this.email = email;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public ArrayList<String> getNumeros() {
		return numeros;
	}

	public void setNumeros(ArrayList<String> numeros) {
		this.numeros = numeros;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
