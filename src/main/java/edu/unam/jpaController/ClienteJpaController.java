/*
	JpaController - Entidad Cliente
	
	(El main de esta clase es de prueba, no tiene que estar, ya que todo será
	empleado desde el Main principal del proyecto)
*/

package edu.unam.jpaController;

// Libs.
// JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Varios
import edu.unam.modelo.Cliente;
import java.util.List;

public class ClienteJpaController {
	// Atribs.
	private static EntityManagerFactory emf;
	private static EntityManager manager;
	
	// main
	public static void main(String [] args) {
		// Creación de gestor de pérsistencia
		emf = Persistence.createEntityManagerFactory("persistencia"); // Devuelve un objetos EMF desde la unidad de persistencia
		manager = emf.createEntityManager(); // Instancia el objeto EM
		
		// Esto es solo de prueba, pero devuelve una lista sin tipo de la tabla cliente. (Esto está casteado)
		List<Cliente> clientes = (List<Cliente>) manager.createQuery("FROM Cliente").getResultList();
		System.out.println("En la BD hay " + clientes.size() + " clientes registrados");
	}
	
	// Teoricamente aca tiene que ir todo el CRUD para poder operar con la base
	// de datos mediante el EMF.
}