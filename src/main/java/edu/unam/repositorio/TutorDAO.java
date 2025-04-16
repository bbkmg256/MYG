/*
	DAO - Entidad Tutor
*/

package edu.unam.repositorio;

/*
	NOTA:
	
	- Tener en cuenta que cuando se relacionen las clases, se tendrá que modificar los
	JPAController para adaptarlas a las relaciones.
 */

// LIBRERIAS
// VARIOS
import java.time.LocalDate;
import java.util.List;

// JPA
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

// ENTIDAD
import edu.unam.modelo.Tutor;

/**
*
* @Autor: BBKMG
*/
public class TutorDAO {
	// ATRIBUTOS
	private EntityManagerFactory emf;
	private EntityManager manager;
	
	// CONSTRUCTOR
	public TutorDAO() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// CREAR ENTIDAD
	public void crearEntidadTutor(Tutor entidadTutor) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.persist(entidadTutor);
			manager.getTransaction().commit();			
		} catch (Exception e) {
			System.out.println(e);
			manager.getTransaction().rollback();			
		} finally {
			manager.close();
		}
	}
	
	// LEER ENTIDAD
	public Tutor obtenerEntidadTutor(int dni) {
		Tutor regEntidad = null;
		manager = emf.createEntityManager();
			
		try {
			regEntidad = manager.find(Tutor.class, dni);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}
		return regEntidad;
	}
	
	// LEER ENTIDADES
	public List<Tutor> obtenerEntidadesTutor() {
		manager = emf.createEntityManager();
		String consulta = String.format("SELECT %c FROM %s %c", 't', "Tutor", 't'); // Consulta JPQL
		System.out.println(consulta);
		TypedQuery<Tutor> consultaPreparada; // Castea automaticamente el dato obtenido
		List<Tutor> regEntidades = null;
			
		try {
			consultaPreparada = manager.createQuery(consulta, Tutor.class);
			regEntidades = consultaPreparada.getResultList();

		} catch (Exception e) {
			System.out.println(e);
		}
		return regEntidades;
	}
	
	// ACTUALIZAR ENTIDAD
	public void actualizarEntidadTutor(Tutor entidadTutor, String nombre, String apellido, char sexo,
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

	// ELIMINAR ENTIDAD
	public void eliminarEntidadTutor(Tutor entidadTutor) {
		manager = emf.createEntityManager();

		try {
			manager.getTransaction().begin();
			entidadTutor = manager.merge(entidadTutor); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
			manager.remove(entidadTutor);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();		
			System.out.println(e);
		} finally {
			manager.close();
		}
	}

	public boolean hayConexion() {
		return emf.isOpen();
	}
	
	public void cerrarEMF() {
		emf.close();
		System.out.println("[ EXITO ] > EMF finalizado correctamente!");
	}
	

	// CODIGO VIEJO
//	// ACTUALIZA RELACION CON ENTRENAMIENTO
//	public void actualizarRelacion(Tutor entidadTutor, Entrenamiento entidadEntrenamiento) {
//		manager = emf.createEntityManager();
//			
//		try {
//			manager.getTransaction().begin();
//			entidadTutor = manager.merge(entidadTutor);
//			
//			// AÑADE LA ENTIDAD ENTRENAMIENTO PERSISTIDA, A LA LISTA PROPIA DE TUTOR
//			entidadTutor.getEntrenamientos().add(entidadEntrenamiento);
//				
//			manager.getTransaction().commit();
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}
//	}
}
