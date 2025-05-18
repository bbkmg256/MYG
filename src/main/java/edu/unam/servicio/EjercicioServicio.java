/*
	Servicio de Ejercicio
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.EjercicioDAO;
import jakarta.persistence.EntityManager;

// VARIOS
import java.util.List;
import utilidades.ParametrosEjercicio;

// SINGLETON EMF
import utilidades.EMFSingleton;

// ENTIDAD
import edu.unam.modelo.Ejercicio;
//import edu.unam.modelo.GrupoMuscular;

/**
*
* @Autor: BBKMG
*/
public class EjercicioServicio {
	// ATRIBUTOS
	private EntityManager manager;
	private EjercicioDAO ejerDao;
//	private GrupoMuscular gm;
	private GMServicio sgm;
	
	// CONSTRUCTOR
	public EjercicioServicio() {
		this.ejerDao = new EjercicioDAO();
		this.sgm = new GMServicio();
	}
	
	// Cargar Ejercicio
	public void cargarEjercicio(Ejercicio ejercicio) {
		// MANEJO DE FALLO [ ATRIBUTOS VACIOS O NULL ]
		if (ejercicio.getGrupoMuscular() == null || this.sgm.obtenerGM(ejercicio.getGrupoMuscular().getIdGM()) == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no tiene grupo muscular asignado o este es nulo!%n", ejercicio.getIdEjercicio());
			return;
		}
		
		if (ejercicio.getNombreEjercicio() == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no tiene un nombre asignado o este es nulo!%n", ejercicio.getIdEjercicio());
			return;
		}
		
		// MODIFICA EL PARAMETRO A MINUSCULA
		ejercicio.setNombreEjercicio(ejercicio.getNombreEjercicio().toLowerCase());
	
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		// ESTANDO TOD0 EN EL MISMO CONTEXTO SE PERSISTE LA ENTIDAD DESDE EL DAO Y SE ENLAZA LA ENTIDAD
		// A SU ENTIDAD PADRE CON EL METODO "enlazarEntidadALista" PROPIO DE ESTA CLASE (PEQUEÑA ACLARACION XD).
		try {
			this.manager.getTransaction().begin();
			this.ejerDao.crearEntidadEjercicio(this.manager, ejercicio);
			//this.enlazarEntidadALista(this.manager, ejercicio); // ENLAZA EL OBJETO A LA LISTA RELACIONAL DE GM
//			this.gm = this.manager.merge(ejercicio.getGrupoMuscular()); // RECONECTA LA ENTIDAD AL CONTEXTO DEL EM
//			this.gm.getEjercicios().add(ejercicio); // AÑADE EL EJERCICIO AL ATRIBUTO LISTA DEL LA ENTIDAD GM
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El ejercicio %d cargado correctamente!%n", ejercicio.getIdEjercicio());
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al cargar el ejercicio!");
		} finally {
			this.manager.close();
		}
	}
	
	// (PROGRAMAR CON MUSICA DOOMER RUSA TE HACE SENTIR COMO SI ESTUVIERAS EN LA ANTIGUA URSS DEARROLLANDO TETRIS EN SECRETO O ALGUNA COSA ASI XD)
	
	// Buscar Ejercicio
	public Ejercicio obtenerEjercicio(int id) {		
		Ejercicio regEjer = null;
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regEjer = this.ejerDao.obtenerEntidadEjercicio(this.manager, id);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener el ejercicio!");
		} finally {
			this.manager.close();
		}
		
		return regEjer;
	}
	
	// Obtener todos los Ejercicios cargados
	public List<Ejercicio> obtenerTodosLosEjercicios(){		
		String consulta = String.format("SELECT %c FROM %s %c", 'e', "Ejercicio", 'e'); // Consulta JPQL
		List<Ejercicio> regEjers = null;
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regEjers = this.ejerDao.obtenerEntidadesEjercicio(this.manager, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener los ejercicios!");
		} finally {
			this.manager.close();
		}
		
		return regEjers;
	}

	// Actualizar Ejercicio
	public void actualizarEstadoEjercicio(int id, ParametrosEjercicio paramEjer) {
		Ejercicio ejer = this.obtenerEjercicio(id);
		
		// PARA EL RE-ENLAZADO DE LA ENTIDAD A LA LISTA DE GM
//		GrupoMuscular gmAntiguo = null;
		
		if (ejer == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		// ALMACENA EL GM ANTIGUO ANTES DE CAMBIARLO
//		gmAntiguo = ejer.getGrupoMuscular();
		
		// SI EL ATRIBUTO NO ES NULO, SE VERIFICA QUE EL INDICE SEA VALIDO EN LA TABLA DE GM
		if (paramEjer.gm != null) {
			if (this.sgm.obtenerGM(paramEjer.gm.getIdGM()) == null) {
				System.out.println("[ ERROR ] > El parámetro GM no es valido!");
				return;
			}
			ejer.setGrupoMuscular(paramEjer.gm);
		}
		
		if (paramEjer.nombreEjercicio != null) {
			ejer.setNombreEjercicio(paramEjer.nombreEjercicio.toLowerCase());
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			ejer = this.ejerDao.actualizarEjercicio(this.manager, ejer);
			
			// ENLAZA EL OBJETO QUE SE ESTA MODIFICANDO A LA LISTA DE LA NUEVA ENTIDAD PADRE
			// Y MERGEA LA ENTIDAD ANTIGUA PARA QUE DE SU LISTA RELACIONAL REMUEVE
			// EL MISMO OBJETO QUE SE ESTA MODIFICANDO (OSEA EL EJERCICIO XD).
//			if (paramEjer.gm != null) {
//				this.gm = this.manager.merge(ejer.getGrupoMuscular()); // RECONECTA LA ENTIDAD AL CONTEXTO DEL EM
//				this.gm.getEjercicios().add(ejer); // AÑADE EL EJERCICIO AL ATRIBUTO LISTA DEL LA ENTIDAD GM
//				gmAntiguo = this.manager.merge(gmAntiguo);
//				gmAntiguo.getEjercicios().remove(ejer);
//			}
			
			this.manager.getTransaction().commit();
			System.out.printf("[ ERROR ] > El ejercicio %d actualizado correctamente!%n", ejer.getIdEjercicio());
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualizar el ejercicio!");
		} finally {
			this.manager.close();
		}
	}

	// Eliminar Ejercicio
	public void eliminarEjercicio(int id) {
		Ejercicio ejercicio = this.obtenerEjercicio(id);
		
		if (ejercicio == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			ejercicio = this.manager.merge(ejercicio);
			this.ejerDao.eliminarEntidadEjercicio(this.manager, ejercicio);
//			this.gm = this.manager.merge(ejercicio.getGrupoMuscular()); // RECONECTA LA ENTIDAD AL CONTEXTO DEL EM
//			this.gm.getEjercicios().remove(ejercicio); // ELIMINA EL EJERCICIO DEL ATRIBUTO LISTA DE LA ENTIDAD GM
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El ejercicio %d eliminado correctamente!%n", id);
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al eliminar el ejercicio!");
		} finally {
			this.manager.close();
		}
	}
}