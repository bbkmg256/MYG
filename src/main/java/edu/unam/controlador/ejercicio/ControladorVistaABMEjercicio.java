/*
 * CLASE CONTROLADORA PARA ABM EJERCICIO
 */

package edu.unam.controlador.ejercicio;

import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import utilidades.NavegadorDeVistas;
import utilidades.RutasVistas;

import java.util.List;
import java.util.Optional;

import edu.unam.controlador.ControladorVistaInicio;
import edu.unam.modelo.Ejercicio;
import edu.unam.servicio.EjercicioServicio;
import edu.unam.modelo.GrupoMuscular;

/*
 * 
 * NOTAS:
 * 
 * FUNCIONALIDAD DE BUSCAR, DESACTIVADA.
 * 
 */

public class ControladorVistaABMEjercicio {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTAtras;

//    @FXML
//    private Button BTBuscar;

    @FXML
    private Button BTCrear;

    @FXML
    private Button BTEliminar;

    @FXML
    private Button BTModificar;

    @FXML
    private TableView<Ejercicio> TVEjercicio;

    @FXML
    private TableColumn<Ejercicio, String> TCGM; // ALMACENARIA EL OBJETO GM DE EJERCICIO

    @FXML
    private TableColumn<Ejercicio, Integer> TCID;

    @FXML
    private TableColumn<Ejercicio, String> TCNE;

    @FXML
    private TextField txtBuscar;
    
    private EjercicioServicio sejer = new EjercicioServicio();

    
    // METODOS Y EVENTOS //
    private <S, T> void asignarValoresColumnas(TableColumn<S, T> columna, String valor) {
    	columna.setCellValueFactory(new PropertyValueFactory<>(valor));
    }

    // LA LISTA DE ENTIDADES LA TOMA CADA VEZ QUE SE LLAMA A ESTE METODO.
    private void actualizarTabla(List<Ejercicio> listaEjercicios) {
    	TVEjercicio.getItems().clear(); // Limpia los items de la tabla
    	TVEjercicio.getItems().addAll(listaEjercicios); // Añade items en la lista de la tabla
    	TVEjercicio.refresh();
    	
    	this.asignarValoresColumnas(this.TCID, "idEjercicio");
    	this.asignarValoresColumnas(this.TCNE, "nombreEjercicio");
    	
    	// COMO ES UN OBJETO, SE FORMATEA SU RENDERIZADO EN LA TABLA POR SEPARADO CON UN LAMBDA
    	this.TCGM.setCellValueFactory(cellData -> {
    		// EL GM NUNCA DEBERIA SER NULL
            GrupoMuscular gm = cellData.getValue().getGrupoMuscular(); // EL BACKEND TIENE CARGA PEREZOSA, ESTO DA PROBLEMAS!! (SOLUCIONADO)
            
            if (gm == null) {
            	return null;
            }
            
            return new SimpleStringProperty(gm.getNombreGrupo());
//          return (gm != null) ? gm.getNombreGrupo() : null;
            // (QUE ENFERMIZO QUE ES ESTO, DIOS!!!)
        });
    }
    
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
    
    @FXML
    void eventoBTAtras(ActionEvent event) {
    	NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_INICIO
			);
    	
    	ControladorVistaInicio CVI = 
    			NavegadorDeVistas
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVI.iniciar();
    	
    	NavegadorDeVistas
			.getInstancia()
			.cambiarVista(
					BTAtras,
					"Inicio"
			);
    }

    @FXML
    void eventoBTCrear(ActionEvent event) {
    	NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_CARGA_MODIF_EJER,
					new ControladorVistaCargaEjercicio()
			);
    	NavegadorDeVistas
			.getInstancia()
			.cambiarVista(
					BTCrear,
					"Crear ejercicio"
			);
    }

    @FXML
    void eventoBTEliminar(ActionEvent event) {
    	Ejercicio regEjer = TVEjercicio.getSelectionModel().getSelectedItem();
    	
    	if (regEjer == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!", "OPERACION FALLIDA",
    				"Seleccione un ejercicio..."
    		);
    		System.out.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
    		return;
    	}
    	
    	// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Eliminacion de ejercicio",
    			"OPERACION ELIMINAR", "Realmente desea eliminar el ejercicio?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Eliminación cancelada!"); // LOG
        	return;
    	}
    	
    	System.out.println("[ ! ] > Se eliminará el ejercicio!"); // LOG
    	this.sejer.eliminarEjercicio(regEjer.getIdEjercicio());
    	
    	if (this.sejer.obtenerEjercicio(regEjer.getIdEjercicio()) != null) {
			this.lanzarMensaje(
					AlertType.ERROR, "Error!", "OPERACION FALLIDA",
					"La operacion de eliminacion no se ejecuto correctamente..."
			);
    		return;
    	}
    	
    	this.actualizarTabla(this.sejer.obtenerTodosLosEjerciciosYSusObjetos());
    }

    @FXML
    void eventoBTModificar(ActionEvent event) {
    	Ejercicio regEjer = this.TVEjercicio.getSelectionModel().getSelectedItem();
    	
    	if (regEjer == null) {
			this.lanzarMensaje(
					AlertType.ERROR, "Error", "OPERACION FALLIDA",
					"Seleccione un ejercicio antes de modificar..."
			);
			System.out.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
			return;
		}
    	
    	NavegadorDeVistas
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_CARGA_MODIF_EJER, // VISTA REUTILIZADA
    				new ControladorVistaModificarEjercicio()
    		);
    	
    	// PASA EL OBJETO AL CONSTR. PARA MODIFICARLO
    	ControladorVistaModificarEjercicio controladorModificarEjercicio = NavegadorDeVistas.getInstancia().obtenerControladorDeNuevaVista();
    	controladorModificarEjercicio.establecerEjercicioParaModificar(regEjer);
    	
    	NavegadorDeVistas
    		.getInstancia()
    		.cambiarVista(
    				BTModificar,
    				"Modificar ejercicio"
    		);
    }
    
    @FXML
    void eventoBTBuscar(ActionEvent event) {
    	
    }
    
    @FXML
    void initialize() {
    	this.actualizarTabla(this.sejer.obtenerTodosLosEjerciciosYSusObjetos());
    }
}
