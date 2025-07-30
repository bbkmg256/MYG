/*
 * CLASE CONTROLADORA PARA VISTA ALTA - MODIFICAR - ELIMINAR CLIENTE
 * LAS 3 OPERACIONES SE PUEDEN HACER EN LA MISMA VISTA.
 * 
 * HAY QUE MODIFICAR LA VISTA DE ESTO, AGREGANDO EL BOTON DE AÑADIR ENTIDAD O ALGO ASI
 * 
 */

package edu.unam.controlador.cliente;

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
//import javafx.scene.control.TextField;
//import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;

//import java.io.IOException;
import java.time.LocalDate;
//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.unam.controlador.ControladorVistaInicio;
//import java.util.function.UnaryOperator;
import edu.unam.modelo.Cliente;
import edu.unam.modelo.Entrenamiento;
import edu.unam.servicio.ClienteServicio;

/*
 * 
 * NOTAS:
 * 
 * A LA VISTA CREAR_CLIENTE HAY QUE CAMBIARLE EL NOMBRE, YA QUE SE USA TAMBIEN PARA
 * MODIFICAR A LOS CLIENTES. [RESUELTO]
 * 
 * EL METODO DEL BOTON MODIFICAR NO ESTA TERMINADO, FALTA OBTENER EL OBJETO DEL REGISTRO
 * DE LA TABLA DE DATOS (DEL TABLEVIEW) [TERMINADO]
 * 
 * HAY QUE VER COMO SE PUEDE SELECCIONAR UN REGISTRO PARA PODER ELEMINAR AL OBJETO QUE
 * REFERENCIA EN LA BD (OSEA, HAY QUE VER COMO OBTENER EL OBJETO DEL REGISTRO DEL TABLEVIEW) [TERMINADO]
 * 
 * BUSCAR UNA FORMA DE INHABILITAR EL BOTON DE "MODIFICAR" HASTA QUE SE SELECCIONE
 * UNO DE LOS REGISTROS DEL "TABLEVIEW". [RESULTO - NO SE INHABILITA EL BOTON, PERO SE AVISA QUE NO HAY REG. SELECCIONADO]
 * 
 * FALTA TERMINAR EL APARTADO DE CONFIRMACION O NEGACION DE ELIMINACION DE CLIENTE (LINEA 224) [TERMINADO]
 * 
 * FUNCIONALIDAD DE BUSCAR, DESACTIVADA.
 * 
 */

public class ControladorVistaABMCliente {
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
    private TableColumn<Cliente, Character> colSexo;

//    @FXML
//    private TextField txtDNI;
    
    private ClienteServicio cs;
    
//    private List<Cliente> clientes = new ArrayList<>();


    // METODOS Y EVENTOS //
    private void inicializarDatos() {
    	this.cs = new ClienteServicio();
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
    
    // SE DELEGAN LA DEFINICION DE LOS GENERICOS DE TableColumn AL POSTERIOR LLAMADO DE ESTE METODO
    private <S, T> void asignarValoresColumnas(TableColumn<S, T> columna, String valor) {
    	columna.setCellValueFactory(new PropertyValueFactory<>(valor));
    }

    // LA LISTA DE ENTIDADES LA TOMA CADA VEZ QUE SE LLAMA A ESTE METODO.
    private void actualizarTabla(List<Cliente> listaClientes) {
    	TVTablaClientes.getItems().clear(); // Limpia los items de la tabla
    	TVTablaClientes.getItems().addAll(listaClientes); // Añade items en la lista de la tabla
//    	TVTablaClientes.refresh();
    	
    	// Esta verga no busca atributos, busca getters de los atributos de las clases!!! >:(
    	this.asignarValoresColumnas(this.colDNI, "dni");
    	this.asignarValoresColumnas(this.colNombre, "nombre");
    	this.asignarValoresColumnas(this.colApellido, "apellido");
    	this.asignarValoresColumnas(this.colSexo, "sexo");
    	this.asignarValoresColumnas(this.colCiudad, "ciudad");
    	this.asignarValoresColumnas(this.colProvincia, "provincia");
    	this.asignarValoresColumnas(this.colCodigoPostal,"codPost"); // <-- Esta mierda está dando error y no entiendo por que!! (Ahora funciona, estaba pasando mal el nombre del getter)
    	this.asignarValoresColumnas(this.colFechaNacimiento, "fechaNacimiento");
    	this.asignarValoresColumnas(this.colFechaIngreso, "fechaIngreso");
    }
    
    // EVITA QUE SE INGRESEN CARACTERES NO NUMERICOS EN UN TEXTFIELD
//    private void limitarATextoNumerico(TextField textField) {
//        // FILTRO
//    	UnaryOperator<TextFormatter.Change> integerFilter = change -> {
//            String newText = change.getControlNewText();
//            if (newText.matches("\\d*")) {
//                return change;
//            }
//            return null;
//        };
//
//        // CONVERSOR DE TIPOS
//        TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter(), null, integerFilter);
//        textField.setTextFormatter(textFormatter);
//    }
    
    public void iniciar() {
    	this.inicializarDatos();
    	this.actualizarTabla(this.cs.obtenerTodosLosClientes());
    }

    @FXML
    private void eventoBTCargar(ActionEvent event) {    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
	    			RutasVistas.VISTA_CARGA_CLIENTE
			);
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cambiarVista(
    				BTCrear,
    				"Cargar cliente"
    		);
    }
    
    // ESTE METODO ESTÁ MEDIO RARO, PERO POR EL MOMENTO SE QUEDA ASÍ
//    @FXML
//    private void eventoBTBuscar(ActionEvent event) {
//    	Cliente cl = null;
//    	String contenido = this.txtDNI.getText();
//    	
//    	if (contenido.isEmpty()) {
//    		System.out.println("[ ! ] > El campo está vacio!");
//    		this.actualizarTabla(this.cs.obtenerTodosLosClientes());
//    		return;
//    	}
//    	
//    	cl = this.cs.obtenerCliente(Integer.parseInt(contenido), true);
//    	
//    	// PARA SOBRE-ESCRIBIR LA LISTA DE CLIENTES (MEDIO CUALQUIERA)
//    	this.clientes.clear(); this.clientes.add(cl);  	
//    	
//    	this.actualizarTabla(this.clientes);
//    }

    @FXML
    private void eventoBTAtras(ActionEvent event) {
//    	this.cambiarVista(RutasVistas.VISTA_INICIO, "Inicio");
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
    private void eventoBTEliminar(ActionEvent event) {
    	Cliente regCli = this.TVTablaClientes.getSelectionModel().getSelectedItem();

    	if (regCli == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!", "OPERACION FALLIDA",
    				"Seleccione un cliente..."
    		);
    		System.out.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
    		return;
    	}

    	if (!this.cs.obtenerListaDeEntrenamientsos(regCli.getDni()).isEmpty()) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"IMPOSIBLE ELIMINAR REGISTRO",
    				"Este cliente es usado o está relacionado con por lo menos 1 entrenamiento..."
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
    	
    	// VERIFICA QUE LA BD ESTE ACTIVA
//    	if (!EMFSingleton.getInstancia().hayConexion()) {
//			this.lanzarMensaje(
//					AlertType.ERROR, "Error!",
//					"ERROR DE CONEXION A BASE DE DATOS!",
//					"Problemas de conexión con la BD!"
//			);
//    		return;
//    	}
    	
    	System.out.println("[ ! ] > Se eliminará el cliente!"); // LOG
    	this.cs.eliminarCliente(regCli.getDni());
    	
    	// SI TODAVIA SE ENCUENTRA LA ENTIDAD, LA OPERACION FALLO
    	if (this.cs.obtenerCliente(regCli.getDni(), false) != null) {
			this.lanzarMensaje(
					AlertType.ERROR, "Error!", "OPERACION FALLIDA",
					"La operacion de eliminación no se ejecuto correctamente..."
			);
    		return;
    	}
    	
    	this.actualizarTabla(this.cs.obtenerTodosLosClientes());
    }

    @FXML
    private void eventoBTModificar(ActionEvent event) {
    	Cliente regCli = this.TVTablaClientes.getSelectionModel().getSelectedItem();
    	
    	if (regCli == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!", "OPERACION FALLIDA",
    				"Seleccione un cliente antes de modificar..."
    		);
    		System.out.println("[ ERROR ] > No se ah seleccionado un registro!"); // LOG
    		return;
    	}
    	
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_MODIFICAR_CLIENTE
    		);
    	
    	// OBTIENE EL CONTROLADOR DE LA VISTA SIGUIENTE Y PASA //
    	// EL OBJETO CLIENTE EXTRAIDO DE LA TABLA DE DATOS     //
    	ControladorVistaModificarCliente controladorModificarCliente =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	controladorModificarCliente.establecerClienteParaModificar(regCli);
    	
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cambiarVista(
    				BTModificar,
    				"Modificar cliente"
    		);
    }
    
    @FXML
    private void initialize() {
//    	this.actualizarTabla(this.cs.obtenerTodosLosClientes());
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
