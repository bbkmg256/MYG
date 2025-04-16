/*
	Servicio de Grupo Muscular
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.GMDAO;

// Varios
import java.util.List;

// Entidad
import edu.unam.modelo.GrupoMuscular;
import edu.unam.modelo.Ejercicio;


/*
 * 
 * NOTA:
 * 
 * ESTE VA A SER EL CODIGO QUE MOMENTANEAMENTE MAS COMENTADO VOY A DEJAR POR EL MOMENTO, AL RESTO LOS LIMPIO TODOS
 * 
 */


/**
*
* @Autor: BBKMG
*/
public class GMServicio {
	private GMDAO gmjpac;
	
	// Constructor
	public GMServicio() {
		gmjpac = new GMDAO();
	}
	
	// Cargar Grupo Muscular al sistema
	public void cargarGM(GrupoMuscular gm) {
		if (gmjpac.obtenerEntidadGM(gm.getIdGM()) != null) {
			System.out.printf("[ ERROR ] > El grupo muscular %d ya se encuentra en el sistema!%n", gm.getIdGM());
			return;
		}
		
		// MODIFICA TODOS LOS VALORESTEXTUALES DEL OBJETO, A MINUSCULA
		gm.setNombreGrupo(gm.getNombreGrupo().toLowerCase());
		
		// CARGA EL OBJETO A LA BD
		gmjpac.crearEntidadGM(gm);
		
		// CODIGO VIEJO
//		if (gmjpac.obtenerEntidad(gm.getIdGM(), GrupoMuscular.class) != null) {
//			System.out.printf("[ ERROR ] > El grupo muscular %d ya se encuentra en el sistema!%n", gm.getIdGM());
//		} else {
//			gmjpac.crearEntidad(gm);
//		}
	}
	
	// Buscar Grupo Muscular en el sistema
	public GrupoMuscular obtenerGM(int id) {
		GrupoMuscular gm = gmjpac.obtenerEntidadGM(id);
		
		if (gm == null) {
			System.out.printf("[ ERROR ] > El grupo muscular %d no se encuentra en el sistema!%n", id);
		}
		
		return gm;
	}
	
	// Obtener todos los Grupo Musculares
	public List<GrupoMuscular> obtenerTodosLosGM(){
		List<GrupoMuscular> gm = gmjpac.obtenerEntidadesGM();
		if (gm == null) {
			System.out.printf("[ ERROR ] > No hay grupos musculares en el sistema!%n");
		}
		return gm;
	}
	
	// Actualizar Grupo Muscular en el sistema
	public void actualizarEstadoGM(int id, String nombreGM) {
		GrupoMuscular gm = gmjpac.obtenerEntidadGM(id);
		
		if (gm == null) {
			System.out.printf("[ ERROR ] > El grupo muscular %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		// ALGUNOS PARAMETROS SE MODIFICAN A MINUSCULA
		gmjpac.actualizareEntidadGM(gm, nombreGM.toLowerCase());
		
		// CODIGO VIEJO
//		if (gm != null) {
//			gmjpac.actualizarEntidad(gm, nombreGM);
//		} else {
//			System.out.printf("[ ERROR ] > El grupo muscular %d no se encuentra en el sistema!%n", id);
//		}
	}

	// CODIGO VIEJO
//	// ACTUALIZAR RELACION
//	public void actualizarRelacionGMEjercicio(GrupoMuscular gm, Ejercicio ejercicio) {
//		if (gm == null && ejercicio == null) {
//			System.out.printf("[ ERROR ] > Alguno de los objetos ingresados es nulo o no es válido!%n");
//			return;
//		}
//		
//		gmjpac.actualizarRelacion(gm, ejercicio);
//		
//		// CODIGO VIEJO
////		if (gm != null && ejercicio != null) {
////			gmjpac.actualizarRelacion(gm, ejercicio);			
////		} else {
////			System.out.printf("[ ERROR ] > Alguno de los objetos ingresados es nulo o no es válido!%n");
////		}
//	}
	
	// Eliminar Grupo Muscular del Sistema
	public void eliminarGM(int id) {
		GrupoMuscular gm = gmjpac.obtenerEntidadGM(id);
		
		if (gm == null) {
			System.out.printf("[ ERROR ] > El grupo muscular %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		gmjpac.eliminarEntidadGM(gm);
		
		// CODIGO VIEJO
//		if (gm != null) {
//			gmjpac.eliminarEntidad(gm);
//		} else {
//			System.out.printf("[ ERROR ] > El grupo muscular %d no se encuentra en el sistema!%n", id);
//		}
	}
	
	// CERRAR CONEXION
	public void finalizarConexion() {
		if (gmjpac != null && gmjpac.hayConexion()) {
			gmjpac.cerrarEMF();			
		}
	}
}
