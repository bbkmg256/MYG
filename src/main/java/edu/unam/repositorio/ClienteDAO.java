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
		em.persist(entidadCliente);
	}
	
	// LEER ENTIDAD
	/**
	 * Recibe un objeto de tipo EntityManager y un primitivo de tipo int como parametro, retorna un objeto de tipo Cliente.
	 */
	public Cliente obtenerEntidadCliente(EntityManager em, int dni) {
		return em.find(Cliente.class, dni);
	}
	
	// LEER ENTIDADES
	/**
	 * Recibe un objeto de tpo EntityManager y un objeto de tipo String, retorna una lista de tipo Cliente.
	 */
	public List<Cliente> obtenerEntidadesCliente(EntityManager em, String consulta) {
		return em.createQuery(consulta, Cliente.class).getResultList();
	}
	
	// ACTUALIZA ENTIDAD
	/**
	 * Recibe un objeto de tipo EntityManager y un objeto de tipo Cliente, retorna un objeto de tipo Cliente.
	 */
	public void actualizarCliente(EntityManager em, Cliente entidadCliente) {
		em.merge(entidadCliente);
	}

	// ELIMINAR ENTIDAD
	/**
	 * Recibe un objeto de tipo EntityManger y un objeto de tipo Cliente como parametro, no retorna datos.
	 */
	public void eliminarEntidadCliente(EntityManager em, Cliente entidadCliente) {
		em.remove(entidadCliente);
	}
}