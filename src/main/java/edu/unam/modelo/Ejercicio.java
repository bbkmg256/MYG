
// Clase ejercicio

/*

Comprende a los ejercicio que trabaja un grupo muscular y que conformará
la rutina que se le asigna al cliente.

*/

//Paquete
package edu.unam.modelo;

// Libs
// import java.util.ArrayList;

import jakarta.persistence.Basic; // Modulo JPA para atributos basicos
import jakarta.persistence.Entity; // Modulo JPA para entidades/objetos

// Modulos JPA para generacion de ID, valores de generación de ID y forma de generación de ID
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
*
* @Autor: BBKMG
*/
@Entity
public class Ejercicio {
	// Atributo
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idEjercicio;
	
	@Basic
	private String nombreEjercicio;
	
	// atributo relacion con clase GM
	// private GrupoMuscular GM;
	
	// atributo relacion con clase Rutina
	// private Rutina rutina;
	
	// Constructor
	public Ejercicio() {
		
	}
	
	public Ejercicio(int paramIdEjer, String paramNombreEjer){
		this.idEjercicio = paramIdEjer;
		this.nombreEjercicio = paramNombreEjer;
	}
	
	// Set
	public void setIdEjercicio(int valIdEjer){
		this.idEjercicio = valIdEjer;
	}
	
	public void setNombreEjercicio(String valNombreEjer){
		this.nombreEjercicio = valNombreEjer;
	}
	
	// Get
	public int getIdEjercicio(){
		return this.idEjercicio;
	}
	
	public String getNombreEjercicio(){
		return this.nombreEjercicio;
	}
}