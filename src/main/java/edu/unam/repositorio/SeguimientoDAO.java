/*
	DAO - Entidad Seguimiento
*/

package edu.unam.repositorio;

// LIBRERIAS
// JPA
import jakarta.persistence.EntityManager;

// VARIOS
import java.util.List;

// ENTIDAD
import edu.unam.modelo.Seguimiento;

/**
*
* @Autor: BBKMG
*/
public class SeguimientoDAO {		
	// CONSTRUCTOR
	public SeguimientoDAO(){}

	// CREAR ENTIDAD
	public void crearEntidadSeguimiento(EntityManager em, Seguimiento entidadSeguimiento) {
		em.persist(entidadSeguimiento);
	}
	
	// LEER ENTIDAD
	public Seguimiento obtenerEntidadSeguimiento(EntityManager em, int id) {		
		return em.find(Seguimiento.class, id);
	}
	
	// LEER ENTIDAD
	public Seguimiento obtenerEntidadSeguimiento(EntityManager em, int id, String consulta) {		
//		return em.find(Seguimiento.class, id);
		return em.createQuery(consulta, Seguimiento.class).setParameter("id", id).getSingleResult();
	}
	
	// LEER ENTIDADES
	public List<Seguimiento> obtenerEntidadesSeguimiento(EntityManager em, String consulta) {
		return em.createQuery(consulta, Seguimiento.class).getResultList();
	}
	
	// ACTUALIZAR ENTIDAD
	public Seguimiento actualizarEntidadSeguimiento(EntityManager em, Seguimiento entidadSeguimiento) {
		return em.merge(entidadSeguimiento);
	}

	// ELIMINAR ENTIDAD
	public void eliminarEntidadSeguimiento(EntityManager em, Seguimiento entidadSeguimiento) {		
		em.remove(entidadSeguimiento);
	}
}
