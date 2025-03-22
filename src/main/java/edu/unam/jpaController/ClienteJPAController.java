/*
	JpaController - Entidad Cliente
	
	(El main de esta clase es de prueba, no tiene que estar, ya que todo será
	empleado desde el Main principal del proyecto)
*/

// Se viene el verdadero BACKEND xd

package edu.unam.jpaController;

/*
	NOTA:
	
	- Debo verificar que no se ingresen registros con DNI iguales, por mas que
	el id sea autoincremental. (Metodo "crearCliente()")
	- El metodo "actualizarCliente()" lo unico que va a hacer es un merge al
	registro, la modificacion de cada atributo se hace con los propios metodos
	de la clase, en todo caso, tambien hay que obtener devuelta la entdidad
	para modificarla, ahí es donde entra el metodo "obtenerCliente()"
*/

// Libs.
// JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Varios
import edu.unam.modelo.Cliente;
import java.util.List;

public class ClienteJPAController {
	// Atribs.
	private static EntityManagerFactory emf;
	private static EntityManager manager;

	// Constructor
	public ClienteJPAController() {
		emf = Persistence.createEntityManagerFactory("persistencia"); // Devuelve un objetos EMF desde la unidad de persistencia;
	}
	
	// Teoricamente aca tiene que ir todo el CRUD para poder operar con la base
	// de datos mediante el EMF.
	
	// Crear entidad (para el test este metodo es estatico, pero NO debe ser estatico) (Funcional, aunque falta cierta caracteristica)
	public void crearCliente(Cliente entidadCliente) {
		manager = emf.createEntityManager();
		
		String consulta = "SELECT c FROM Cliente c WHERE c.dni = :dni";
		Cliente regCli = null;
		
		try {
			regCli = manager.createQuery(consulta, Cliente.class) // Prepara una consulta SQL (JPQL)
					.setParameter("dni", entidadCliente.getDni()) // Asociacion del parametro a la consulta
					.setMaxResults(1) // Es como el "LIMIT 1" en SQL
					.getSingleResult(); // Espera 1 solo resultado			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		// Sale del metodo si la condición es verdadera (Comprbación de corto circuito)
		if (regCli != null && regCli.getDni() == entidadCliente.getDni()) {
			System.out.println("[ Error ] - El registro a cargar ya existe en la BD!");
			return;
		}
		
		try {
			// Bloque de transacciones para persistencia
			manager.getTransaction().begin();
			manager.persist(entidadCliente);
			manager.getTransaction().commit();			
		} catch (Exception e) {
			// En caso de fallo en la transaccion
			System.out.println(e);
			manager.getTransaction().rollback();			
		} finally {
			// Cierra o destruye el gestor de entitades para liberar memoria
			manager.close();		
		}
	}
	
	/*
	// Leer entidad (No listo)
	public Cliente obtenerCliente() {
		return xxxx;
	}
	*/
	
	// Actualizar entidad (No listo)
	public void actualizarCliente() {
	}
	
	// Eliminar entidad (Funcional)
	public void eliminarCliente(int dni) {
		manager = emf.createEntityManager();
		
		// Consulta preparada (ESTO NO ES SQL, ES JPQL!!)
		String consulta = "SELECT c FROM Cliente c WHERE c.dni = :dni";
		Cliente regCli = null;

		try {
			regCli = manager.createQuery(consulta, Cliente.class) // Prepara una consulta SQL (JPQL)
					.setParameter("dni", dni) // Asociacion del parametro a la consulta
					.setMaxResults(1) // Es como el "LIMIT 1" en SQL
					.getSingleResult(); // Espera 1 solo resultado
		} catch (Exception e) {
			System.out.println("[ ERROR ] - Cliente no encontrado!");
			manager.getTransaction().rollback();
		}
		
		// Se supone que si "regCli" es null por que no hay un registro con
		// el dni ingresado, entonces simplemente se exceptuará y se finaliza el
		// EntityManager.
		try {
			// Bloque de transacciones para eliminar un registro
			manager.getTransaction().begin();
			manager.remove(regCli);
			manager.getTransaction().commit();
			System.out.println("[ EXITO ] - Cliente " + dni + " eliminado!");
		} catch (Exception e) {
			// En caso de fallo en la transaccion
			System.out.println(e);
			manager.getTransaction().rollback();			
		} finally {
			// Cierra o destruye el gestor de entitades para liberar memoria
			manager.close();		
		}
	}
	
	// Solo es para testear, eliminar posteriormente
	public void test() {
		// Esto es solo de prueba, pero devuelve una lista sin tipo de la tabla cliente. (Esto está casteado)
		List<Cliente> clientes = (List<Cliente>) manager.createQuery("FROM Cliente").getResultList();
		System.out.println("En la BD hay " + clientes.size() + " clientes registrados");
	}
}