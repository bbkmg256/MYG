package edu.unam.controlador.rutina;

import java.util.function.UnaryOperator;
import edu.unam.modelo.Rutina;
import edu.unam.servicio.RutinaServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import utilidades.NavegadorDeVistas;
import utilidades.RutasVistas;
//import java.util.HashMap;

/*
 * 
 */

public class ControladorVistaModificarRutina {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTCancelar;

    @FXML
    private Button BTFinalizar;

    @FXML
    private TextField txtNombreRutina;
    
    @FXML
    private Label LTitulo;
    
    private RutinaServicio srut = new RutinaServicio();
    
    private int rutinaID;
    
    
    // METODOS Y EVENTOS //
    private void lanzarMensaje(
    		AlertType tipoDeAlerta, String titulo,
    		String cabecera, String contenido
    ) {
    	Alert alerta = new Alert(tipoDeAlerta);
    	alerta.setTitle(titulo);
    	alerta.setHeaderText(cabecera);
    	alerta.setContentText(contenido);
    	alerta.showAndWait();
    }
    
    // LIMITAR TF SOLO A TEXTO
    private void limitarATexto(TextField tf) {
        // FILTRO DE ENTRADA DE CARACTERES
    	UnaryOperator<TextFormatter.Change> textFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z]*")) {
                return change;
            }
            return null;
        };

        // CONVERSOR DE TIPOS
        TextFormatter<String> textFormatter = new TextFormatter<>(textFilter);
        tf.setTextFormatter(textFormatter);
    }
    
    public void establecerRutinaParaModificar(Rutina regRutina) {
    	// PROBAR DE USAR HASHMAPS PARA MANEJAR LOS DATOS DEL OBJETO //
    	// (NO CONVIENE, POR LA FORMA EN QUE ESTÁ DISEÑADO JAVA)	 //
    	this.rutinaID = regRutina.getIdRutina();
    	this.txtNombreRutina.setText(regRutina.getNombreRutina());
    }
    
    @FXML
    private void eventoBTFinalizar(ActionEvent event) {
    	String nombreRutina = this.txtNombreRutina.getText();
    	
    	if (nombreRutina.isBlank()) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE CAMPOS", "La rutina necesita un nombre..."
    		);
    		System.out.println("[ ERROR ] > Faltan campos que completar!"); // LOG
    		return;
    	}
    	
    	// EL METODO RETORNA TRUE SI LA ENTIDAD SE ACTUALIZA, //
    	// DE LO CONTRARIO, RETORNA FALSE					  //
    	boolean actualizacionCorrecta =
    			this.srut.actualizarEstadoRutina(
    					rutinaID, nombreRutina
    			);
    	
    	if (!actualizacionCorrecta) {
	    	this.lanzarMensaje(
	    			AlertType.ERROR, "Error!",
	    			"ERROR DE OPERACION",
	    			"Algo falló al modificar la rutina..."
	    	);
			System.out.println("[ ERROR ] > Fallo al modificar la rutina!"); // LOG
	    	return;
    	}
    	
    	this.lanzarMensaje(
    			AlertType.INFORMATION, "Exito!",
    			"OPERACION REALIZADA",
    			"La rutina fue modificada con exito..."
    	);
    	
    	NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_RUTINA
			);
		NavegadorDeVistas
			.getInstancia()
			.cambiarVista(
					BTFinalizar,
					"Rutina"
			);
    }

    @FXML
    private void eventoBTCancelar(ActionEvent event) {
    	NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_RUTINA
			);
		NavegadorDeVistas
			.getInstancia()
			.cambiarVista(
					BTCancelar,
					"Rutina"
			);
    }
    
    @FXML
    private void initialize() {
    	this.limitarATexto(this.txtNombreRutina);
    	
    	this.LTitulo.setText("Modificar rutina");
    }
}



// CODIGO SIN USAR //

//this.srut.crearRutina(this.rutina = new Rutina(nombreRutina));

//if (this.srut.obtenerRutina(this.rutina.getIdRutina()) == null) {
//	this.lanzarMensaje(
//			AlertType.ERROR, "Error!",
//			"ERROR DE OPERACION",
//			"Algo falló al modificar la rutina..."
//	);
//	System.out.println("[ ERROR ] > Fallo al modificar la rutina!"); // LOG
//	return;
//}
