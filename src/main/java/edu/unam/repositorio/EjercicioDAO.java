/*
	DAO - Entidad Ejercicio
*/

package edu.unam.repositorio;

// LIBRERIAS
// VARIOS
import java.util.List;

// JPA
import jakarta.persistence.EntityManager;

// ENTIDADES
import edu.unam.modelo.Ejercicio;


/*
 * 
 * NOTA:
 * 
 * VOY A MODIFICAR EL METODO DE CARGA DE ENTIDADES AL A BD, DE ESTE DAO PARA PODER SIMPLIFICAR LA RELACION DE
 * LA ENTIDAD CON AL QUE SE RELACIONA (GRUPO MUSCULAR)
 * 
 * 
 * VOY A EMPEZAR A QUITAR LAS EXTENCION DE LA CLASE JPACONTROLLER DE LOS DAO, PERFIERO TENER CODIGO REPETIDO,
 * PERO QUE UNA MODIFICACION NO AFECTE AL RESTO DE DAO'S
 * 
 */


/**
*
* @Autor: BBKMG
*/
public class EjercicioDAO {	
	// CONSTRUCTOR
	public EjercicioDAO() {
	}
	
	// CREAR ENTIDAD
	public void crearEntidadEjercicio(EntityManager em, Ejercicio entidadEjercicio) {
		// CODIGO VIEJO
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			
//			// PERSISTIR EJERCICIO
//			manager.persist(entidadEjercicio);
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Ejercicio creado!");
//		} catch (Exception e) {
//			// En caso de fallo en la transaccion
//			System.out.println(e);
//			manager.getTransaction().rollback();			
//		} finally {
//			manager.close();
//		}
		
		em.persist(entidadEjercicio);
	}

	// ACTUALIZAR ENTIDAD
//	public void actualizarEntidadEjercicio(Ejercicio entidadEjercicio, String nombreEj) {
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			entidadEjercicio = manager.merge(entidadEjercicio);
//			
//			entidadEjercicio.setNombreEjercicio(nombreEj);
//			
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Ejercicio actualizado!");
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}
//	}
	
	// ACTUALIZA EL ATRIBUTO "NOMBREEJERCICIO"
	public Ejercicio actualizarEjercicio(EntityManager em, Ejercicio entidadEjercicio) {
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			entidadEjercicio = manager.merge(entidadEjercicio);
//			
//			entidadEjercicio.setNombreEjercicio(nombreEj);
//			
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Atributo actualizado!");
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}
		
		return em.merge(entidadEjercicio);
	}

	// LEER ENTIDAD
	public Ejercicio obtenerEntidadEjercicio(EntityManager em, int dni) {
		// CODIGO VIEJO
//		Ejercicio regEntidad = null; // Variable para almacenar el registro
//		manager = emf.createEntityManager();
//			
//		try { // Busca un cliente mediante su dni
//			regEntidad = manager.find(Ejercicio.class, dni);
//		} catch (Exception e) {
//			System.out.println(e);
//		} finally {
//			manager.close(); // Cierra el Entity Manager
//		}			
//		return regEntidad;
		
		return em.find(Ejercicio.class, dni);
	}

	// LEER ENTIDAD (SOBREESCRITA -> RETORNA LA ENTIDAD + SUS OBJETOS ASOCIADOS)
	public Ejercicio obtenerEntidadEjercicio(EntityManager em, int id, String consulta) {		
		return em.createQuery(consulta, Ejercicio.class).setParameter("id", id).getSingleResult();
	}
	
	// LEER ENTIDADES
	public List<Ejercicio> obtenerEntidadesEjercicio(EntityManager em, String consulta) {
//		manager = emf.createEntityManager();
//		String consulta = String.format("SELECT %c FROM %s %c", 'e', "Ejercicio", 'e'); // Consulta JPQL
//		System.out.println(consulta);
//		TypedQuery<Ejercicio> consultaPreparada; // Variable de consulta (Castea automaticamente el dato obtenido)
//		List<Ejercicio> regEntidades = null; // Variable para almacenar el registro
//			
//		try { // Crea y prepara una consulta SQL (JPQL)
//			consultaPreparada = manager.createQuery(consulta, Ejercicio.class);
//			regEntidades = consultaPreparada.getResultList(); // Devuelve los registros obtenidos en una lista
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		// Retorna una lista de los clientes de la BD
//		return regEntidades;
		
		return em.createQuery(consulta, Ejercicio.class).getResultList();
	}
	
	// ELIMINAR ENTIDAD
	public void eliminarEntidadEjercicio(EntityManager em, Ejercicio entidadEjercicio) {
		// CODIGO VIEJO
//		manager = emf.createEntityManager();
//
//		try {
//			// Bloque de transacciones para eliminar un registro
//			manager.getTransaction().begin();
//			entidadEjercicio = manager.merge(entidadEjercicio); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
//			manager.remove(entidadEjercicio); // Elimina la entidad de la persistencia
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Ejercicio eliminado!");
//		} catch (Exception e) {
//			// En caso de fallo en la transaccion
//			manager.getTransaction().rollback();		
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}
		
		// entidadEjercicio = em.merge(entidadEjercicio);
		em.remove(entidadEjercicio);
	}
}
