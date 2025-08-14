/*
 * CLASE CONTROLADORA PARA LA CARGA DE GM
 */

package edu.unam.controlador.grupoMuscular;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;
import edu.unam.servicio.GMServicio;
import edu.unam.modelo.GrupoMuscular;

/*
 * 
 * NOTA:
 * 
 * EL BOTON ADEMAS DE CARGAR LOS DATOS TIENE QUE CERRAR LA VENTANA...
 * 
 */

public class ControladorVistaCargarGM {
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
    
    private GrupoMuscular gm = null;


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

    @FXML
    private void eventoBTAceptar(ActionEvent event) {
    	String nombreGM = this.txtNombre.getText();
    	
    	if (nombreGM.isBlank()) {
    		System.out.println("[ ERROR ] > Faltan campos que completar!");
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!", "ERROR DE CAMPOS!",
    				"Faltan campos que completar..."
    		);
    		System.out.println("[ ERROR ] > Faltan campos que completar!");
    		return;
    	}
    	
    	// BLOQUE DE VERIFICACIÓN DE NOMBRES DUPLICADOS //
    	List<GrupoMuscular> listaGM = this.sgm.obtenerTodosLosGM();
    	
    	boolean gmConNombreRepetido = false;
    	
    	for (GrupoMuscular regGM : listaGM) {
//    		System.out.printf("%s | %s%n", regGM.getNombreGrupo(), this.gm.getNombreGrupo());
//    		gmConNombreRepetido = (regGM.getNombreGrupo() == this.gm.getNombreGrupo()) ? true : false;
    		if (regGM.getNombreGrupo().equals(nombreGM.toLowerCase())) {
    			gmConNombreRepetido = true;
    			break;
    		}
    	}
    	
    	if (gmConNombreRepetido) {
    		this.lanzarMensaje(
    				AlertType.WARNING, "Atención!",
        			"NOMBRES DUPLICADOS",
        			"Ya existe un GM con el mismo nombre..."
        	);
    		System.err.println("[ ! ] > Existencia de nombre repetidos!");
        	return;
    	}
    	
    	this.gm = new GrupoMuscular(nombreGM);

    	// nombreGM = this.txtNombre.getText();
    	
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
    	
    	// PERSISTE EL OBJETO
    	this.sgm.cargarGM(this.gm);
    	
    	// SI NO ENCUENTRA EL GM PREVIAMENTE CARGADO, AL BUSCARLO RETORNA NULL, POR ENDE ENTRA EN LA CONDICON.
    	if (sgm.obtenerGM(gm.getIdGM(), false) == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE OPERACION!",
    				"Algo falló al cargar el GM..."
    		);
    		return;
    	}
    	
    	// AVISO DE CARGA EXITOSA
		this.lanzarMensaje(
				AlertType.INFORMATION, "Exito!",
				"OPERACION REALIZADA CON EXITO!",
				"Se cargó correctamente el GM..."
		);
		
		NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_GM,
					BTAceptar
			);
		NavegadorDeVistasSingleton
    		.getInstancia()
    		.cambiarVista(
    				BTAceptar,
    				"Grupo Muscular"
    		);
    }
    
    @FXML
    private void eventoBTCancelar(ActionEvent event) {
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
        			RutasVistas.VISTA_ABM_GM,
        			BTCancelar
        	);
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cambiarVista(
    				BTCancelar,
    				"Grupo Muscular"
    		);
    }
    
    @FXML
    private void initialize() {
    	this.limitarATextoSinNumeros(txtNombre);
    	this.LTitulo.setText("Cargar grupo muscular");
    }
}
