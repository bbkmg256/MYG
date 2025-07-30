/*
 * CLASE CONTROLADORA PARA VISTA ABM GESTION ENTRENAMIENTO
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import edu.unam.modelo.Ejercicio;
import edu.unam.modelo.Entrenamiento;
import edu.unam.modelo.Rutina;
import edu.unam.modelo.RutinaEjercicio;
import edu.unam.servicio.RutinaEjercicioServicio;
import edu.unam.modelo.RutinaEntrenamiento;
import edu.unam.servicio.RutinaEntrenamientoServicio;
import edu.unam.servicio.RutinaServicio;

/*
 * 
 * NOTAS:
 * 
 * HAY QUE PROBAR ESTA CLASE!
 * 
 */

public class ControladorVistaABMRutinaEntrenamiento {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
	@FXML
	private Button BTAgregar;

	@FXML
	private Button BTAtras;

	@FXML
	private Button BTEliminar;

	@FXML
	private Button BTVerContenido;

	// TABLA DE CONTENIDO DE RUTINAS
	@FXML
	private TableView<RutinaEjercicio> TVRutEj; // <- TABLA 1
	@FXML
	private TableColumn<RutinaEjercicio, String> TCEj;
	@FXML
	private TableColumn<RutinaEjercicio, String> TCRep;
	@FXML
	private TableColumn<RutinaEjercicio, String> TCSe;

	// TABLA DE RUTINAS DISPONIBLES
	@FXML
	private TableView<Rutina> TVLR; // <- TABLA 2
//	@FXML
//	private TableColumn<Rutina, Integer> TCIDLR;
	@FXML
	private TableColumn<Rutina, String> TCNombLR;

	// TABLAS DE RUTINAS "CARGADAS" AL ENTENAMIENTO
	@FXML
	private TableView<RutinaEntrenamiento> TVRC; // <- TABLA 3
//	@FXML
//	private TableColumn<RutinaEntrenamiento, Integer> TCIDRC;
	@FXML
	private TableColumn<RutinaEntrenamiento, String> TCNombRC;

    
    private RutinaEjercicioServicio sre;

    private RutinaServicio sru;
    
    private RutinaEntrenamientoServicio sruent;
    
    private Entrenamiento ent;
    
    private List<Rutina> listaRutina;
    
    private List<RutinaEntrenamiento> listaRE;


    // METODOS Y EVENTOS //
    private void inicializarDatos() {
    	this.sre = new RutinaEjercicioServicio();
    	this.ent = AlmacenadorDeEntidadesSingleton.getInstancia().getEntrenamiento();
    	this.sru = new RutinaServicio();
    	this.sruent = new RutinaEntrenamientoServicio();
    	
    	this.listaRE = new ArrayList<RutinaEntrenamiento>();
    	this.listaRutina = new ArrayList<Rutina>();
    	
//    	System.out.print();
    	
    	this.listaRE.addAll(this.sruent.obtenerTodosLosREYSusObjetos(this.ent));
    	this.listaRutina.addAll(this.sru.obtenerTodasLasRutinas());
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
    
    private <S, T> void asignarValoresColumnas(TableColumn<S, T> columna, String valor) {
    	columna.setCellValueFactory(new PropertyValueFactory<>(valor));
    }
    
    private <T> void actualizarTabla(TableView<T> tabla, List<T> lista) {
    	tabla.getItems().clear();
    	tabla.getItems().addAll(
    			lista
    	);
    	
    	String idTabla = tabla.getId();
    	
    	switch (idTabla) {
			case "TVRutEj": {
				this.TCEj.setCellValueFactory(celllData -> {
					Ejercicio ej = celllData.getValue().getEjercicio();
					
					return (ej == null) ? null : new SimpleStringProperty(ej.getNombreEjercicio());
				});
				
				this.asignarValoresColumnas(this.TCSe, "serie");
				this.asignarValoresColumnas(this.TCRep, "repeticion");
				break;
			}
			
			case "TVLR": {
//				this.asignarValoresColumnas(this.TCIDLR, "idRutina");
				this.asignarValoresColumnas(this.TCNombLR, "nombreRutina");
				break;
			}
			
			case "TVRC": {
//				this.asignarValoresColumnas(this.TCIDRC, "id");
				this.TCNombRC.setCellValueFactory(cellData -> {
					Rutina rt = cellData.getValue().getRutina();
					
					return (rt == null) ? null: new SimpleStringProperty(rt.getNombreRutina());
				});
				break;
			}
			
			default: {
				throw new IllegalArgumentException("[ ERROR ] > " + idTabla + " no es valido!");	
			}
		}
    	
    }
    
    public void iniciar() {
    	this.inicializarDatos();
    	
    	// FILTRA LOS ELEMENTOS VISIBLES EN LA TABLA DE RUTINAS POSIBLES PARA CARGAR		   //
    	// O ASOCIAR AL ENTRENAMIENTO (LAS RUTINAS QUE SE CARGAN SE VEN EN LA TABLA IZQUIERDA) //
    	List<Rutina> listaAux = new ArrayList<Rutina>();
    	listaAux.addAll(this.listaRutina);
    	for (RutinaEntrenamiento re : this.listaRE) {
    		for (Rutina ru : listaAux) {
    			if (ru.getIdRutina() == re.getRutina().getIdRutina()) {
    				this.listaRutina.remove(ru);
    			}
    		}
    	}
//    	this.listaRutina = listaAux;
    	
//    	System.out.println(this.listaRutina);
    	
    	this.actualizarTabla(this.TVLR, this.listaRutina);
    	this.actualizarTabla(this.TVRC, this.listaRE);
    	
    }
    
    @FXML
    private void eventoBTAgregar(ActionEvent event) {
    	Rutina regRutEj = this.TVLR.getSelectionModel().getSelectedItem();
    	
    	if (regRutEj == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, 
    				"Error!", 
    				"ERROR DE OPERACION", 
    				"Seleccione una rutina para cargar..."
    		);
    		System.err.println(
    				"[ ERROR ] > Seleccione un registro para continuar!"
    		); // LOG
    		return;
    	}
    	
    	RutinaEntrenamiento re =
    			new RutinaEntrenamiento(regRutEj, this.ent);
    	
    	this.sruent.crearRE(re);
    	
    	if (this.sruent.obtenerRE(re.getId()) == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE OPERACION",
    				"Algo falló al agregar la rutina al entrenamiento..."
    		);
    		System.out.println("[ ERROR ] > Fallo al agregar la rutina!"); // LOG
    		return;
    	}
    	
    	this.listaRutina.remove(re.getRutina());
    	this.listaRE.add(re);
    	this.actualizarTabla(this.TVLR, this.listaRutina);
    	this.actualizarTabla(this.TVRC, this.listaRE);
    }

    @FXML
    private void eventoBTAtras(ActionEvent event) {
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_ENT
			);
    	
    	ControladorVistaABMEntrenamiento CVABME =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABME.iniciar();
    	
		NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					this.BTAtras,
					"Entrenamiento"
			);
    }

    @FXML
    private void eventoBTEliminar(ActionEvent event) {
    	RutinaEntrenamiento rutEnt = this.TVRC.getSelectionModel().getSelectedItem();
    	
    	if (rutEnt == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, 
    				"Error!", 
    				"ERROR DE OPERACION", 
    				"Seleccione una rutina cargada para eliminar..."
    		);
    		System.err.println(
    				"[ ERROR ] > Seleccione un registro para continuar!"
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
    	
    	System.out.println("[ ! ] > Se eliminará la rutina cargada al entrenamiento!"); // LOG
    	
    	this.sruent.eliminarRE(rutEnt.getId());
    	
    	if (this.sruent.obtenerRE(rutEnt.getId()) != null) {
//    		this.lanzarMensaje(
//    				AlertType.ERROR, 
//    				"Exito!", 
//    				"OPERACION REALIZADA", 
//    				"Seleccione una rutina cargada para eliminar..."
//    		);
    		System.err.println(
    				"[ ERROR ] > Problemas al quitar la rutina del entrenamiento!"
    		); // LOG
    		return;
    	}
    	
    	// COSA TRAMBOLICA QUE MANIPULA LAS LISTAS PARA LAS TABLAS RUTINA Y RUTINAENTRENAMIENTO
//    	this.listaRE = this.sruent.obtenerTodosLosREYSusObjetos();
    	this.listaRE.remove(rutEnt);
    	this.listaRutina.add(rutEnt.getRutina());
    	this.actualizarTabla(this.TVLR, this.listaRutina);
    	this.actualizarTabla(this.TVRC, this.listaRE);
//    	this.actualizarTabla(this.TVRC, this.sruent.obtenerTodosLosRE());
    }
    
    @FXML
    void eventoBTVerContenido(ActionEvent event) {
//    	this.actualizarTabla(this.TVRutEnt, this.sre.obtenerTodasLasRutinaEjercicio, 1);
    	Rutina regRutina = this.TVLR.getSelectionModel().getSelectedItem();
    	
    	if (regRutina == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, 
    				"Error!", 
    				"ERROR DE OPERACION", 
    				"Seleccione una rutina para ver su contenido..."
    		);
    		System.err.println(
    				"[ ERROR ] > Seleccione un registro para continuar!"
    		); // LOG
    		return;
    	}
    	
    	List<RutinaEjercicio> listaRutEj = 
    			this.sre.obtenerTodoElContenidoDesdeUnaRutina(
    					regRutina.getIdRutina()
    			);
    	this.actualizarTabla(this.TVRutEj, listaRutEj);
    }

    @FXML
    private void initialize() {
    	
    }
}
