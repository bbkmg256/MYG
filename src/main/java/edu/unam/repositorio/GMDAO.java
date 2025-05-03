/*
	DAO - Entidad Grupo Muscular
*/

package edu.unam.repositorio;

// JPA
import jakarta.persistence.EntityManager;

// ENTIDAD
import edu.unam.modelo.GrupoMuscular;

// VARIOS
import java.util.List;

/**
*
* @Autor: BBKMG
*/
public class GMDAO {
	// Constructor
	public GMDAO() {}
	
	// Crear entidad (Funcional)
	public void crearEntidadGM(EntityManager em, GrupoMuscular entidadGM) {		
		em.persist(entidadGM);
	}
	
	// Leer entidad (Funcional)
	public GrupoMuscular obtenerEntidadGM(EntityManager em, int dni) {
		return em.find(GrupoMuscular.class, dni);
	}
	
	// Leer todas las entidades (FUNCIONAL)
	public List<GrupoMuscular> obtenerEntidadesGM(EntityManager em, String consulta) {		
		return em.createQuery(consulta, GrupoMuscular.class).getResultList();
	}
	
	// PROBABLEMENTE TENGA QUE HACER UN METODO MAS PARA ACCEDER A LOS OBJETOS DE LA LISTA QUE ALMACENA PARA SU RELACION
	// OSEA ESTA -> private List<Ejercicio> ejercicios = new ArrayList<>();
	// ASÍ PARA CADA CLASE PADRE...
	
	// ACTUALIZAR ATRIBUTO "NOMBREGM"
	public void actualizarNombreGM(EntityManager em, GrupoMuscular entidadGM) {
		em.merge(entidadGM);
	}

	// Eliminar entidad (Funcional)
	public void eliminarEntidadGM(EntityManager em, GrupoMuscular entidadGM) {		
		em.remove(entidadGM);
	}
}