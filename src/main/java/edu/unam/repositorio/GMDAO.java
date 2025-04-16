/*
	DAO - Entidad Grupo Muscular
*/

package edu.unam.repositorio;

// JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery; // PARA ALMACENAR OBJETOS DE CONSULTAS, EVITANDO CASTEAR MANUALMENTE
import edu.unam.modelo.Ejercicio;
// ENTIDAD
import edu.unam.modelo.GrupoMuscular;

import java.util.ArrayList;
// VARIOS
import java.util.List;

/**
*
* @Autor: BBKMG
*/
public class GMDAO {
	// Atribs.
	private EntityManagerFactory emf;
	private EntityManager manager;
		
	// Constructor
	public GMDAO() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Crear entidad (Funcional)
	public void crearEntidadGM(GrupoMuscular entidadGM) {
		manager = emf.createEntityManager(); // Administrador de entidades
		
		try {
			// Bloque de transacciones para persistencia
			manager.getTransaction().begin();
			manager.persist(entidadGM);
			manager.getTransaction().commit();
			System.out.println("[ EXITO ] > El grupo muscular creado!");
		} catch (Exception e) {
			// En caso de fallo en la transaccion
			System.out.println(e);
			manager.getTransaction().rollback();			
		} finally {
			manager.close();
		}
	}
	
	// Leer entidad (Funcional)
	public GrupoMuscular obtenerEntidadGM(int dni) {
		GrupoMuscular regEntidad = null; // Variable para almacenar el registro
		manager = emf.createEntityManager();
			
		try { // Busca un cliente mediante su dni
			regEntidad = manager.find(GrupoMuscular.class, dni);
			// System.out.println(regCli);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close(); // Cierra el Entity Manager
		}			
		return regEntidad;
	}
	
	// Leer todas las entidades (FUNCIONAL)
	public List<GrupoMuscular> obtenerEntidadesGM() {
		manager = emf.createEntityManager();
		String consulta = String.format("SELECT %c FROM %s %c", 'g', "GrupoMuscular", 'g'); // Consulta JPQL
		System.out.println(consulta);
		TypedQuery<GrupoMuscular> consultaPreparada; // Variable de consulta (Castea automaticamente el dato obtenido)
		List<GrupoMuscular> regEntidades = null; // Variable para almacenar el registro
			
		try { // Crea y prepara una consulta SQL (JPQL)
			consultaPreparada = manager.createQuery(consulta, GrupoMuscular.class);
			regEntidades = consultaPreparada.getResultList(); // Devuelve los registros obtenidos en una lista

		} catch (Exception e) {
			System.out.println(e);
			// System.out.println("EL PROBLEMA ES ACÁ");
		}
		// Retorna una lista de los clientes de la BD
		return regEntidades;
	}
	
	// PROBABLEMENTE TENGA QUE HACER UN METODO MAS PARA ACCEDER A LOS OBJETOS DE LA LISTA QUE ALMACENA PARA SU RELACION
	// OSEA ESTA -> private List<Ejercicio> ejercicios = new ArrayList<>();
	// ASÍ PARA CADA CLASE PADRE...
	
	// ACTUALIZAR ENTIDAD
	public void actualizareEntidadGM(GrupoMuscular entidadGM, String nombreGM) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadGM = manager.merge(entidadGM); // Reconecta el objeto al contexto de persistencia
			
			entidadGM.setNombreGrupo(nombreGM);
			
			manager.getTransaction().commit();
			System.out.println("[ EXITO ] > Grupo muscular actualizado!");
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}

	// Eliminar entidad (Funcional)
	public void eliminarEntidadGM(GrupoMuscular entidad) {
		manager = emf.createEntityManager();

		try {
			// Bloque de transacciones para eliminar un registro
			manager.getTransaction().begin();
			entidad = manager.merge(entidad); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
			manager.remove(entidad); // Elimina la entidad de la persistencia
			manager.getTransaction().commit();
			System.out.println("[ EXITO ] > El grupo muscular eliminado!");
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
	
	// Por el momento voy a cerrar el EMF así, ya que no escuentro una forma mejor xd (Funcional)
	public void cerrarEMF() {
		emf.close(); // Cierra el Entity Manager Factory
		System.out.println("[ EXITO ] > EMF finalizado correctamente!");
	}


	
	// CODIGO VIEJO
//	// ACTUALIZAR RELACION CON EJERCICIO
//	public void actualizarRelacion(GrupoMuscular entidadGM, Ejercicio entidad) {
//		manager = emf.createEntityManager();
//		
//		try {
//			manager.getTransaction().begin();
//			entidadGM = manager.merge(entidadGM); // RECONECTAR LA ENTIDAD CON EL EM
//			
//			entidadGM.getEjercicios().add(entidad); // AÑADE LA ENTIDAD A LA LISTA RELACIONADA DE GM
//			
//			manager.getTransaction().commit();
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//		} finally {
//			manager.close();
//		}
//	}
}