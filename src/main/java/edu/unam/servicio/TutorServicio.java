/*
	Servicio de tutor
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.TutorDAO;
import jakarta.persistence.EntityManager;

// Varios
import java.time.LocalDate;
import java.util.List;

import utilidades.EMFSingleton;
import utilidades.ParametrosClienteTutor;

// Entidad
import edu.unam.modelo.Tutor;

/**
*
* @Autor: BBKMG
*/
public class TutorServicio {
	// ATRIBUTOS
	// ADMINISTRADOR DE ENTIDADES
	private EntityManager manager;
	// OBJETO DE ACCESO A DATOS
	private TutorDAO tutorDao;
	
	// Contructor
	public TutorServicio() {
		tutorDao = new TutorDAO();
	}
	
	// Registra un tutor en el sistema
	public void cargarTutor(Tutor tutor) {
		if (this.obtenerTutor(tutor.getDni()) != null) {
			System.out.printf("[ ERROR ] > El tutor %d ya se encuentra en el sistema!%n", tutor.getDni());
			return;
		}
	
		// MODIFICA TODOS LOS VALORES TEXTUALES QUE TENGA EL OBJETO, A MINUSCULA.
		tutor.setNombre(tutor.getNombre().toLowerCase());
		tutor.setApellido(tutor.getApellido().toLowerCase());
		tutor.setCiudad(tutor.getCiudad().toLowerCase());
		tutor.setProvicia(tutor.getProvincia().toLowerCase());
		tutor.setSexo(Character.toLowerCase(tutor.getSexo()));
		
		// ADMINISTRADOR DE ENTIDADES
		manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			manager.getTransaction().begin();
			tutorDao.crearEntidadTutor(manager, tutor);
			manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El tutor %d cargado correctamente!%n", tutor.getDni());
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al cargar el tutor!");
		} finally {
			manager.close();
		}
	}

	// Busca un tutor en el sistema
	public Tutor obtenerTutor(int dni) {		
		Tutor regTutor = null;
		
		// ADMINISTRADOR DE ENTIDADES
		manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regTutor = tutorDao.obtenerEntidadTutor(manager, dni);
		} catch (Exception e) {
			System.out.println(e);
			System.out.print("[ ERROR ] > Falla al obtener el tutor!");
		} finally {
			manager.close();
		}
		
		return regTutor;
	}
	
	// Obtener todos los Tutores del sistema
	public List<Tutor> obtenerTodosLosTutores(){
		String consulta = String.format("SELECT %c FROM %s %c", 't', "Tutor", 't'); // Consulta JPQL
		List<Tutor> listaTutores = null;
		
		// ADMINISTRADOR DE ENTIDADES
		manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			listaTutores = tutorDao.obtenerEntidadesTutor(manager, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener los tutores!");
		} finally {
			manager.close();
		}
				
		return listaTutores;
	}
	
	// Actualiza la informacion de un tutor en el sistema
	public void actualizarEstadoTutor(int dni, ParametrosClienteTutor paramTutor) {
//		Tutor tutor = tjpac.obtenerEntidadTutor(dni);
//		
//		if (tutor == null) {
//			System.out.printf("[ ERROR ] > El tutor %d no se encuentra en el sistema!%n", dni);
//			return;
//		}
//
//		// CONDICIONES PARA VERIFICAR CUALES ATRIBUTOS SE MODIFICAN, Y CUALES NO
//		if (paramTutor.nombre != null) {
//			tutor.setNombre(paramTutor.nombre.toLowerCase());
//		}
//		
//		if (paramTutor.apellido != null) {
//			tutor.setApellido(paramTutor.apellido.toLowerCase());
//		}
//		
//		if (paramTutor.sexo != 'x') {
//			tutor.setSexo(Character.toLowerCase(paramTutor.sexo));
//		}
//		
//		if (paramTutor.ciudad != null) {
//			tutor.setCiudad(paramTutor.ciudad.toLowerCase());
//		}
//		
//		if (paramTutor.provincia != null) {
//			tutor.setProvicia(paramTutor.provincia.toLowerCase());
//		}
//		
//		if (paramTutor.codigoPostal != 0) {
//			tutor.setCodigoPostal(paramTutor.codigoPostal);
//		}
//		
//		if (paramTutor.fechaNacimiento != null) {
//			tutor.setFechaNacimiento(paramTutor.fechaNacimiento);
//		}
//		
//		tjpac.actualizarEstadoTutor(tutor);
		
		Tutor tutor = this.obtenerTutor(dni);
		
		if (tutor == null) {
			System.out.printf("[ ERROR ] > El tutor %d no se encuentra en el sistema!%n", dni);
			return;
		}

		// CONDICIONES PARA VERIFICAR CUALES ATRIBUTOS SE MODIFICAN, Y CUALES NO
		if (paramTutor.nombre != null) {
			tutor.setNombre(paramTutor.nombre.toLowerCase());
		}
		
		if (paramTutor.apellido != null) {
			tutor.setApellido(paramTutor.apellido.toLowerCase());
		}
		
		if (paramTutor.sexo != 'x') {
			tutor.setSexo(Character.toLowerCase(paramTutor.sexo));
		}
		
		if (paramTutor.ciudad != null) {
			tutor.setCiudad(paramTutor.ciudad.toLowerCase());
		}
		
		if (paramTutor.provincia != null) {
			tutor.setProvicia(paramTutor.provincia.toLowerCase());
		}
		
		if (paramTutor.codigoPostal != 0) {
			tutor.setCodigoPostal(paramTutor.codigoPostal);
		}
		
		if (paramTutor.fechaNacimiento != null) {
			tutor.setFechaNacimiento(paramTutor.fechaNacimiento);
		}
		
		// ADMINISTRADOR DE ENTIDADES
		manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			manager.getTransaction().begin();
			tutorDao.actualizarEstadoTutor(manager, tutor);
			manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El tutor %d actualizado correctamente!%n", tutor.getDni());
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualizar el tutor!");
		} finally {
			manager.close();
		}
	}
	
	// Da de baja a un tutor del sistema
	public void eliminarTutor(int dni) {
//		Tutor tutor = tjpac.obtenerEntidadTutor(dni);
//		
//		if (tutor == null) {
//			System.out.printf("[ ERROR ] > El tutor %d no se encuentra en el sistema!%n", dni);
//			return;
//		}
//		
//		tjpac.eliminarEntidadTutor(tutor);
		
		Tutor tutor = this.obtenerTutor(dni);
		
		if (tutor == null) {
			System.out.printf("[ ERROR ] > El tutor %d no se encuentra en el sistema!%n", dni);
			return;
		}
		
		// (MAN, COMO ME ENCANTA DEFTONES!!) :)
		
		// ADMINISTRADOR DE ENTIDADES
		manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			manager.getTransaction().begin();
			tutorDao.eliminarEntidadTutor(manager, tutor);
			manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El tutor %d eliminado correctamente!%n", tutor.getDni());
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al eliminar el Tutor!");
		} finally {
			manager.close();
		}
	}
}
