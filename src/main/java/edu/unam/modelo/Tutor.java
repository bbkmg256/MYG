
// Clase tutor

/*

El tutor imparte el entrenamiento al cliente, fin.

*/

// Paquete
package edu.unam.modelo;

// Libs
import java.util.Date;
import java.util.ArrayList;

import jakarta.persistence.Basic; // Modulo JPA para atributos basicos
import jakarta.persistence.Entity; // Modulo JPA para entidades/objetos

// Modulos JPA para generacion de ID, valores de generación de ID y forma de generación de ID
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// Modulo JPA para atributos referentes a fechas
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author bbkmg
 */
@Entity
public class Tutor {
	// Atributos
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int dni;
	
	@Basic
	private String nombre;
	private String apellido;
	private char sexo;
	private String ciudad;
	private String provincia;
	private int codigoPostal;
	
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	// atributo relacion con clase Entrenamiento (Lista)
	// private ArrayList<Entrenamiento> entrenamientos_t = new ArrayList<>();
		
	// Constructor
        Tutor(int paramDni, String paramNombre, String paramApellido,
		Date paramFechaNac, char paramSexo, String paramCiudad,
		String paramProvincia, int paramCodPost, Date paramFechaIng){
		
		this.dni = paramDni;
		this.nombre = paramNombre;
		this.apellido = paramApellido;
		this.fechaNacimiento = paramFechaNac;
		this.sexo = paramSexo;
		this.ciudad = paramCiudad;
		this.provincia = paramProvincia;
		this.codigoPostal = paramCodPost;
        }
	
	// Set
	public void setDni(int valDni){
		this.dni = valDni;
	}
	
	public void setNombre(String valNombre){
		this.nombre = valNombre;
	}
	
	public void setApellido(String valApellido){
		this.apellido = valApellido;
	}
	
	public void setFechaNacimiento(Date valFechaNac){
		this.fechaNacimiento = valFechaNac;
	}
	
	public void setSexo(char valSexo){
		this.sexo = valSexo;
	}
	
	public void setCiudad(String valCiudad){
		this.ciudad = valCiudad;
	}
	
	public void setProvicia(String valProvincia){
		this.provincia = valProvincia;
	}
	
	public void setCodigoPostal(int valCodPost){
		this.codigoPostal = valCodPost;
	}	
	
	// Get
	public int getDni(){
		return this.dni;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public String getApellido(){
		return this.apellido;
	}
	
	public Date getFechaNacimiento(){
		return this.fechaNacimiento;
	}
	
	public char getSexo(){
		return this.sexo;
	}
	
	public String getCiudad(){
		return this.ciudad;
	}
	
	public String getProvincia(){
		return this.provincia;
	}
	
	public int getCodPost(){
		return this.codigoPostal;
	}
	
	// Metodos
}