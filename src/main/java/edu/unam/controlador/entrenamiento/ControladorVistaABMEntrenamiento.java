/*
 * CLASE CONTROLADORA ABM ENTRENAMIENTO
 */

package edu.unam.controlador.entrenamiento;

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
import utilidades.AlmacenadorDeEntidadesSingleton;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;
import edu.unam.modelo.Entrenamiento;
import java.time.LocalDate;
import java.util.Optional;
import edu.unam.servicio.EntrenamientoServicio;
import edu.unam.controlador.ControladorVistaInicio;
import edu.unam.modelo.Cliente;
import edu.unam.modelo.Tutor;
//import edu.unam.modelo.Rutina;

/*
 * 
 * NOTAS:
 * 
 * NO PARAMETRIZO EL METODO DE ACTUALIZACION DE LA TABLA DE DATOS,
 * POR QUE LA VISTA NO EMPLEA UN BUSCADOR, POR ENDE NO SE DEBE
 * RENDERIZAR LOS DATOS DE FORMA PARAMETRIZADA O CON DATOS
 * ESPECIFICOS (YO SE QUE ME ENTIENDO XD)
 * 
 */

public class ControladorVistaABMEntrenamiento {
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
    private Button BTAbrir;
    
    @FXML
    private Button BTFinalizarEnt; // PERMITE FINALIZAR EL ENTRENAMIENTO Y PUNTUAR AL TUTOR

    @FXML
    private TableView<Entrenamiento> TVEntrenamiento;

    @FXML
    private TableColumn<Entrenamiento, String> TCCli;

    @FXML
    private TableColumn<Entrenamiento, LocalDate> TCFI;

    @FXML
    private TableColumn<Entrenamiento, LocalDate> TCFF;

    @FXML
    private TableColumn<Entrenamiento, Integer> TCID;

    @FXML
    private TableColumn<Entrenamiento, Integer> TCPT;

//    @FXML
//    private TableColumn<Entrenamiento, String> TCRu;

    @FXML
    private TableColumn<Entrenamiento, String> TCTu;
    
    @FXML
    private TableColumn<Entrenamiento, String> TCEstado;
    
    private EntrenamientoServicio sentre;

    
    // METODOS Y EVENTOS //
    private void inicializarDatos() {
    	sentre = new EntrenamientoServicio();
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
    	TVEntrenamiento.getItems().clear();
    	TVEntrenamiento
    		.getItems()
    			.addAll(
    					this.sentre.obtenerTodosLosEntrenamientosYSusObjetos()
    			);
//    	TVEntrenamiento.refresh();
    	
    	this.asignarValoresColumnas(this.TCID, "idEntrenamiento");
    	this.asignarValoresColumnas(this.TCFI, "fechaInicio");
    	this.asignarValoresColumnas(this.TCFF, "fechaFin");
    	this.asignarValoresColumnas(this.TCPT, "puntaje");
    	this.asignarValoresColumnas(this.TCEstado, "estado");
    	
    	// RENDERIZADO DE OBJETOS DE ENTRENAMIENTO //
    	// CLIENTE
    	this.TCCli.setCellValueFactory(cellData -> {
    		Cliente ent = cellData.getValue().getCliente();
    		return (ent == null) ? null : new SimpleStringProperty(ent.getNombre());
    	});
    	
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
    
    public void iniciar() {
    	this.inicializarDatos();
    	this.actualizarTabla();
    }

    @FXML
    private void eventoBTCrear(ActionEvent event) {
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_CARGA_MODIF_ENT,
	    			this.BTCrear,
	    			new ControladorVistaCargaEntrenamiento()
	    	);
    	
    	ControladorVistaCargaEntrenamiento CVCE =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVCE.iniciar();
	
		NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTCrear,
	    			"Cargar entrenamiento"
	    	);
    }

    @FXML
    private void eventoBTEliminar(ActionEvent event) {
    	Entrenamiento regEnt = this.TVEntrenamiento.getSelectionModel().getSelectedItem();
    	
    	if (regEnt == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, 
    				"Error!", 
    				"ERROR DE OPERACION", 
    				"Seleccione un entrenamiento para eliminar..."
    		);
    		System.err.println(
    				"[ ERROR ] > Seleccione un entrenamiento para eliminar!"
    		); // LOG
    		return;
    	}
    	
    	if (!this.sentre.obtenerListaDeSeguimientos(regEnt.getIdEntrenamiento()).isEmpty()) {
    		this.lanzarMensaje(
    				AlertType.ERROR, 
    				"Error!", 
    				"IMPOSIBLE ELIMINAR REGISTRO", 
    				"Este entrenamiento es usado o está asociado con por lo menos 1 seguimiento..."
    		);
    		System.err.println(
    				"[ ERROR ] > No se puede eliminar el registro " +
    				"por que tiene 1 o mas registros hijos asociados!"
    		); // LOG
    		return;
    	}
    	
    	// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Eliminacion de ejercicio",
    			"OPERACION ELIMINAR", "Realmente desea eliminar el entrenamiento?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Eliminación cancelada!"); // LOG
        	return;
    	}
    	
    	this.sentre.eliminarEntrenamiento(regEnt.getIdEntrenamiento());
    	
    	if (this.sentre.obtenerEntrenamiento(regEnt.getIdEntrenamiento()) != null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, 
    				"Error!", 
    				"ERROR DE OPERACION", 
    				"Seleciones un entrenamiento para eliminar..."
    		);
    		System.err.println(
    				"[ ERROR ] > Operacion de eliminación fallida!"
    		); // LOG
    		return;
    	}
    	this.actualizarTabla();
    }

    @FXML
    private void eventoBTModificar(ActionEvent event) {
    	Entrenamiento regEnt = 
    			TVEntrenamiento
    				.getSelectionModel()
    				.getSelectedItem();
    	
    	if (regEnt == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error",
    				"ERROR DE OPERACION",
    				"Debe seleccionar un registo antes de modificar..."
    		);
    		System.err.println(
    				"[ ERROR ] > Seleccione un registro para de modificar!"
    		);
    		return;
    	}
    	
    	AlmacenadorDeEntidadesSingleton
    		.getInstancia()
    		.setEntrenamiento(regEnt);
    	
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_CARGA_MODIF_ENT,
	    			this.BTModificar,
	    			new ControladorVistaModifEntrenamiento()
	    	);
    	
    	ControladorVistaModifEntrenamiento CVCME =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	// DESPUES ARREGLAR ESTO QUE ESTA HORRIBLE
//    	CVCME.establecerEntrenamiento(regEnt);
    	CVCME.iniciar();
    	
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTModificar,
	    			"Modificar entrenamiento"
	    	);
    }
    
    @FXML
    private void eventoBTAbrir(ActionEvent event) {
    	Entrenamiento regEnt = TVEntrenamiento.getSelectionModel().getSelectedItem();
    	
    	if (regEnt == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error",
    				"ERROR DE OPERACION",
    				"Debe seleccionar un registo antes de modificar..."
    		);
    		System.err.println(
    				"[ ERROR ] > Seleccione un registro para de modificar!"
    		);
    		return;
    	}
    	
    	AlmacenadorDeEntidadesSingleton.getInstancia().setEntrenamiento(regEnt);
    	System.out.println(regEnt);
    	
//    	NavegadorDeVistas
//	    	.getInstancia()
//	    	.cargarNuevaVista(
//	    			this.getClass(),
//	    			RutasVistas.VISTA_ABM_RUT_ENT
//	    	);
    	
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_ABM_RUT_ENT_B,
	    			this.BTAbrir
	    	);
	
    	ControladorVistaABMRutinaEntrenamiento CVAMBRE = 
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVAMBRE.iniciar();
		
		NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTAbrir,
	    			"Gestion del entrenamiento"
	    	);
    }

    @FXML
    private void eventoBTAtras(ActionEvent event) {
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_INICIO,
	    			this.BTAtras
	    	);
    	
    	ControladorVistaInicio CVI = 
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVI.iniciar();
    	
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTAtras,
	    			"Inicio"
	    	);
    }
    
    @FXML
    void eventoBTFinalizarEnt(ActionEvent event) {
    	Entrenamiento regEnt = this.TVEntrenamiento.getSelectionModel().getSelectedItem();
    	
    	if (regEnt == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, 
    				"Error!", 
    				"ERROR DE OPERACION", 
    				"Seleccione un entrenamiento..."
    		);
    		System.err.println(
    				"[ ERROR ] > Seleccione un entrenamiento para eliminar!"
    		); // LOG
    		return;
    	}
    	
    	// SI EL ENTRENAMIENTO YA FINALIZÓ, NO SE PUEDE FINALIZARLO NUEVAMENTE
    	if (regEnt.getEstado().equals("Finalizado")) {
    		this.lanzarMensaje(
    				AlertType.ERROR, 
    				"Error!", 
    				"ERROR DE OPERACION", 
    				"Imposible finalizar el entrenamiento, ya que el mismo está finalizado..."
    		);
    		System.err.println(
    				"[ ERROR ] > No se puede finalizar un entrenamiento que ya está finalizado!"
    		); // LOG
    		return;
    	}
    	
    	// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Gestión de estado del entrenamiento",
    			"FINALIZAR ENTRENAMIENTO", "Realmente desea finalizar el entrenamiento?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Eliminación cancelada!"); // LOG
        	return;
    	}
    	
    	AlmacenadorDeEntidadesSingleton
    		.getInstancia()
    		.setEntrenamiento(regEnt);
    	
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_PUNTUAR_TUTOR,
    				this.BTFinalizarEnt
    		);
    	
    	ControladorVistaPuntuarTutor CVPT =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVPT.iniciar();
    	
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cambiarVista(
    				this.BTFinalizarEnt,
    				"Puntuar tutor"
    		);
    }
    
    @FXML
    private void initialize() {
    	// VER DESPUES QUE SE PODRÁ, Y QUE NO, MODIFICAR
//    	this.BTModificar.setVisible(false);
    }
}
