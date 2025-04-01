/*
	JpaController - Entidad Tutor
*/

package edu.unam.repositorio;

/*
	NOTA:
	
	- Tener en cuenta que cuando se relacionen las clases, se tendrá que modificar los
	JPAController para adaptarlas a las relaciones.
 */

// Libs.
// JPA
// import jakarta.persistence.Persistence;
// import jakarta.persistence.EntityManagerFactory;
// import jakarta.persistence.EntityManager;

// Varios
import java.time.LocalDate;

// Entidad
import edu.unam.modelo.Tutor;

/**
*
* @Autor: BBKMG
*/
public class TutorJPAController extends JPAController {
	// Atribs.
	// private EntityManagerFactory emf;
	// private EntityManager manager;
	
	// Constructor
	public TutorJPAController() {
		// super();
		// emf = Persistence.createEntityManagerFactory("persistencia");
		// System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Operaciones CRUD:
	// Crear -> En clase padre
	// Obtener -> En clase padre
	// Eliminar -> En clase padre
	
	// Actualizar
	public void actualizarEntidad(Tutor entidadTutor, String nombre, String apellido, char sexo,
								String ciudad, String provincia, int codigoPostal,
								LocalDate fechaNacimiento, LocalDate fechaIngreso) {
		
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadTutor = manager.merge(entidadTutor);
			
			entidadTutor.setNombre(nombre);
			entidadTutor.setApellido(apellido);
			entidadTutor.setSexo(sexo);
			entidadTutor.setCiudad(ciudad);
			entidadTutor.setProvicia(provincia);
			entidadTutor.setCodigoPostal(codigoPostal);
			entidadTutor.setFechaNacimiento(fechaNacimiento);
			entidadTutor.setFechaIngreso(fechaIngreso);
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
}
