/*
 * CLASE CONTROLADORA DE SEGUIMIENTO PARA SELECCIONAR CLIENTE
 */

package edu.unam.controlador.seguimiento;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.Optional;
import edu.unam.modelo.Cliente;
import edu.unam.servicio.ClienteServicio;
import utilidades.AlmacenadorDeEntidadesSingleton;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;

/*
 * 
 */

public class ControladorVistaCliSeg {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTAbrir;

    @FXML
    private Button BTAtras;

    @FXML
    private Button BTBuscar;

    @FXML
    private TableView<Cliente> TVTablaClientes;

    @FXML
    private TableColumn<Cliente, String> colApellido;

    @FXML
    private TableColumn<Cliente, String> colCiudad;

    @FXML
    private TableColumn<Cliente, Integer> colCodigoPostal;

    @FXML
    private TableColumn<Cliente, Integer> colDNI;

    @FXML
    private TableColumn<Cliente, LocalDate> colFechaIngreso;

    @FXML
    private TableColumn<Cliente, LocalDate> colFechaNacimiento;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colProvincia;

    @FXML
    private TableColumn<Cliente, String> colSexo;

//    @FXML
//    private TextField txtDNI;

    private ClienteServicio scli = new ClienteServicio();
    
    
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
    
    private <S, T> void asignarValoresColumnas(
    		TableColumn<S, T> columna, String valor
    ) {
    	columna.setCellValueFactory(new PropertyValueFactory<S, T>(valor));
    }
    
    private void actualizarTabla() {
    	this.TVTablaClientes.getItems().clear();
    	this.TVTablaClientes.getItems().addAll(this.scli.obtenerTodosLosClientes());
    	
    	this.asignarValoresColumnas(this.colDNI, "dni");
    	this.asignarValoresColumnas(this.colNombre, "nombre");
    	this.asignarValoresColumnas(this.colApellido, "apellido");
    	this.asignarValoresColumnas(this.colSexo, "sexo");
    	this.asignarValoresColumnas(this.colCiudad, "ciudad");
    	this.asignarValoresColumnas(this.colProvincia, "provincia");
    	this.asignarValoresColumnas(this.colCodigoPostal,"codPost");
    	this.asignarValoresColumnas(this.colFechaNacimiento, "fechaNacimiento");
    	this.asignarValoresColumnas(this.colFechaIngreso, "fechaIngreso");
    }
    
    @FXML
    private void eventoBTAbrir(ActionEvent event) {
    	Cliente regCli =
    			this.TVTablaClientes
    				.getSelectionModel()
    				.getSelectedItem();
    	
    	if (regCli == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE OPERACION",
    				"Debe seleccionar un cliente para continuar..."
    		);
    		System.err.println(
    				"[ ERROR ] > Seleccione un registro para continuar!"
    		);
    		return;
    	}

    	AlmacenadorDeEntidadesSingleton
    		.getInstancia()
    		.setCliente(regCli);
    	
//    	System.out.println(AlmacenadorDeEntidades.getInstancia().getCliente());
    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ENT_SEG,
					BTAbrir
			);
    	
    	ControladorVistaEntSeg CVES = 
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVES.iniciar();
    	
		NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					BTAbrir,
					"Seguimiento"
			);
    }

    @FXML
    private void eventoBTAtras(ActionEvent event) {
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_INICIO,
					BTAtras
			);
		NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					BTAtras,
					"Inicio"
			);
    }

//    @FXML
//    private void eventoBTBuscar(ActionEvent event) {
//
//    }
    
    @FXML
    private void initialize() {
    	this.actualizarTabla();
    }
}
