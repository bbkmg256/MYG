/*
	DAO - Entidad Ejercicio
*/

package edu.unam.repositorio;

// LIBRERIAS
// VARIOS
import java.util.List;

// JPA
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

// ENTIDADES
import edu.unam.modelo.Ejercicio;
import edu.unam.modelo.GrupoMuscular;


/*
 * 
 * NOTA:
 * 
 * VOY A MODIFICAR EL METODO DE CARGA DE ENTIDADES AL A BD, DE ESTE DAO PARA PODER SIMPLIFICAR LA RELACION DE
 * LA ENTIDAD CON AL QUE SE RELACIONA (GRUPO MUSCULAR)
 * 
 * 
 * VOY A EMPEZAR A QUITAR LAS EXTENCION DE LA CLASE JPACONTROLLER DE LOS DAO, PERFIERO TENER CODIGO REPETIDO,
 * PERO QUE UNA MODIFICACION NO AFECTE AL RESTO DE DAO'S
 * 
 */


/**
*
* @Autor: BBKMG
*/
public class EjercicioDAO {
	// ATRIBUTOS
	private EntityManagerFactory emf;
	private EntityManager manager;
	
	// CONSTRUCTOR
	public EjercicioDAO() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// CREAR ENTIDAD
	public void crearEntidadEjercicio(Ejercicio entidadEjercicio) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			
			// ENLACE DE EL EJERCICIO A LA LISTA DE GRUPO MUSCULAR
			GrupoMuscular gm = manager.merge(entidadEjercicio.getGrupoMuscular()); // RECONECTA LA ENTIDAD AL CONTEXTO DEL EM
			gm.getEjercicios().add(entidadEjercicio); // AÑADE EL EJERCICIO AL ATRIBUTO LISTA DEL LA ENTIDAD GM
			System.out.println("[ EXITO ] > Ejercicio enlazado al grupo muscular!");
			// System.out.println(gm.getEjercicios()); // LISTA DE EJERCICIOS DEL GRUPO MUSCULAR AL QUE SE RELACIONA EL EJERCICIO INSTANCIADO
			
			// PERSISTIR EJERCICIO
			manager.persist(entidadEjercicio);
			manager.getTransaction().commit();
			System.out.println("[ EXITO ] > Ejercicio creado!");
		} catch (Exception e) {
			// En caso de fallo en la transaccion
			System.out.println(e);
			manager.getTransaction().rollback();			
		} finally {
			manager.close();
		}
	}

	// ACTUALIZAR ENTIDAD
	public void actualizarEntidadEjercicio(Ejercicio entidadEjercicio, String nombreEj) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadEjercicio = manager.merge(entidadEjercicio);
			
			entidadEjercicio.setNombreEjercicio(nombreEj);
			
			manager.getTransaction().commit();
			System.out.println("[ EXITO ] > Ejercicio actualizado!");
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}

	// LEER ENTIDAD
	public Ejercicio obtenerEntidadEjercicio(int dni) {
		Ejercicio regEntidad = null; // Variable para almacenar el registro
		manager = emf.createEntityManager();
			
		try { // Busca un cliente mediante su dni
			regEntidad = manager.find(Ejercicio.class, dni);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close(); // Cierra el Entity Manager
		}			
		return regEntidad;
	}
	
	// LEER ENTIDADES
	public List<Ejercicio> obtenerEntidadesEjercicio() {
		manager = emf.createEntityManager();
		String consulta = String.format("SELECT %c FROM %s %c", 'e', "Ejercicio", 'e'); // Consulta JPQL
		System.out.println(consulta);
		TypedQuery<Ejercicio> consultaPreparada; // Variable de consulta (Castea automaticamente el dato obtenido)
		List<Ejercicio> regEntidades = null; // Variable para almacenar el registro
			
		try { // Crea y prepara una consulta SQL (JPQL)
			consultaPreparada = manager.createQuery(consulta, Ejercicio.class);
			regEntidades = consultaPreparada.getResultList(); // Devuelve los registros obtenidos en una lista
		} catch (Exception e) {
			System.out.println(e);
		}
		// Retorna una lista de los clientes de la BD
		return regEntidades;
	}
	
	// ELIMINAR ENTIDAD
	public void eliminarEntidadEjercicio(Ejercicio entidadEjercicio) {
		manager = emf.createEntityManager();

		try {
			// Bloque de transacciones para eliminar un registro
			manager.getTransaction().begin();
			entidadEjercicio = manager.merge(entidadEjercicio); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
			manager.remove(entidadEjercicio); // Elimina la entidad de la persistencia
			manager.getTransaction().commit();
			System.out.println("[ EXITO ] > El ejercicio eliminado!");
		} catch (Exception e) {
			// En caso de fallo en la transaccion
			manager.getTransaction().rollback();		
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// PARA VERIFICAR SI HAY CONEXION ABIERTA
	public boolean hayConexion() {
		return emf.isOpen();
	}
	
	// Por el momento voy a cerrar el EMF así, ya que no escuentro una forma mejor xd
	public void cerrarEMF() {
		emf.close(); // Cierra el Entity Manager Factory
		System.out.println("[ EXITO ] > EMF finalizado correctamente!");
	}
}
