/*
 * 
 * CLASE SEGUIMIENTO
 * 
 * ESTA CLASE REGISTRA LO QUE EL CLIENTE REALIZA RESPECTO A SU ENTRENAMIENTO
 * (DE FORMA DIARIA)
 * 
 */


// PAQUETES
package edu.unam.modelo;

// LIBRERIAS
// VARIOS
import java.time.LocalDate;

import jakarta.persistence.Basic; // MODULO JPA PARA ATRIBUTOS BASICOS
import jakarta.persistence.Entity; // MODULO JPA PARA ENTIDADES/OBJETOS
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

// MODULOS JPA PARA GENERACIONES DE ID, VALORES DE GENERACION DE ID Y FORMA DE GENERACION DE ID
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// MODULO JPA PARA ATRIBUTOS REFERENTES A FECHAS
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/*
 * 
 * NOTA:
 * 
 * 
 */


/**
*
* @Autor: BBKMG
*/
@Entity
public class Seguimiento {
	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idSeguimiento;
	
	@Basic
	@Column(name = "cantidad_series_realizadas")
	private int cantSerieRealizado;
	@Column(name = "cantidad_repeticiones_realizadas")
	private int cantRepeticionesRealizado;
	@Column(name = "ejercicio_realizado")
	private String ejercicioRealizado;
	@Column(name = "peso_trabajado")
	private double pesoTrabajado;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_hoy")
	private LocalDate fechaHoy;
	
	// ATRIBUTO RELACION CON CLASE ENTRENAMIENTO
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entrenamiento_id")
	private Entrenamiento entrenamiento; // FK'S DE ENTRENAMIENTO
	
	// CONTRUCTOR
	public Seguimiento() {}
	
	public Seguimiento(LocalDate paramFechaHoy, String paramEjercicio,
		int paramCantSeries, int paramCantRep,
		double paramPesoTrabajado,
		Entrenamiento paramEntrenamiento) {
		
		this.fechaHoy = paramFechaHoy;
		this.ejercicioRealizado = paramEjercicio;
		this.cantSerieRealizado = paramCantSeries;
		this.cantRepeticionesRealizado = paramCantRep;
		this.pesoTrabajado = paramPesoTrabajado;
		this.entrenamiento = paramEntrenamiento;
	}
	
	// SET
	public void setIdSeguimiento(int valIdSeg) {
		this.idSeguimiento = valIdSeg;
	}
	
	public void setFechaHoy(LocalDate valFechaHoy) {
		this.fechaHoy = valFechaHoy;
	}
	
	public void setCantSerieRealizado(int valCantSerieRelizado) {
		this.cantSerieRealizado = valCantSerieRelizado;
	}
	
	public void setCantRepeticionRealizado(int valCantRepeticionRealizado) {
		this.cantRepeticionesRealizado = valCantRepeticionRealizado;
	}
	
	public void setEjercicioRealizado(String valEjercicioRealizado) {
		this.ejercicioRealizado = valEjercicioRealizado;
	}
	
	public void setPesoTrabajado(double valPesoTrabajado) {
		this.pesoTrabajado = valPesoTrabajado;
	}
	
	public void setEntrenamiento(Entrenamiento paramEntrenamiento) {
		this.entrenamiento = paramEntrenamiento;
	}
	
	// GET
	public int getIdSeguimiento() {
		return this.idSeguimiento;
	}
	
	public LocalDate getFechaHoy() {
		return this.fechaHoy;
	}
	
	public int getCantSerieRealizado() {
		return this.cantSerieRealizado;
	}
	
	public int getCantRepeticionesRealizado() {
		return this.cantRepeticionesRealizado;
	}
	
	public String getEjercicioRealizado() {
		return this.ejercicioRealizado;
	}
	
	public double getPesoTrabajado() {
		return this.pesoTrabajado;
	}
	
	public Entrenamiento getEntrenamiento() {
		return this.entrenamiento;
	}
}