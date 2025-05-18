/*
	DAO - Entidad Entrenamiento
*/

package edu.unam.repositorio;

// LIBRERIAS
// VARIOS
import java.util.List;

// JPA
import jakarta.persistence.EntityManager;

// ENTIDADES
import edu.unam.modelo.Entrenamiento;

/**
*
* @Autor: BBKMG 
*/
public class EntrenamientoDAO{	
	// CONSTRUCTOR
	public EntrenamientoDAO() {}
	
	// CREAR ENTIDAD
	public void crearEntidadEntrenamiento(EntityManager em, Entrenamiento entidadEntrenamiento) {
//		manager = emf.createEntityManager(); // Administrador de entidades
//		
//		try {
//			manager.getTransaction().begin();
//			
//			// ENLACE DEL ENTRENAMIENTO A LA LISTA DE CLIENTE
////			Cliente cli = manager.merge(entidadEntrenamiento.getCliente()); // RECONECTA LA ENTIDAD AL CONTEXTO DEL EM
////			cli.getEntrenamientos().add(entidadEntrenamiento); // AÑADE EL ENTRENAMIENTO AL ATRIBUTO LISTA DE LA ENTIDAD CLIENTE
////			
////			// ENLACE DEL ENTRENAMIENTO A LA LISTA DEL TUTOR (LO MISMO QUE LO DE ARRIBA, PERO PARA LA ENTIDAD TUTOR)
////			Tutor tutor = manager.merge(entidadEntrenamiento.getTutor());
////			tutor.getEntrenamientos().add(entidadEntrenamiento);
////			
////			// ENLACE DEL ENTRENAMIENTO A LA LISTA DE RUTINA (TAMBIEN IGUAL A LO DE ARRIBA XD)
////			Rutina rutina = manager.merge(entidadEntrenamiento.getRutina());
////			rutina.getEntrenamientos().add(entidadEntrenamiento);
//			
//			manager.persist(entidadEntrenamiento);
//			manager.getTransaction().commit();			
//		} catch (Exception e) {
//			System.out.println(e);
//			manager.getTransaction().rollback();			
//		} finally {
//			manager.close();
//		}
		
		em.persist(entidadEntrenamiento);
	}
	
	// LEER ENTIDAD
	public Entrenamiento obtenerEntidadEntrenamiento(EntityManager em, int id) {
//		Entrenamiento regEntidad = null;
//		manager = emf.createEntityManager();
//			
//		try {
//			regEntidad = manager.find(Entrenamiento.class, dni);
//		} catch (Exception e) {
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}			
//		return regEntidad;
		
		return em.find(Entrenamiento.class, id);
	}
	
	// LEER ENTIDADES
	public List<Entrenamiento> obtenerEntidadesEntrenamiento(EntityManager em, String consulta) {
//		manager = emf.createEntityManager();
//		String consulta = String.format("SELECT %c FROM %s %c", 'e', "Entrenamiento", 'e'); // Consulta JPQL
//		System.out.println(consulta);
//		TypedQuery<Entrenamiento> consultaPreparada; // Castea automaticamente el dato obtenido
//		List<Entrenamiento> regEntidades = null;
//			
//		try {
//			consultaPreparada = manager.createQuery(consulta, Entrenamiento.class);
//			regEntidades = consultaPreparada.getResultList();
//
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return regEntidades;
		
		return em.createQuery(consulta, Entrenamiento.class).getResultList();
	}
	
	// CODIGO VIEJO
	// ACTUALIZAR ENTIDAD (TODAVIA NO ESTA COMPLETO HASTA QUE TODAS SUS RELACIONES ESTEN TERMINADAS)
//	public void actualizarEntidadEntrenamiento(Entrenamiento entidadEntrenamiento,
//												int puntaje, LocalDate fechaInicio,
//												LocalDate fechaFin) {
//		
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			entidadEntrenamiento = manager.merge(entidadEntrenamiento); // Reconecta el objeto al contexto de persistencia
//			
//			// Todavia faltan atributos a modificar
//			entidadEntrenamiento.setPuntaje(puntaje);
//			entidadEntrenamiento.setFechaInicio(fechaInicio);
//			entidadEntrenamiento.setFechaFin(fechaFin);	
//			
//			manager.getTransaction().commit();
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}
//	}
	
//	// ACTUALIZA EL ATRIBUTO PUNTAJE
//	public void actualizarPuntajeEntrenamiento(Entrenamiento entidadEntrenamiento, int puntaje) {
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			entidadEntrenamiento = manager.merge(entidadEntrenamiento); // Reconecta el objeto al contexto de persistencia
//			
//			// Todavia faltan atributos a modificar
//			entidadEntrenamiento.setPuntaje(puntaje);
//			
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Atributo actualizado!");
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}		
//	}
//	
//	// ACTUALIZA EL ATRIBUTO "FECHAINICIO"
//	public void actualizarFechaDeInicioEntrenamiento(Entrenamiento entidadEntrenamiento, LocalDate fechaInicio) {
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			entidadEntrenamiento = manager.merge(entidadEntrenamiento);
//
//			entidadEntrenamiento.setFechaInicio(fechaInicio);
//			
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Atributo actualizado!");
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}		
//	}
//	
//	// ACTUALIZA EL ATRIBUTO "FECHAFIN"
//	public void actualizarFechaDeFinEntrenamiento(Entrenamiento entidadEntrenamiento, LocalDate fechaFin) {
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			entidadEntrenamiento = manager.merge(entidadEntrenamiento);
//			
//			entidadEntrenamiento.setFechaFin(fechaFin);	
//			
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Atributo actualizado!");
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}		
//	}
	
	// ACTUALIZAR ENTIDAD (RETORNA, POR SER CLASE HIJA, LO REQUIERE)
	public Entrenamiento actualizarEntidadEntrenamiento(EntityManager em, Entrenamiento entidadEntrenamiento) {
		return em.merge(entidadEntrenamiento);
	}
	
	// ELIMINAR ENTIDAD
	public void eliminarEntidadEntrenamiento(EntityManager em, Entrenamiento entidadEntrenamiento) {
//		manager = emf.createEntityManager();
//
//		try {
//			manager.getTransaction().begin();
//			entidadEntrenamiento = manager.merge(entidadEntrenamiento); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
//			manager.remove(entidadEntrenamiento); // Elimina la entidad de la persistencia
//			manager.getTransaction().commit();
//			System.out.println("[ EXITO ] > Entrenamiento eliminado!");
//		} catch (Exception e) {
//			manager.getTransaction().rollback();		
//			System.out.println(e);
//		} finally {
//			manager.close();
//		}
		
		em.remove(entidadEntrenamiento);
	}
}
