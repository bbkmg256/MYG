/*
 * 
 * CLASE EJERCICIO
 * 
 * COMPRENDE A LOS EJERCICIOS QUE TRABAJAN UN GRUPO MUSCULAR Y QUE CONFORMARÁN
 * LA RUTINA QUE SE LE ASIGNA AL CLIENTE.
 * 
 */


// PAQUETES

package edu.unam.modelo;

// LIBRERIAS
// VARIOS
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Basic; // MODULO JPA PARA ATRIBUTOS BASICOS
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity; // MODULO JPA PARA ENTIDADES/OBJETOS
import jakarta.persistence.Column;

// MODULOS JPA PARA GENERACIONES DE ID, VALORES DE GENERACION DE ID Y FORMA DE GENERACION DE ID
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// MODULOS JPA DE MAPEADO DE RELACIONES
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToMany;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
//import jakarta.persistence.CascadeType;
// import jakarta.persistence.ManyToMany;


/*
 * 
 * NOTAS:
 * 
 * JoinColumn -> SIRVE PARA DETALLAR DATOS REPSECTO A LA COLUMNA DE UAN ENTIDAD
 * QUE ALMACENA LAS CLAVER FORANEAS DE OTRA ENTIDAD.
 * (LA ENTIDAD DE MUCHOS SIEMPRE ES LA QUE GUARDA LA FK)
 * 
 * fetch -> ESPECIFICA LA FORMA EN QUE SE TOMARÁN LOS DATOS DE ESTA ENTIDAD
 * DESDE LA BD, PARA NO TRAER TODOS LOS DATOS DE UNA Y SOBRE EXIGIR EL MOTOR.
 * SE SUPONE QUE LOS DATOS SOLO SE TRAERAN CUANDO SE LLAME AL METODO GET DEL
 * ATRIBUTO.
 * 
 * LA PERSONALIZADA ES LA RUTINA, ES ELLA LA QUE GUARDA LAS SERIES Y REPETICIONES
 * NO EL EJERCICIO, UNA RUTINA SE COMPONE DE VARIOS EJERCICIOS Y Y EJERCICIO PUEDE
 * ESTAR EN VARIAS RUTINAS, POR ENDE, PUEDEN HABER RUTINAS CON EJERICIOS SIMILARES.
 * 
 * ES DECIR, ES:
 * 
 * VARIOS EJERCICIOS SE RELACIONA CON VARIAS RUTINAS.
 * 
 * 
 * 	// ATRIBUTO RELACIONADO CON CLASE RUTINA (LISTA)
 *	// @OneToMany(mappedBy = "ejercicio", cascade = CascadeType.ALL)
 *	// private List<Rutina> rutina = new ArrayList<>();
 *	
 *	// ATRIBUTO RELACIONADO CON CLASE RUTINA (LISTA) [NO TERMINADO]
 *	@ManyToMany(mappedBy = "ejercicios", cascade = CascadeType.ALL)
 *	private List<Rutina> rutina = new ArrayList<>();
 *
 *	
 *	POR EL MOMENTO VOY A RELACIONAR A LA INVERSA DEL DIAGRAMA POR CUESTIONES DE INSTANCIACION (NO TIENE MUCHO SENTIDO COMO VENIA HACIENDO)
 *	// ATRIBUTO RELACIONADO CON CLASE RUTINA
 *	@ManyToOne(fetch = FetchType.LAZY)
 *	@JoinColum(name = "rutina_id")
 *	private Rutina rutina; // FK'S DE RUTINA
 * 
 */


/**
*
* @Autor: BBKMG
*/
@Entity
public class Ejercicio {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ejercicio")
	private int idEjercicio;
	
	@Basic
	@Column(name = "nombre_ejercicio")
	private String nombreEjercicio; // PROBABLEMENTE PODRIA HACERLO UNIQUE PARA EVITAR CARGAR EJERCICIO CON NOMBRE IGUALES
	
	// ATRIBUTO RELACIONADO CON CLASE GM
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grupo_muscular_id") // ESPECIFICA QUE ESTA COLUMNA ES LA RELACIONAL ENTRE LA TABLA EJERCICIO Y GM
	private GrupoMuscular GM; // FK'S DE GRUPO MUSCULAR
	
	// ATRIBUTO RELACION CON CLASE RUTINAEJERCICIO
	@OneToMany(mappedBy = "ejercicio", cascade = CascadeType.ALL)
	private List<RutinaEjercicio> rutinaEjercicio = new ArrayList<>();

	// CONSTRUCTOR
	public Ejercicio() {}
	
	public Ejercicio(String paramNombreEjer, GrupoMuscular paramGrupoMuscular){
		this.nombreEjercicio = paramNombreEjer;
		this.GM = paramGrupoMuscular; // EJERCICIO "ALMACENA" UN GRUPO MUSCULAR
	}
	
	// SET
	public void setIdEjercicio(int valIdEjer){
		this.idEjercicio = valIdEjer;
	}
	
	public void setNombreEjercicio(String valNombreEjer){
		this.nombreEjercicio = valNombreEjer;
	}
	
	public void setGrupoMuscular(GrupoMuscular paramGM) {
		this.GM = paramGM;
	}
	
	public void setRutinaEjercicio(List<RutinaEjercicio> listRutinaEjercicio) {
		this.rutinaEjercicio = listRutinaEjercicio;
	}
	
	// GET
	public int getIdEjercicio(){
		return this.idEjercicio;
	}
	
	public String getNombreEjercicio(){
		return this.nombreEjercicio;
	}
	
	public GrupoMuscular getGrupoMuscular(){
		return this.GM;
	}
	
	public List<RutinaEjercicio> getRutinaEjercicio() {
		return this.rutinaEjercicio;
	}
}