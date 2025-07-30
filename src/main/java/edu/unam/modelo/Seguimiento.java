/*
 * 
 * CLASE SEGUIMIENTO
 * 
 * ESTA CLASE REGISTRA LO QUE EL CLIENTE REALIZA RESPECTO A SU ENTRENAMIENTO
 * (DE FORMA DIARIA)
 * 
 */


// PAQUETES
package edu.unam.modelo;

// LIBRERIAS
// VARIOS
import java.time.LocalDate;

import jakarta.persistence.Basic; // MODULO JPA PARA ATRIBUTOS BASICOS
import jakarta.persistence.Entity; // MODULO JPA PARA ENTIDADES/OBJETOS
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToMany;

// MODULOS JPA PARA GENERACIONES DE ID, VALORES DE GENERACION DE ID Y FORMA DE GENERACION DE ID
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// MODULO JPA PARA ATRIBUTOS REFERENTES A FECHAS
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/*
 * 
 * NOTA:
 * 
 * PARA OBTENER EL VOLUMEN DE ENTRENAMIENTO SEMANAL, DEBERÍA TENER COMO ATRIBUTO
 * UNA INSTANCIA DE Ejercicio. EN BASE A ESTE SE PUEDE CALCULAR EL VOLUMEN DE
 * ENTRENAMIENTO SEMANAL, ADEMAS DE QUE ESTA ENTIDAD DEBERÍA ALMACENAR QUE EJERCICIO
 * TRABAJÓ EN EL DÍA.
 * 
 * RECORDATORIO: TRANQUI VIEJO, LA ENTIDAD ESTÁ BIEN, SE SUPONE QUE ALMACENA TODOS
 * LOS SEGUIMIENTOS COMO EN CRUDO, EN EL FRONT SE LO VA A MOLDEAR PARA PRESENTARLO
 * DE FORMA ENTENDIBLE AL USUARIO.
 * 
 * SEGUNDO RECORDATORIO: NO TAN TRANQUI VIEJO, ESTA MIERDA TIENE QUE ESTAR APARTADA
 * Y NO RELACIONADA CON EL RESTO DE LAS CLASES PARA EVITAR PROBLEMAS AL QUERES ELIMINAR
 * OBJETOS PADRES E HIJOS AL SER MODIFICADOS, EN FORMA DE CASCADA. SE SUPONE QUE EL
 * SEGUIMIENTO ES COMO UN HISTORIAL O REGISTRO DEL ENTRENAMIENTO REALIZADO POR EL
 * CLIENTE, SI ALGUN GRUPO MUSCULAR, EJERCICIO, RUTINA O ENTRENAMIENTO SE ELIMINA,
 * ESTE NO DEBERÍA VERSE AFECTADO, A NO SER QUE EL MISMO USUARIO META MANO EN LA
 * ENTIDAD, DE LO CONTRARIO NO SE DEBE MODIFICAR NI ELIMINAR NADA ACÁ.
 * 
 */


/**
*
* @Autor: BBKMG
*/
@Entity
public class Seguimiento {
	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idSeguimiento;
	
	@Basic
	@Column(name = "cantidad_series_realizadas")
	private int cantSerieRealizado;
	@Column(name = "cantidad_repeticiones_realizadas")
	private int cantRepeticionesRealizado;
	@Column(name = "peso_trabajado")
	private double pesoTrabajado;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_hoy")
	private LocalDate fechaHoy;
	
	// ATRIBUTO RELACION CON CLASE ENTRENAMIENTO
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entrenamiento_id")
	private Entrenamiento entrenamiento; // FK'S DE ENTRENAMIENTO
	
	// ATRIBUTO RELACION CON CLASE EJERCICIO
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ejercicio_realizado")
	private Ejercicio ejercicioRealizado;
	
	// CONTRUCTOR
	public Seguimiento() {}
	
	public Seguimiento(
			LocalDate paramFechaHoy, Ejercicio paramEjercicio,
			int paramCantSeries, int paramCantRep,
			double paramPesoTrabajado,
			Entrenamiento paramEntrenamiento
	) {
		
		this.fechaHoy = paramFechaHoy;
		this.ejercicioRealizado = paramEjercicio;
		this.cantSerieRealizado = paramCantSeries;
		this.cantRepeticionesRealizado = paramCantRep;
		this.pesoTrabajado = paramPesoTrabajado;
		this.entrenamiento = paramEntrenamiento;
	}
	
	// SET
	public void setIdSeguimiento(int valIdSeg) {
		this.idSeguimiento = valIdSeg;
	}
	
	public void setFechaHoy(LocalDate valFechaHoy) {
		this.fechaHoy = valFechaHoy;
	}
	
	public void setCantSerieRealizado(int valCantSerieRelizado) {
		this.cantSerieRealizado = valCantSerieRelizado;
	}
	
	public void setCantRepeticionRealizado(int valCantRepeticionRealizado) {
		this.cantRepeticionesRealizado = valCantRepeticionRealizado;
	}
	
	public void setEjercicioRealizado(Ejercicio paramEjercicioRealizado) {
		this.ejercicioRealizado = paramEjercicioRealizado;
	}
	
	public void setPesoTrabajado(double valPesoTrabajado) {
		this.pesoTrabajado = valPesoTrabajado;
	}
	
	public void setEntrenamiento(Entrenamiento paramEntrenamiento) {
		this.entrenamiento = paramEntrenamiento;
	}
	
	// GET
	public int getIdSeguimiento() {
		return this.idSeguimiento;
	}
	
	public LocalDate getFechaHoy() {
		return this.fechaHoy;
	}
	
	public int getCantSerieRealizado() {
		return this.cantSerieRealizado;
	}
	
	public int getCantRepeticionesRealizado() {
		return this.cantRepeticionesRealizado;
	}
	
	public Ejercicio getEjercicioRealizado() {
		return this.ejercicioRealizado;
	}
	
	public double getPesoTrabajado() {
		return this.pesoTrabajado;
	}
	
	public Entrenamiento getEntrenamiento() {
		return this.entrenamiento;
	}
}