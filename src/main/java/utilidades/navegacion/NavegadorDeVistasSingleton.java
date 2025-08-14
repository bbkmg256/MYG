/*
 * CLASE (SINGLETON) PARA NAVEGAR ENTRE VISTAS
 */

package utilidades.navegacion;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/*
 * TODO: DEBERÍA UNIFICAR EL METODO DEL CONTROLADOR CON LOS METODOS DE CARGA
 * DE LA NUEVA VISTA, Y LLAMAR AL METODO "iniciar()" DE CADA CONTROLADOR
 * DE VISTA, DENTRO DE ESOS METODOS (cargarNuevaVista).
 */
public class NavegadorDeVistasSingleton {
	// ATRIBUTOS //
	private static NavegadorDeVistasSingleton ndvs = null;
	private FXMLLoader nuevaVista = null;
	private Stage ventana = null;
	private Scene escena = null;
	
	// NOTE: DIMENSIONES MINIMAS
	private final double ANCHO = 800;
	private final double ALTO = 600;
	
	// NOTE: DIMENSIONES DE VENTANA DE VISTA ANTERIOR
	private HashMap<String, Double> dimenVentanaAnterior;
	
	
	// CONSTRUCTOR Y GET //
	private NavegadorDeVistasSingleton() {}
	
    public static NavegadorDeVistasSingleton getInstancia() {
    	if (ndvs == null) {
    		ndvs = new NavegadorDeVistasSingleton();
    	}
    	return ndvs;
    }
	
    // METODOS //
    /*
     * NOTE: OBTIENE LAS DIMENSIONES DE LA VENTANA MIENTRAS SE VISUALIZA LA VISTA ANTERIOR
     */
    private HashMap<String, Double> tomarDimensionesDeVentanaAnterior(Node nodoPropiedadEscenas) {
    	HashMap<String, Double> dimensiones = new HashMap<>();
    	
    	Stage ventanaAnterior = (Stage) nodoPropiedadEscenas.getScene().getWindow();
    	dimensiones.put("ancho", ventanaAnterior.getWidth());
    	dimensiones.put("alto", ventanaAnterior.getHeight());
    	
    	return dimensiones;
    }
    
    // SOLAMENTE CARGA LA NUEVA VISTA A UNA NUEVA ESCENA
    public <T> void cargarNuevaVista(
    		Class<T> claseActual,
    		String rutaVista,
    		Node componenteDeVentana
    ) {
    	try {
    		this.dimenVentanaAnterior = 
    				this.tomarDimensionesDeVentanaAnterior(componenteDeVentana);
    		
    		/* 
    		 * NOTE: SE CARGA LA VISTA (FXML) A UN TIPO PARENT POR QUE
    		 * NO SE SABE EL TIPO DE CONTENEDOR DE LA VISTA ANTERIOR
    		 */
    		this.nuevaVista = new FXMLLoader(claseActual.getResource(rutaVista));
    		Parent vista = this.nuevaVista.load();
    		// HACK: MOMENTANEAMENTE PARA ESTABLECER DIMENSION DE VENTANA MINIMA
//    		AnchorPane vistaPane = (AnchorPane) vista;
//    		vistaPane.setMinSize(this.ANCHO, this.ALTO);
    		this.escena = new Scene(vista);
    	} catch (IOException e) {
    		System.out.println(e);
    	}
    }
    
    // CARGA UNA NUEVA VISTA CON EL CONTROLADOR ESPECIFICADO
    public <T, C> void cargarNuevaVista(
    		Class<C> claseActual,
    		String rutaVista,
    		Node componenteDeVentana,
    		T controlador
    ) {
    	try {
    		this.dimenVentanaAnterior = 
    				this.tomarDimensionesDeVentanaAnterior(componenteDeVentana);
    		
    		// SE CARGA LA VISTA (FXML) A UN TIPO PARENT POR QUE NO SE SABE EL TIPO DE CONTENEDOR DE LA VISTA PADRE/PRINCIPAL
    		this.nuevaVista = new FXMLLoader(claseActual.getResource(rutaVista));
    		this.nuevaVista.setController(controlador);
    		Parent vista = this.nuevaVista.load();
    		// HACK: MOMENTANEAMENTE PARA ESTABLECER DIMENSION DE VENTANA MINIMA
//    		AnchorPane vistaPane = (AnchorPane) vista;
//    		vistaPane.setMinSize(this.ANCHO, this.ALTO);
    		this.escena = new Scene(vista);
    	} catch (IOException e) {
    		System.out.println(e);
    	}
    }
    
    // PARA PODER OBTENER Y TRABAJAR CON EL CONTROLADOR DE LA NUEVA VISTA
    public <T> T obtenerControladorDeNuevaVista() {
    	return this.nuevaVista.getController();
    }

    // HACE EL CAMBIO DE VISTA DE LA ACTUAL A LA NUEVA
    public void cambiarVista(
    		Node nodoPropiedadEscenas,
    		String tituloDeVentana
    ) {
    	// OBTIENE EL STAGE O VENTANA ACTUAL DESDE ALGUNO DE LOS NODOS QUE CONTIENE
		this.ventana = (Stage) nodoPropiedadEscenas.getScene().getWindow();
		this.ventana.setScene(escena);
		this.ventana.setTitle(tituloDeVentana);
		/*
		 * HACK: MOMENTANEO PARA HEREDAR LAS DIMENSIONES
		 * DE LA VENTANA DE LA VISTA ANTERIOR.
		 * (EVITA QUE SE REDIMENSIONE LA VENTANA
		 * AL CAMBIAR DE ESCENA)
		 */
		this.ventana.setWidth(this.dimenVentanaAnterior.get("ancho"));
		this.ventana.setHeight(this.dimenVentanaAnterior.get("alto"));
		this.ventana.setMinWidth(ANCHO); this.ventana.setMinHeight(ALTO);
		this.ventana.setResizable(true);
		this.ventana.show();
    }
}


// DEPRECATE: CODIGO SIN USAR

//// PARA PODER OBTENER Y TRABAJAR CON EL CONTROLADOR DE LA NUEVA VISTA
//public FXMLLoader obtenerNuevaVista() {
//	return this.nuevaVista;
//}

//    // PARA PODER ESTABLECER UN CONTROLADOR A LA VISTA NUEVA DE FORMA GENERICA
//    public <T> void establecerControladorDeNuevaVista(T controlador) {
//    	this.nuevaVista.setController(controlador);
//    }