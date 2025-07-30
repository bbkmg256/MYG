/*
 * SINGLETON PARA ALMACENAR ENTIDADES QUE SE
 * MUEVEN MUCHO ENTRE CONTROLADORES DE VISTAS.
 */

package utilidades;

import edu.unam.modelo.*;

/*
 * 
 * NOTAS:
 * 
 * ESTE SINGLETON ESTÁ SUJETO A MODIFICAR LOS ATRIBUTOS, Y LOS
 * GETTERS Y SETTERS, SI NO NECESITO ALMACENAR X ENTIDAD ACÁ.
 * 
 */

public class AlmacenadorDeEntidadesSingleton {
	// PARA EL SINGLETON
	private static AlmacenadorDeEntidadesSingleton instancia = null;
	
	// PARA ALMACENAR LAS ENTIDADES
	private Cliente entidadCliente;
	private Tutor entidadTutor;
	private GrupoMuscular entidadGM;
	private Ejercicio entidadEjercicio;
	private Entrenamiento entidadEntrenamiento;
	private Rutina entidadRutina;
	private Seguimiento entidadSeguimiento;
	
	
	// CONTRUCTOR //
	private AlmacenadorDeEntidadesSingleton() {}
	
	// RETORNO DE INSTANCIA //
	public static AlmacenadorDeEntidadesSingleton getInstancia() {
//		instancia = (instancia == null) ? new AlmacenadorDeEntidades() : instancia;
		if (instancia == null) {
			instancia = new AlmacenadorDeEntidadesSingleton();
		}
		return instancia;
	}
	
	// SET //
	public void setCliente(Cliente objCli) {
		this.entidadCliente = objCli;
	}
	
	public void setTutor(Tutor objTutor) {
		this.entidadTutor = objTutor;
	}
	
	public void setGM(GrupoMuscular objGM) {
		this.entidadGM = objGM;
	}
	
	public void setEjercicio(Ejercicio objEj) {
		this.entidadEjercicio = objEj;
	}
	
	public void setEntrenamiento(Entrenamiento objEnt) {
		this.entidadEntrenamiento = objEnt;
	}
	
	public void setRutina(Rutina objRut) {
		this.entidadRutina = objRut;
	}
	
	public void setSeguimiento(Seguimiento objSeg) {
		this.entidadSeguimiento = objSeg;
	}
	
	// GET //
	public Cliente getCliente() {
		return this.entidadCliente;
	}
	
	public Tutor getTutor() {
		return this.entidadTutor;
	}
	
	public GrupoMuscular getGM() {
		return this.entidadGM;
	}
	
	public Ejercicio getEjercicio() {
		return this.entidadEjercicio;
	}
	
	public Entrenamiento getEntrenamiento() {
		return this.entidadEntrenamiento;
	}
	
	public Rutina getRutina() {
		return this.entidadRutina;
	}
	
	public Seguimiento getSeguimiento() {
		return this.entidadSeguimiento;
	}
}
