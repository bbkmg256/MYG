/*
 * CLASE DAO PAA ENTIDAD INTERMEDIA RUTINAENTRENAMIENTO
 */

package edu.unam.repositorio;

import edu.unam.modelo.Entrenamiento;
import edu.unam.modelo.RutinaEntrenamiento;
import jakarta.persistence.EntityManager;
import java.util.List;

/*
 * 
 */

public class RutinaEntrenamientoDAO {
	// CONTRUCTOR
	public RutinaEntrenamientoDAO() {}
	
	// CREAR ENTIDAD
	public void crearEntidadRutinaEntrenamiento(
			EntityManager em, RutinaEntrenamiento entidadRE
	) {
		em.persist(entidadRE);
	}
	
	//  LEER ENTIDAD
	public RutinaEntrenamiento obtenerEntidadRutinaEntrenamiento(
			EntityManager em, int id
	) {
		return em.find(RutinaEntrenamiento.class, id);
	}

	//  LEER ENTIDAD (CON OBJETOS)
	public RutinaEntrenamiento obtenerEntidadRutinaEntrenamiento(
			EntityManager em, int id, String consulta
	) {
//		return em.find(RutinaEntrenamiento.class, id);
		return em.createQuery(consulta, RutinaEntrenamiento.class).setParameter("id", id).getSingleResult();
	}
	
	// OBTENER TODAS LAS ENTIDADES
	public List<RutinaEntrenamiento> obtenerTodasLasEntidadesRutinaEntrenamiento(
			EntityManager em, String consulta
	) {
		return em.createQuery(consulta, RutinaEntrenamiento.class).getResultList();
	}
	
	// OBTENER TODAS LAS ENTIDADES
	public List<RutinaEntrenamiento> obtenerTodasLasEntidadesRutinaEntrenamiento(
			EntityManager em, String consulta, Entrenamiento obj
	) {
		return em.createQuery(consulta, RutinaEntrenamiento.class).setParameter("obj", obj).getResultList();
	}
	
	// ACTUALIZAR ENTIDAD
	public RutinaEntrenamiento actualizarEntidadRutinaEntrenamiento(
			EntityManager em, RutinaEntrenamiento entidadRE
	) {
		return em.merge(entidadRE);
	}
	
	// ELIMINAR ENTIDAD
	public void eliminarEntidadRutinaEntrenamiento(
			EntityManager em, RutinaEntrenamiento entidadRE
	) {
		em.remove(entidadRE);
	}
	
	// HACK: NO ES LA MEJOR IDEA, PERO NO SE ME OCURRE NADA MEJOR EN ESTE MOMENTO
	// CONSULTA GENERICA
	public <T, S> List<T> consultaGenerica(
			EntityManager em, Class<T> clase, String consulta, S obj
	) {
		return em.createQuery(consulta, clase).setParameter("obj", obj).getResultList();
	}
}
