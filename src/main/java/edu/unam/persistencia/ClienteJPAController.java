/*
	JpaController - Entidad Cliente
*/

// Se viene el verdadero BACKEND xd

package edu.unam.persistencia;

/*
	NOTA:
	
	- Debo verificar que no se ingresen registros con DNI iguales, por mas que
	el id sea autoincremental. (Metodo "crearCliente()")
	- El metodo "actualizarCliente()" lo unico que va a hacer es un merge al
	registro, la modificacion de cada atributo se hace con los propios metodos
	de la clase, en todo caso, tambien hay que obtener devuelta la entdidad
	para modificarla, ahí es donde entra el metodo "obtenerCliente()"
	
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
		// Verifica si ya existe el cliente en la BD
		Cliente regCli = this.obtenerCliente(entidadCliente.getDni());
		
		// Sale del metodo si la condición es verdadera (Comprbación de corto circuito)
		if (regCli != null && entidadCliente.getDni() == regCli.getDni()) {
			System.out.println("[ ERROR ] > El cliente ya existe en la BD!");
			return; // Termina la ejecución del metodo
		}
		
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
	
	// Actualizar entidad (No Listo)
	public void actualizarCliente(int dni, String nombre, String apellido,
								LocalDate fechaNac, char sexo, String ciudad,
								String provincia, int codPost, LocalDate fechaIng) {
		
		Cliente regCli = this.obtenerCliente(dni);
		System.out.println(regCli);
		
		if (regCli == null) {
			System.out.println("[ ERROR ] > Cliente no encontrado!");
			return;
		}
		
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			regCli = manager.merge(regCli);
			
			// Actualización de atributos de entidad
			regCli.setNombre(nombre);
			regCli.setApellido(apellido);
			regCli.setFechaNacimiento(fechaNac);
			regCli.setSexo(sexo);
			regCli.setCiudad(ciudad);
			regCli.setProvicia(provincia);
			regCli.setCodigoPostal(codPost);
			regCli.setFechaIngreso(fechaIng);
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e);
			manager.getTransaction().rollback();
		} finally {
			manager.close();
		}
	}
	
	// Eliminar entidad (Funcional)
	public void eliminarCliente(int dni) {
		Cliente regCli = this.obtenerCliente(dni);
		System.out.println(regCli);
		
		if (regCli == null) {
			System.out.println("[ ERROR ] > Cliente no encontrado!");
			return;
		}
		
		manager = emf.createEntityManager();

		// Se supone que si "regCli" es null es por que no hay un registro con
		// el dni ingresado, entonces simplemente se exceptuará y se finaliza el
		// EntityManager.
		try {
			// Bloque de transacciones para eliminar un registro
			manager.getTransaction().begin();
			regCli = manager.merge(regCli); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
			manager.remove(regCli); // Elimina la entidad de la persistencia
			manager.getTransaction().commit();
			System.out.println("[ EXITO ] > Cliente " + dni + " eliminado!");
		} catch (Exception e) {
			// En caso de fallo en la transaccion
			System.out.println(e);
			manager.getTransaction().rollback();		
		} finally {
			manager.close();
		}
	}

	// Por el momento voy a cerrrar el EMF así, ya que no escuentro una forma mejor xd (Funcional)
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