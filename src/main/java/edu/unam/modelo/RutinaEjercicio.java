/*
 * ESTA CLASE SIRVE COMO INTERMEDIARIA ENTRE RUTINA Y EJERCICIO,
 * POSEE LAS SERIES Y REPETICIONES (Y HAY QUE VER SI TAMBIEN ALMACENARIA
 * EL VOLUMEN DE ENTRENAMIENTO) PARA CADA EJERCICIO, EL CONJUNTO DE ESTO
 * (SERIE, REPETICION Y EJERCICIO) DEBE SER ASOCIADO A UNA RUTINA.
 * 
 * LO UNICO MALO ES QUE PROBABLEMENTE DEBA HACER UNA JUGADA MUY FEA PARA
 * RELACIONAR LA CLASE RUTINA CON ENTRENAMIENTO Y OBTENER DESDE LA RELACION
 * BIDIRECCIONAL DE RUTINA CON ESTA CLASE, LOS DATOS PERTINENTES A LO QUE SERIA
 * CADA RUTINA, COSAS MUY RARAS HAY QUE HACER AHORA.
 * 
 * FALTA TERMINARLA Y ASOCIARLA!
 */

package edu.unam.modelo;

// LIBRERIAS
// JPA
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Basic;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// JPA PARA MAPEADO DE RELACIONES
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;


/*
 * 
 * NOTA:
 * 
 * POR EL MOMENTO SE SUPONE QUE TENDRIA ESOS ATRIBUTOS.
 * 
 */


/**
*
* @Autor: BBKMG
*/
@Entity
public class RutinaEjercicio {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Basic
	private int serie, repeticion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rutina_id")
	private Rutina rutina; // FK'S DE RUTINA
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ejercicio_id")
	private Ejercicio ejercicio; // FK'S DE EJERCICIO
	
	// CONSTRUCTOR
	public RutinaEjercicio() {}
	
	public RutinaEjercicio(Rutina paramRutina, Ejercicio paramEjercicio,
							int paramSerie, int paramRepeticion) {
		
		this.rutina = paramRutina;
		this.ejercicio = paramEjercicio;
		this.serie = paramSerie;
		this.repeticion = paramRepeticion;
	}
	
	// SET
	public void setId(int valId) {
		this.id = valId;
	}
	
	public void setRutina(Rutina paramRutina) {
		this.rutina = paramRutina;
	}
	
	public void setEjercicio(Ejercicio paramEjercicio) {
		this.ejercicio = paramEjercicio;
	}
	
	public void setSerie(int valSerie) {
		this.serie = valSerie;
	}
	
	public void setRepeticion(int valRepeticion) {
		this.repeticion = valRepeticion;
	}
	
	// GET
	public int getId() {
		return this.id;
	}
	
	public Rutina getRutina() {
		return this.rutina;
	}
	
	public Ejercicio getEjercicio() {
		return this.ejercicio;
	}
	
	public int getSerie() {
		return this.serie;
	}
	
	public int getRepeticion() {
		return this.repeticion;
	}
}
