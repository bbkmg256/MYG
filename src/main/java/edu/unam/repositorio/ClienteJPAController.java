/*
	JpaController - Entidad Cliente
*/

// Se viene el verdadero BACKEND xd

package edu.unam.repositorio;

/*
	NOTA:
	
	- Tener en cuenta que cuando se relacionen las clases, se tendrá que modificar los
	JPAController para adaptarlas a las relaciones.
*/

// Libs.
// JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Entidad
import edu.unam.modelo.Cliente;

// Varios
import java.time.LocalDate;

/**
*
* @author bbkmg
*/
public class ClienteJPAController {
	// Atribs.
	private EntityManagerFactory emf;
	private EntityManager manager;

	// Constructor
	public ClienteJPAController() {
		emf = Persistence.createEntityManagerFactory("persistencia"); // Devuelve un objetos EMF desde la unidad de persistencia;
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Teoricamente aca tiene que ir todo el CRUD para poder operar con la base
	// de datos mediante el EMF.
	
	// Crear entidad (Funcional)
	public void crearCliente(Cliente entidadCliente) {
		manager = emf.createEntityManager(); // Administrador de entidades
		
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
			manager.close();
		}
	}
	
	// Leer entidad (Funcional)
	public Cliente obtenerCliente(int dni) {
		Cliente regCli = null; // Variable para almacenar el registro
		manager = emf.createEntityManager();
			
		try { // Busca un cliente mediante su dni
			regCli = manager.find(Cliente.class, dni);
			// System.out.println(regCli);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close(); // Cierra el Entity Manager
		}			
		return regCli;
	}
	
	// Actualizar entidad (No Testeado)
	public void actualizarCliente(Cliente entidadCliente, String nombre, String apellido,
								LocalDate fechaNac, char sexo, String ciudad,
								String provincia, int codPost, LocalDate fechaIng) {
		
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadCliente = manager.merge(entidadCliente);
			
			// Actualización de atributos de entidad
			entidadCliente.setNombre(nombre);
			entidadCliente.setApellido(apellido);
			entidadCliente.setFechaNacimiento(fechaNac);
			entidadCliente.setSexo(sexo);
			entidadCliente.setCiudad(ciudad);
			entidadCliente.setProvicia(provincia);
			entidadCliente.setCodigoPostal(codPost);
			entidadCliente.setFechaIngreso(fechaIng);
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// Eliminar entidad (Funcional)
	public void eliminarCliente(Cliente entidadCliente) {
		manager = emf.createEntityManager();

		try {
			// Bloque de transacciones para eliminar un registro
			manager.getTransaction().begin();
			entidadCliente = manager.merge(entidadCliente); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
			manager.remove(entidadCliente); // Elimina la entidad de la persistencia
			manager.getTransaction().commit();
		} catch (Exception e) {
			// En caso de fallo en la transaccion
			manager.getTransaction().rollback();		
			System.out.println(e);
		} finally {
			manager.close();
		}
	}

	// Por el momento voy a cerrar el EMF así, ya que no escuentro una forma mejor xd (Funcional)
	public void cerrarEMF() {
		if (emf != null && emf.isOpen()) {
			emf.close(); // Cierra el Entity Manager Factory
			System.out.println("[ EXITO ] > EMF finalizado correctamente!");
		}
	}
	
	/*
	// Leer entidad (No listo)
	public Cliente obtenerCliente(Cliente entidadCliente) {
		manager = emf.createEntityManager();
		String consulta = "SELECT c FROM Cliente c WHERE c.dni = :dni"; // Consulta JPQL
		TypedQuery<Cliente> consultaPreparada; // Variable de consulta (Castea automaticamente el dato obtenido)
		Cliente regCli = null; // Variable para almacenar el registro
		
		try { // Crea y prepara una consulta SQL (JPQL
			consultaPreparada = manager.createQuery(consulta, Cliente.class)
								.setParameter("dni", entidadCliente.getDni()); // Asocia el parametro a la consulta
			
			regCli = consultaPreparada
					.setMaxResults(1) // Es como el "LIMIT 1" en SQL
					.getSingleResult(); // Espera 1 solo resultado			
		} catch (Exception e) {
			System.out.println(e);
		}
		return regCli;
	}
	*/
}