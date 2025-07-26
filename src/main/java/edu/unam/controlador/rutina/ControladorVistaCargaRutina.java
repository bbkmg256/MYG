package edu.unam.controlador.rutina;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import edu.unam.modelo.Rutina;
import edu.unam.servicio.RutinaServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
//import javafx.util.converter.IntegerStringConverter;
import utilidades.NavegadorDeVistas;
import utilidades.RutasVistas;

/*
 * 
 */

public class ControladorVistaCargaRutina {
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
    
    private Rutina rutina; 
    
    
    // METODOS Y EVENTOS //
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
    	
    	// BLOQUE DE VERIFICACIÓN DE NOMBRES DUPLICADOS //
    	List<Rutina> listaRu = this.srut.obtenerTodasLasRutinas();
    	
    	boolean rtConNombreRepetido = false;
    	
    	for (Rutina regRt : listaRu) {
    		if (regRt.getNombreRutina().equals(nombreRutina)) {
    			rtConNombreRepetido = true;
    			break;
    		}
    	}
    	
    	if (rtConNombreRepetido) {
        	Optional<ButtonType> resultado =  this.lanzarMensaje(
        			AlertType.CONFIRMATION, "Atención!",
        			"NOMBRES DUPLICADOS",
        			"Ya existe una rutina con el mismo nombre, quiere continuar?"
        	);
        	
        	// CONFIRMAR O DENEGAR OPERACION
        	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
        		System.out.println("[ ! ] > Cancelado!"); // LOG
            	return;
        	}
    	}
    	
    	this.srut.crearRutina(this.rutina = new Rutina(nombreRutina));
    	
    	if (this.srut.obtenerRutina(this.rutina.getIdRutina()) == null) {
        	this.lanzarMensaje(
        			AlertType.ERROR, "Error!",
        			"ERROR DE OPERACION",
        			"Algo falló al carga la rutina..."
        	);
    		System.out.println("[ ERROR ] > Fallo al cargar la rutina!"); // LOG
        	return;
    	}
    	
    	this.lanzarMensaje(
    			AlertType.INFORMATION, "Exito!",
    			"OPERACION REALIZADA",
    			"La rutina fue cargada con exito..."
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
    	this.LTitulo.setText("Cargar rutina");
    }
}
