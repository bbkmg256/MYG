/*
 * CONTROLADOR DE VISTA SEGUIMIENTO PARA MANEJAR Y ELEGIR ENTRENAMIENTO DE CLIENTE
 */

package edu.unam.controlador.seguimiento;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import edu.unam.modelo.Entrenamiento;
//import edu.unam.modelo.Rutina;
import edu.unam.modelo.Tutor;
import edu.unam.servicio.EntrenamientoServicio;
import java.util.List;
import java.util.Optional;
import edu.unam.modelo.Cliente;
import java.time.LocalDate;
import utilidades.AlmacenadorDeEntidadesSingleton;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;

/*
 * 
 */

public class ControladorVistaEntSeg {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTAbrir;

    @FXML
    private Button BTAtras;
    
    @FXML
    private Label txtCliente;

    @FXML
    private TableView<Entrenamiento> TVEntrenamiento;

    @FXML
    private TableColumn<Entrenamiento, LocalDate> TCFI;

    @FXML
    private TableColumn<Entrenamiento, LocalDate> TCFf;

    @FXML
    private TableColumn<Entrenamiento, Integer> TCID;

    @FXML
    private TableColumn<Entrenamiento, Integer> TCPT;

//    @FXML
//    private TableColumn<Entrenamiento, String> TCRu;

    @FXML
    private TableColumn<Entrenamiento, String> TCTu;

    private Cliente cli;

    private EntrenamientoServicio sentre;

    
    // METODOS Y EVENTOS //
    private void inicializarDatos() {
    	cli = AlmacenadorDeEntidadesSingleton.getInstancia().getCliente();
    	sentre = new EntrenamientoServicio();
    	
    	System.out.println("A");
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

    private void actualizarTabla(List<Entrenamiento> lista) {
    	TVEntrenamiento.getItems().clear();
    	TVEntrenamiento
    		.getItems()
    			.addAll(
    					lista
    			);
//    	TVEntrenamiento.refresh();
    	
    	this.asignarValoresColumnas(this.TCID, "idEntrenamiento");
    	this.asignarValoresColumnas(this.TCFI, "fechaInicio");
    	this.asignarValoresColumnas(this.TCFf, "fechaFin");
    	this.asignarValoresColumnas(this.TCPT, "puntaje");
    	
    	// RENDERIZADO DE OBJETOS DE ENTRENAMIENTO //
    	// TUTOR
    	this.TCTu.setCellValueFactory(cellData -> {
    		Tutor tutor = cellData.getValue().getTutor();
    		return (tutor == null) ? null : new SimpleStringProperty(tutor.getNombre());
    	});
    	
    	// RUTINA
//    	this.TCRu.setCellValueFactory(cellData -> {
//    		Rutina rut = cellData.getValue().getRutina();
//    		return (rut == null) ? null : new SimpleStringProperty(rut.getNombreRutina());
//    	});
    }
    
//    public void establecerCliente() {
//    	this.cli = AlmacenadorDeEntidades.getInstancia().getCliente();
//    	
//    	this.txtCliente.setText(cli.getNombre());
//    	
//    	this.actualizarTabla(
//    			this.sentre
//    				.obtenerTodosLosEntrenamientosYSusObjetos(
//    						this.cli
//    				)
//    	);
//    }

    // COMO initialize(), PERO APLICADO A MI LOGICA
    public void iniciar() {
    	this.inicializarDatos();
    	
    	this.txtCliente.setText(this.cli.getNombre());
    	
    	this.actualizarTabla(
    			this.sentre
    				.obtenerTodosLosEntrenamientosYSusObjetos(
    						this.cli
    				)
    	);
    }
    
    @FXML
    private void eventoBTAbrir(ActionEvent event) {
    	Entrenamiento regEnt = this.TVEntrenamiento.getSelectionModel().getSelectedItem();
    	
    	if (regEnt == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, 
    				"Error!", 
    				"ERROR DE OPERACION", 
    				"Seleccione un entrenamiento para continuar..."
    		);
    		System.err.println("[ ERROR ] > Seleccione un registro para continuar!");
    		return;
    	}
    	
//    	if (regEnt.getEstado().equals("Finalizado")) {
//    		this.lanzarMensaje(
//    				AlertType.ERROR, 
//    				"Error!", 
//    				"ENTRENAMIENTO FINALIZADO",
//    				"Seleccione un entrenamiento que esté en curso..."
//    		);
//    		System.err.println("[ ERROR ] > El entrenamiento está finalizado!");
//    		return;    		
//    	}
    	
    	// PASA LA ENTIDAD ENTRENAMIENTO PARA USARLO EN LA SIGUIENTE VISTA
    	AlmacenadorDeEntidadesSingleton
    		.getInstancia()
    		.setEntrenamiento(regEnt);
    	
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_ABM_SEG
	    	);
    	
    	ControladorVistaABMSeg CVABMS =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABMS.iniciar();
    	
		NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTAtras,
	    			"Seguimiento"
	    	);
    }

    @FXML
    private void eventoBTAtras(ActionEvent event) {
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_CLI_SEG
	    	);
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTAtras,
	    			"Seguimiento"
	    	);
    }
    
    @FXML
    private void initialize() {
    	System.out.println("B");
    }
}
