/*
 * CLASE CONTROLADORA PARA VISTA CARGA GM (ALTA - BAJA - MODIFICACION)
 */

package edu.unam.controlador.grupoMuscular;

// LIBRERIAS
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;

import java.util.Optional;

import edu.unam.controlador.ControladorVistaInicio;
import edu.unam.modelo.GrupoMuscular;
import edu.unam.servicio.GMServicio;

/*
 * 
 */

public class ControladorVistaABMGM {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTAgregar;

    @FXML
    private Button BTEliminar;

    @FXML
    private Button BTModificar;
    
    @FXML
    private Button BTAtras;

    @FXML
    private TableColumn<GrupoMuscular, Integer> TCId;

    @FXML
    private TableColumn<GrupoMuscular, String> TCNombre;

    @FXML
    private TableView<GrupoMuscular> TVTodo;
    
    private GMServicio gms = new GMServicio();

    
    // METODOS Y EVENTOS //
    private <S, T> void asignarValoresColumnas(TableColumn<S, T> columna, String valor) {
    	columna.setCellValueFactory(new PropertyValueFactory<>(valor));
    }

    // LA LISTA DE ENTIDADES QUE SE CARGA A LA TABLA, SE ACTUALIZA CON
    // CADA LLAMADO QUE HACE HACE A ESTE MISMO METODO.
    private void actualizarTabla() {
    	TVTodo.getItems().clear();
    	TVTodo.getItems().addAll(gms.obtenerTodosLosGM());
    	TVTodo.refresh();
    	
    	this.asignarValoresColumnas(this.TCId, "idGM");
    	this.asignarValoresColumnas(this.TCNombre, "nombreGrupo");
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
    
    @FXML
    private void eventoBTAgregar(ActionEvent event) {    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_CARGA_GM, // SE REUTILIZA LA VISTA DE CARGA PARA LA MODIFICACION
					new ControladorVistaCargarGM()
			);
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cambiarVista(BTAgregar, "Cargar GM");
    }

    @FXML
    private void eventoBTEliminar(ActionEvent event) {    	
    	GrupoMuscular regGM = this.TVTodo.getSelectionModel().getSelectedItem();
    	
    	if (regGM == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error", "OPERACION FALLIDA",
    				"Seleccione un grupo muscular..."
    		);
    		System.out.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
    		return;
    	}
    	
    	// CREO QUE ESTO ESTÁ MEJOR
    	if (!this.gms.obtenerListaDeEjercicios(regGM.getIdGM()).isEmpty()) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"IMPOSIBLE ELIMINAR REGISTRO",
    				"Este grupo muscular es usado o está relacionado con por lo menos 1 ejercicio..."
    		);
    		System.out.println(
    				"[ ERROR ] > No se puede eliminar el registro " +
    				"por que tiene registros hijos asociados!"
    		); // LOG
    		return;
    	}
    		
    	Optional<ButtonType> resultado = this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Eliminacion de grupo muscular",
    			"OPERACION ELIMINAR", "Realmente desea eliminar el grupo muscular?"
    	);
    	
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Eliminación cancelada!"); // LOG
    		return;
    	}
    	
    	System.out.println("[ ! ] > Se eliminará el cliente!"); // LOG
    	gms.eliminarGM(regGM.getIdGM());
    	this.actualizarTabla();
//    	System.out.println("[ EXITO ] > Grupo muscular eliminado correctamente!"); // LOG
    }

    @FXML
    private void eventoBTModificar(ActionEvent event) {
    	GrupoMuscular regGM = this.TVTodo.getSelectionModel().getSelectedItem();
    	
    	if (regGM == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error", "OPERACION FALLIDA",
    				"Seleccione un grupo musucualr antes de modificar..."
    		);
    		System.out.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
    		return;
    	}
    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_CARGA_GM, // SE REUTILIZA LA VISTA DE CARGA PARA LA MODIFICACION
					new ControladorVistaModificarGM()
			);

    	ControladorVistaModificarGM cvmgm = NavegadorDeVistasSingleton.getInstancia().obtenerControladorDeNuevaVista();
    	cvmgm.establecerGMParaModificar(regGM);
    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					BTModificar,
					"Modificar GM"
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
    	this.actualizarTabla();
    }
}


// CODIGO SIN USAR //
//private static Stage ventana;

//private void cambiarVista(String rutaVista, String tituloDeVentana) {
//try {
//	// SE CARGA LA VISTA A UN TIPO PARENT POR QUE NO SE SABE EL TIPO DE CONTENEDOR DE LA VISTA PADRE/PRINCIPAL
//	Parent nuevaVista = FXMLLoader.load(this.getClass().getResource(rutaVista));
//	Scene escena = new Scene(nuevaVista);
//	
////	ventana.setScene(escena);
////	ventana.setOnCloseRequest(e -> ventana = null); // AL CERRAR LA VENTANA, EL ESTATICO "VENTANA" SE VUELVE NULO
////	// ventana.setAlwaysOnTop(true);
////	ventana.show();
//	
//	// OBTIENE EL STAGE O VENTANA ACTUAL DESDE ALGUNO DE LOS NODOS QUE CONTIENE
//	Stage ventana = (Stage) BTCancelar.getScene().getWindow();
//	ventana.setScene(escena);
//	ventana.setTitle(tituloDeVentana);
//	ventana.setResizable(false);
//	ventana.show();
//	
////	// CIERRA LA VENTANA ACTUAL
////	ventanaActual = (Stage) this.BTCancelar.getScene().getWindow();
////	this.ventanaActual.close();
//} catch (IOException e) {
//	System.out.println(e);
//}  
//}

//@FXML
//private void eventoBTAgregar(ActionEvent event) {
//	// SI YA HAY UNA VENTANA DE ESTE TIPO, SOLO LA TRAE AL FRENTE
//if (ventana != null && ventana.isShowing()) {
//	ventana.toFront();
//	return;
//}
//
//try {
//	FXMLLoader cargaVistaCargarGM = new FXMLLoader(this.getClass().getResource(RutasVistas.VISTA_CARGA_GM));
//	AnchorPane raizVistaCargarGM = cargaVistaCargarGM.load();
//	Scene escenaCargarGM = new Scene(raizVistaCargarGM);
//	ventana = new Stage();
//	ventana.setTitle("Agregue un Grupo Muscular");
//	ventana.setResizable(false);
//	ventana.setScene(escenaCargarGM);
//	ventana.setOnCloseRequest(e -> ventana = null); // AL CERRAR LA VENTANA, EL ESTATICO "VENTANA" SE VUELVE NULO
//	// ventana.setAlwaysOnTop(true);
//	ventana.show();
//} catch (IOException e) {
//	System.out.println(e);
//}
//}

//Cliente regCli = this.TVTablaClientes.getSelectionModel().getSelectedItem();
//
//if (regCli == null) {
//	this.lanzarMensaje(
//			AlertType.ERROR, "Error", "OPERACION FALLIDA",
//			"Seleccione un cliente antes de modificar..."
//	);
//	System.out.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
//	return;
//}
//
//
//// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
//Optional<ButtonType> resultado =  this.lanzarMensaje(
//		AlertType.CONFIRMATION, "Eliminacion de cliente",
//		"OPERACION ELIMINAR", "Realmente desea eliminar el cliente?"
//);
//
//
//// CONFIRMAR O DENEGAR OPERACION
//if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
//	System.out.println("[!] > Eliminación cancelada!"); // LOG
//	return;
//}
//
//System.out.println("[!] > Se eliminará el cliente!"); // LOG
//cs.eliminarCliente(regCli.getDni());
//this.actualizarTabla();
//System.out.println("[ EXITO ] > Cliente eliminado correctamente!"); // LOG

//this.cambiarVista(RutasVistas.VISTA_INICIO, "Inicio");
//NavegadorDeVistas.cambiarVista(BTCancelar, this.getClass(), RutasVistas.VISTA_INICIO, "Inicio");

//NavegadorDeVistas
//.getInstancia()
//.cargarNuevaVista(this.getClass(), RutasVistas.VISTA_CARGA_GM);