/*
 * CLASE CONTROLADORA PARA VISTA ALTA - MODIFICAR - ELIMINAR TUTOR
 */

package edu.unam.controlador.tutor;

// LIBRERIAS
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import utilidades.RutasVistas;
import utilidades.bd.EMFSingleton;

//import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import edu.unam.modelo.Tutor;
import edu.unam.servicio.TutorServicio;
import utilidades.NavegadorDeVistas;

/*
 * 
 * NOTAS:
 * 
 * ESTE CONTROLADOR ES LITERALMENTE IGUAL AL DE CLIENTE.
 * 
 * HAY QUE MODIFICAR ESTE CONTROLADOR PARA ADAPTARLO A LA ENTIDAD TUTOR.
 * 
 * FUNCIONALIDAD DE BUSCAR, DESACTIVADA.
 * 
 */

public class ControladorVistaABMTutor {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
//    @FXML
//    private Button BTBuscar;

	@FXML
	private Button BTCrear;
  
    @FXML
    private Button BTAtras;

    @FXML
    private Button BTEliminar;

    @FXML
    private Button BTModificar;
    
    @FXML
    private TableView<Tutor> TVTablaTutores;

    @FXML
    private TableColumn<Tutor, String> colApellido;

    @FXML
    private TableColumn<Tutor, String> colCiudad;

    @FXML
    private TableColumn<Tutor, Integer> colCodigoPostal;

    @FXML
    private TableColumn<Tutor, Integer> colDNI;

    @FXML
    private TableColumn<Tutor, LocalDate> colFechaNacimiento;

    @FXML
    private TableColumn<Tutor, String> colNombre;

    @FXML
    private TableColumn<Tutor, String> colProvincia;

    @FXML
    private TableColumn<Tutor, Character> colSexo;

//    @FXML
//    private TextField txtDNI;
    
    private TutorServicio ts = new TutorServicio();
    
    private List<Tutor> tutores = new ArrayList<>();


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
    
    // ASIGNA CONTENIDO A CADA COLUMNA
    private <S, T> void asignarValoresColumnas(TableColumn<S, T> columna, String valor) {
    	columna.setCellValueFactory(new PropertyValueFactory<>(valor));
    }

    // LA LISTA DE ENTIDADES LA TOMA CADA VEZ QUE SE LLAMA A ESTE METODO.
    private void actualizarTabla(List<Tutor> listaClientes) {
    	TVTablaTutores.getItems().clear(); // Limpia los items de la tabla
    	TVTablaTutores.getItems().addAll(listaClientes); // Muestra items en la tabla
    	TVTablaTutores.refresh();

    	this.asignarValoresColumnas(this.colDNI, "dni");
    	this.asignarValoresColumnas(this.colNombre, "nombre");
    	this.asignarValoresColumnas(this.colApellido, "apellido");
    	this.asignarValoresColumnas(this.colSexo, "sexo");
    	this.asignarValoresColumnas(this.colCiudad, "ciudad");
    	this.asignarValoresColumnas(this.colProvincia, "provincia");
    	this.asignarValoresColumnas(this.colCodigoPostal,"codPost");
    	this.asignarValoresColumnas(this.colFechaNacimiento, "fechaNacimiento");
    }
    
    // EVITA QUE SE INGRESEN CARACTERES NO NUMERICOS EN UN TEXTFIELD
    private void limitarATextoNumerico(TextField textField) {
        // FILTRO
    	UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };

        // CONVERSOR DE TIPOS
        TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter(), null, integerFilter);
        textField.setTextFormatter(textFormatter);
    }

    @FXML
    private void eventoBTCargar(ActionEvent event) {    	
    	NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
	    			RutasVistas.VISTA_CARGAR_TUTOR
			);
    	NavegadorDeVistas
    		.getInstancia()
    		.cambiarVista(
    				BTCrear,
    				"Cargar cliente"
    		);
    }
    
//    // ESTE METODO ESTÁ MEDIO RARO, PERO POR EL MOMENTO SE QUEDA ASÍ
//    @FXML
//    private void eventoBTBuscar(ActionEvent event) {
//    	Tutor cl = null;
//    	String contenido = this.txtDNI.getText();
//    	
//    	if (contenido.isEmpty()) {
//    		System.out.println("[ ! ] > El campo está vacio!");
//    		this.actualizarTabla(this.ts.obtenerTodosLosTutores());
//    		return;
//    	}
//    	
//    	cl = this.ts.obtenerTutor(Integer.parseInt(contenido), true);
//    	
//    	// PARA SOBRE-ESCRIBIR LA LISTA DE TUTORES (MEDIO CUALQUIERA)
//    	this.tutores.clear(); this.tutores.add(cl);  	
//    	
//    	this.actualizarTabla(this.tutores);
//    }

    @FXML
    private void eventoBTAtras(ActionEvent event) {
    	NavegadorDeVistas
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_INICIO
    		);
    	NavegadorDeVistas
    		.getInstancia()
    		.cambiarVista(
    				BTAtras,
    				"Inicio"
    		);
    }

    @FXML
    private void eventoBTEliminar(ActionEvent event) {
    	Tutor regTu = this.TVTablaTutores.getSelectionModel().getSelectedItem();

    	if (regTu == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!", "OPERACION FALLIDA",
    				"Seleccione un tutor..."
    		);
    		System.out.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
    		return;
    	}
    	
    	
    	// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Eliminacion de tutor",
    			"OPERACION ELIMINAR", "Realmente desea eliminar el tutor?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Eliminación cancelada!"); // LOG
        	return;
    	}
    	
    	// VERIFICA QUE LA BD ESTE ACTIVA (ESTOY EMPEZANDO A PENSAR QUE NO ES MUY NECESARIO)
    	if (!EMFSingleton.getInstancia().hayConexion()) {
			this.lanzarMensaje(
					AlertType.ERROR, "Error!",
					"ERROR DE CONEXION A BASE DE DATOS!",
					"Problemas de conexión con la BD!"
			);
    		return;
    	}
    	
    	System.out.println("[ ! ] > Se eliminará el tutor!"); // LOG
    	this.ts.eliminarTutor(regTu.getDni());
    	
    	// SI TODAVIA SE ENCUENTRA LA ENTIDAD, LA OPERACION FALLO
    	if (this.ts.obtenerTutor(regTu.getDni(), false) != null) {
			this.lanzarMensaje(
					AlertType.ERROR, "Error!", "OPERACION FALLIDA",
					"La operacion de eliminacion no se ejecuto correctamente..."
			);
    		return;
    	}
    	
    	this.actualizarTabla(this.ts.obtenerTodosLosTutores());
    }

    @FXML
    private void eventoBTModificar(ActionEvent event) {
    	Tutor regTu = this.TVTablaTutores.getSelectionModel().getSelectedItem();
    	
    	if (regTu == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error", "OPERACION FALLIDA",
    				"Seleccione un tutor antes de modificar..."
    		);
    		System.out.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
    		return;
    	}
    	
    	NavegadorDeVistas
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_MODIFICAR_TUTOR
    		);
    	
    	// OBTIENE EL CONTROLADOR DE LA VISTA SIGUIENTE Y PASA EL OBJETO TUTORES EXTRAIDO DE LA TABLA DE DATOS
    	ControladorVistaModificarTutor controladorModificarTutor = NavegadorDeVistas.getInstancia().obtenerControladorDeNuevaVista();
    	controladorModificarTutor.establecerTutorParaModificar(regTu);
    	
    	NavegadorDeVistas
    		.getInstancia()
    		.cambiarVista(
    				BTModificar,
    				"Modificar tutor"
    		);
    }
    
    @FXML
    private void initialize() {
    	this.actualizarTabla(this.ts.obtenerTodosLosTutores());
//    	this.limitarATextoNumerico(txtDNI);
    }
}


// CODIGO SIN USAR //

//@FXML
//private Stage ventanaActual;

//private Stage ventana;

//private void abrirVentanaVista(String rutaVista, String tituloDeVentana) {
//// SI YA HAY UNA VENTANA DE ESTE TIPO, SOLO LA TRAE AL FRENTE
//if (ventana != null && ventana.isShowing()) {
//	ventana.toFront();
//	return;
//}
//
//try {
//	FXMLLoader cargaVista = new FXMLLoader(this.getClass().getResource(rutaVista));
//	AnchorPane raizVista = cargaVista.load();
//	Scene escena = new Scene(raizVista);
//	ventana = new Stage();
//	ventana.setTitle(tituloDeVentana);
//	ventana.setResizable(false);
//	ventana.setScene(escena);
//	ventana.setOnCloseRequest(e -> ventana = null); // AL CERRAR LA VENTANA, EL ESTATICO "VENTANA" SE VUELVE NULO
//	// ventana.setAlwaysOnTop(true);
//	ventana.show();
//	
//	// CIERRA LA VENTANA ACTUAL
//	ventanaActual = (Stage) this.BTCancelar.getScene().getWindow();
//	this.ventanaActual.close();
//} catch (IOException e) {
//	System.out.println(e);
//}  
//}

//private void cambiarVista(String rutaVista, String tituloDeVentana) {
//try {
//	// SE CARGA LA VISTA A UN TIPO PARENT POR QUE NO SE SABE EL TIPO DE CONTENEDOR DE LA VISTA PADRE/PRINCIPAL
//	Parent nuevaVista = FXMLLoader.load(this.getClass().getResource(rutaVista));
//	Scene escena = new Scene(nuevaVista);
//	
//	// OBTIENE EL STAGE O VENTANA ACTUAL DESDE ALGUNO DE LOS NODOS QUE CONTIENE
//	Stage ventana = (Stage) BTCancelar.getScene().getWindow();
//	ventana.setScene(escena);
//	ventana.setTitle(tituloDeVentana);
//	ventana.setResizable(false);
//	ventana.show();
//} catch (IOException e) {
//	System.out.println(e);
//}
//}

//Alert alertaConfirmacion = new Alert(AlertType.CONFIRMATION);
//alertaConfirmacion.setTitle("Eliminacion de cliente");
//alertaConfirmacion.setHeaderText("OPERACION ELIMINAR");
//alertaConfirmacion.setContentText("Realmente desea eliminar el cliente?");
//Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();

//this.cambiarVista(RutasVistas.VISTA_CARGA_CLIENTE, "Cargar cliente");
//NavegadorDeVistas.getInstancia().cambiarVista(
//		BTBuscar, this.getClass(),
//		RutasVistas.VISTA_CARGA_CLIENTE,
//		"Cargar cliente"
//);

//this.cambiarVista(RutasVistas.VISTA_INICIO, "Inicio");
