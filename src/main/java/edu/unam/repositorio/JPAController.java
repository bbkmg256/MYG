/*
	"JPAController Padre", heredado para todas las entidades JPAController.
	
	(Es un JPA controller generico para evitar repetir tanto codigo en los
	controllers de las entidades)
*/

package edu.unam.repositorio;

import java.util.List;

// JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

/**
*
* @Autor: BBKMG
*/
public class JPAController {
	// Atribs.
	// Visibles para las clases hijas
	protected EntityManagerFactory emf;
	protected EntityManager manager;

	// Constructor
	public JPAController() {
		emf = Persistence.createEntityManagerFactory("persistencia"); // Devuelve un objetos EMF desde la unidad de persistencia;
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
		
	// Crear entidad (Funcional)
	public <T> void crearEntidad(T entidad) {
		manager = emf.createEntityManager(); // Administrador de entidades
		
		try {
			// Bloque de transacciones para persistencia
			manager.getTransaction().begin();
			manager.persist(entidad);
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
	public <T> T obtenerEntidad(int dni, Class<T> clase) {
		T regEntidad = null; // Variable para almacenar el registro
		manager = emf.createEntityManager();
			
		try { // Busca un cliente mediante su dni
			regEntidad = manager.find(clase, dni);
			// System.out.println(regCli);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close(); // Cierra el Entity Manager
		}			
		return regEntidad;
	}
	
	// Leer todas las entidades (No listo)
	public <T> List<T> obtenerEntidades(String entidad, Class<T> clase) {
		manager = emf.createEntityManager();
		String consulta = String.format("SELECT %c FROM %s %c", entidad.charAt(0), entidad, entidad.charAt(0)); // Consulta JPQL
		System.out.println(consulta);
		TypedQuery<T> consultaPreparada; // Variable de consulta (Castea automaticamente el dato obtenido)
		List<T> regEntidades = null; // Variable para almacenar el registro
			
		try { // Crea y prepara una consulta SQL (JPQL)
			consultaPreparada = manager.createQuery(consulta, clase);
			regEntidades = consultaPreparada.getResultList(); // Devuelve los registros obtenidos en una lista

		} catch (Exception e) {
			System.out.println(e);
			// System.out.println("EL PROBLEMA ES ACÁ");
		}
		// Retorna una lista de los clientes de la BD
		return regEntidades;
	}
	
	// Actualizar entidad (Metodo escrito en cada clase hija)

	// Eliminar entidad (Funcional)
	public <T> void eliminarEntidad(T entidad) {
		manager = emf.createEntityManager();

		try {
			// Bloque de transacciones para eliminar un registro
			manager.getTransaction().begin();
			entidad = manager.merge(entidad); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
			manager.remove(entidad); // Elimina la entidad de la persistencia
			manager.getTransaction().commit();
		} catch (Exception e) {
			// En caso de fallo en la transaccion
			manager.getTransaction().rollback();		
			System.out.println(e);
		} finally {
			manager.close();
		}
	}

	// Por el momento voy a cerrar el EMF así, ya que no escuentro una forma mejor xd (Funcional)
	public void cerrarEMF() {
		if (emf != null && emf.isOpen()) {
			emf.close(); // Cierra el Entity Manager Factory
			System.out.println("[ EXITO ] > EMF finalizado correctamente!");
		}
	}
}

































// You do too many searches, Billy!
// https://www.youtube.com/watch?v=MRV8mFWwtS4