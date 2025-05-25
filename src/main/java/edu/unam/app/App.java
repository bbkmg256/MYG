/*
	CLASE PRINCIPAL
*/

/**
 *
 *	@Nombre_del_proyecto: MYG
 *	@Versión: 0.1.0
 *	@Autor: BBKMG
 *
 */

package edu.unam.app;

// LIBRERIAS
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import utilidades.ComprobarConexionBD;
import utilidades.EMFSingleton;
import utilidades.RutasVistas;

public class App extends Application {
	@Override
	public void start(Stage ventana) throws Exception {
		// SOLO SE PAPA EL EMF, PARA QUE FALLE DENTRO DEL METODO AL CREAR EL EM, DANDO SEÑAL DE SI HAY O NO, CONEXION
		if (!ComprobarConexionBD.hayConexion(EMFSingleton.getInstancia().getEMF())) {
			// MOMENTANEAMENTE DEJO ESTO ASI, DESPUES LO CAMBIO
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error!");
			alerta.setHeaderText("ERROR DE CONEXION A BASE DE DATOS!");
			alerta.setContentText("No se encuentra una base de datos activa!");
			alerta.showAndWait();
			System.out.println("[ ERROR ] > No hay conexión a una BD o la misma está caida!");
			return;
		}
		
		FXMLLoader cargaVistaInicio = new FXMLLoader(this.getClass().getResource(RutasVistas.VISTA_INICIO)); // LEE EL FICHERO FXML DE LA VISTA
		AnchorPane raizVistaInicio = cargaVistaInicio.load(); // CARGA EL FICHERO A UN CONTENEDOR RAIZ
		Scene escenaInicio = new Scene(raizVistaInicio); // CREA UNA NUEVA ESCENA EN BASE AL CONTENEDOR RAIZ
		ventana.setScene(escenaInicio); // ESTABLECE LA ESCENA EN LA VENTANA
		ventana.setTitle("Inicio"); // ESTABLECE TITUTO A LA VENTANA
		ventana.setResizable(false);
		ventana.show(); // MUESTRA LA VENTANA
	}
	
	public static void main(String [] args) {
		// MENSAJE DE INICIO
		System.out.println("[ MYG - v0.1.0 ]");
		System.out.println("by BBKMG");
		
		// A ESTE CONTEXTO ME REFIERO (VER CLASE EMFSINGLETON EN PAQUETE UTILIDADES PARA MAS CONTEXTO XD)
		EMFSingleton.getInstancia().iniciarEMF(); // INICIA CONEXION A BD
		
		// LANZA LA INTERFAZ INICIAL
		launch();
		
		// System.out.println("Codigo funcional!");
		EMFSingleton.getInstancia().cerrarEMF(); // MATA LA CONEXION A BD
	}
}