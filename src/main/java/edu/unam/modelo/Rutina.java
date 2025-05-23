/*
 * 
 * CLASE RUTINA
 * 
 * ESTABLECE LAS RUTINAS QUE SE CREAN A PARTIR DE LOS EJERCICIOS,
 * CON SUS SERIES Y REPETICIONES, ASIGNADAS AL ENTRENAMIENTO QUE RECIBE
 * EL CLIENTE
 * 
 */


// PAQUETES
package edu.unam.modelo;

// LIBRERIAS
// VARIOS
import java.util.List;
import java.util.ArrayList;

// JPA
import jakarta.persistence.Id; // DEFINE EL ID DE LA ENTIDAD
import jakarta.persistence.Basic; // DEFINE BASICOS
import jakarta.persistence.GeneratedValue; // PARA GENERAR VALORES AL ID DE LA ENTIDAD
import jakarta.persistence.GenerationType; // DEFINE LA FORMA DE GENERACION DEL ID
import jakarta.persistence.Entity; // MODULO JPA PARA ENTIDADES/OBJETOS
import jakarta.persistence.Column; // DEFINE UNA COLUMNA DE LA ENTIDAD
import jakarta.persistence.OneToMany; // DEFINE UN TIPO DE RELACION ENTRE ENTIDADES
import jakarta.persistence.CascadeType; // DEFINE UNA PROPIEDAD DE RELACION ENTRE ENTIDADES


/*
 * 
 * NOTAS:
 * 
 * NO DESCOMENTES LAS RELACIONES TODAVIA POR QUE SE ROMPE EL CODIGO
 * 
 * LA PERSONALIZADA ES LA RUTINA, ES ELLA LA QUE GUARDA LAS SERIES Y REPETICIONES
 * NO EL EJERCICIO, UNA RUTINA SE COMPONE DE VARIOS EJERCICIOS Y Y EJERCICIO PUEDE
 * ESTAR EN VARIAS RUTINAS, POR ENDE, PUEDEN HABER RUTINAS CON EJERICIOS SIMILARES.
 * 
 * ES DECIR, ES:
 * 
 * VARIOS EJERCICIOS SE RELACIONA CON VARIAS RUTINAS.
 * 
 * RUTINA DEJA DE TENER SERIE Y REPETICION COMO ATRIBUTO, ES RARO PERO ESTA CLASE SE QUEDA
 * "VACIA" PERO CON UTILIDAD PARA RELACIONAR E IDENTIFICAR A LA RUTINA CON ENTRENAMIENTO,
 * A SU VEZ ESTA CLASE SE RELACIONARÁ CON EJERCICIO MEDIANTE UNA CLASE INTERMEDIA LLAMADA
 * "RutinaEjercicio" QUE CONTENDRA LAS SERIES Y REPETICIONES PARA CADA EJERCICIO, QUE PERTENECERAN
 * A X RUTINA. ADEMAS, PARA ACCEDER A LAS ASOCIACIONES, TENDRÉ QUE HACERLO MEDIANTE LA RELACION
 * BIDIRECCIONAL DE ESTE CON LA CLASE INTERMEDIA, ES DECIR QUE DESDE UNA INSTANCIA DE RUTINA
 * TENDRÍA QUE PODER ACCEDER A TODOS LOS REGISTRO DE LA ENTIDAD INTERMEDIA "RutinaEjercicio",
 * TENIENDO ASÍ AL INFORMACION PERTINENTE A CADA RUTINA (OSEA, SUS EJERCICIOS, SERIES Y REPETICIONES).
 * 
 * POR EL MOMENTO NO SE ME OCURRE OTRA FORMA MENOS REBUSCADA O TOSCA PARA PODER LLEVAR A CABO ESTA RELACION. :'[
 * 
 * 
 * //	// ATRIBUTO RELACION CON CLASE EJERCICIO (LISTA) [NO TERMINADO]
 *	//	@ManyToMany
 *	//	@JoinTable(
 *	//			name = "rutinas_ejercicios",
 *	//			joinColumns = @JoinColumn(name = "rutina_id"), // COLUMNA ID DE ENTIDAD RUTINA
 *	//			inverseJoinColumns = @JoinColumn(name = "ejercicio_id") // COLUMNA ID DE ENTIDAD EJERCICIO
 *	//	)
 *	//	private List<Ejercicio> ejercicios = new ArrayList<>(); // FK'S DE EJERCICIO
 * 
 */


/**
*
* @Autor: BBKMG
*/
@Entity
public class Rutina {
	// ATIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_rutina")
	private int idRutina;
	
	@Basic
	@Column(name = "nombre_rutina")
	private String nombreRutina; // PARA JUSTIFICAR UN POCO LA EXISTENCIA DE ESTA CLASE E IDENTIFICAR MEJOR CADA RUTINA XD
	
	// ATRIBUTO RELACION CON CLASE RUTINAEJERCICIO
	@OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL)
	private List<RutinaEjercicio> rutinaEjercicio = new ArrayList<>();
	
	// ATRIBUTO RELACION CON CLASE ENTRENAMIENTO (LISTA)
//	@OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL)
//	private List<Entrenamiento> entrenamientos = new ArrayList<>();
	
	// CONTRUCTOR
	public Rutina() {}
	
	public Rutina(String paramNombreRutina) {
		this.nombreRutina = paramNombreRutina;
	}
	
	// SET
	public void setIdRutina(int valIdRutina) {
		this.idRutina = valIdRutina;
	}
	
	public void setRutinaEjercicio(List<RutinaEjercicio> listRutinaEjercico) {
		this.rutinaEjercicio = listRutinaEjercico;
	}
	
//	public void setEntrenamientos(List<Entrenamiento> listEntrenamientos) {
//		this.entrenamientos = listEntrenamientos;
//	}
	
	public void setNombreRutina(String valNombre) {
		this.nombreRutina = valNombre;
	}
	
	// GET
	public int getIdRutina() {
		return this.idRutina;
	}
	
	public List<RutinaEjercicio> getRutinaEjercicio() {
		return this.rutinaEjercicio;
	}
	
//	public List<Entrenamiento> getEntrenamientos() {
//		return this.entrenamientos;
//	}
	
	public String getNombreRutina() {
		return this.nombreRutina;
	}
}