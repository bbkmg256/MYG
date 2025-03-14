
// Clase entrenamiento

/*

Basicamente esta clase se el entrenamiento en si que imparte el tutor al
cliente, acá tambien se supone que el cliente al finalizar las 5 semanas
puntua el servicio del tutor.

*/

// Paquete
package edu.unam.modelo;

// Libs
import java.util.Date;
import java.util.ArrayList;

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
 * @author bbkmg
 */
@Entity
public class Entrenamiento {
	// Atributos
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idEntrenamiento;
	
	@Basic
	private int puntaje; // Puntuación por parte del cliente al tutor.
	
	// Volumen de entrenamiento semanal
	// (si no estoy mal, es lo que se entreno en la semana).
	private int volumenEntrenamiento;

	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	private Date fechaFin;	
	
	// atributo relacion a clase Cliente
	// private Cliente cliente;
	
	// atributo relacion a clase Tutor
	// private Tutor tutor;
	
	// atributo relacion a clase Rutina (Lista)
	// private ArrayList<Rutina> rutinas = new ArrayList<>();
	
	// atributo relacion a clase Seguimiento (Lista)
	// private ArrayList<Seguimiento> seguimientoEntrenamiento = new ArrayList<>();
	
	// Constructor
	public Entrenamiento() {
		
	}
	
	public Entrenamiento(int paramIdEntre, Date paramFechaInicio, Date paramFechaFin){
		this.idEntrenamiento = paramIdEntre;
		this.puntaje = 0; // Al inicio es 0 hasta que el cliente lo puntue.
		this.fechaInicio = paramFechaInicio;
		this.fechaFin = paramFechaFin;
		this.volumenEntrenamiento = 0;
		// El volumen es 0 hasta que se empiece a registrar los
		// entrenamientos del cliente.
	}
	
	// Set
	public void setIdEntrenamiento(int valIdEntre){
		this.idEntrenamiento = valIdEntre;
	}
	
	public void setPuntaje(int valPuntaje){
		this.puntaje = valPuntaje;
	}
	
	public void setFechaInicio(Date valFechaInicio){
		this.fechaInicio = valFechaInicio;
	}
	
	public void setFechaFin(Date valFechaFin){
		this.fechaFin = valFechaFin;
	}
	
	public void setVolumenEntrenamiento(int valVE){
		this.volumenEntrenamiento = valVE;
	}
	
	// Get
	public int getIdEntrenamiento(){
		return this.idEntrenamiento;
	}
	
	public int getPuntaje(){
		return this.puntaje;
	}
	
	public Date getFechaInicio(){
		return this.fechaInicio;
	}
	
	public Date getFechaFin(){
		return this.fechaFin;
	}
	
	public int getVolumenEntrenamiento(){
		return this.volumenEntrenamiento;
	}
	
	// Metodos
}