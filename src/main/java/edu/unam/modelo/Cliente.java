/*
 * 
 * CLASE CLIENTE
 * 
 * EL CLIENTE TIENE UN TUTOR Y UN ENTRENAMIENTO ASIGNADO, FIN.
 * 
 */


// PAQUETE
package edu.unam.modelo;

// LIBRERIAS
// VARIOS
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Basic; // MODULO JPA PARA ATRIBUTOS BASICOS
import jakarta.persistence.Entity; // MODULO JPA PARA ENTIDADES/OBJETOS

// MODULOS JPA PARA EL ID
import jakarta.persistence.Id;

// MODULO JPA PARA ATRIBUTOS REFERENTES A FECHAS
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

// MODULOS JPA PARA EL MAPEADO DE RELACIONES
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;


/*
 * NOTA:
 * 
 * ESTA MAMADA TIENE INFORMACION DE MAS O MENOS COMO VA EL TEMA DEL
 * MAPEADO DE RELACIONES, YA QUE ES MEDIO UN LIO XD.
 * 
 * LINK -> https://www.youtube.com/watch?v=4p-cwPQ-b4Y&list=PLTd5ehIj0goPcnQs34i0F-Kgp5JHX8UUv&index=14
 * 
 * NO DESCOMENTAR LOS MAPEADOS RELACIONALES POR EL MOMENTO POR QUE SE ROMPE EL CODIGO!!
 * 
 * TEORICAMENTE TUTOR Y CLIENTE DEBERÍAN HEREDAR DE UNA CLASE PERSONA, PERO PARA NO COMPLICARLA VOY A DEJARLO ASÍ POR EL MOMENTO,
 * CUANDO EL BACKEND ESTE MAS AVANZADO, VERE COMO APLICAR ESO.
 */


/**
*
* @Autor: BBKMG
*/
@Entity
public class Cliente {
	// ATRIBUTOS
	@Id
	private int dni;

	@Basic
	private String nombre;
	private String apellido;
	private char sexo;
	private String ciudad;
	private String provincia;
	@Column(name = "codigo_postal")
	private int codigoPostal;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;
	@Column(name = "fecha_ingreso")
	private LocalDate fechaIngreso;
	
	// ATRIBUTO RELACION CON CLASE ENTRENAMIENTO (LISTA)
	// MappedBy -  MAPEA EL ATRIBUTO DE LA ENTIDAD DE MUCHAS A EL CONJUNTO DE ESTA ENTIDAD
	// cascade -  ES PARA DEFINIR QUE SE CREARAN ENTIDADES EN LA TABLA RELACIONADA SI SON CREADAS EN ESTA ENTIDAD PRIMERO
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Entrenamiento> entrenamientos = new ArrayList<>(); // Cliente (1) a Entrenamiento (*)
	
	// CONSTRUCTOR
	public Cliente() {}
	
	public Cliente(int paramDni, String paramNombre, String paramApellido,
		LocalDate paramFechaNac, char paramSexo, String paramCiudad,
		String paramProvincia, int paramCodPost, LocalDate paramFechaIng) {
		
		this.dni = paramDni;
		this.nombre = paramNombre;
		this.apellido = paramApellido;
		this.fechaNacimiento = paramFechaNac;
		this.sexo = paramSexo;
		this.ciudad = paramCiudad;
		this.provincia = paramProvincia;
		this.codigoPostal = paramCodPost;
		this.fechaIngreso = paramFechaIng;
	}
	
	// SET
	public void setDni(int valDni) {
		this.dni = valDni;
	}
	
	public void setNombre(String valNombre) {
		this.nombre = valNombre;
	}
	
	public void setApellido(String valApellido) {
		this.apellido = valApellido;
	}
	
	public void setFechaNacimiento(LocalDate valFechaNac) {
		this.fechaNacimiento = valFechaNac;
	}
	
	public void setSexo(char valSexo) {
		this.sexo = valSexo;
	}
	
	public void setCiudad(String valCiudad) {
		this.ciudad = valCiudad;
	}
	
	public void setProvicia(String valProvincia) {
		this.provincia = valProvincia;
	}
	
	public void setCodigoPostal(int valCodPost) {
		this.codigoPostal = valCodPost;
	}
	
	public void setFechaIngreso(LocalDate valFechaIng) {
		this.fechaIngreso = valFechaIng;
	}
	
	public void setEntrenamientos(List<Entrenamiento> listEntrenamientos) {
		this.entrenamientos = listEntrenamientos;
	}
	
	// GET
	public int getDni() {
		return this.dni;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getApellido() {
		return this.apellido;
	}
	
	public LocalDate getFechaNacimiento() {
		return this.fechaNacimiento;
	}
	
	public char getSexo() {
		return this.sexo;
	}
	
	public String getCiudad() {
		return this.ciudad;
	}
	
	public String getProvincia() {
		return this.provincia;
	}
	
	public int getCodPost() {
		return this.codigoPostal;
	}
	
	public LocalDate getFechaIngreso() {
		return this.fechaIngreso;
	}
	
	public List<Entrenamiento> getEntrenamientos(){
		return this.entrenamientos;
	}
}