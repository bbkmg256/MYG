/*
	DAO - entidad Rutina
*/

package edu.unam.repositorio;

// LIBRERIAS
// JPA
import jakarta.persistence.EntityManager;

// VARIOS
import java.util.List;

// ENTIDAD
import edu.unam.modelo.Rutina;

/**
*
* @Autor: BBKMG
*/
public class RutinaDAO{	
	// CONTRUCTOR
	public RutinaDAO() {}
	
	// CREAR ENTIDAD
	public void crearEntidadRutina(EntityManager em, Rutina entidadRutina) {
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			manager.persist(entidadRutina);
//			manager.getTransaction().commit();			
//		} catch (Exception e) {
//			System.out.println(e);
//			manager.getTransaction().rollback();			
//		} finally {
//			manager.close();
//		}
		
		em.persist(entidadRutina);
	}
	
	// LEER ENTIDAD
	public Rutina obtenerEntidadRutina(EntityManager em, int dni) {
//		Rutina regEntidad = null;
//		manager = emf.createEntityManager();
//			
//		try {
//			regEntidad = manager.find(Rutina.class, dni);
//		} catch (Exception e) {
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}			
//		return regEntidad;
		
		return em.find(Rutina.class, dni);
	}
	
	// LEER ENTIDADES
	public List<Rutina> obtenerEntidadesRutina(EntityManager em, String consulta) {
//		manager = emf.createEntityManager();
//		String consulta = String.format("SELECT %c FROM %s %c", 'r', "Rutina", 'r'); // Consulta JPQL
//		System.out.println(consulta);
//		TypedQuery<Rutina> consultaPreparada;
//		List<Rutina> regEntidades = null;
//			
//		try {
//			consultaPreparada = manager.createQuery(consulta, Rutina.class);
//			regEntidades = consultaPreparada.getResultList(); // Devuelve los registros obtenidos en una lista
//
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return regEntidades;
		
		return em.createQuery(consulta, Rutina.class).getResultList();
	}
	
	// ACTUALIZAR ENTIDAD
	public void actualizarEntidadRutina(EntityManager em, Rutina entidadRutina) {
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			manager.merge(entidadRutina);
//			manager.getTransaction().commit();
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}
		
		em.merge(entidadRutina);
	}

	// ELIMINAR ENTIDAD
	public void eliminarEntidadRutina(EntityManager em, Rutina entidadRutina) {
//		manager = emf.createEntityManager();
//
//		try {
//			manager.getTransaction().begin();
//			entidad = manager.merge(entidad); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
//			manager.remove(entidad);
//			manager.getTransaction().commit();
//		} catch (Exception e) {
//			manager.getTransaction().rollback();		
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}
		
		em.remove(entidadRutina);
	}
}
