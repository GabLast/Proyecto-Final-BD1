package logic;

import java.util.Date;

public class Anuncio {
	
	private String descripcion;
	private float costo;
	private float precioVehiculo;
	private Date fechaInicio;
	private Date fechaFin;
	public Anuncio(String descripcion, float costo, float precioVehiculo, Date fechaInicio, Date fechaFin) {
		super();
		this.descripcion = descripcion;
		this.costo = costo;
		this.precioVehiculo = precioVehiculo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public float getCosto() {
		return costo;
	}
	public void setCosto(float costo) {
		this.costo = costo;
	}
	public float getPrecioVehiculo() {
		return precioVehiculo;
	}
	public void setPrecioVehiculo(float precioVehiculo) {
		this.precioVehiculo = precioVehiculo;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
}
