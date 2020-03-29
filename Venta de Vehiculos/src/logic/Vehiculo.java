package logic;

public class Vehiculo {

	private String estado;
	private int anio;
	int idTipoVehiculo;
	int idMarca;
	int idModelo;
	private String descripcion;
	
	public Vehiculo(String estado, int anio, int idTipoVehiculo, int idMarca, int idModelo, String descripcion) {
		super();
		this.estado = estado;
		this.anio = anio;
		this.idTipoVehiculo = idTipoVehiculo;
		this.idMarca = idMarca;
		this.idModelo = idModelo;
		this.descripcion = descripcion;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public int getIdTipoVehiculo() {
		return idTipoVehiculo;
	}
	public void setIdTipoVehiculo(int idTipoVehiculo) {
		this.idTipoVehiculo = idTipoVehiculo;
	}
	public int getIdMarca() {
		return idMarca;
	}
	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
	}
	public int getIdModelo() {
		return idModelo;
	}
	public void setIdModelo(int idModelo) {
		this.idModelo = idModelo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
