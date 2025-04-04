
// Clase seguimiento

/*

Se supone que esta clase va a servir como registro de lo que realiza el cliente
acá se debería detallar cuanto se ejercito y que ejercicios realizo, entre otras
cosas.

*/

// Paquete
package edu.unam.modelo;

// Libs
import java.time.LocalDate;

import jakarta.persistence.Basic; // Modulo JPA para atributos basicos
import jakarta.persistence.Entity; // Modulo JPA para entidades/objetos

// Modulos JPA para generacion de ID, valores de generación de ID y forma de generación de ID
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// Modulo JPA para atributos referentes a fechas
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
*
* @Autor: BBKMG
*/
@Entity
public class Seguimiento {
	//Atributos
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idSeguimiento;
	
	@Basic
	private int cantSerieRealizado;
	private int cantRepeticionesRealizado;
	private String ejercicioRealizado; // Por el momento se queda en un String, despues veré si es necesario cambiarlo a una entidad Ejercicio
	private double pesoTrabajado;
	
	@Temporal(TemporalType.DATE)
	private LocalDate fechaHoy;
	
	// atributo relacion con clase Entrenamiento
	// private Entrenamiento registroEntrenamiento;
	
	// Constructor
	public Seguimiento() {
		
	}
	
	public Seguimiento(int paramIdSeg, int paramCantSeries, int paramCantRep,
		String paramEjer, double paramPesoTrabajado) {
		
		this.idSeguimiento = paramIdSeg;
		this.fechaHoy = null; // Ver como se obtiene la fecha actual del sistema.
		this.cantSerieRealizado = paramCantSeries;
		this.cantRepeticionesRealizado = paramCantRep;
		this.ejercicioRealizado = paramEjer;
		this.pesoTrabajado = paramPesoTrabajado;
	}
	
	// Set
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
	
	// Get
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
}