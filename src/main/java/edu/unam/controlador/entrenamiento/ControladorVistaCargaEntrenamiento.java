/*
 * CLASE CONTROLADORA PARA CARGA DE ENTRENAMIENTOS
 */

package edu.unam.controlador.entrenamiento;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;
import utilidades.NavegadorDeVistas;
import utilidades.RutasVistas;
import java.util.List;
import java.util.function.Function;
import edu.unam.modelo.Cliente;
import edu.unam.modelo.Tutor;
import edu.unam.servicio.ClienteServicio;
import edu.unam.servicio.EntrenamientoServicio;
import edu.unam.modelo.Entrenamiento;
//import edu.unam.servicio.RutinaServicio;
import edu.unam.servicio.TutorServicio;
//import edu.unam.modelo.Rutina;
import java.time.LocalDate; 

/*
 * 
 */

public class ControladorVistaCargaEntrenamiento {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTCancelar;

    @FXML
    private Button BTFinalizar;

    @FXML
    private ComboBox<Cliente> CBCli;

//    @FXML
//    private ComboBox<Rutina> CBRu;

    @FXML
    private ComboBox<Tutor> CBTu;

    @FXML
    private DatePicker DPFF;

    @FXML
    private DatePicker DPFI;

    @FXML
    private Label LTitulo;

    @FXML
    private TextField txtPuntaje; // NO USADO PARA ESTE CONSTRUCTOR
    
    private ClienteServicio scli = new ClienteServicio();
    
    private TutorServicio stu = new TutorServicio();
    
//    private RutinaServicio sru = new RutinaServicio();
    
    private EntrenamientoServicio sentre = new EntrenamientoServicio();
    
    
    // METODOS Y EVENTOS //
    // METODO GENERICO PARA ACTUALIZAR LOS CBs
    private <T> void actualizarCB(
    		List<T> lista,
    		ComboBox<T> cb,
    		Function<T, String> metodoConversorDeObj
    ) {
    	cb.getItems().clear();
    	cb.getItems().addAll(lista);
    	
    	cb.setConverter(new StringConverter<T>() {
    		@Override
    		public String toString(T obj) {
    			return (obj == null) ? null : metodoConversorDeObj.apply(obj);
    		}
    		
    		@Override
    		public T fromString(String str) {
    			return null;
    		}
    	});
    }
    
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
    
    @FXML
    private void eventoBTCancelar(ActionEvent event) {
    	NavegadorDeVistas
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_ABM_ENT
	    	);
    	
    	ControladorVistaABMEntrenamiento CVABME =
    			NavegadorDeVistas
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABME.iniciar();
	
    	NavegadorDeVistas
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTCancelar,
	    			"Entrenamiento"
	    	);
    }

    @FXML
    private void eventoBTFinalizar(ActionEvent event) {
    	Cliente regCli = this.CBCli.getSelectionModel().getSelectedItem();
    	Tutor regTu = this.CBTu.getSelectionModel().getSelectedItem();
//    	Rutina regRu = this.CBRu.getSelectionModel().getSelectedItem();
    	
    	LocalDate fechaInicio, fechaFin;
    	fechaInicio = this.DPFI.getValue();
    	fechaFin = this.DPFF.getValue();
    	
    	boolean hayErrorDeCampos = false;
    	
    	// ERROR DE CAMPOS //
    	if (regCli == null) {
    		hayErrorDeCampos = true;
    		// DIFERENTE PRINT PARA PROBAR EL SYSTEM.ERR    		
    		System.err.println(
    				"[ ERROR ] > Debe seleccionar un cliente para continuar!"
    		); // LOG
    	}
    	
    	if (!hayErrorDeCampos && regTu == null) {
    		hayErrorDeCampos = true;
    		System.err.println(
    				"[ ERROR ] > Debe seleccionar un tutor para continuar!"
    		); // LOG
    	}
    	
//    	if (!hayErrorDeCampos && regRu == null) {
//    		hayErrorDeCampos = true;
//    		System.err.println(
//    				"[ ERROR ] > Debe seleccionar una rutina para continuar!"
//    		); // LOG
//    	}
    	
    	if (!hayErrorDeCampos && fechaInicio == null) {
    		hayErrorDeCampos = true;
    		System.err.println(
    				"[ ERROR ] > Debe asignar una fecha de inicio para continuar!"
    		); // LOG
    	}
    	
    	if (!hayErrorDeCampos && fechaFin == null) {
    		hayErrorDeCampos = true;
    		System.err.println(
    				"[ ERROR ] > Debe asignar una fecha de finalización para continuar!"
    		); // LOG
    	}
    	
    	if (hayErrorDeCampos) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE CAMPOS",
    				"No puede dejar campos vacios o sin seleccionar..."
    		);
    		return;
    	}
    	
    	// ERROR DE FECHAS //
    	if (fechaFin.isBefore(fechaInicio)) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE CAMPOS",
    				"La fecha de finalización no puede ser menor a la de inicio..."
    		);
    		System.err.println("[ ERROR ] >  Inconsistencia en las fechas!"); // LOG
    		return;
    	}
    	
//    	Entrenamiento ent = new Entrenamiento(regCli, regTu, regRu, fechaInicio, fechaFin);
    	
    	Entrenamiento ent = new Entrenamiento(regCli, regTu, fechaInicio, fechaFin);
    	
    	this.sentre.cargarEntrenamiento(ent);
    	
    	if (this.sentre.obtenerEntrenamiento(ent.getIdEntrenamiento()) == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE OPERACION",
    				"Algo falló al cargar el entrenamiento..."
    		);
    		System.err.println("[ ERROR ] >  Fallo al cargar el enetrenamiento!"); // LOG
    		return;
    	}
    	
		this.lanzarMensaje(
				AlertType.INFORMATION,
				"Exito!!",
				"OPERACION REALIZADA",
				"El entrenamiento fue cargado con exito!"
		);
		
    	NavegadorDeVistas
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_ABM_ENT
	    	);
    	
    	ControladorVistaABMEntrenamiento CVABME =
    			NavegadorDeVistas
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABME.iniciar();

		NavegadorDeVistas
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTFinalizar,
	    			"Entrenamiento"
	    	);
    }
    
    @FXML
    private void initialize() {
    	this.LTitulo.setText("Cargar entrenamiento");
    	this.txtPuntaje.setVisible(false);
    	
    	this.actualizarCB(
    			this.scli.obtenerTodosLosClientes(),
    			this.CBCli,
    			cli -> cli.getNombre()
    	);
    	
    	this.actualizarCB(
    			this.stu.obtenerTodosLosTutores(),
    			this.CBTu,
    			tu -> tu.getNombre()
    	);
    	
//    	this.actualizarCB(
//    			this.sru.obtenerTodasLasRutinas(),
//    			this.CBRu,
//    			ru -> ru.getNombreRutina()
//    	);
    	
    	// PARA QUE EL USUARIO NO PUEDA DIGITAR //
    	// CUALQUIERA EN LOS DP.				//
    	this.DPFI.setEditable(false);
    	this.DPFF.setEditable(false);
    }
}
