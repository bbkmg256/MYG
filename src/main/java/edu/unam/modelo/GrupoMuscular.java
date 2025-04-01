
// Clase grupo muscular

/*

Un simple grupo muscular, al se relaciona con uno o mas ejercicios para
que la trabaje.

*/

// Paquete
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
public class GrupoMuscular {
	// Atributos
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idGM;
	
	@Basic
	private String nombreGrupo;
	
	// atributo relacion con clase Ejercicio (Lista)	
	// private ArrayList<Ejercicio> ejercicios = new ArrayList<>();
	
	// Constructor
	public GrupoMuscular() {
		
	}
	
	public GrupoMuscular(int paramIdGM, String paramNombreGrupo){
		this.idGM = paramIdGM;
		this.nombreGrupo = paramNombreGrupo;
	}
	
	// Set
	public void setIdGM(int valIdGM){
		this.idGM = valIdGM;
	}
	
	public void setNombreGrupo(String valNombreGrupo){
		this.nombreGrupo = valNombreGrupo;
	}
	
	// Get
	public int getIdGM(){
		return this.idGM;
	}
	
	public String getNombreGrupo(){
		return this.nombreGrupo;
	}
}