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
import jakarta.persistence.TypedQuery;
// import jakarta.persistence.EntityManager;
// import jakarta.persistence.EntityManagerFactory;
// import jakarta.persistence.Persistence;

// Entidad
import edu.unam.modelo.Cliente;

// Varios
import java.time.LocalDate;
import java.util.List;

/**
*
* @Autor: BBKMG
*/
public class ClienteJPAController extends JPAController {
	// Atribs.
	// private EntityManagerFactory emf;
	// private EntityManager manager;

	// Constructor
	public ClienteJPAController() {
		// super(); // No necesario por que la superclase no tiene parametros en su constructor
		// emf = Persistence.createEntityManagerFactory("persistencia"); // Devuelve un objetos EMF desde la unidad de persistencia;
		// System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Teoricamente aca tiene que ir todo el CRUD para poder operar con la base
	// de datos mediante el EMF.
	
	// Crear entidad (Funcional) -> Clase padre	
	// Leer entidad (Funcional)	 -> Clase padre
	// Eliminar entidad (Funcional) -> Clase padre
	
	// Actualizar entidad (No Testeado)
	public void actualizarEntidad(Cliente entidadCliente, String nombre, String apellido,
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

	// Leer todas las entidades (No listo) -> Clase padre
}