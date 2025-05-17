/*
 * 
 * CLASE TUTOR
 * 
 * ESTA CLASE SE RELACIONA CON EL ENTRENAMIENTO (EL TUTOR IMPARTE EL ENTRENAMIENTO), FIN.
 * 
 */


// PAQUETE
package edu.unam.modelo;

// LIBRERIAS
// VARIOS
import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;

import jakarta.persistence.Basic; // MODULO JPA PARA ATRIBUTOS BASICOS
//import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity; // MODULO JPA PARA ENTIDADES/OBJETOS

// MODULOS JPA PARA EL ID
import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
// MODULO JPA PARA ATRIBUTOS REFERENTES A FECHAS
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/*
 *
 * NOTA:
 * 
 * EL TUTOR NO TIENE FECHA DE INGRESO
 * 
 */


/**
*
* @Autor: BBKMG
*/
@Entity
public class Tutor {
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
	
	// ATRIBUTO RELACION CON CLASE ENTRENAMIENTO (LISTA)
//	@OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
//	private List<Entrenamiento> entrenamientos = new ArrayList<>();
		
	// CONTRUCTOR
	public Tutor() {}
	
	public Tutor(int paramDni, String paramNombre, String paramApellido,
		LocalDate paramFechaNac, char paramSexo, String paramCiudad,
		String paramProvincia, int paramCodPost) {
		
		this.dni = paramDni;
		this.nombre = paramNombre;
		this.apellido = paramApellido;
		this.fechaNacimiento = paramFechaNac;
		this.sexo = paramSexo;
		this.ciudad = paramCiudad;
		this.provincia = paramProvincia;
		this.codigoPostal = paramCodPost;
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
	
//	public void setEntrenamientos(List<Entrenamiento> listEntrenamientos) {
//		this.entrenamientos = listEntrenamientos;
//	}
	
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
	
//	public List<Entrenamiento> getEntrenamientos(){
//		return this.entrenamientos;
//	}
}