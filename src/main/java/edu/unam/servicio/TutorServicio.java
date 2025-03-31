/*
	Servicio de tutor
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.TutorJPAController;

// Varios
import java.time.LocalDate;

// Entidad
import edu.unam.modelo.Tutor;

/**
*
* @author bbkmg
*/
public class TutorServicio {
	TutorJPAController tjpac;
	
	// Contructor
	public TutorServicio() {
		tjpac = new TutorJPAController();
	}
	
	// Registra un tutor en el sistema
	public void registrarTutor(Tutor tutor) {
		if (tjpac.obtenerTutor(tutor.getDni()) != null) {
			System.out.printf("[ERROR] > El tutor %d ya se encuentra en el sistema!%n", tutor.getDni());
		} else {
			tjpac.crearTutor(tutor);
		}
		
		tjpac.cerrarEMF();
	}
	
	// Busca un tutor en el sistema
	public Tutor obtenerTutor(int dni) {
		Tutor tutor = tjpac.obtenerTutor(dni);
		
		if (tutor == null) {
			System.out.printf("[ERROR] > El tutor %d no se encuentra en el sistema!%n", dni);
		}
		
		tjpac.cerrarEMF();
		return tutor;
	}
	
	// Actualiza la informacion de un tutor en el sistema
	public void actualizarInfTutor(int dni, String nombre, String apellido, char sexo,
									String ciudad, String provincia, int codigoPostal,
									LocalDate fechaNacimiento, LocalDate fechaIngreso) {
		
		Tutor tutor = tjpac.obtenerTutor(dni);
		
		if (tutor != null) {
			tjpac.actualizarTutor(tutor, nombre, apellido, sexo, ciudad, provincia, codigoPostal, fechaNacimiento, fechaIngreso);
		} else {
			System.out.printf("[ERROR] > El tutor %d no se encuentra en el sistema!%n", dni);
		}
		
		tjpac.cerrarEMF();
	}
	
	// Da de baja a un tutor del sistema
	public void eliminarTutor(int dni) {
		Tutor tutor = tjpac.obtenerTutor(dni);
		
		if (tutor != null) {
			tjpac.eliminarTutor(tutor);
		} else {
			System.out.printf("[ERROR] > El tutor %d no se encuentra en el sistema!%n", dni);
		}
		
		tjpac.cerrarEMF();
	}
}
