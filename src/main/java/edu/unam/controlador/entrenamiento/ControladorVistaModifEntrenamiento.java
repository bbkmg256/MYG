package edu.unam.controlador.entrenamiento;

//import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import edu.unam.modelo.Cliente;
import edu.unam.modelo.Entrenamiento;
//import edu.unam.modelo.Rutina;
import edu.unam.modelo.Tutor;
import edu.unam.servicio.ClienteServicio;
import edu.unam.servicio.EntrenamientoServicio;
//import edu.unam.servicio.RutinaServicio;
import edu.unam.servicio.TutorServicio;
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
import utilidades.parametros.ParametrosEntrenamiento;

/*
 * 
 * 
 */

public class ControladorVistaModifEntrenamiento {
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
    private TextField txtPuntaje;
    
    private ClienteServicio scli = new ClienteServicio();
    
    private TutorServicio stu = new TutorServicio();
    
//    private RutinaServicio sru = new RutinaServicio();
    
    private EntrenamientoServicio sentre = new EntrenamientoServicio();
    
//    private Entrenamiento entre;
    private int idEnt;
    
    private ParametrosEntrenamiento paramEnt = new ParametrosEntrenamiento();
    
    
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
    
    public void establecerEntrenamiento(Entrenamiento entrenamiento) {
    	this.idEnt = entrenamiento.getIdEntrenamiento();
    	this.CBCli.setValue(entrenamiento.getCliente());
    	this.CBTu.setValue(entrenamiento.getTutor());
//    	this.CBRu.setValue(entrenamiento.getRutina());
    	this.DPFI.setValue(entrenamiento.getFechaInicio());
    	this.DPFF.setValue(entrenamiento.getFechaFin());
    	this.txtPuntaje.setText(Integer.toString(entrenamiento.getPuntaje()));
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
    	this.paramEnt.cliente = this.CBCli.getSelectionModel().getSelectedItem();
    	this.paramEnt.tutor = this.CBTu.getSelectionModel().getSelectedItem();
//    	this.paramEnt.rutina = this.CBRu.getSelectionModel().getSelectedItem();
    	this.paramEnt.fechaInicio = this.DPFI.getValue();
    	this.paramEnt.fechaFin = this.DPFF.getValue();
    	
    	boolean hayErrorDeCampos = false;
    	
    	// ERROR DE CAMPOS //
    	if (this.paramEnt.cliente == null) {
    		hayErrorDeCampos = true;
    		// DIFERENTE PRINT PARA PROBAR EL SYSTEM.ERR    		
    		System.err.println(
    				"[ ERROR ] > Debe seleccionar un cliente para continuar!"
    		); // LOG
    	}
    	
    	if (!hayErrorDeCampos && this.paramEnt.tutor == null) {
    		hayErrorDeCampos = true;
    		System.err.println(
    				"[ ERROR ] > Debe seleccionar un tutor para continuar!"
    		); // LOG
    	}
    	
//    	if (!hayErrorDeCampos && this.paramEnt.rutina == null) {
//    		hayErrorDeCampos = true;
//    		System.err.println(
//    				"[ ERROR ] > Debe seleccionar una rutina para continuar!"
//    		); // LOG
//    	}
    	
    	// NO ES NECESARIO POR QUE AL NO SER EDITABLE, //
    	// EL CAMPO NO PUEDE QUEDAR VACIO			   //
//    	if (!hayErrorDeCampos && this.paramEnt.fechaInicio == null) {
//    		hayErrorDeCampos = true;
//    		System.err.println(
//    				"[ ERROR ] > Debe asignar una fecha de inicio para continuar!"
//    		); // LOG
//    	}
//    	
//    	if (!hayErrorDeCampos && this.paramEnt.fechaFin == null) {
//    		hayErrorDeCampos = true;
//    		System.err.println(
//    				"[ ERROR ] > Debe asignar una fecha de finalización para continuar!"
//    		); // LOG
//    	}
    	
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
    	if (this.paramEnt.fechaFin.isBefore(this.paramEnt.fechaInicio)) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE CAMPOS",
    				"La fecha de finalización no puede ser menor a la de inicio..."
    		);
    		System.err.println("[ ERROR ] > Inconsistencia en las fechas!"); // LOG
    		return;
    	}
    	 
    	boolean actualizacionCorrecta = 
    			this.sentre
    				.actualizarEstadoEntrenamiento(
    						this.idEnt, this.paramEnt
    				);
    	
    	if (!actualizacionCorrecta) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE OPERACION",
    				"Algo falló al modificar el entrenamiento..."
    		);
    		System.err.println("[ ERROR ] > Fallo al cargar el enetrenamiento!"); // LOG
    		return;
    	}
    	
		this.lanzarMensaje(
				AlertType.INFORMATION,
				"Exito!",
				"OPERACION REALIZADA",
				"El entrenamiento fue modificado con exito!"
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
	    			this.BTCancelar,
	    			"Entrenamiento"
	    	);
    }
    
    @FXML
    private void initialize() {
    	this.LTitulo.setText("Modificar entrenamiento");
    	this.txtPuntaje.setVisible(true);
    	
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
