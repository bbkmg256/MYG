/*
 * CLASE INTERMEDIA ENTRE RUTINA Y ENTRENAMIENTO.
 */

package edu.unam.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/*
 * 
 */

@Entity
public class RutinaEntrenamiento {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rutina_id")
	private Rutina rutina;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entrenamiento_id")
	private Entrenamiento entrenamiento;
	
	// CONSTRUCTOR
	public RutinaEntrenamiento() {}
	
	public RutinaEntrenamiento(Rutina paramRut, Entrenamiento paramEntr) {
		this.rutina = paramRut;
		this.entrenamiento = paramEntr;
	}
	
	// SET
	public void setId(int paramId) {
		this.id = paramId;
	}
	
	public void setRutina(Rutina paramRut) {
		this.rutina = paramRut;
	}
	
	public void setEntrenamiento(Entrenamiento paramEntr) {
		this.entrenamiento = paramEntr;
	}
	
	// GET
	public int getId() {
		return this.id;
	}
	
	public Rutina getRutina() {
		return this.rutina;
	}
	
	public Entrenamiento getEntrenamiento() {
		return this.entrenamiento;
	}
}
