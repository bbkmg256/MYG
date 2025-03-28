/*
	JpaController - Entidad Tutor
*/

package edu.unam.persistencia;

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
		Tutor regTutor = this.obtenerTutor(entidadTutor.getDni());
		
		if (regTutor != null && entidadTutor.getDni() == regTutor.getDni()) {
			System.out.println("[ ERROR ] > El tutor ya existe en la BD!");
			return;
		}
		
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
	public void eliminarTutor(int dni) {
		Tutor regTutor = this.obtenerTutor(dni);
		
		if (regTutor == null) {
			System.out.println("[ ERROR ] > Tutor no encontrado!");
			return;
		}
		
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			regTutor = manager.merge(regTutor);
			manager.remove(regTutor);
			manager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// Actualizar
	public void actualizarTutor(int dni, String nombre, String apellido, char sexo,
								String ciudad, String provincia, int codigoPostal,
								LocalDate fechaNacimiento, LocalDate fechaIngreso) {
		
		Tutor regTutor = this.obtenerTutor(dni);
		
		if (regTutor == null) {
			System.out.println("[ ERROR ] > Tutor no encontrado!");
			return;
		}
		
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			regTutor = manager.merge(regTutor);
			
			regTutor.setNombre(nombre);
			regTutor.setApellido(apellido);
			regTutor.setSexo(sexo);
			regTutor.setCiudad(ciudad);
			regTutor.setProvicia(provincia);
			regTutor.setCodigoPostal(codigoPostal);
			regTutor.setFechaNacimiento(fechaNacimiento);
			regTutor.setFechaIngreso(fechaIngreso);
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e);
			manager.getTransaction().rollback();
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
