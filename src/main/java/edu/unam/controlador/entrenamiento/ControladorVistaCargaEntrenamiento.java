/*
 * CLASE CONTROLADORA PARA CARGA DE ENTRENAMIENTOS
 */

package edu.unam.controlador.entrenamiento;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import edu.unam.modelo.Cliente;
import edu.unam.modelo.Tutor;
import edu.unam.servicio.ClienteServicio;
import edu.unam.servicio.EntrenamientoServicio;
import edu.unam.modelo.Entrenamiento;
//import edu.unam.servicio.RutinaServicio;
import edu.unam.servicio.TutorServicio;

import java.time.DayOfWeek;
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

    @FXML
    private Button BTInfo; // TAMPOCO USADO ACÁ
    
    @FXML
    private HBox HBPuntaje;
    
    private ClienteServicio scli;
    
    private TutorServicio stu;
    
//    private RutinaServicio sru = new RutinaServicio();
    
    private EntrenamientoServicio sentre;
    
    
    // METODOS Y EVENTOS //
    // METODO GENERICO PARA ACTUALIZAR LOS CBs
    private void inicializarDatos() {
        scli = new ClienteServicio();
        stu = new TutorServicio();        
        sentre = new EntrenamientoServicio();
    }
    
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
    	// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Carga de entrenamiento",
    			"OPERACION CARGA",
    			"Confirmar operación? " +
    			"(tenga en cuenta que las fechas de inicio " +
    			"y finalización del entrenamiento no podrán modificarse)"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Carga de entrenamiento cancelado!"); // LOG
        	return;
    	}
    	
    	Cliente regCli = this.CBCli.getSelectionModel().getSelectedItem();
    	Tutor regTu = this.CBTu.getSelectionModel().getSelectedItem();
//    	Rutina regRu = this.CBRu.getSelectionModel().getSelectedItem();
    	
    	LocalDate fechaInicio, fechaFin;
    	fechaInicio = this.DPFI.getValue();
//    	fechaFin = this.DPFF.getValue();
    	fechaFin = fechaInicio;
    	
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
    	
//    	if (!hayErrorDeCampos && fechaFin == null) {
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
//    	if (fechaFin.isBefore(fechaInicio)) {
//    		this.lanzarMensaje(
//    				AlertType.ERROR,
//    				"Error!",
//    				"ERROR DE CAMPOS",
//    				"La fecha de finalización no puede ser anterior a la de inicio..."
//    		);
//    		System.err.println("[ ERROR ] >  Inconsistencia en las fechas!"); // LOG
//    		return;
//    	}
    	
    	
//    	if (regCli.getFechaIngreso().isAfter(fechaInicio)) {
    	if (fechaInicio.isBefore(regCli.getFechaIngreso())) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE CAMPOS",
    				"La fecha de inicio no puede ser anterior a la del ingreso del cliente al gym..."
    		);
    		System.err.println("[ ERROR ] >  Inconsistencia en las fechas!"); // LOG
    		return;
    	}
    	
    	if (fechaInicio.getDayOfWeek() == DayOfWeek.SUNDAY) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE CAMPOS",
    				"La fecha de inicio no puede ser una fecha domingo..."
    		);
    		System.err.println("[ ERROR ] >  Inconsistencia en las fechas!"); // LOG
    		return;
    	}
    	
    	// ESTA RAREZA FUNCIONA!!! //
    	// CALCULO PARA FECHA FINAL DE ENTRENAMIENTO
    	// 4 SEMANAS CON 6 DIAS CADA UNO
		LocalDate fechaActual = fechaInicio;
//		LocalDate arregloFechas[] = new LocalDate[6*4];
		LocalDate fechaFinal = null;
		
		for (int i = 0; i < 6*4; i++) {
			if (fechaActual.getDayOfWeek() == DayOfWeek.SUNDAY) {
				fechaActual = fechaActual.plusDays(1);
			}
			if (i == 6*4 - 1) {
//				arregloFechas[i] = fechaActual;
				fechaFinal = fechaActual;
			}
			fechaActual = fechaActual.plusDays(1);
		}
//		fechaFin = arregloFechas[6*4-1];
		fechaFin = fechaFinal;
		
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
		
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_ABM_ENT,
	    			this.BTFinalizar
	    	);
    	
    	ControladorVistaABMEntrenamiento CVABME =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABME.iniciar();

		NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTFinalizar,
	    			"Entrenamiento"
	    	);
    }
    
    @FXML
    void eventoBTInfo(ActionEvent event) {
    	this.lanzarMensaje(
    			AlertType.INFORMATION,
    			"Acerca de puntaje...",
    			"PUNTUACION AL TUTOR",
    			"Al haber terminado el entrenamiento, " +
    			"puede definir una calificación al tutor entre 1 a 10..."
    	);
    }
    
    
    @FXML
    private void initialize() {
    	this.LTitulo.setText("Cargar entrenamiento");
//    	this.txtPuntaje.setVisible(false);
//    	this.HBPuntaje.setVisible(false);
    	this.txtPuntaje.setDisable(true);
    	
    	// PARA QUE EL USUARIO NO PUEDA DIGITAR //
    	// CUALQUIERA EN LOS DP.				//
    	this.DPFI.setEditable(false);
    	this.DPFF.setEditable(false);
    	
    	this.DPFF.setVisible(false);

//    	this.actualizarCB(
//    			this.scli.obtenerTodosLosClientes(),
//    			this.CBCli,
//    			cli -> cli.getNombre()
//    	);
//    	
//    	this.actualizarCB(
//    			this.stu.obtenerTodosLosTutores(),
//    			this.CBTu,
//    			tu -> tu.getNombre()
//    	);
    	
//    	this.actualizarCB(
//    			this.sru.obtenerTodasLasRutinas(),
//    			this.CBRu,
//    			ru -> ru.getNombreRutina()
//    	);    	
    }
}
