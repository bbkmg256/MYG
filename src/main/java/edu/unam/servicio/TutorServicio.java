/*
	Servicio de tutor
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.TutorDAO;

// Varios
import java.time.LocalDate;
import java.util.List;

// Entidad
import edu.unam.modelo.Tutor;

/**
*
* @Autor: BBKMG
*/
public class TutorServicio {
	private TutorDAO tjpac;
	
	// Contructor
	public TutorServicio() {
		tjpac = new TutorDAO();
	}
	
	// Registra un tutor en el sistema
	public void cargarrTutor(Tutor tutor) {
		if (tjpac.obtenerEntidadTutor(tutor.getDni()) != null) {
			System.out.printf("[ ERROR ] > El tutor %d ya se encuentra en el sistema!%n", tutor.getDni());
			return;
		}
		
		// MODIFICA TODOS LOS VALORES TEXTUALES QUE TENGA EL OBJETO, A MINUSCULA.
		tutor.setNombre(tutor.getNombre().toLowerCase());
		tutor.setApellido(tutor.getApellido().toLowerCase());
		tutor.setCiudad(tutor.getCiudad().toLowerCase());
		tutor.setProvicia(tutor.getProvincia().toLowerCase());
		tutor.setSexo(Character.toLowerCase(tutor.getSexo()));
		
		// CARGA EL OBJETO A LA BD
		tjpac.crearEntidadTutor(tutor);
	}
	
	// Busca un tutor en el sistema
	public Tutor obtenerTutor(int dni) {
		Tutor tutor = tjpac.obtenerEntidadTutor(dni);
		
		if (tutor == null) {
			System.out.printf("[ ERROR ] > El tutor %d no se encuentra en el sistema!%n", dni);
		}
		return tutor;
	}
	
	// Obtener todos los Tutores del sistema
	public List<Tutor> obtenerTodosLosTutores(){
		List<Tutor> tutor = tjpac.obtenerEntidadesTutor();
		
		if (tutor == null) {
			System.out.printf("[ ERROR ] > No hay tutores en el sistema!%n");
		}
		return tutor;
	}
	
	// Actualiza la informacion de un tutor en el sistema
	public void actualizarEstadoTutor(int dni, String nombre, String apellido, char sexo,
									String ciudad, String provincia, int codigoPostal,
									LocalDate fechaNacimiento, LocalDate fechaIngreso) {
		
		Tutor tutor = tjpac.obtenerEntidadTutor(dni);
		
		if (tutor == null) {
			System.out.printf("[ ERROR ] > El tutor %d no se encuentra en el sistema!%n", dni);
			return;
		}
		
		// ALGUNOS PARAMETROS SE MODIFICAN A MINISCULA
		tjpac.actualizarEntidadTutor(tutor, nombre.toLowerCase(), apellido.toLowerCase(),
								Character.toLowerCase(sexo), ciudad.toLowerCase(),
								provincia.toLowerCase(), codigoPostal,
								fechaNacimiento, fechaIngreso);
	}
	
	// Da de baja a un tutor del sistema
	public void eliminarTutor(int dni) {
		Tutor tutor = tjpac.obtenerEntidadTutor(dni);
		
		if (tutor == null) {
			System.out.printf("[ ERROR ] > El tutor %d no se encuentra en el sistema!%n", dni);
			return;
		}
		
		tjpac.eliminarEntidadTutor(tutor);
	}

	// CERRAR CONEXION
	public void finalizarConexion() {
		if (tjpac != null && tjpac.hayConexion()) {
			tjpac.cerrarEMF();			
		}
	}
}
