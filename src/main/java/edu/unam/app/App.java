/*
	Esta es la clase para las vista en javaFX
*/

package edu.unam.controlador;

//JavaFX
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import utilidades.RutasVistas;

public class LanzadorVistas extends Application {
	
	public LanzadorVistas() {}
	
	@Override
	public void start(Stage stage) throws Exception {
		// Carga el fxml desde la ruta ingresada
//		FXMLLoader carga = new FXMLLoader(this.getClass().getResource(RutasVistas.VISTA_UNO));
		
		// Crea la estructura raiz con el fxml cargado
//		AnchorPane esctructuraRaiz = carga.load();
		
		// Establece la escena para visualizar
//		Scene escena = new Scene(esctructuraRaiz);
//		stage.setScene(escena);
		
		
		FXMLLoader vistaDos = new FXMLLoader(this.getClass().getResource(RutasVistas.VISTA_DOS));
		AnchorPane raiz = vistaDos.load();
		Scene escena = new Scene(raiz);
		stage.setScene(escena);
		
		// Muestra la escena
		stage.show();
	}
	
	public void iniciar() {
		launch();
	}
}