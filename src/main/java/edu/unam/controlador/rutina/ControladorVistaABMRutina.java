package edu.unam.controlador.rutina;

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

import java.util.List;
import java.util.Optional;

import edu.unam.controlador.ControladorVistaInicio;
import edu.unam.modelo.Rutina;
import edu.unam.servicio.RutinaServicio;

/*
 * 
 */

public class ControladorVistaABMRutina {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTAbrir;

    @FXML
    private Button BTAtras;

    @FXML
    private Button BTCrear;

    @FXML
    private Button BTEliminar;

    @FXML
    private Button BTModificar;

    @FXML
    private TableView<Rutina> TVRutina;

    @FXML
    private TableColumn<Rutina, Integer> TCID;

    @FXML
    private TableColumn<Rutina, String> TCNR;
    
    private RutinaServicio srut = new RutinaServicio();
    
    private Rutina regRut = null;

    
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
    
    private <S, T> void asignarValoresColumnas(TableColumn<S, T> columna, String valor) {
    	columna.setCellValueFactory(new PropertyValueFactory<>(valor));
    }

    // LA LISTA DE ENTIDADES LA TOMA CADA VEZ QUE SE LLAMA A ESTE METODO.
    private void actualizarTabla(List<Rutina> listaRutinas) {
    	TVRutina.getItems().clear(); // Limpia los items de la tabla
    	TVRutina.getItems().addAll(listaRutinas); // Añade items en la lista de la tabla
//    	TVTablaClientes.refresh();
    	
    	this.asignarValoresColumnas(this.TCID, "idRutina");
    	this.asignarValoresColumnas(this.TCNR, "nombreRutina");
    }
    
    @FXML
    private void eventoBTCrear(ActionEvent event) {
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_CARGA_MODIF_RUTINA,
					new ControladorVistaCargaRutina()
			);
		NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					BTAtras,
					"Inicio"
			);
    }

    @FXML
    private void eventoBTEliminar(ActionEvent event) {
    	this.regRut = this.TVRutina.getSelectionModel().getSelectedItem();
    	
    	if (regRut == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!", "OPERACION FALLIDA",
    				"Seleccione una rutina..."
    		);
    		System.out.println(
    				"[ ERROR ] > No se ah seleccionado un registro!"
    		); // LOG
    		return;
    	}
    	
    	if (!this.srut.obtenerListaDeRutinaEntrenamientos(this.regRut.getIdRutina()).isEmpty()) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"IMPOSIBLE ELIMINAR REGISTRO",
    				"Esta rutina es usada o está relacionada con por lo menos 1 entrenamiento..."
    		);
    		System.out.println(
    				"[ ERROR ] > No se puede eliminar el registro " +
    				"por que tiene registros hijos asociados!"
    		); // LOG
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
    	
    	System.out.println("[ ! ] > Se eliminará la rutina!"); // LOG
    	
    	this.srut.eliminarRutina(this.regRut.getIdRutina());
    	
    	if (this.srut.obtenerRutina(this.regRut.getIdRutina()) != null) {
			this.lanzarMensaje(
					AlertType.ERROR, "Error!", "OPERACION FALLIDA",
					"La operacion de eliminación no se ejecuto correctamente..."
			);
    		return;
    	}
    	
    	this.actualizarTabla(this.srut.obtenerTodasLasRutinas());
    }

    @FXML
    private void eventoBTModificar(ActionEvent event) {
    	this.regRut = this.TVRutina.getSelectionModel().getSelectedItem();
    	
    	if (regRut == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!", "OPERACION FALLIDA",
    				"Seleccione una rutina..."
    		);
    		System.out.println(
    				"[ ERROR ] > No se ah seleccionado un registro!"
    		); // LOG
    		return;
    	}
    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_CARGA_MODIF_RUTINA,
					new ControladorVistaModificarRutina()
			);
    	
    	ControladorVistaModificarRutina CVMR =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVMR.establecerRutinaParaModificar(regRut);
    	
		NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					BTModificar,
					"Modificar rutina"
			);
    }
    
    @FXML
    private void eventoBTAbrir(ActionEvent event) {
    	// TODAVÍA FALTA ESTE APARTADO, ES NECESARIO HACER LAS VISTAS ANTES...
    	this.regRut = this.TVRutina.getSelectionModel().getSelectedItem();
    	
    	if (regRut == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!", "OPERACION FALLIDA",
    				"Seleccione una rutina..."
    		);
    		System.out.println(
    				"[ ERROR ] > No se ah seleccionado un registro!"
    		); // LOG
    		return;
    	}
    	
    	NavegadorDeVistasSingleton
		.getInstancia()
		.cargarNuevaVista(
				this.getClass(),
				RutasVistas.VISTA_ABM_RUTEJ
		);
    	
//    	NavegadorDeVistas
//			.getInstancia()
//			.cargarNuevaVista(
//					this.getClass(),
//					RutasVistas.VISTA_ABM_RUTEJ,
//					new ControladorVistaABMRutinaEjercicio()
//		);
    	
    	ControladorVistaABMRutinaEjercicio CVABMRE =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABMRE.establecerRutina(regRut);
//    	CVABMRE.ejecutarActualizacionTablaVista();
    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					BTAbrir,
					"Rutina"
		);
    }
    
    @FXML
    private void eventoBTAtras(ActionEvent event) {
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_INICIO
    		);
    	
    	ControladorVistaInicio CVI = 
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVI.iniciar();
    	
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cambiarVista(
    				BTAtras,
    				"Inicio"
    		);
    }
    
    @FXML
    private void initialize() {
    	this.actualizarTabla(this.srut.obtenerTodasLasRutinas());
    }
}
