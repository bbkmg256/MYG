/*
	DAO - Entidad Tutor
*/

package edu.unam.repositorio;

/*
	NOTA:
	
	- Tener en cuenta que cuando se relacionen las clases, se tendrá que modificar los
	JPAController para adaptarlas a las relaciones.
	
	Esto se arreglo por que el diseño estaba mal planteado, ahora esta clase solo se
	preocupa de realizar acciones en BD, las transacciones se manejan en el servicio.
 */

// LIBRERIAS
// VARIOS
import java.util.List;

// JPA
// import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

// SINGLETON PARA EL EMF
import utilidades.EMFSingleton;

// ENTIDAD
import edu.unam.modelo.Tutor;

/**
*
* @Autor: BBKMG
*/
public class TutorDAO {
	// ATRIBUTOS
//	private EntityManagerFactory emf;
	private EntityManager manager;
	
	// CONSTRUCTOR
	public TutorDAO() {
//		emf = EMFSingleton.getInstancia().getEMF();
	}
	
	// CREAR ENTIDAD
	public void crearEntidadTutor(EntityManager em, Tutor entidadTutor) {
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			manager.persist(entidadTutor);
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Tutor cargado!");
//		} catch (Exception e) {
//			System.out.println(e);
//			manager.getTransaction().rollback();
//			System.out.println("[ ERROR ] > Falla al cargar el tutor!");
//		} finally {
//			manager.close();
//		}
		
		em.persist(entidadTutor);
	}
	
	// LEER ENTIDAD
	public Tutor obtenerEntidadTutor(EntityManager em, int dni) {
//		Tutor regEntidad = null;
//		manager = emf.createEntityManager();
//			
//		try {
//			regEntidad = manager.find(Tutor.class, dni);
//		} catch (Exception e) {
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}
//		return regEntidad;
		
		return em.find(Tutor.class, dni);
	}
	
	// LEER ENTIDADES
	public List<Tutor> obtenerEntidadesTutor(EntityManager em, String consulta) {
//		manager = emf.createEntityManager();
//		String consulta = String.format("SELECT %c FROM %s %c", 't', "Tutor", 't'); // Consulta JPQL
//		System.out.println(consulta);
//		TypedQuery<Tutor> consultaPreparada; // Castea automaticamente el dato obtenido
//		List<Tutor> regEntidades = null;
//			
//		try {
//			consultaPreparada = manager.createQuery(consulta, Tutor.class);
//			regEntidades = consultaPreparada.getResultList();
//
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return regEntidades;

		return em.createQuery(consulta, Tutor.class).getResultList();
	}
	
	// ACTUALIZA ATRIBUTO NOMBRE
	public void actualizarEstadoTutor(EntityManager em, Tutor entidadTutor) {
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			manager.merge(entidadTutor);
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Entidad actualizada!");
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			System.out.println(e);
//			System.out.println("[ ERROR ] > Falla al actualizar la entidad!");
//		} finally {
//			manager.close();
//		}
		
		em.merge(entidadTutor);
		
	}

	// ELIMINAR ENTIDAD
	public void eliminarEntidadTutor(EntityManager em, Tutor entidadTutor) {
//		manager = emf.createEntityManager();
//
//		try {
//			manager.getTransaction().begin();
//			entidadTutor = manager.merge(entidadTutor); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
//			manager.remove(entidadTutor);
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Tutor eliminado!");
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}
		entidadTutor = em.merge(entidadTutor); // RECONECTAR ENTIDAD PARA ELIMINARLA
		em.remove(entidadTutor);
	}
}