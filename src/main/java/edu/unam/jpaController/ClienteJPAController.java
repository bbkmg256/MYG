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
import java.time.LocalDate;

public class ClienteJPAController {
	// Atribs.
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia"); // Devuelve un objetos EMF desde la unidad de persistencia;
	private static EntityManager manager = emf.createEntityManager();

	/*
	// Constructor
	public ClienteJPAController() {
		emf = Persistence.createEntityManagerFactory("persistencia"); // Devuelve un objetos EMF desde la unidad de persistencia;
		manager = emf.createEntityManager();
	}
	*/
	
	// Teoricamente aca tiene que ir todo el CRUD para poder operar con la base
	// de datos mediante el EMF.
	
	// Crear entidad (para el test este metodo es estatico, pero NO debe ser estatico)
	public static void crearCliente(Cliente entidadCliente) {
		try {
			// Persistencia mediante transacciones
			manager.getTransaction().begin();
			manager.persist(entidadCliente);
			manager.getTransaction().commit();
		} catch (Exception e) {
			// En caso de fallo en la transaccion
			System.out.println(e);
			manager.getTransaction().rollback();
		}
	}
	
	/*
	// Leer entidad
	public Cliente obtenerCliente() {
		return xxxx;
	}
	*/
	
	// Actualizar entidad
	public void actualizarCliente() {
	}
	
	// Eliminar entidad
	public void eliminarCliente() {
	}
	
	// main (test)
	public static void main(String [] args) {
		// Creación de gestor de pérsistencia
		// manager = emf.createEntityManager(); // Instancia el objeto EM
		
		/*
		Cliente(int paramDni, String paramNombre, String paramApellido,
		Date paramFechaNac, char paramSexo, String paramCiudad,
		String paramProvincia, int paramCodPost, Date paramFechaIng)
		*/
		
		Cliente c1 = new Cliente(12345, "Jorge", "LOLMAN", LocalDate.of(2001, 05, 12), 'M', "LANDIA", "India", 12345, LocalDate.now());
		
		crearCliente(c1);
		
		// Esto es solo de prueba, pero devuelve una lista sin tipo de la tabla cliente. (Esto está casteado)
		List<Cliente> clientes = (List<Cliente>) manager.createQuery("FROM Cliente").getResultList();
		System.out.println("En la BD hay " + clientes.size() + " clientes registrados");
	}
}