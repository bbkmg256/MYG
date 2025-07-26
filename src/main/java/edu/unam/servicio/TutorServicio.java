/*
	Servicio de tutor
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.TutorDAO;
import jakarta.persistence.EntityManager;

// Varios
// import java.time.LocalDate;
import java.util.List;

import utilidades.bd.EMFSingleton;
import utilidades.parametros.ParametrosClienteTutor;
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
		// VERIFICA QUE NO EXISTA YA UN TUTOR CON EL MISMO DNI
		if (this.obtenerTutor(tutor.getDni(), false) != null) {
			System.out.printf("[ ERROR ] > El tutor %d ya se encuentra en el sistema!%n", tutor.getDni()); // LOG
			return;
		}
		
		if (tutor.getNombre() == null) {
			System.out.printf("[ ERROR ] > El tutor %d no tiene un nombre asignado o este es nulo!%n", tutor.getDni()); // LOG
			return;
		}
		
		if (tutor.getApellido() == null) {
			System.out.printf("[ ERROR ] > El tutor %d no tiene un apellido asignado o este es nulo!%n", tutor.getDni()); // LOG
			return;
		}
		
		if (tutor.getFechaNacimiento() == null) {
			System.out.printf("[ ERROR ] > El tutor %d no tiene una fecha de namcimiento asignada o este es nulo!%n", tutor.getDni()); // LOG
			return;
		}
		
		if (tutor.getCiudad() == null) {
			System.out.printf("[ ERROR ] > El tutor %d no tiene una ciudad asignada o este es nulo!%n", tutor.getDni()); // LOG
			return;
		}
		
		if (tutor.getProvincia() == null) {
			System.out.printf("[ ERROR ] > El tutor %d no tiene una ciudad asignada o este es nulo!%n", tutor.getDni()); // LOG
			return;
		}
		
		// MODIFICA TODOS LOS VALORES TEXTUALES QUE TENGA EL OBJETO, A MINUSCULA.
		tutor.setNombre(tutor.getNombre().toLowerCase());
		tutor.setApellido(tutor.getApellido().toLowerCase());
		tutor.setCiudad(tutor.getCiudad().toLowerCase());
		tutor.setProvicia(tutor.getProvincia().toLowerCase());
		tutor.setSexo(Character.toLowerCase(tutor.getSexo()));
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			this.tutorDao.crearEntidadTutor(this.manager, tutor);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El tutor %d cargado correctamente!%n", tutor.getDni()); // LOG
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al cargar el tutor!"); // LOG
		} finally {
			this.manager.close();
		}
	}

	// Busca un tutor en el sistema
	public Tutor obtenerTutor(int dni, boolean mostrarLog) {		
		Tutor regTutor = null;
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regTutor = this.tutorDao.obtenerEntidadTutor(this.manager, dni);
		} catch (Exception e) {
			System.out.println(e);
			System.out.print("[ ERROR ] > Falla al obtener el tutor!"); // LOG
		} finally {
			this.manager.close();
		}
		
		if (mostrarLog && regTutor == null) {
			System.out.printf("[ ERROR ] > Tutor %d no encontrado%n", dni); // LOG
		}
		
		return regTutor;
	}
	
	// Obtener todos los Tutores del sistema
	public List<Tutor> obtenerTodosLosTutores(){
		String consulta = String.format("SELECT %c FROM %s %c", 't', "Tutor", 't'); // Consulta JPQL
		List<Tutor> listaTutores = null;
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			listaTutores = this.tutorDao.obtenerEntidadesTutor(this.manager, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener los tutores!"); // LOG
		} finally {
			this.manager.close();
		}
				
		return listaTutores;
	}
	
	// Actualiza la informacion de un tutor en el sistema
	public boolean actualizarEstadoTutor(int dni, ParametrosClienteTutor paramTutor) {
		Tutor tutor = this.obtenerTutor(dni, false);
		
		if (tutor == null) {
			System.out.printf("[ ERROR ] > El tutor %d no se encuentra en el sistema!%n", dni); // LOG
			return false;
		}

		// CONDICIONES PARA VERIFICAR CUALES ATRIBUTOS SE MODIFICAN, Y CUALES NO
		if (paramTutor.nombre != null && paramTutor.nombre != tutor.getNombre()) {
			tutor.setNombre(paramTutor.nombre.toLowerCase());
		}
		
		if (paramTutor.apellido != null && paramTutor.apellido != tutor.getApellido()) {
			tutor.setApellido(paramTutor.apellido.toLowerCase());
		}
		
		if (paramTutor.sexo != ' ' && paramTutor.sexo != tutor.getSexo()) {
			tutor.setSexo(Character.toLowerCase(paramTutor.sexo));
		}
		
		if (paramTutor.ciudad != null && paramTutor.ciudad != tutor.getCiudad()) {
			tutor.setCiudad(paramTutor.ciudad.toLowerCase());
		}
		
		if (paramTutor.provincia != null && paramTutor.provincia != tutor.getProvincia()) {
			tutor.setProvicia(paramTutor.provincia.toLowerCase());
		}
		
		if (paramTutor.codigoPostal != 0 && paramTutor.codigoPostal != tutor.getCodPost()) {
			tutor.setCodigoPostal(paramTutor.codigoPostal);
		}
		
		if (paramTutor.fechaNacimiento != null && paramTutor.fechaNacimiento != tutor.getFechaNacimiento()) {
			tutor.setFechaNacimiento(paramTutor.fechaNacimiento);
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			this.tutorDao.actualizarEstadoTutor(this.manager, tutor);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El tutor %d actualizado correctamente!%n", tutor.getDni()); // LOG
			return true;
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualizar el tutor!"); // LOG
			return false;
		} finally {
			this.manager.close();
		}
	}
	
	// Da de baja a un tutor del sistema
	public void eliminarTutor(int dni) {		
		Tutor tutor = this.obtenerTutor(dni, false);
		
		if (tutor == null) {
			System.out.printf("[ ERROR ] > El tutor %d no se encuentra en el sistema!%n", dni); // LOG
			return;
		}
		
		// (MAN, COMO ME ENCANTA DEFTONES!!) :)
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			tutor = this.manager.merge(tutor);
			this.tutorDao.eliminarEntidadTutor(this.manager, tutor);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El tutor %d eliminado correctamente!%n", tutor.getDni()); // LOG
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al eliminar el Tutor!"); // LOG
		} finally {
			this.manager.close();
		}
	}
}
