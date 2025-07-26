/*
 * DAO - INTERMEDIO ENTRE RUTINA Y EJERCICIO
 */

package edu.unam.repositorio;

// LIBRERIAS
// VARIOS
import java.util.List;

// JPA
import jakarta.persistence.EntityManager;

// ENTIDADES
import edu.unam.modelo.RutinaEjercicio;


/*
 * 
 * NOTA:
 * 
 * OBVIAMENTE ES NECESARIO TENER ALGO EN LA ENTIDAD EJERCICIO Y ALGO EN LA ENTIDA RUTINA
 * SINO SERA IMPOSIBLE CREAR UNA INSTANCIA DE ASOCIOACION DE ESTA ENTIDAD PARA RUTINA Y EJERCIO.
 * 
 */


/*
*
* @Autor: BBKMG
*/
public class RutinaEjercicioDAO {	
	// CONTRUCTOR
	public RutinaEjercicioDAO() {}
	
	// CREAR ENTIDAD
	public void crearEntidadRutinaEjercicio(EntityManager em, RutinaEjercicio entidadRE) {		
		em.persist(entidadRE);
	}
	
	// LEER ENTIDAD
	public RutinaEjercicio obtenerEntidadRutinaEjercicio(EntityManager em, int id) {		
		return em.find(RutinaEjercicio.class, id);
	}
	
	// LEER ENTIDAD (CON OBJETOS)
	public RutinaEjercicio obtenerEntidadRutinaEjercicio(EntityManager em, int id, String consulta) {		
//		return em.find(RutinaEjercicio.class, id);
		return em.createQuery(consulta, RutinaEjercicio.class).setParameter("id", id).getSingleResult();
		
	}

	// LEER ENTIDADES
	public List<RutinaEjercicio> obtenerEntidadesRutinaEjercicio(EntityManager em, String consulta) {
		return em.createQuery(consulta, RutinaEjercicio.class).getResultList();
	}
	
	// LEER ENTIDADES (CON OBJETOS)
	public List<RutinaEjercicio> obtenerEntidadesRutinaEjercicio(EntityManager em, String consulta, int idRut) {		
		return em.createQuery(consulta, RutinaEjercicio.class).setParameter("idRutina", idRut).getResultList();
	}
	
	// ACTUALIZAR ENTIDAD
	public RutinaEjercicio actualizarEntidadRutinaEjercicio(EntityManager em, RutinaEjercicio entidadRE) {		
		return em.merge(entidadRE);
	}

	// ELIMINAR ENTIDAD
	public void eliminarEntidadRutinaEjercicio(EntityManager em, RutinaEjercicio entidadRE) {		
		em.remove(entidadRE);
	}
}