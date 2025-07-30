/*
 * ESTA CLASE ES UN CONTROLADOR PARA LA SUBVISTA DENTRO DE RUTINA, QUE MODIFICA,
 * CREA O ELIMINA, TODOS LOS ELEMENTOS QUE LA COMPONEN (EJERCICIO, SERIE, REPETICION),
 * TENIENDO EN CUENTA QUE TODO ESTO SE ENCUENTRA EN LA ENTIDAD 'RutinaEjercicio'.
 */

package edu.unam.controlador.rutina;

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
import edu.unam.modelo.RutinaEjercicio;
import edu.unam.servicio.RutinaEjercicioServicio;
import java.util.List;
import java.util.Optional;

import edu.unam.modelo.Ejercicio;
import edu.unam.modelo.Rutina;

/*
 * NOTA:
 * 
 * FALTA LA FUNCIONALIDAD PARA ELIMINAR Y MODIFICAR LOS COMPONENTES DE LA RUTINA
 * (EJERCICIO, SERIE, REPETICION)
 * 
 * FUNCIONALIDAD DE ELIMINACION LISTA, FALTA LA DE MODIFICAR.
 * 
 */

public class ControladorVistaABMRutinaEjercicio {
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
    private TableView<RutinaEjercicio> TVRE;

    @FXML
    private TableColumn<RutinaEjercicio, String> TCEjercicio;

//    @FXML
//    private TableColumn<RutinaEjercicio, Integer> TCID;

    @FXML
    private TableColumn<RutinaEjercicio, String> TCRep;

    @FXML
    private TableColumn<RutinaEjercicio, String> TCSerie;
    
    private Rutina rutina = null;
    
    private RutinaEjercicioServicio sre = new RutinaEjercicioServicio();

    
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
    private void actualizarTabla(List<RutinaEjercicio> listaRE) {
    	this.TVRE.getItems().clear(); // Limpia los items de la tabla
    	this.TVRE.getItems().addAll(listaRE); // Añade items en la lista de la tabla
//    	TVTablaClientes.refresh();
    	
//    	this.asignarValoresColumnas(this.TCID, "id");
    	this.asignarValoresColumnas(this.TCSerie, "serie");
    	this.asignarValoresColumnas(this.TCRep, "repeticion");

    	// ES UN OBJETO, SE DEBE RENDERIZAR POR SEPARADO
    	this.TCEjercicio.setCellValueFactory(cellData -> {
            Ejercicio ej = cellData.getValue().getEjercicio();
            
            if (ej == null) {
            	return null;
            }
            
            return new SimpleStringProperty(ej.getNombreEjercicio());
        });
    }
    
    public void establecerRutina(Rutina regRut) {
    	this.rutina = regRut;
    	this.actualizarTabla(
    			this.sre.obtenerTodoElContenidoDesdeUnaRutina(
    					this.rutina.getIdRutina()
    			)
    	);
//    	System.out.println("A");
    }
    
//    public void ejecutarActualizacionTablaVista() {
//    	this.actualizarTabla(this.sre.obtenerTodoElContenidoDesdeUnaRutina(this.rutina.getIdRutina()));
//    }
    
    @FXML
    private void eventoBTCrear(ActionEvent event) {
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_CARGA_MODIF_RUTEJ,
					new ControladorVistaCargarRutinaEjercicio()
			);
    	
    	ControladorVistaCargarRutinaEjercicio CVCRE =
    			NavegadorDeVistasSingleton.getInstancia().obtenerControladorDeNuevaVista();
    	
    	CVCRE.establecerRutina(this.rutina);
    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					this.BTCrear,
					"Crear rutina"
			);
    }

    @FXML
    private void eventoBTModificar(ActionEvent event) {
    	RutinaEjercicio regRE = this.TVRE.getSelectionModel().getSelectedItem();
    	
    	if (regRE == null) {
			this.lanzarMensaje(
					AlertType.ERROR, "Error", "OPERACION FALLIDA",
					"Seleccione un elemento de rutina antes de modificar..."
			);
			System.out.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
			return;
		}
    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_CARGA_MODIF_RUTEJ,
					new ControladorVistaModificarRutinaEjercicio()
			);
    	
    	ControladorVistaModificarRutinaEjercicio CVCRE =
    			NavegadorDeVistasSingleton.getInstancia().obtenerControladorDeNuevaVista();
    	
    	CVCRE.establecerRutinaYRE(this.rutina, regRE);
    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					this.BTModificar,
					"Modificar rutina"
			);
    }

    @FXML
    private void eventoBTEliminar(ActionEvent event) {
    	RutinaEjercicio regRut = this.TVRE.getSelectionModel().getSelectedItem();
    	
    	if (regRut == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!", "OPERACION FALLIDA",
    				"Seleccione un elemento de la rutina..."
    		);
    		System.out.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
    		return;
    	}
    	
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Eliminacion de elemento de rutina",
    			"OPERACION ELIMINAR", "Realmente desea eliminar el elemento de rutina?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Eliminación cancelada!"); // LOG
        	return;
    	}
    	
    	System.out.println("[ ! ] > Se eliminará el elemento!"); // LOG
    	this.sre.eliminarRE(regRut.getId());
    	
    	if (this.sre.obtenerRE(regRut.getId()) != null) {
			this.lanzarMensaje(
					AlertType.ERROR, "Error!", "OPERACION FALLIDA",
					"La operacion de eliminación no se ejecuto correctamente..."
			);
    		return;
    	}
    	
    	this.actualizarTabla(
    			this.sre.obtenerTodoElContenidoDesdeUnaRutina(
    					this.rutina.getIdRutina()
    			)
    	);
    }
    
    @FXML
    private void eventoBTAtras(ActionEvent event) {
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_RUTINA
			);
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					this.BTAtras,
					"Rutina"
			);
    }

    @FXML
    private void initialize() {
//    	System.out.println("B");
    	if (this.rutina == null) {
    		return;
    	}    	
    	this.actualizarTabla(
    			this.sre.obtenerTodoElContenidoDesdeUnaRutina(
    					this.rutina.getIdRutina()
    			)
    	);
    }
}
