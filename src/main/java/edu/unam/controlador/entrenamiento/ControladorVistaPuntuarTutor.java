/*
 * CLASE CONTROLADORA PARA LA VISTA DE PUNTUACIÓN DEL TUTOR
 */

package edu.unam.controlador.entrenamiento;

import java.util.Optional;

import edu.unam.modelo.Entrenamiento;
import edu.unam.servicio.EntrenamientoServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import utilidades.AlmacenadorDeEntidadesSingleton;
//import javafx.scene.control.TextField;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;
import utilidades.parametros.ParametrosEntrenamiento;

public class ControladorVistaPuntuarTutor {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTCancelar;

    @FXML
    private Button BTFinalizar;
    
    @FXML
    private ChoiceBox<Integer> CBPuntaje;
    
    EntrenamientoServicio sentre;
    
    Entrenamiento ent;
    
    ParametrosEntrenamiento paramEnt;


    // METODOS Y EVENTOS //
    private void inicializarDatos() {
    	this.sentre = new EntrenamientoServicio();
    	this.paramEnt = new ParametrosEntrenamiento();
    	this.ent = AlmacenadorDeEntidadesSingleton.getInstancia().getEntrenamiento();
    }
    
    private Optional<ButtonType> lanzarMensaje(
    		AlertType tipoDeAlerta, String titulo,
    		String cabecera, String contenido
    ) {
    	Alert alerta = new Alert(tipoDeAlerta);
    	alerta.setTitle(titulo);
    	alerta.setHeaderText(cabecera);
    	alerta.setContentText(contenido);
    	return alerta.showAndWait();
    }
    
    public void iniciar() {
    	this.inicializarDatos();
    	
    	this.CBPuntaje.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    	this.CBPuntaje.setValue(1);
    }

    @FXML
    private void eventoBTCancelar(ActionEvent event) {
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_ENT,
					this.BTCancelar
			);
    	
    	ControladorVistaABMEntrenamiento CVABME =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABME.iniciar();
    	
		NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					this.BTCancelar,
					"Entrenamiento"
			);
    }

    @FXML
    private void eventoBTFinalizar(ActionEvent event) {
    	int punto = this.CBPuntaje.getSelectionModel().getSelectedItem();
    	
    	this.paramEnt.puntaje = punto;
    	this.paramEnt.estado = "Finalizado";
    	
    	this.sentre.actualizarEstadoEntrenamiento(
    			this.ent.getIdEntrenamiento(),
    			this.paramEnt
    	);
    	
    	System.out.println(
    			"[ EXITO ] > Entrenamient finalizado " +
    			"y tutor puntado correctamente!"
    	); // LOG
    	
    	this.lanzarMensaje(
    			AlertType.INFORMATION, 
    			"Exito!", 
    			"OPERACION REALIZADA", 
    			"Puntuación exitosa, el entrenamiento ahora está finalizado..."
    	);
    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_ENT,
					this.BTCancelar
			);
    	
    	ControladorVistaABMEntrenamiento CVABME =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABME.iniciar();
    	
		NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					this.BTCancelar,
					"Entrenamiento"
			);
    }

    @FXML
    private void initialize() {
    	
    }
}
