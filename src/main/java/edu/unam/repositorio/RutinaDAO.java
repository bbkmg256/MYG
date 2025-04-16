/*
	DAO - entidad Rutina
*/

package edu.unam.repositorio;

// JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

// VARIOS
import java.util.List;

// ENTIDAD
import edu.unam.modelo.Rutina;

/**
*
* @Autor: BBKMG
*/
public class RutinaDAO{
	// ATRIBUTOS
	private EntityManagerFactory emf;
	private EntityManager manager;
	
	// CONTRUCTOR
	public RutinaDAO() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// CREAR ENTIDAD
	public void crearEntidadRutina(Rutina entidadRutina) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.persist(entidadRutina);
			manager.getTransaction().commit();			
		} catch (Exception e) {
			System.out.println(e);
			manager.getTransaction().rollback();			
		} finally {
			manager.close();
		}
	}
	
	// LEER ENTIDAD
	public Rutina obtenerEntidadRutina(int dni) {
		Rutina regEntidad = null;
		manager = emf.createEntityManager();
			
		try {
			regEntidad = manager.find(Rutina.class, dni);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}			
		return regEntidad;
	}
	
	// LEER ENTIDADES
	public List<Rutina> obtenerEntidadesRutina() {
		manager = emf.createEntityManager();
		String consulta = String.format("SELECT %c FROM %s %c", 'r', "Rutina", 'r'); // Consulta JPQL
		System.out.println(consulta);
		TypedQuery<Rutina> consultaPreparada;
		List<Rutina> regEntidades = null;
			
		try {
			consultaPreparada = manager.createQuery(consulta, Rutina.class);
			regEntidades = consultaPreparada.getResultList(); // Devuelve los registros obtenidos en una lista

		} catch (Exception e) {
			System.out.println(e);
		}
		return regEntidades;
	}
	
	// ACTUALIZAR ENTIDAD
	public void actualizarEntidadRutina(Rutina entidadRutina, int cantSeries, int cantRep) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadRutina = manager.merge(entidadRutina);
			
			entidadRutina.setCantSeries(cantSeries);
			entidadRutina.setCantRepeticiones(cantRep);
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}

	// ELIMINAR ENTIDAD
	public void eliminarEntidadRutina(Rutina entidad) {
		manager = emf.createEntityManager();

		try {
			manager.getTransaction().begin();
			entidad = manager.merge(entidad); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
			manager.remove(entidad);
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
