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

//import jakarta.persistence.Basic; // MODULO JPA PARA ATRIBUTOS BASICOS
import jakarta.persistence.Entity; // MODULO JPA PARA ENTIDADES/OBJETOS
import jakarta.persistence.Column;

// MODULOS JPA PARA GENERACIONES DE ID, VALORES DE GENERACION DE ID Y FORMA DE GENERACION DE ID
import jakarta.persistence.Id;
import jakarta.persistence.Basic;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// MODULOS JPA PARA MAPEADO DE RELACIONES
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinTable;


/*
 * 
 * NOTA:
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
	
//	@Basic
//	@Column(name = "cantidad_series")
//	private int cantSeries;
//	@Column(name = "cantidad_repeticiones")
//	private int cantRepeticiones;
	
	// ATRIBUTO RELACION CON CLASE RUTINAEJERCICIO
	@OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL)
	private List<RutinaEjercicio> rutinaEjercicio = new ArrayList<>();
	
	// ATRIBUTO RELACION CON CLASE EJERCICIO
	
	// ATRIBUTO RELACION CON CLASE EJERCICIO
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "ejercicio_id")
	// private Ejercicio ejercicio;
	
//	// ATRIBUTO RELACION CON CLASE EJERCICIO (LISTA) [NO TERMINADO]
//	@ManyToMany
//	@JoinTable(
//			name = "rutinas_ejercicios",
//			joinColumns = @JoinColumn(name = "rutina_id"), // COLUMNA ID DE ENTIDAD RUTINA
//			inverseJoinColumns = @JoinColumn(name = "ejercicio_id") // COLUMNA ID DE ENTIDAD EJERCICIO
//	)
//	private List<Ejercicio> ejercicios = new ArrayList<>(); // FK'S DE EJERCICIO
	
	// ATRIBUTO RELACION CON CLASE ENTRENAMIENTO (LISTA)
	@OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL)
	private List<Entrenamiento> entrenamientos = new ArrayList<>();
	
	// CONTRUCTOR
	public Rutina() {}
	
	public Rutina(String paramNombreRutina) {
		this.nombreRutina = paramNombreRutina; // CREO QUE TAMBIÉN DEBERÍA HACERLO UNIQUE, NO TIENE SENTIDO CARGAR RUTINAS NOMBRADAS IGUAL
		
	}

//	public Rutina(int paramIdRutina, int paramCantSeries, int paramCantRepet, List<Ejercicio> listEjercicio){
//		this.idRutina = paramIdRutina;
//		this.cantSeries = paramCantSeries;
//		this.cantRepeticiones = paramCantRepet;
//		this.ejercicios = listEjercicio; // RUTINA "ALMACENA" EJERCICIOS
//	}
	
	// SET
	public void setIdRutina(int valIdRutina) {
		this.idRutina = valIdRutina;
	}
	
	public void setRutinaEjercicio(List<RutinaEjercicio> listRutinaEjercico) {
		this.rutinaEjercicio = listRutinaEjercico;
	}
	
	public void setEntrenamientos(List<Entrenamiento> listEntrenamientos) {
		this.entrenamientos = listEntrenamientos;
	}
	
	public void setNombreRutina(String valNombre) {
		this.nombreRutina = valNombre;
	}
	
//	public void setCantSeries(int valCantSeries){
//		this.cantSeries = valCantSeries;
//	}
//	
//	public void setCantRepeticiones(int valCantRepet){
//		this.cantRepeticiones = valCantRepet;
//	}
	
//	public void setEjercicio(List<Ejercicio> listEjer) {
//		this.ejercicios = listEjer;
//	}
	
	// GET
	public int getIdRutina() {
		return this.idRutina;
	}
	
	public List<RutinaEjercicio> getRutinaEjercicio() {
		return this.rutinaEjercicio;
	}
	
	public List<Entrenamiento> getEntrenamientos() {
		return this.entrenamientos;
	}
	
	public String getNombreRutina() {
		return this.nombreRutina;
	}
	
//	public int getCantSeries(){
//		return this.cantSeries;
//	}
//	
//	public int getCantRepeticiones(){
//		return this.cantRepeticiones;
//	}
	
//	public List<Ejercicio> getEjercicio() {
//		return this.ejercicios;
//	}
}