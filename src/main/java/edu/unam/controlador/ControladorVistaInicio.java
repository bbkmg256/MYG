/*
 * VISTA INICIAL DEL SISTEMA
 */

package edu.unam.controlador;

// LIBRERIAS
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import utilidades.RutasVistas;

public class ControladorVistaInicio {
    @FXML
    private Button BTCliente;

    @FXML
    private Button BTEjercicio;

    @FXML
    private Button BTEntrenamiento;

    @FXML
    private Button BTGM;

    @FXML
    private Button BTRutina;

    @FXML
    private Button BTSeguimiento;

    @FXML
    private Button BTTutor;
    
    @FXML
    private Button BTLOL;
    
    @FXML
    private Label LBEGG;

    private static Stage ventana;
    
    @FXML
    private void eventoBTCliente(ActionEvent event) {
    	// SI YA HAY UNA VENTANA DE ESTE TIPO, SOLO LA TRAE AL FRENTE
    	if (ventana != null && ventana.isShowing()) {
    		ventana.toFront();
    		return;
    	}

    	try {
    		FXMLLoader cargaVistaCargarCli = new FXMLLoader(this.getClass().getResource(RutasVistas.VISTA_CARGA_CLIENTE));
    		AnchorPane raizVistaCargarCli = cargaVistaCargarCli.load();
    		Scene escenaCargarCli = new Scene(raizVistaCargarCli);
    		ventana = new Stage();
    		ventana.setTitle("Cliente");
    		ventana.setResizable(false);
    		ventana.setScene(escenaCargarCli);
    		ventana.setOnCloseRequest(e -> ventana = null); // AL CERRAR LA VENTANA, EL ESTATICO "VENTANA" SE VUELVE NULO
//    		ventana.setAlwaysOnTop(true);
    		ventana.show();
    	} catch (IOException e) {
    		System.out.println(e);
    	}    		
    }

    @FXML
    private void eventoBTEjercicio(ActionEvent event) {

    }

    @FXML
    private void eventoBTEntrenamiento(ActionEvent event) {

    }

    @FXML
    private void eventoBTGM(ActionEvent event) {

    }

    @FXML
    private void eventoBTRutina(ActionEvent event) {

    }

    @FXML
    private void eventoBTSeguimiento(ActionEvent event) {

    }

    @FXML
    private void eventoBTTutor(ActionEvent event) {

    }
    
    @FXML
    void BTLOL(ActionEvent event) {
    	this.LBEGG.setVisible(true);
    }

    
    @FXML
    private void initialize() {
    }
}