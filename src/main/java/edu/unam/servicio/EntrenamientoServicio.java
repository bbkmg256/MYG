/*
	Servicio de Entrenamiento
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.EntrenamientoDAO;
import jakarta.persistence.EntityManager;
import utilidades.EMFSingleton;

// VARIOS
import java.util.List;
import utilidades.ParametrosEntrenamiento;

// ENTIDAD
import edu.unam.modelo.Entrenamiento;
import edu.unam.modelo.Cliente;
import edu.unam.modelo.Rutina;
import edu.unam.modelo.Tutor;

/**
*
* @Autor: BBKMG
*/
public class EntrenamientoServicio {
	private EntrenamientoDAO entreDao;
	private EntityManager manager;
//	private Cliente cli;
//	private Tutor tutor;
//	private Rutina rutina;
	private ClienteServicio scli;
	private TutorServicio stutor;
	private RutinaServicio srut;
	
	// Constructor
	public EntrenamientoServicio() {
		this.entreDao = new EntrenamientoDAO();
		this.scli = new ClienteServicio();
		this.stutor = new TutorServicio();
		this.srut = new RutinaServicio();
	}
	
	// Cargar Entrenamiento
	public void cargarEntrenamiento(Entrenamiento entrenamiento) {				
		// MANEJO DE FALLO [ ATRIBUTOS SIN CONTENIDO O NULOS ]
		if (entrenamiento.getCliente() == null || scli.obtenerCliente(entrenamiento.getCliente().getDni()) == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no tiene cliente asignado o este es nulo!%n", entrenamiento.getIdEntrenamiento());
			return;
		}
		
		if (entrenamiento.getTutor() == null || stutor.obtenerTutor(entrenamiento.getTutor().getDni()) == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no tiene tutor asignado o este es nulo!%n", entrenamiento.getIdEntrenamiento());
			return;
		}
		
		if (entrenamiento.getRutina() == null || srut.obtenerRutina(entrenamiento.getRutina().getIdRutina()) == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no tiene rutina asignada o este es nulo!%n", entrenamiento.getIdEntrenamiento());
			return;
		}
		
		if (entrenamiento.getFechaInicio() == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no tiene una fecha de inicio asignada o este es nulo!%n", entrenamiento.getIdEntrenamiento());
			return;
		}
		
		if (entrenamiento.getFechaFin() == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no tiene una fecha de finalización asignada o este es nulo!%n", entrenamiento.getIdEntrenamiento());
			return;
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			this.entreDao.crearEntidadEntrenamiento(this.manager, entrenamiento);
			
			// ENLACE A ENTIDAD CLIENTE
//			this.cli = this.manager.merge(entrenamiento.getCliente());
//			this.cli.getEntrenamientos().add(entrenamiento);
			
			// ENLACE A ENTIDAD TUTOR
//			this.tutor = this.manager.merge(entrenamiento.getTutor());
//			this.tutor.getEntrenamientos().add(entrenamiento);
			
			// ENLACE A ENTIDAD RUTINA
//			this.rutina = this.manager.merge(entrenamiento.getRutina());
//			this.rutina.getEntrenamientos().add(entrenamiento);
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El entrenamiento %d cargado correctamente!%n", entrenamiento.getIdEntrenamiento());
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al carga el entrenamiento!");
		} finally {
			this.manager.close();
		}
	}
	
	// Buscar Entrenamiento
	public Entrenamiento obtenerEntrenamiento(int id) {		
		Entrenamiento regEntre = null;
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regEntre = this.entreDao.obtenerEntidadEntrenamiento(this.manager, id);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener el entrenamiento!");
		} finally {
			this.manager.close();
		}
		
		return regEntre;
	}
	
	// Obtiene todos los Entrenamientos del sistema
	public List<Entrenamiento> obtenerTodosLosEntrenamientos() {		
		List<Entrenamiento> entr = null;
		String consulta = String.format("SELECT %c FROM %s %c", 'e', "Entrenamiento", 'e'); // Consulta JPQL

		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			entr = this.entreDao.obtenerEntidadesEntrenamiento(this.manager, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener los entrenamiento!");
		} finally {
			this.manager.close();
		}
		
		return entr;
	}

	// Actualizar Entrenamiento
	public void actualizarEstadoEntrenamiento(int id, ParametrosEntrenamiento paramEntre) {		
		Entrenamiento entrenamiento = this.obtenerEntrenamiento(id);
//		Cliente clienteAntiguo = null;
//		Tutor tutorAntiguo = null;
//		Rutina rutinaAntigua = null;
		
		// MANEJO DE FALLO [ ENTIDAD NO EXISTENTE EN LA BD ]
		if (entrenamiento == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		// ALMACENA LOS OBJETOS ANTIGUAMENTE RELACIONADOS CON LA ENTIDAD
//		clienteAntiguo = entrenamiento.getCliente();
//		tutorAntiguo = entrenamiento.getTutor();
//		rutinaAntigua = entrenamiento.getRutina();
		
		// SE MODIFICA EL OBJETO
		if (paramEntre.cliente != null) {
			if (scli.obtenerCliente(paramEntre.cliente.getDni()) == null) {
				System.out.println("[ ERROR ] > El parámetro cliente no es válido!");
				return;
			}
			entrenamiento.setCliente(paramEntre.cliente);
		}
		
		if (paramEntre.tutor != null) {
				if (stutor.obtenerTutor(paramEntre.tutor.getDni()) == null) {
					System.out.println("[ ERROR ] > El parámetro tutor no es válido!");
					return;
			}
			entrenamiento.setTutor(paramEntre.tutor);
		}
		
		if (paramEntre.rutina != null) {
			if (srut.obtenerRutina(paramEntre.rutina.getIdRutina()) == null) {
				System.out.println("[ ERROR ] > El parámetro rutina no es válido!");
				return;
			}
			entrenamiento.setRutina(paramEntre.rutina);
		}
		
		if (paramEntre.fechaFin != null) {
			entrenamiento.setFechaFin(paramEntre.fechaFin);
		}
		
		if (paramEntre.fechaInicio != null) {
			entrenamiento.setFechaInicio(paramEntre.fechaInicio);
		}
		
		// EL LIMITE INFERIOR SERIA 1 (PEOR VALOR)
		if (paramEntre.puntaje != 0) {
			entrenamiento.setPuntaje(paramEntre.puntaje);
		}
		
		//  QUTAR ESTO MAS TARDE (TANTO DE LA CLASE DE PARAMETROS, COMO DE LA ENTIDAD)
//		if (paramEntre.volumenEntrenamiento != 0) {
//			entrenamiento.setVolumenEntrenamiento(paramEntre.volumenEntrenamiento);
//		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			// ACTUALIZA Y RECONECTA EL OBJETO AL CONTEXTO DEL E.M
			entrenamiento = this.entreDao.actualizarEntidadEntrenamiento(this.manager, entrenamiento);
			// System.out.println("A");
			
//			if (paramEntre.cliente != null) {
//				// ENLAZADO A CLIENTE NUEVO
//				this.cli = this.manager.merge(entrenamiento.getCliente());
//				this.cli.getEntrenamientos().add(entrenamiento);
//				
//				// System.out.println("B");
//				
//				// DESENLAZADO DE CLIENTE VIEJO
//				clienteAntiguo = this.manager.merge(clienteAntiguo);
//				clienteAntiguo.getEntrenamientos().remove(entrenamiento);
//				
//				// System.out.println("C");
//			}
//			
//			if (paramEntre.tutor != null) {
//				// ENLAZADO A TUTOR NUEVO
//				this.tutor = this.manager.merge(entrenamiento.getTutor());
//				this.tutor.getEntrenamientos().add(entrenamiento);
//				
//				// System.out.println("D");
//				
//				// DESENLAZADO DE TUTOR VIEJO
//				tutorAntiguo = this.manager.merge(tutorAntiguo);
//				tutorAntiguo.getEntrenamientos().remove(entrenamiento);
//				
//				// System.out.println("F");
//			}
//			
//			if (paramEntre.rutina != null) {
//				// ENLAZADO A RUTINA NUEVA
//				this.rutina = this.manager.merge(entrenamiento.getRutina());
//				this.rutina.getEntrenamientos().add(entrenamiento);
//				
//				// System.out.println("G");
//				
//				// DESENLAZADO DE RUTINA VIEJA
//				rutinaAntigua = this.manager.merge(rutinaAntigua);
//				rutinaAntigua.getEntrenamientos().remove(entrenamiento);
//				
//				// System.out.println("H");
//			}
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El entrenamiento %d actualizado correctamente!%n", id);
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualizar el entrenamiento!");
		} finally {
			this.manager.close();
		}
	}

	// Eliminar Entrenamiento
	public void eliminarEntrenamiento(int id) {		
		Entrenamiento entrenamiento = this.obtenerEntrenamiento(id);
		
		// MANEJO DE FALLO [ ENTIDAD NO EXISTENTE EN LA BD ]
		if (entrenamiento == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			entrenamiento = this.manager.merge(entrenamiento); // RECONECTA LA ENTIDAD AL CONTEXTO DE LA TRANSACCION
			this.entreDao.eliminarEntidadEntrenamiento(this.manager, entrenamiento);
			
			// DESENLACE CLIENTE
//			this.cli = this.manager.merge(entrenamiento.getCliente());
//			this.cli.getEntrenamientos().remove(entrenamiento);
			
			// DESENLACE TUTOR
//			this.tutor = this.manager.merge(entrenamiento.getTutor());
//			this.tutor.getEntrenamientos().remove(entrenamiento);
			
			// DESENLACE RUTINA
//			this.rutina = this.manager.merge(entrenamiento.getRutina());
//			this.rutina.getEntrenamientos().remove(entrenamiento);
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El entrenamiento %d eliminado correctamente!%n", id);
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al eliminar el entrenamiento!");
		} finally {
			this.manager.close();
		}
	}
}