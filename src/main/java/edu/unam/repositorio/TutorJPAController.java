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
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;

// Varios
import java.time.LocalDate;

// Entidad
import edu.unam.modelo.Tutor;

/**
*
* @author bbkmg
*/
public class TutorJPAController {
	// Atribs.
	private EntityManagerFactory emf;
	private EntityManager manager;
	
	// Constructor
	public TutorJPAController() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Operaciones CRUD:
	// Crear
	public void crearTutor(Tutor entidadTutor) {		
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.persist(entidadTutor);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
		} finally {
			manager.close();
		}
	}
	
	// Obtener
	public Tutor obtenerTutor(int dni) {
		Tutor regTutor = null;
		manager = emf.createEntityManager();
		try {
			regTutor = manager.find(Tutor.class, dni);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}
		return regTutor;
	}
	
	// Eliminar
	public void eliminarTutor(Tutor entidadTutor) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadTutor = manager.merge(entidadTutor);
			manager.remove(entidadTutor);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// Actualizar
	public void actualizarTutor(Tutor entidadTutor, String nombre, String apellido, char sexo,
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
	
	public void cerrarEMF() {
		if (emf != null && emf.isOpen()) {
			emf.close(); // Cierra el Entity Manager Factory
			System.out.println("[ EXITO ] > EMF finalizado correctamente!");
		}
	}
}
