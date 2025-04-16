/*
	DAO - Entidad Entrenamiento
*/

package edu.unam.repositorio;

// LIBRERIAS
// VARIOS
import java.time.LocalDate;
import java.util.List;

// JPA
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

// ENTIDADES
import edu.unam.modelo.Entrenamiento;
import edu.unam.modelo.Cliente;
import edu.unam.modelo.Tutor;

/**
*
* @Autor: BBKMG
*/
public class EntrenamientoDAO{
	// ATRIBUTOS
	private EntityManagerFactory emf;
	private EntityManager manager;
		
	// CONSTRUCTOR
	public EntrenamientoDAO() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// CREAR ENTIDAD
	public void crearEntidadEntrenamiento(Entrenamiento entidadEntrenamiento) {
		manager = emf.createEntityManager(); // Administrador de entidades
		
		try {
			manager.getTransaction().begin();
			
			// ENLACE DEL ENTRENAMIENTO A LA LISTA DE CLIENTE
			Cliente cli = manager.merge(entidadEntrenamiento.getCliente()); // RECONECTA LA ENTIDAD AL CONTEXTO DEL EM
			cli.getEntrenamientos().add(entidadEntrenamiento); // AÑADE EL ENTRENAMIENTO AL ATRIBUTO LISTA DE LA ENTIDAD CLIENTE
			
			// ENLACE DEL ENTRENAMIENTO A LA LISTA DEL TUTOR (LO MISMO QUE LO DE ARRIBA, PERO PARA LA ENTIDAD TUTOR)
			Tutor tutor = manager.merge(entidadEntrenamiento.getTutor());
			tutor.getEntrenamientos().add(entidadEntrenamiento);
			
			manager.persist(entidadEntrenamiento);
			manager.getTransaction().commit();			
		} catch (Exception e) {
			System.out.println(e);
			manager.getTransaction().rollback();			
		} finally {
			manager.close();
		}
	}
	
	// LEER ENTIDAD
	public Entrenamiento obtenerEntidadEntrenamiento(int dni) {
		Entrenamiento regEntidad = null;
		manager = emf.createEntityManager();
			
		try {
			regEntidad = manager.find(Entrenamiento.class, dni);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}			
		return regEntidad;
	}
	
	// LEER ENTIDADES
	public List<Entrenamiento> obtenerEntidadesEntrenamiento() {
		manager = emf.createEntityManager();
		String consulta = String.format("SELECT %c FROM %s %c", 'e', "Entrenamiento", 'e'); // Consulta JPQL
		System.out.println(consulta);
		TypedQuery<Entrenamiento> consultaPreparada; // Castea automaticamente el dato obtenido
		List<Entrenamiento> regEntidades = null;
			
		try {
			consultaPreparada = manager.createQuery(consulta, Entrenamiento.class);
			regEntidades = consultaPreparada.getResultList();

		} catch (Exception e) {
			System.out.println(e);
		}
		return regEntidades;
	}
	
	// ACTUALIZAR ENTIDAD (TODAVIA NO ESTA COMPLETO HASTA QUE TODAS SUS RELACIONES ESTEN TERMINADAS)
	public void actualizarEntidadEntrenamiento(Entrenamiento entidadEntrenamiento,
												int puntaje, LocalDate fechaInicio,
												LocalDate fechaFin) {
		
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadEntrenamiento = manager.merge(entidadEntrenamiento); // Reconecta el objeto al contexto de persistencia
			
			// Todavia faltan atributos a modificar
			entidadEntrenamiento.setPuntaje(puntaje);
			entidadEntrenamiento.setFechaInicio(fechaInicio);
			entidadEntrenamiento.setFechaFin(fechaFin);	
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}

	// ELIMINAR ENTIDAD
	public void eliminarEntidadEntrenamiento(Entrenamiento entidadEntrenamiento) {
		manager = emf.createEntityManager();

		try {
			manager.getTransaction().begin();
			entidadEntrenamiento = manager.merge(entidadEntrenamiento); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
			manager.remove(entidadEntrenamiento); // Elimina la entidad de la persistencia
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
	
}
