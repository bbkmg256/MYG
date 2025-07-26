/*
 * 
 * CLASE GRUPO MUSCULAR
 * 
 * ESTA CLASE SE RELACIONA CON EJERCICIO, QUE ES LA QUE ENTRENA AL GRUPO MUSCULAR
 * 
 */


// PAQUETE
package edu.unam.modelo;


// LIBRERIAS
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Basic; // MODULO JPA PARA ATRIBUTOS BASICOS
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity; // MODULO JPA PARA ENTIDADES/OBJETOS
import jakarta.persistence.Column;

// MODULOS JPA PARA GENERACIONES DE ID, VALORES DE GENERACION DE ID Y FORMA DE GENERACION DE ID
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// MODULOS JPA DE MAPEADO DE RELACIONES
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.CascadeType;


/*
 * 
 * NOTA:
 * 
 * mappedBy -> REFERENCIA QUE LAS PK'S DE ESTA ENTIDAD VAN A PARAR COMO FK'S
 * A LA ENTIDAD CON LA QUE SE RELACIONA, EN EL ATRIBUTO (DE ESA MISMA ENTIDAD)
 * QUE SE ESPECIFICA ENTRE COMILLAS (OSEA, EL NOMBRE DEL ATRIBUTO).
 * 
 * LAS CLASES NECESITAN SI O SI CONOCERSE PARA PERSISTIRSE DE FORMA CORRECTA
 * CUANDO ESTAN RELACIONADAS, ES POR ESO QUE ES IMPORTANTE LA LISTA DE LA
 * ENTIDAD DE UNO (RESPECTO A CARDINALIDAD DE RELACION), SI NO SE ALMACENAN
 * LAS ENTIDADES DE MUCHO QUE SE RELACIONAN CON ESTA, POR MAS QUE SE TENGA
 * LA OPCION DE CASCADA SETEADA EN "CascadeType.ALL" NO PERSISTIRÁ LOS OBJETOS
 * DE LAS ENTIDAD DE MUCHOS HABIENDO PERSISTIDO SOLAMENTE EL OBJETO DE LA ENTIDAD
 * DE UNO.
 * 
 */


/**
*
* @Autor: BBKMG
*/
@Entity
public class GrupoMuscular {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_gm")
	private int idGM;
	
	@Basic
	@Column(name = "nombre_gm")
	private String nombreGrupo;
	
	// ATRIBUTO RELACION CON CLASE EJERCICIO (LISTA)
//	@OneToMany(mappedBy = "GM", cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "GM")
	private List<Ejercicio> ejercicios = new ArrayList<>();
	
	// CONTRUCTOR
	public GrupoMuscular() {}
	
	public GrupoMuscular(String paramNombreGrupo){
		this.nombreGrupo = paramNombreGrupo;
	}
	
	// SET
	public void setIdGM(int valIdGM){
		this.idGM = valIdGM;
	}
	
	public void setNombreGrupo(String valNombreGrupo){
		this.nombreGrupo = valNombreGrupo;
	}
	
	public void setEjercicios(List<Ejercicio> listaEjercicios) {
		this.ejercicios = listaEjercicios;
	}
	
	// GET
	public int getIdGM(){
		return this.idGM;
	}
	
	public String getNombreGrupo(){
		return this.nombreGrupo;
	}
	
	public List<Ejercicio> getEjercicios(){
		return this.ejercicios;
	}
}