/*
 * 
 * CLASE ENTRENAMIENTO
 * 
 * BASICAMENTE ESTA CLASE ES EL ENTRENAMIENTO EN SI QUE IMPARTE EL TUTOR AL
 * CLIENTE.
 * 
 * TAMBIEN SE SUPONE QUE ACA SE ESPECIFICA LA PUNTUACION QUE EL CLIENTE LE DA
 * AL TUTOR DESPUES DE TERMINAR EL ENTRENAMIENTO DE 5 SEMANAS
 * 
 */


// PAQUETE
package edu.unam.modelo;

// LIBRERIAS
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Basic; // MODULO JPA PARA ATRIBUTOS BASICOS
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity; // MODULO JPA PARA ENTIDADES/OBJETOS

// MODULOS JPA PARA GENERACIONES DE ID, VALORES DE GENERACION DE ID Y FORMA DE GENERACION DE ID
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// MODULO JPA PARA ATRIBUTOS REFERENTES A FECHAS
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

// MODULOS JPA PARA MAPEADO DE RELACIONES
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;


/*
 * 
 * NOTA:
 * 
 * NO HAY XD
 * 
 */


/**
*
* @Autor: BBKMG
*/
@Entity
public class Entrenamiento {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_entrenamiento")
	private int idEntrenamiento;
	
	@Basic
	private int puntaje; // PUNTUACION POR PARTE DEL CLIENTE AL TUTOR.

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_inicio")
	private LocalDate fechaInicio;
	@Column(name = "fecha_fin")
	private LocalDate fechaFin;	
	
	// ATRIBUTO RELACION A CLASE CLIENTE
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente; // FK'S DE CLIENTE
	
	// ATRIBUTO RELACION A CLASE TUTOR
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tutor_id")
	private Tutor tutor; // FK'S DE TUTOR
	
	// ATRIBUTO RELACION A CLASE RUTINA (LISTA)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rutina_id")
	private Rutina rutina;
	
	// ATRIBUTO RELACION A CLASE SEGUIMIENTO (LISTA)
	@OneToMany(mappedBy = "entrenamiento", cascade = CascadeType.ALL)
	private List<Seguimiento> seguimientos = new ArrayList<>();
	
	// CONTRUCTOR
	public Entrenamiento() {}
	
	public Entrenamiento(LocalDate paramFechaInicio, LocalDate paramFechaFin,
		Cliente paramCli, Tutor paramTutor, Rutina paramRutina) {
		
		this.puntaje = 0; // AL INICIO ES 0 HASTA QUE EL CLIENTE LO PUNTUE DESPUES DE 5 SEMANAS DEL ENTRENAMIENTO
		this.fechaInicio = paramFechaInicio;
		this.fechaFin = paramFechaFin;
		this.cliente = paramCli;
		this.tutor = paramTutor;
		this.rutina = paramRutina;
	}
	
	// SET
	public void setIdEntrenamiento(int valIdEntre) {
		this.idEntrenamiento = valIdEntre;
	}
	
	public void setPuntaje(int valPuntaje) {
		this.puntaje = valPuntaje;
	}
	
	public void setFechaInicio(LocalDate valFechaInicio) {
		this.fechaInicio = valFechaInicio;
	}
	
	public void setFechaFin(LocalDate valFechaFin) {
		this.fechaFin = valFechaFin;
	}
	
	
	public void setCliente(Cliente valCli) {
		this.cliente = valCli;
	}
	
	public void setTutor(Tutor valTutor) {
		this.tutor = valTutor;
	}
	
	public void setSeguimientos(List<Seguimiento> listSeguimiento) {
		this.seguimientos = listSeguimiento;
	}
	
	public void setRutina(Rutina valRutina) {
		this.rutina = valRutina;
	}
	
	// GET
	public int getIdEntrenamiento() {
		return this.idEntrenamiento;
	}
	
	public int getPuntaje() {
		return this.puntaje;
	}
	
	public LocalDate getFechaInicio() {
		return this.fechaInicio;
	}
	
	public LocalDate getFechaFin() {
		return this.fechaFin;
	}
	
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	public Tutor getTutor() {
		return this.tutor;
	}
	
	public List<Seguimiento> getSeguimientos() {
		return this.seguimientos;
	}
	
	public Rutina getRutina() {
		return this.rutina;
	}
}