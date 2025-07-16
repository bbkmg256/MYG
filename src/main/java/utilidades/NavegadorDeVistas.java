/*
 * CLASE (SINGLETON) PARA NAVEGAR ENTRE VISTAS
 */

package utilidades;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavegadorDeVistas {
	// ATRIBUTOS //
	private static final NavegadorDeVistas ndvs = new NavegadorDeVistas();
	private FXMLLoader nuevaVista = null;
	private Stage ventana = null;
	private Scene escena = null;
	
	// CONSTRUCTOR Y GET //
	private NavegadorDeVistas() {}
	
    public static NavegadorDeVistas getInstancia() {
    	return ndvs;
    }
	
    // METODOS //
    // SOLAMENTE CARGA LA NUEVA VISTA A UNA NUEVA ESCENA
    public <T> void cargarNuevaVista(
    		Class<T> claseActual,
    		String rutaVista
    ) {
    	try {
    		// SE CARGA LA VISTA (FXML) A UN TIPO PARENT POR QUE NO SE SABE EL TIPO DE CONTENEDOR DE LA VISTA PADRE/PRINCIPAL
    		this.nuevaVista = new FXMLLoader(claseActual.getResource(rutaVista));
    		Parent vista = this.nuevaVista.load();
    		this.escena = new Scene(vista);
    	} catch (IOException e) {
    		System.out.println(e);
    	}
    }
    
    // CARGA UNA NUEVA VISTA CON EL CONTROLADOR ESPECIFICADO
    public <T, C> void cargarNuevaVista(
    		Class<C> claseActual,
    		String rutaVista,
    		T controlador
    ) {
    	try {
    		// SE CARGA LA VISTA (FXML) A UN TIPO PARENT POR QUE NO SE SABE EL TIPO DE CONTENEDOR DE LA VISTA PADRE/PRINCIPAL
    		this.nuevaVista = new FXMLLoader(claseActual.getResource(rutaVista));
    		this.nuevaVista.setController(controlador);
    		Parent vista = this.nuevaVista.load();
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
		ventana = (Stage) nodoPropiedadEscenas.getScene().getWindow();
		ventana.setScene(escena);
		ventana.setTitle(tituloDeVentana);
		ventana.setResizable(false);
		ventana.show();
    }
}


// CODIGO SIN USAR //

//// PARA PODER OBTENER Y TRABAJAR CON EL CONTROLADOR DE LA NUEVA VISTA
//public FXMLLoader obtenerNuevaVista() {
//	return this.nuevaVista;
//}

//    // PARA PODER ESTABLECER UN CONTROLADOR A LA VISTA NUEVA DE FORMA GENERICA
//    public <T> void establecerControladorDeNuevaVista(T controlador) {
//    	this.nuevaVista.setController(controlador);
//    }