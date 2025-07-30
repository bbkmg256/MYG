/*
	Servicio de Ejercicio
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.EjercicioDAO;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
// VARIOS
import java.util.List;

import utilidades.bd.EMFSingleton;
import utilidades.parametros.ParametrosEjercicio;
// ENTIDAD
import edu.unam.modelo.Ejercicio;
import edu.unam.modelo.GrupoMuscular;
import edu.unam.modelo.RutinaEjercicio;
import edu.unam.modelo.Seguimiento;

/**
*
* @Autor: BBKMG
*/
public class EjercicioServicio {
	// ATRIBUTOS
	private EntityManager manager;
	private EjercicioDAO ejerDao;
	private GrupoMuscular gm;
	private GMServicio sgm;
	
	// CONSTRUCTOR
	public EjercicioServicio() {
		this.ejerDao = new EjercicioDAO();
		this.sgm = new GMServicio();
	}
	
	// Cargar Ejercicio
	public void cargarEjercicio(Ejercicio ejercicio) {
		// MANEJO DE FALLO [ ATRIBUTOS VACIOS O NULL ]
		if (ejercicio.getGrupoMuscular() == null || this.sgm.obtenerGM(ejercicio.getGrupoMuscular().getIdGM(), false) == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no tiene grupo muscular asignado o este es nulo!%n", ejercicio.getIdEjercicio()); // LOG
			return;
		}
		
		if (ejercicio.getNombreEjercicio() == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no tiene un nombre asignado o este es nulo!%n", ejercicio.getIdEjercicio()); // LOG
			return;
		}
		
		// MODIFICA EL PARAMETRO A MINUSCULA
		ejercicio.setNombreEjercicio(ejercicio.getNombreEjercicio().toLowerCase());
	
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		// ESTANDO TOD0 EN EL MISMO CONTEXTO SE PERSISTE LA ENTIDAD DESDE EL DAO Y SE ENLAZA LA ENTIDAD
		// A SU ENTIDAD PADRE CON EL METODO "enlazarEntidadALista" PROPIO DE ESTA CLASE (PEQUEÑA ACLARACION XD).
		// LO DE ARRIBA ES MENTIRA, YA NO SE HACE!! //
		try {
			this.manager.getTransaction().begin();
			this.ejerDao.crearEntidadEjercicio(this.manager, ejercicio);
			//this.enlazarEntidadALista(this.manager, ejercicio); // ENLAZA EL OBJETO A LA LISTA RELACIONAL DE GM
			this.gm = this.manager.merge(ejercicio.getGrupoMuscular()); // RECONECTA LA ENTIDAD AL CONTEXTO DEL EM
			this.gm.getEjercicios().add(ejercicio); // AÑADE EL EJERCICIO AL ATRIBUTO LISTA DEL LA ENTIDAD GM
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El ejercicio %d cargado correctamente!%n", ejercicio.getIdEjercicio()); // LOG
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al cargar el ejercicio!"); // LOG
		} finally {
			this.manager.close();
		}
	}
	
	// (PROGRAMAR CON MUSICA DOOMER RUSA TE HACE SENTIR COMO SI ESTUVIERAS
	// EN LA ANTIGUA URSS DEARROLLANDO TETRIS EN SECRETO O ALGUNA COSA ASI XD)
	
	// Buscar Ejercicio
	public Ejercicio obtenerEjercicio(int id) {		
		Ejercicio regEjer = null;
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regEjer = this.ejerDao.obtenerEntidadEjercicio(this.manager, id);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener el ejercicio!"); // LOG
		} finally {
			this.manager.close();
		}
		
		return regEjer;
	}
	
	// Buscar Ejercicio (CON SUS OBJETOS ATRIBUTO)
	public Ejercicio obtenerEjercicioYSusObjetos(int id) {		
		Ejercicio regEjer = null;
		// Consulta JPQL -> TRAE A LA ENTIDAD Y SUS OBJETOS ASOCIADOS
		String consulta = String.format("SELECT %c FROM %s %c JOIN FETCH %c.GM WHERE %c.idEjercicio = :id", 'e', "Ejercicio", 'e', 'e', 'e');
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regEjer = this.ejerDao.obtenerEntidadEjercicio(this.manager, id, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener el ejercicio!"); // LOG
		} finally {
			this.manager.close();
		}
		
		return regEjer;
	}

	// Obtener todos los Ejercicios cargados
	public List<Ejercicio> obtenerTodosLosEjercicios(){		
		String consulta = String.format("SELECT %c FROM %s %c", 'e', "Ejercicio", 'e'); // Consulta JPQL
//		String consulta = "SELECT e FROM Ejercicio e JOIN FETCH e.GM"; // Consulta JPQL
//		String consulta = String.format("SELECT %c FROM %s %c JOIN FETCH %c.GM", 'e', "Ejercicio", 'e', 'e'); // Consulta JPQL
		List<Ejercicio> regEjers = null;
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regEjers = this.ejerDao.obtenerEntidadesEjercicio(this.manager, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener los ejercicios!"); // LOG
		} finally {
			this.manager.close();
		}
		
		return regEjers;
	}
	
	// Obtener todos los Ejercicios cargados (CON SUS OBJETOS ATRIBUTO)
	public List<Ejercicio> obtenerTodosLosEjerciciosYSusObjetos(){		
//		String consulta = String.format("SELECT %c FROM %s %c", 'e', "Ejercicio", 'e'); // Consulta JPQL
//		String consulta = "SELECT e FROM Ejercicio e JOIN FETCH e.GM"; // Consulta JPQL
//		String consulta = String.format("SELECT %c FROM %s %c JOIN FETCH %c.GM", 'e', "Ejercicio", 'e', 'e'); // Consulta JPQL
		String consulta = 
				"SELECT e FROM Ejercicio e " +
				"JOIN FETCH e.GM ";
		List<Ejercicio> regEjers = null;
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regEjers = this.ejerDao.obtenerEntidadesEjercicio(this.manager, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener los ejercicios!"); // LOG
		} finally {
			this.manager.close();
		}
		
		return regEjers;
	}
	
	public List<Seguimiento> obtenerListaDeSeguimientos(int id) {
		List<Seguimiento> lista = new ArrayList<>();
		Ejercicio ej = this.obtenerEjercicio(id);
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			lista.addAll(this.manager.merge(ej).getSeguimientos());
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("[ ERROR ] > Falla al obtener la lista de seguimientos!");
		} finally {
			this.manager.close();
		}

		return lista;
	}
	
	public List<RutinaEjercicio> obtenerListaDeRutinaEjercicios(int id) {
		List<RutinaEjercicio> lista = new ArrayList<>();
		Ejercicio ej = this.obtenerEjercicio(id);
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			lista.addAll(this.manager.merge(ej).getRutinaEjercicios());
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("[ ERROR ] > Falla al obtener la lista de rutinaEjercicios!");
		} finally {
			this.manager.close();
		}

		return lista;
	}

	// Actualizar Ejercicio
	public boolean actualizarEstadoEjercicio(int id, ParametrosEjercicio paramEjer) {
		Ejercicio ejer = this.obtenerEjercicioYSusObjetos(id);
		
		// PARA EL RE-ENLAZADO DE LA ENTIDAD A LA LISTA DE GM
		GrupoMuscular gmAntiguo = null;
		
		if (ejer == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no se encuentra en el sistema!%n", id); // LOG
			return false;
		}
		
		// ALMACENA EL GM ANTIGUO ANTES DE CAMBIARLO
		gmAntiguo = ejer.getGrupoMuscular();
		
		// SI EL ATRIBUTO NO ES NULO, SE VERIFICA QUE EL INDICE SEA VALIDO EN LA TABLA DE GM
		if (paramEjer.gm != null && paramEjer.gm != ejer.getGrupoMuscular()) {
			if (this.sgm.obtenerGM(paramEjer.gm.getIdGM(), true) == null) {
				System.out.println("[ ERROR ] > El parámetro GM no es valido!"); // LOG
				return false;
			}
			ejer.setGrupoMuscular(paramEjer.gm);
		}
		
		if (paramEjer.nombreEjercicio != null && paramEjer.nombreEjercicio != ejer.getNombreEjercicio()) {
			ejer.setNombreEjercicio(paramEjer.nombreEjercicio.toLowerCase());
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			ejer = this.ejerDao.actualizarEjercicio(this.manager, ejer);
			
			// ENLAZA EL OBJETO QUE SE ESTA MODIFICANDO A LA LISTA DE LA NUEVA ENTIDAD PADRE
			// Y MERGEA LA ENTIDAD ANTIGUA PARA QUE DE SU LISTA RELACIONAL REMUEVA
			// EL MISMO OBJETO QUE SE ESTA MODIFICANDO (OSEA EL EJERCICIO XD).
			if (paramEjer.gm != null) {
				this.gm = this.manager.merge(ejer.getGrupoMuscular()); // RECONECTA LA ENTIDAD AL CONTEXTO DEL EM
				this.gm.getEjercicios().add(ejer); // AÑADE EL EJERCICIO AL ATRIBUTO LISTA DEL LA ENTIDAD GM
				gmAntiguo = this.manager.merge(gmAntiguo);
				gmAntiguo.getEjercicios().remove(ejer);
			}
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El ejercicio %d actualizado correctamente!%n", ejer.getIdEjercicio()); // LOG
			return true;
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualizar el ejercicio!"); // LOG
			return false;
		} finally {
			this.manager.close();
		}
	}

	// Eliminar Ejercicio
	public void eliminarEjercicio(int id) {
		Ejercicio ejercicio = this.obtenerEjercicioYSusObjetos(id);
		
		if (ejercicio == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no se encuentra en el sistema!%n", id); // LOG
			return;
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			ejercicio = this.manager.merge(ejercicio);
			this.ejerDao.eliminarEntidadEjercicio(this.manager, ejercicio);
			this.gm = this.manager.merge(ejercicio.getGrupoMuscular()); // RECONECTA LA ENTIDAD AL CONTEXTO DEL EM
			this.gm.getEjercicios().remove(ejercicio); // ELIMINA EL EJERCICIO DEL ATRIBUTO LISTA DE LA ENTIDAD GM
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El ejercicio %d eliminado correctamente!%n", id); // LOG
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al eliminar el ejercicio!"); // LOG
		} finally {
			this.manager.close();
		}
	}
	
//	// RETORNA EL GRUPO MUSCULAR DE UN EJERCICIO ESPECIFICO
//	public GrupoMuscular obtenerGMDeEjercicio(Ejercicio ejer) {
//		GrupoMuscular gm = null;
//		
//		// ADMINISTRADOR DE ENTIDADES
//		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
//		
//		try {
//			gm = this.ejerDao.obtenerEntidadEjercicio(this.manager, ejer.getIdEjercicio()).getGrupoMuscular();
//		} catch (Exception e) {
//			System.out.println(e);
//			System.out.println("[ ERROR ] > Falla al obtener el ejercicio!");
//		} finally {
//			this.manager.close();
//		}
//		
//		return gm;
//	}
}