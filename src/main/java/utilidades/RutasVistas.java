/*
	CORRESPONDE A LA RUTA DEL FXML UBICADO EN EL DIRECTORIO RESOURCES JUNTO A META-INF
*/

package utilidades;

/*
 * 
 * NOTA:
 * 
 * Pequeño recordatorio xd - El subdirectorio "resources" es... , o se ubica en... ,la raiz del
 * classpath del proyecto, por eso especificar "/" antes de toda la ruta funciona correctamente.
 * 
 */

public class RutasVistas {
	// RUTA VISTA INICIO
	public static final String VISTA_INICIO = "/vistas/vinicio.fxml";

	// RUTAS VISTAS CLIENTE
	private static final String RUTA_VCLIENTE = "/vistas/vcliente/";
	public static final String VISTA_ABM_CLIENTE = RUTA_VCLIENTE + "vistaABMCliente.fxml";
	public static final String VISTA_CARGA_CLIENTE = RUTA_VCLIENTE + "vistaCargaCliente.fxml";
	public static final String VISTA_MODIFICAR_CLIENTE = RUTA_VCLIENTE + "vistaModificarCliente.fxml";
	
	// RUTAS VISTAS TUTOR
	private static final String RUTA_VTUTOR = "/vistas/vtutor/";
	public static final String VISTA_ABM_TUTOR = RUTA_VTUTOR + "vistaABMTutor.fxml";
	public static final String VISTA_CARGAR_TUTOR = RUTA_VTUTOR + "vistaCargaTutor.fxml";
	public static final String VISTA_MODIFICAR_TUTOR = RUTA_VTUTOR + "vistaModificarTutor.fxml";
	
	// RUTAS VISTAS GM
	private static final String RUTA_VGM = "/vistas/vgm/";
	public static final String VISTA_ABM_GM = RUTA_VGM + "vistaABMGM.fxml";
	public static final String VISTA_CARGA_GM = RUTA_VGM + "vistaCargaModifGM.fxml"; // CAMBIARLE EL NOMBRE A ALGO MAS GENERICO
//	public static final String VISTA_MODIFICAR_GM = RUTA_VGM + "vistaModifcarGM.fxml"; // NO SE USA
	
	// RUTAS VISTAS EJERCICIO
	private static final String RUTA_VEJ = "/vistas/vejercicio/";
	public static final String VISTA_ABM_EJERCICIO = RUTA_VEJ + "vistaABMEjercicio.fxml";
	public static final String VISTA_CARGA_MODIF_EJER = RUTA_VEJ + "vistaCargaModifEjer.fxml";
	
	// RUTAS VISTAS RUTINA
	private static final String RUTA_VRUT = "/vistas/vrutina/";
	public static final String VISTA_ABM_RUTINA = RUTA_VRUT + "vistaABMRutina.fxml";
	public static final String VISTA_CARGA_MODIF_RUTINA = RUTA_VRUT + "vistaCargaModifRutina.fxml";
	public static final String VISTA_ABM_RUTEJ = RUTA_VRUT + "vistaABMRutinaEjercicio.fxml";
	public static final String VISTA_CARGA_MODIF_RUTEJ = RUTA_VRUT + "vistaCargaModifRutinaEjercicio.fxml";
	
	// RUTAS VISTAS ENTRENAMIENTO
	private static final String RUTA_VENT = "/vistas/ventrenamiento/";
	public static final String VISTA_ABM_ENT = RUTA_VENT + "vistaABMEntrenamiento.fxml";
	public static final String VISTA_CARGA_MODIF_ENT = RUTA_VENT + "vistaCargaModifEntrenamiento.fxml";
	
	// RUTAS VISTAS SEGUIMIENTO
	private static final String RUTA_VSEG = "/vistas/vseguimiento/";
	public static final String VISTA_CLI_SEG = RUTA_VSEG + "vistaClienteSeguimiento.fxml";
	public static final String VISTA_ENT_SEG = RUTA_VSEG + "vistaEntrenamientoSeguimiento.fxml";
	public static final String VISTA_ABM_SEG = RUTA_VSEG + "vistaABMSeguimiento.fxml";
	public static final String VISTA_CARGA_MODIF_SEG = RUTA_VSEG + "vistaCargarModifSeguimiento.fxml";
}
