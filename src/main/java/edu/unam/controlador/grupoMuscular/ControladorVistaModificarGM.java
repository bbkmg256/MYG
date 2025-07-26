package edu.unam.controlador.grupoMuscular;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import edu.unam.modelo.GrupoMuscular;
import edu.unam.servicio.GMServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import utilidades.NavegadorDeVistas;
import utilidades.RutasVistas;
//import utilidades.bd.ComprobarConexionBD;
//import utilidades.bd.EMFSingleton;

/*
 * 
 * NOTA:
 * 
 * HAY QUE MODIFICAR ESTA CLASE PARA LA VISTA DE MODIFICACIÓN DE GRUPO MUSCULARES
 * 
 * [TODAVIA NO TERMINADA]
 * 
 */

public class ControladorVistaModificarGM {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTAceptar;
    
    @FXML
    private Button BTCancelar;

    @FXML
    private TextField txtNombre;
    
    @FXML
    private Label LTitulo;
    
    private GMServicio sgm = new GMServicio();
    
//    private GrupoMuscular gm = null;
    
    private int IDgm;


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
    
    // EVITA QUE SE INGRESEN DIGITOS NUMERICOS EN UN TEXTFIELD
    private void limitarATextoSinNumeros(TextField textField) {
    	// FILTRO
    	UnaryOperator<TextFormatter.Change> stringFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z]*")) {
                return change;
            }
            return null;
        };

        // NO LLEVA CONVERSOR POR QUE NO SE CASTEA EN ESTE CASO
        TextFormatter<String> textFormatter = new TextFormatter<>(stringFilter);
        textField.setTextFormatter(textFormatter);
    }
    
    public void establecerGMParaModificar(GrupoMuscular pgm) {
    	this.txtNombre.setText(pgm.getNombreGrupo());
    	this.IDgm = pgm.getIdGM();
    }

    @FXML
    private void eventoBTAceptar(ActionEvent event) {
    	String nombreGM;
    	
    	if (this.txtNombre.getText().isBlank()) {
    		System.out.println("[ ERROR ] > Faltan campos que completar!");
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!", "ERROR DE CAMPOS!",
    				"Faltan campos que completar..."
    		);
    		System.out.println("[ ERROR ] > Faltan campos que completar!");
    		return;
    	}
    	
    	nombreGM = this.txtNombre.getText();
    	
    	// POR SI FALLA LA CONEXION CON LA BD POR X MOTIVO
//    	if (!ComprobarConexionBD.hayConexion(EMFSingleton.getInstancia().getEMF())) {
//			this.lanzarMensaje(
//					AlertType.ERROR, "Error!",
//					"ERROR DE CONEXION A BASE DE DATOS!",
//					"No se encuentra una base de datos activa..."
//			);
//			System.out.println(
//					"[ ERROR ] > No hay conexión a una BD o la misma está caida!"
//			);
//			return;
//		}
    	
    	// BLOQUE DE VERIFICACIÓN DE NOMBRES DUPLICADOS //
    	List<GrupoMuscular> listaGM = this.sgm.obtenerTodosLosGM();
    	
    	boolean gmConNombreRepetido = false;
    	
    	for (GrupoMuscular regGM: listaGM) {
    		if (regGM.getNombreGrupo().equals(nombreGM)) {
    			gmConNombreRepetido = true;
    			break;
    		}
    	}
    	
    	if (gmConNombreRepetido) {
        	Optional<ButtonType> resultado =  this.lanzarMensaje(
        			AlertType.CONFIRMATION, "Atención!",
        			"NOMBRES DUPLICADOS", "Ya existe un GM con el mismo nombre, quiere continuar?"
        	);
        	
        	// CONFIRMAR O DENEGAR OPERACION
        	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
        		System.out.println("[ ! ] > Cancelado!"); // LOG
            	return;
        	}
    	}
    	
    	boolean actualizacionCorrecta = this.sgm.actualizarEstadoGM(IDgm, nombreGM);
    	
     	if (!actualizacionCorrecta) {
     		this.lanzarMensaje(
     				AlertType.ERROR, "Error!",
     				"OPERACION RELIZADA",
     				"Algo falló al modificar el grupo muscular..."
     		);
     		System.err.println("[ ERROR ] > Fallo al modificar el GM!"); // LOG
     		return;
     	}
    	
    	// AVISO DE CARGA EXITOSA
		this.lanzarMensaje(
				AlertType.INFORMATION, "Exito!",
				"OPERACION REALIZADA CON EXITO!",
				"El grupo muscular fue modificado con exito..."
		);
		
		NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_GM
			);
		NavegadorDeVistas
    		.getInstancia()
    		.cambiarVista(
    				BTCancelar,
    				"Grupo Muscular"
    		);
    }
    
    @FXML
    private void eventoBTCancelar(ActionEvent event) {
    	NavegadorDeVistas
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
        			RutasVistas.VISTA_ABM_GM
        	);
    	NavegadorDeVistas
    		.getInstancia()
    		.cambiarVista(
    				BTCancelar,
    				"Grupo Muscular"
    		);
    }
    
    @FXML
    private void initialize() {
    	this.limitarATextoSinNumeros(txtNombre);
    	this.LTitulo.setText("Modificar grupo muscular");
//    	System.out.println("[ ! ] > Funciona!");
    }
}
