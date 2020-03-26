package logic;

public class Vendedor extends Persona {

	private String tipoVendedor;
	
	public Vendedor(String cedula, String nombre, String apellido, String provincia, String email, String tipo) {
		super(cedula, nombre, apellido, provincia, email);
		// TODO Auto-generated constructor stub
		if(tipo.equalsIgnoreCase("Dealer"))
		{
			this.tipoVendedor = "1";
		}
		else if(tipo.equalsIgnoreCase("Particular"))
		{
			this.tipoVendedor = "2";
		}else
			this.tipoVendedor = null;
	}

	public String getTipoVendedor() {
		return tipoVendedor;
	}

	public void setTipoVendedor(String tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}

}
