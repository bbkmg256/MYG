/*
	DAO - entidad Rutina
*/

package edu.unam.repositorio;

// LIBRERIAS
// JPA
import jakarta.persistence.EntityManager;

// VARIOS
import java.util.List;

// ENTIDAD
import edu.unam.modelo.Rutina;

/**
*
* @Autor: BBKMG
*/
public class RutinaDAO{	
	// CONTRUCTOR
	public RutinaDAO() {}
	
	// CREAR ENTIDAD
	public void crearEntidadRutina(EntityManager em, Rutina entidadRutina) {
		em.persist(entidadRutina);
	}
	
	// LEER ENTIDAD
	public Rutina obtenerEntidadRutina(EntityManager em, int dni) {		
		return em.find(Rutina.class, dni);
	}
	
	// LEER ENTIDADES
	public List<Rutina> obtenerEntidadesRutina(EntityManager em, String consulta) {
		return em.createQuery(consulta, Rutina.class).getResultList();
	}
	
	// ACTUALIZAR ENTIDAD
	public void actualizarEntidadRutina(EntityManager em, Rutina entidadRutina) {
		em.merge(entidadRutina);
	}

	// ELIMINAR ENTIDAD
	public void eliminarEntidadRutina(EntityManager em, Rutina entidadRutina) {
		em.remove(entidadRutina);
	}
}
