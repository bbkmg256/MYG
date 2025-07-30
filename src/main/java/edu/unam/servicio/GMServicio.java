/*
	Servicio de Grupo Muscular
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.GMDAO;
import jakarta.persistence.EntityManager;
import utilidades.bd.EMFSingleton;

import java.util.ArrayList;
// Varios
import java.util.List;

import edu.unam.modelo.Ejercicio;
// Entidad
import edu.unam.modelo.GrupoMuscular;


/*
 * 
 * NOTA:
 * 
 * PROBABLEMENTE TENGA QUE CREAR UN CAMPO BUSCAR LOS GM, LO VERE DESPUES
 * 
 */


/**
*
* @Autor: BBKMG
*/
public class GMServicio {
	private GMDAO gmDao;
	private EntityManager manager;
	
	// Constructor
	public GMServicio() {
		gmDao = new GMDAO();
	}
	
	// Cargar Grupo Muscular al sistema
	public void cargarGM(GrupoMuscular gm) {		
		if (gm.getNombreGrupo() == null) {
			System.out.printf("[ ERROR ] > El GM %d no tiene un nombre asignado o este es nulo!%n", gm.getIdGM());
			return;
		}
		
		// MODIFICA TODOS LOS VALORESTEXTUALES DEL OBJETO, A MINUSCULA
		gm.setNombreGrupo(gm.getNombreGrupo().toLowerCase());
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			this.gmDao.crearEntidadGM(this.manager, gm);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El GM %d cargado correctamente!%n", gm.getIdGM());
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al cargar el GM!");
		} finally {
			this.manager.close();
		}
	}
	
	// Buscar Grupo Muscular en el sistema
	public GrupoMuscular obtenerGM(int id, boolean mostrarLog) {		
		GrupoMuscular gmReg = null;

		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			gmReg = this.gmDao.obtenerEntidadGM(this.manager, id);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener los GM's!");
		} finally {
			this.manager.close();
		}
		
		if (mostrarLog && gmReg == null) {
			System.out.printf("[ ERROR ] > GM %d no encontrado%n", id); // LOG
		}
		
		return gmReg;
	}
	
	// Obtener todos los Grupo Musculares (NUNCA RETORNA NULL)
	public List<GrupoMuscular> obtenerTodosLosGM() {		
		String consulta = String.format("SELECT %c FROM %s %c", 'g', "GrupoMuscular", 'g'); // Consulta JPQL
		List<GrupoMuscular> regEntidades = null; // Variable para almacenar el registro
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regEntidades = this.gmDao.obtenerEntidadesGM(this.manager, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener los GM's!");
		} finally {
			this.manager.close();
		}
		
		return regEntidades;
	}
	
	// (CREO QUE DEBERÍA MODIFICAR TODOS LOS METODOS //
	// CON UN ORDENAMIENTO MEJOR COMO ESTE 			 //
	// POR QUE SON UN ASCO) 						 //
	public List<Ejercicio> obtenerListaDeEjercicios(int id) {
		List<Ejercicio> lista = new ArrayList<>();
		GrupoMuscular gm = this.obtenerGM(id, false);
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			lista.addAll(this.manager.merge(gm).getEjercicios());
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("[ ERROR ] > Falla al obtener la lista de ejercicios!");
		} finally {
			this.manager.close();
		}
		
		return lista;
	}
	
	// Actualizar Grupo Muscular en el sistema
	public boolean actualizarEstadoGM(int id, String nombreGM) {		
		GrupoMuscular gm = this.obtenerGM(id, false);
		
		if (gm == null) {
			System.out.printf("[ ERROR ] > El GM %d no se encuentra en el sistema!%n", id);
			return false;
		}
		
		// ALGUNOS PARAMETROS SE MODIFICAN A MINUSCULA
		gm.setNombreGrupo(nombreGM.toLowerCase());
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			this.gmDao.actualizarNombreGM(this.manager, gm);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El GM %d actualizado correctamente!%n", id);
			return true;
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualiza el GM!");
			return false;
		} finally {
			this.manager.close();
		}
	}
	
	// Eliminar Grupo Muscular del Sistema
	public void eliminarGM(int id) {
		GrupoMuscular gm = this.obtenerGM(id, false);
		
		if (gm == null) {
			System.out.printf("[ ERROR ] > El GM %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		// ADMINISTRADO DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			gm = this.manager.merge(gm); // RECONEXION DE LA ENTIDAD
			this.gmDao.eliminarEntidadGM(this.manager, gm);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El GM %d eliminado correctamente!%n", gm.getIdGM()); // LOG
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al eliminar el GM!"); // LOG
		} finally {
			this.manager.close();
		}
	}
}
