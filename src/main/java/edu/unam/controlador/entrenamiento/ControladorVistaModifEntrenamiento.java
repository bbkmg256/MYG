package edu.unam.controlador.entrenamiento;

import java.util.ArrayList;
//import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import edu.unam.modelo.Cliente;
import edu.unam.modelo.Entrenamiento;
import edu.unam.modelo.Seguimiento;
//import edu.unam.modelo.Rutina;
import edu.unam.modelo.Tutor;
import edu.unam.servicio.ClienteServicio;
import edu.unam.servicio.EntrenamientoServicio;
import edu.unam.servicio.SeguimientoServicio;
//import edu.unam.servicio.RutinaServicio;
import edu.unam.servicio.TutorServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import utilidades.AlmacenadorDeEntidadesSingleton;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;
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
    
    @FXML
    private Button BTInfo;
    
    @FXML
    private HBox HBPuntaje;
    
    private ClienteServicio scli;
    
    private TutorServicio stu;
    
//    private RutinaServicio sru = new RutinaServicio();
    
    private EntrenamientoServicio sentre;
    
//    private Entrenamiento entre;
    private int idEnt;
    
    private ParametrosEntrenamiento paramEnt;
    
    private Entrenamiento ent;
    
    private SeguimientoServicio sseg;

    
    // METODOS Y EVENTOS //
    private void inicializarDatos() {
        scli = new ClienteServicio();
        stu = new TutorServicio();
        sentre = new EntrenamientoServicio();
        paramEnt = new ParametrosEntrenamiento();
    	ent = AlmacenadorDeEntidadesSingleton.getInstancia().getEntrenamiento();
    	sseg = new SeguimientoServicio();
    }
    
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
    
    // TENGO FICA DE CAMBIARLO, POR ESO LO DEJO ASÍ
    private void establecerEntrenamiento(Entrenamiento entrenamiento) {
    	this.idEnt = entrenamiento.getIdEntrenamiento();
    	this.CBCli.setValue(entrenamiento.getCliente());
    	this.CBTu.setValue(entrenamiento.getTutor());
//    	this.CBRu.setValue(entrenamiento.getRutina());
    	this.DPFI.setValue(entrenamiento.getFechaInicio());
    	this.DPFF.setValue(entrenamiento.getFechaFin());
    	
    	if (entrenamiento.getPuntaje() != 0) {
    		this.txtPuntaje.setText(Integer.toString(entrenamiento.getPuntaje()));
    	}    	
    }
    
    private boolean hayFechaFinalEnSeguimiento(Entrenamiento paramEnt) {
    	List<Seguimiento> lista = new ArrayList<>();
    	lista =
    			this.sseg.obtenerListaFiltradaPorEntrenamiento(paramEnt);

    	if (lista == null) {
    		return false;
    	}
    	
    	// SI HAY UN REGISTRO CON FECHA COINCIDENTE A LA DE LA FINALIZACION //
    	// DEL ENTRENAMIENTO, SE RETORNA VERDADERO, SINO NO XD				//
    	for (Seguimiento i : lista) {
    		if (i.getFechaHoy().isEqual(paramEnt.getFechaFin())) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public void iniciar() {
    	this.inicializarDatos();
    	
    	this.establecerEntrenamiento(ent);
    	
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
    	
    	// LA MODIFICACIÓN DEL PUNTAJE SE HABILITA SI EXISTE   //
    	// POR LO MENOS UN SEGUIMIENTO REGISTRADO CON LA FECHA //
    	// EL ULTIMO DIA DE LA ULTIMA SEMANA DEL ENTRENAMIENTO //
    	
    	
//    	if (this.hayFechaFinalEnSeguimiento(this.ent) && this.ent.getEstado().equals("Finalizado")) {
////        	this.txtPuntaje.setVisible(true);
//        	this.txtPuntaje.setDisable(false);
//        	System.out.println("WTF?");
//    	}
    	
    	if (this.ent.getEstado().equals("Finalizado")) {
    		this.CBCli.setDisable(true);
    		this.CBTu.setDisable(true);
    		this.txtPuntaje.setDisable(false);
    	}
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
    			AlertType.CONFIRMATION, "Modificación de entrenamiento",
    			"OPERACION MODIFICAR", "Confirmar operación?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Operación cancelada!"); // LOG
        	return;
    	}
    	
    	this.paramEnt.cliente = this.CBCli.getSelectionModel().getSelectedItem();
    	this.paramEnt.tutor = this.CBTu.getSelectionModel().getSelectedItem();
//    	this.paramEnt.rutina = this.CBRu.getSelectionModel().getSelectedItem();
    	this.paramEnt.fechaInicio = this.DPFI.getValue();
    	this.paramEnt.fechaFin = this.DPFF.getValue();
    	
    	// AHORA ESTO PUEDE SER BLANK, ASÍ QUE TENGO QUE VERIFICARLO MAS ABAJO, YA VUELVO XD
    	if (this.ent.getEstado().equals("Finalizado")) {    		
    		this.paramEnt.puntaje = Integer.parseInt(this.txtPuntaje.getText());
    	}
    	
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
    	
    	/* HACK: SE PUEDE MEJORAR
    	 */
    	if (this.ent.getEstado().equals("Finalizado")) {
    		if (!hayErrorDeCampos && this.paramEnt.puntaje == 0) {
    			hayErrorDeCampos = true;
    			System.err.println(
    					"[ ERROR ] > El puntaje del tutor debe ser un numero entre 1 y 10!"
    					); // LOG
    		}    		
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
    	
    	// VERIFICAR QUE LA FECHA DE INICIO Y FIN, NO COINCIDAN CON UN DOMINGO.
    	
    	
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
    	
    	if (this.paramEnt.fechaInicio.isBefore(this.paramEnt.cliente.getFechaIngreso())) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE CAMPOS",
    				"La fecha de inicio no puede ser anterior a la del ingreso del cliente al gym..."
    		);
    		System.err.println("[ ERROR ] >  Inconsistencia en las fechas!"); // LOG
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
    	this.LTitulo.setText("Modificar entrenamiento");
//    	this.HBPuntaje.setVisible(false);
    	this.txtPuntaje.setDisable(true);

    	// PARA QUE EL USUARIO NO PUEDA DIGITAR //
    	// CUALQUIERA EN LOS DP.				//
    	this.DPFI.setEditable(false);
    	this.DPFF.setEditable(false);
    	
//    	this.DPFF.setVisible(false);
    	
    	this.DPFI.setDisable(true);
    	this.DPFF.setDisable(true);
    	
//    	this.txtPuntaje.setVisible(true);
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
