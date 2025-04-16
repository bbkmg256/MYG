/*
	DAO - Entidad Seguimiento
*/

package edu.unam.repositorio;

// LIBRERIAS
// JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

// VARIOS
import java.time.LocalDate;
import java.util.List;

// ENTIDAD
import edu.unam.modelo.Seguimiento;
import edu.unam.modelo.Entrenamiento;

/**
*
* @Autor: BBKMG
*/
public class SeguimientoDAO {
	// ATRIBUTOS
	private EntityManagerFactory emf;
	private EntityManager manager;
		
	// CONSTRUCTOR
	public SeguimientoDAO(){
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}

	// CREAR ENTIDAD
	public void crearEntidadSeguimiento(Seguimiento entidadSeguimiento) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			
			// FALTA RELACIONAR CON LA LISTA DE ENTRENAMIENTO
			Entrenamiento ent = manager.merge(entidadSeguimiento.getEntrenamiento()); // RECONECTA LA ENTIDAD AL CONTEXTO DEL EM
			ent.getSeguimientos().add(entidadSeguimiento); // AÑADE EL SEGUIMIENTO AL ATRIBUTO LISTA DE LA ENTIDAD ENTRENAMIENTO
			
			// PERSISTIR SEGUIMIENTO
			manager.persist(entidadSeguimiento);
			manager.getTransaction().commit();			
		} catch (Exception e) {
			System.out.println(e);
			manager.getTransaction().rollback();			
		} finally {
			manager.close();
		}
	}
	
	// LEER ENTIDAD
	public Seguimiento obtenerEntidadSeguimiento(int dni) {
		Seguimiento regEntidad = null;
		manager = emf.createEntityManager();
			
		try {
			regEntidad = manager.find(Seguimiento.class, dni);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}			
		return regEntidad;
	}
	
	// LEER ENTIDADES
	public List<Seguimiento> obtenerEntidadesSeguimiento() {
		manager = emf.createEntityManager();
		String consulta = String.format("SELECT %c FROM %s %c", 's', "Seguimiento", 's'); // Consulta JPQL
		System.out.println(consulta);
		TypedQuery<Seguimiento> consultaPreparada; // Castea automaticamente el dato obtenido
		List<Seguimiento> regEntidades = null;
			
		try {
			consultaPreparada = manager.createQuery(consulta, Seguimiento.class);
			regEntidades = consultaPreparada.getResultList();
		} catch (Exception e) {
			System.out.println(e);
		}
		return regEntidades;
	}
	
	// ACTUALIZAR ENTIDAD
	public void actualizarEntidadSeguimiento(Seguimiento entidadSeguimiento, LocalDate fechaActual,
											int cantSerieRealizado, int cantRepeticionesRealizado,
											String ejercicioRealizado, double pesoTrabajado) {
		
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadSeguimiento = manager.merge(entidadSeguimiento); // Reconecta el objeto al contexto de persistencia
			
			entidadSeguimiento.setFechaHoy(fechaActual);
			entidadSeguimiento.setCantSerieRealizado(cantSerieRealizado);
			entidadSeguimiento.setCantRepeticionRealizado(cantRepeticionesRealizado);
			entidadSeguimiento.setEjercicioRealizado(ejercicioRealizado);
			entidadSeguimiento.setPesoTrabajado(pesoTrabajado);
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}

	// ELIMINAR ENTIDAD
	public void eliminarEntidadSeguimiento(Seguimiento entidadSeguimiento) {
		manager = emf.createEntityManager();

		try {
			manager.getTransaction().begin();
			entidadSeguimiento = manager.merge(entidadSeguimiento); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
			manager.remove(entidadSeguimiento); // Elimina la entidad de la persistencia
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
