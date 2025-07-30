/*
 * CLASE CONTROLADOR PARA VISTA ABM SEGUIMIENTO
 */

package edu.unam.controlador.seguimiento;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;
import edu.unam.modelo.Ejercicio;
//import edu.unam.modelo.Entrenamiento;
import edu.unam.modelo.Seguimiento;
import java.time.LocalDate;
import java.util.Optional;
import edu.unam.servicio.SeguimientoServicio;

/*
 * 
 */

public class ControladorVistaABMSeg {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
	@FXML
    private Button BTAtras;

    @FXML
    private Button BTCrear;

    @FXML
    private Button BTEliminar;

    @FXML
    private Button BTModificar;

    @FXML
    private TableView<Seguimiento> TVSeguimiento;

    @FXML
    private TableColumn<Seguimiento, String> TCEjercicio;

    @FXML
    private TableColumn<Seguimiento, LocalDate> TCFecha;

    @FXML
    private TableColumn<Seguimiento, Integer> TCID;

    @FXML
    private TableColumn<Seguimiento, Double> TCPeso;

    @FXML
    private TableColumn<Seguimiento, Integer> TCRep;

    @FXML
    private TableColumn<Seguimiento, Integer> TCSerie;
    
//    private Entrenamiento ent;
    
    private SeguimientoServicio sseg;
    
    
    // METODOS Y EVENTOS //
    private void inicializarDatos() {
//    	this.ent =
//    			AlmacenadorDeEntidades
//    				.getInstancia()
//        			.getEntrenamiento();
    	
    	this.sseg = new SeguimientoServicio();
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
    
    private <S, T> void asignarValoresColumnas(
    		TableColumn<S, T> columna,
    		String valor
    ) {
    	columna.setCellValueFactory(new PropertyValueFactory<>(valor));
    }
    
    private void actualizarTabla() {
    	this.TVSeguimiento.getItems().clear();
    	this.TVSeguimiento
    		.getItems()
    			.addAll(
    					this.sseg.obtenerTodosLosSeguimientosYSusObjetos()
    			);
//    	TVEntrenamiento.refresh();
    	
    	this.asignarValoresColumnas(this.TCID, "idSeguimiento");
    	this.asignarValoresColumnas(this.TCSerie, "cantSerieRealizado");
    	this.asignarValoresColumnas(this.TCRep, "cantRepeticionesRealizado");
    	this.asignarValoresColumnas(this.TCPeso, "pesoTrabajado");
    	this.asignarValoresColumnas(this.TCFecha, "fechaHoy");
    	
    	this.TCEjercicio.setCellValueFactory(cellData -> {
    		Ejercicio ej = cellData.getValue().getEjercicioRealizado();
    		return (ej == null) ? null : new SimpleStringProperty(ej.getNombreEjercicio());
    	});
    }
    
    public void iniciar() {
    	this.inicializarDatos();
    	this.actualizarTabla();
    }
    
    @FXML
    private void eventoBTAtras(ActionEvent event) {
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_ENT_SEG
	    	);
    	
    	ControladorVistaEntSeg CVES =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVES.iniciar();

    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTAtras,
	    			"Seguimiento"
	    	);
    }

    @FXML
    private void eventoBTCrear(ActionEvent event) {
    	NavegadorDeVistasSingleton
    	.getInstancia()
    	.cargarNuevaVista(
    			this.getClass(),
    			RutasVistas.VISTA_CARGA_MODIF_SEG,
    			new ControladorVistaCargarSeg()
    	);
	
    	// MODIFICAR ESTA BERGA
		ControladorVistaCargarSeg CVCS =
				NavegadorDeVistasSingleton
					.getInstancia()
					.obtenerControladorDeNuevaVista();
		
		CVCS.iniciar();
	
		NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTCrear,
	    			"Cargar seguimiento"
	    	);
    }

    @FXML
    private void eventoBTEliminar(ActionEvent event) {
    	Seguimiento regSeg =
    			this.TVSeguimiento
    				.getSelectionModel()
    				.getSelectedItem();
    	
    	if (regSeg == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, 
    				"Error!", 
    				"OPERACION FALLIDA", 
    				"Seleccione un seguimiento..."
    		);
    		System.err.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
    		return;
    	}
    	
    	// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Eliminacion de cliente",
    			"OPERACION ELIMINAR", "Realmente desea eliminar el cliente?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Eliminación cancelada!"); // LOG
        	return;
    	}
    	
    	System.out.println("[ ! ] > Se eliminará el seguimiento!"); // LOG
    	this.sseg.eliminarSeguimiento(regSeg.getIdSeguimiento());
    	
    	// COMPRUEBA QUE EL SEGUIMIENTO NO EXISTA EN LA BD //
    	if (this.sseg.obtenerSeguimiento(regSeg.getIdSeguimiento()) != null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, 
    				"Error!", 
    				"OPERACION FALLIDA", 
    				"La operacion de eliminacion no se ejecuto correctamente..."
    		);
    		return;
    	}
    	
    	this.actualizarTabla();
    }

    @FXML
    private void eventoBTModificar(ActionEvent event) {

    }
    
    @FXML
    private void initialize() {
    	
    }
}
