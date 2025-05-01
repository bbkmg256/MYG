/*
	DAO - Entidad Cliente
*/

// Se viene el verdadero BACKEND xd

package edu.unam.repositorio;

// Libs.
// Varios
import java.util.List;

// Entidad
import edu.unam.modelo.Cliente;

// JPA
import jakarta.persistence.EntityManager;


/*
 * ...
 */


/**
 *
 * @Autor: BBKMG
 */
public class ClienteDAO {
	// Constructor
	public ClienteDAO() {}
	
	// CREAR ENTIDAD
	/**
	 * Recibe un objeto de tipo EntityManager y un objeto de tipo Cliente como parametro, no retorna datos.
	 */
	public void crearEntidadCliente(EntityManager em, Cliente entidadCliente) {
//		manager = emf.createEntityManager(); // Administrador de entidades
//		
//		try {
//			manager.getTransaction().begin();
//			manager.persist(entidadCliente);
//			manager.getTransaction().commit();	
//			System.out.println("[ EXITO ] > Cliente cargado!");
//		} catch (Exception e) {
//			System.out.println(e);
//			manager.getTransaction().rollback();
//			System.out.println("[ ERROR ] > Falla al cargar el cliente!");
//		} finally {
//			manager.close();
//		}
		
		em.persist(entidadCliente);
	}
	
	// LEER ENTIDAD
	/**
	 * Recibe un objeto de tipo EntityManager y un primitivo de tipo int como parametro, retorna un objeto de tipo Cliente.
	 */
	public Cliente obtenerEntidadCliente(EntityManager em, int dni) {
		// CODIGO VIEJO
//		Cliente regEntidad = null;
//		manager = emf.createEntityManager();
//			
//		try {
//			regEntidad = manager.find(Cliente.class, dni);
//			// System.out.println(regCli);
//		} catch (Exception e) {
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}			
//		return regEntidad;
		
		return em.find(Cliente.class, dni);
	}
	
	// LEER ENTIDADES
	/**
	 * Recibe un objeto de tpo EntityManager y un objeto de tipo String, retorna una lista de tipo Cliente.
	 */
	public List<Cliente> obtenerEntidadesCliente(EntityManager em, String consulta) {
//		manager = emf.createEntityManager();
//		String consulta = String.format("SELECT %c FROM %s %c", 'c', "Cliente", 'c'); // Consulta JPQL
//		System.out.println(consulta);
//		TypedQuery<Cliente> consultaPreparada; // Variable de consulta (Castea automaticamente el dato obtenido)
//		List<Cliente> regEntidades = null;
//			
//		try {
//			consultaPreparada = manager.createQuery(consulta, Cliente.class);
//			regEntidades = consultaPreparada.getResultList();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return regEntidades;

		return em.createQuery(consulta, Cliente.class).getResultList();
	}
	
	// ACTUALIZA ENTIDAD
	/**
	 * Recibe un objeto de tipo EntityManager y un objeto de tipo Cliente, no retorna datos.
	 */
	public void actualizarEstadoCliente(EntityManager em, Cliente entidadCliente) {
		// CODIGO VIEJO
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			manager.merge(entidadCliente); // ACTUALIZA TAMBIEN XD
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Entidad actualizado!");
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			System.out.println(e);
//			System.out.println("[ ERROR ] > Falla al actualizar la entidad!");
//		} finally {
//			manager.close();
//		}
		
		em.merge(entidadCliente);
	}

	// ELIMINAR ENTIDAD
	/**
	 * Recibe un objeto de tipo Cliente como parametro, no retorna datos.
	 */
	public void eliminarEntidadCliente(EntityManager em, Cliente entidadCliente) {
		// CODIGO VIEJO
//		manager = emf.createEntityManager();
//
//		try {
//			manager.getTransaction().begin();
//			entidadCliente = manager.merge(entidadCliente); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
//			manager.remove(entidadCliente);
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Cliente eliminado!");
//		} catch (Exception e) {
//			manager.getTransaction().rollback();		
//			System.out.println(e);
//			System.out.println("[ ERROR ] > Falla al eliminar la entidad!");
//		} finally {
//			manager.close();
//		}
		entidadCliente = em.merge(entidadCliente);
		em.remove(entidadCliente);
	}
}