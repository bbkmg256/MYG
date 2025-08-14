/*
 * CLASE CONTROLADORA DE CARGA DE CLIENTE
 * 
 * (ODIO EL FRONTEND)
 */

package edu.unam.controlador.cliente;

// LIBRERIAS
import javafx.fxml.FXML;
//import javafx.fxml.Initializable; // NO REQUERIDO
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
//import javafx.scene.input.KeyEvent; // NO REQUERIDO
import javafx.util.converter.IntegerStringConverter;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.event.ActionEvent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.UnaryOperator;
import edu.unam.modelo.Cliente;
import edu.unam.servicio.ClienteServicio;

/*
 * 
 * NOTE:
 * 
 * EN EL BOTON DE FINALIZAR FALTAN ALGUNAS LLAMAR A ALGUNA VERIFICACION QUE DE AVISO DE QUE
 * SI EL DNI YA ESTA, ENTONCES NO LO AGREGUE Y SE LO INFORME AL USUARIO (ESTO YA ESTA EN EL BACKEND, PERO
 * HAY QUE LLAMARLO O USARLO ACA).
 * 
 * TAMBIEN A QUE HACER USO DE ALGUNA FORMA DE VERIFICACION PARA CUANDO SE CARGA EL CLIENTE, ASI SE LE DA AVISO
 * AL USUARIO DE QUE LA CREACION/CARGA FUE EXITOSA Y SE VALIDAD QUE ESTE EL CLIENTE CARGADO REALMENTE.
 * 
 * HAY QUE COMPROBAR QUE NO HAYA INCONSISTENCIA ENTRE LA FECHA DE NACIMIENTO Y LA DE INGRESO AL GYM, SIEMPRE
 * LA DE NACIMIENTO SERA MENOR A LA DEL INGRESO.
 * 
 */

public class ControladorVistaCargaCliente {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
	@FXML
    private Button BTCancelar;

    @FXML
    private Button BTFinalizar;

    @FXML
    private DatePicker DPFechaIng;

    @FXML
    private DatePicker DPFechaNac;

    @FXML
    private RadioButton RBFemenino;

    @FXML
    private RadioButton RBMasculino;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtCodigoPostal;

    @FXML
    private TextField txtDNI;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtProvincia;
    
    // PARA OBTENER EL OBJETO DEL TABLEVIEW Y PASARLO A LA VISTA DE MODIFICACION
//    private Cliente cliObj = null;
    
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
    
    // EVITA QUE SE MARQUE MAS DE UN RADIO BUTTON
    private void radioButtons() {
    	ToggleGroup grupo = new ToggleGroup();
    	this.RBFemenino.setToggleGroup(grupo); // ES UNO
    	this.RBMasculino.setToggleGroup(grupo); // U OTRO, NO AMBOS XD.
    }
    
    // EVITA QUE SE INGRESEN CARACTERES NO NUMERICOS EN UN TEXTFIELD (SI, LO COPIE, Y QUE??, QUE TE PASA??!!)
    private void limitarATextoNumerico(TextField textField) {
        // FILTRO DE ENTRADA DE CARACTERES
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
    private void eventoBTFinalizar(ActionEvent event) {
    	String nombre, apellido, ciudad, provincia;
    	int dni, codPost;
    	char sexo;
    	LocalDate fechaNac, fechaIng;
    	boolean camposIncompletos = false;
    	
    	if (this.txtDNI.getText().isBlank()) {
    		camposIncompletos = true;
    	}
    	
    	if (this.txtNombre.getText().isBlank()) {
    		camposIncompletos = true;
    	}
    	
    	if (this.txtApellido.getText().isBlank()) {
    		camposIncompletos = true;
    	}
    	
    	// SE NECESITA AL MENOS 1 DE LOS 2 RADIOBUTTON PRESIONADOS
    	if (!this.RBMasculino.isSelected() && !this.RBFemenino.isSelected()) {
    		camposIncompletos = true;
    	}
    	
    	if (this.txtCiudad.getText().isBlank()) {
    		camposIncompletos = true;
    	}
    	
    	if (this.txtProvincia.getText().isBlank()) {
    		camposIncompletos = true;
    	}
    	
    	if (this.txtCodigoPostal.getText().isBlank()) {
    		camposIncompletos = true;
    	}
    	
    	if (this.DPFechaNac.getValue() == null) {
    		camposIncompletos = true;
    	}
    	
    	if (this.DPFechaIng.getValue() == null) {
    		camposIncompletos = true;
    	}
    	
    	if (camposIncompletos) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE CAMPOS",
    				"Faltan campos que completar..."
    		);
    		System.err.println("[ ERROR ] > Faltan campos que completar!"); // LOG
    		return;
    	}
    	
    	// LOG
//    	LocalDate fIng = this.DPFechaIng.getValue(), fNac = this.DPFechaNac.getValue();
//    	System.out.printf("Fecha nac: %td/%tm/%tY %nFecha ing: %td/%tm/%tY%n", fIng, fIng, fIng, fNac, fNac, fNac);
    	
    	// NOTE: EVITA QUE SE INGRESE UNA FECHA DE NACIMIENTO POSTERIOR A LA DEL INGRESO AL GYM
    	if (!this.DPFechaNac.getValue().isBefore(this.DPFechaIng.getValue())) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE CAMPOS",
    				"La fecha de ingreso no puede ser menor a la de nacimiento..."
    		);
    		// LOG
    		System.err.println("[ ERROR ] > Incoherencia en fecha de nacimiento y de ingreso!");
    		return;    		
    	}
    	
    	// NOTE: EVITA LAS FECHAS QUE CAEN DOMINGO
    	if (this.DPFechaIng.getValue().getDayOfWeek() == DayOfWeek.SUNDAY) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE CAMPOS",
    				"La fecha de ingreso no puede ser un día domingo..."
    		);
    		// LOG
    		System.err.println("[ ERROR ] > Incoherencia en fecha de ingreso!");
    		return;    		
    	}
    	
    	// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Creación de cliente",
    			"OPERACIÓN DE CARGA", "Confirmar operación?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Operación cancelada!"); // LOG
        	return;
    	}
    	
    	// VER DESPUES POR QUE CARAJO ESTOY REEMPLAZANDO CARACTERES  //
    	// QUE CLARAMENTE NO SE PUEDEN INGRESAR POR EL FILTRO DEL TF //
    	// CASTEO DE CADENAS A ENTEROS
    	dni = Integer.parseInt(
    			this.txtDNI.getText().replaceAll(" ", "")
    	);
    	codPost = Integer.parseInt(
    			this.txtCodigoPostal.getText().replaceAll(" ", "")
    	);
    	
    	nombre = this.txtNombre.getText();
    	apellido = this.txtApellido.getText();
    	ciudad = this.txtCiudad.getText();
    	provincia = this.txtProvincia.getText();
    	
    	// NO ESTA MUY CORRECTO, DESPUES LO ARREGLO
    	if (this.RBMasculino.isSelected()) {
    		sexo = 'M';
    	} else {
    		sexo = 'F';
    	}
    	
    	fechaNac = this.DPFechaNac.getValue();
    	fechaIng = this.DPFechaIng.getValue();
    	
    	// POR SI FALLA LA CONEXION CON LA BD POR X MOTIVO (NO CREO QUE HAGA FALTA)
//    	if (!ComprobarConexionBD.hayConexion(EMFSingleton.getInstancia().getEMF())) {
//			this.lanzarMensaje(
//					AlertType.ERROR, "Error!",
//					"ERROR DE CONEXIÓN A BASE DE DATOS",
//					"No se encuentra una base de datos activa..."
//			);
//			// LOG
//			System.out.println(
//					"[ ERROR ] > No hay conexión a una BD o la misma está caída!"
//			);
//			return;
//		}
    	
    	// CREA Y CARGA UN CLIENTE A LA BD
    	boolean cargaCorrecta = this.scli.cargarCliente(
    			new Cliente(
	    			dni,
	    			nombre,
	    			apellido,
	    			fechaNac,
	    			sexo,
	    			ciudad,
	    			provincia,
	    			codPost,
	    			fechaIng
    	));
    	
    	/*
    	 * BUG: EL CONTROLADOR MARCA COMO CARGA CORRECTA AUN CUANDO SE INGRESA
    	 * UN CLIENTE QUE YA ESTÁ EN LA BD.
    	 */
    	// POR SI OCURREN ALGUN PROBLEMA DESPUES DE HABER HECHO LA TRANSACCION
//    	if (scli.obtenerCliente(dni, false) == null) {
//    		this.lanzarMensaje(
//    				AlertType.ERROR, "Error!",
//    				"ERROR DE OPERACIÓN",
//    				"Algo falló al cargar el cliente..."
//    		);
//    		System.err.println("[ ERROR ] > Fallo al cargar el cliente!"); // LOG
//    		return;
//    	}
    	
    	/*
    	 * NOTE: COMPRUEBA QUE LA CARGA SEA EXITOSA
    	 */
    	if (!cargaCorrecta) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE OPERACIÓN",
    				"Algo falló al cargar el cliente..."
    		);
    		System.err.println("[ ERROR ] > Fallo al cargar el cliente!"); // LOG
    		return;
    	}
    	
    	this.lanzarMensaje(
				AlertType.INFORMATION, "Éxito!",
				"OPERACIÓN REALIZADA",
				"El cliente fue cargado con éxito..."
		);
    	
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_ABM_CLIENTE,
    				BTFinalizar
    		);
    	
    	ControladorVistaABMCliente CVABMC =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABMC.iniciar();
    	
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cambiarVista(
    				BTFinalizar,
    				"Cliente"
    		);
    }
    
    @FXML
    private void eventoBTCancelar(ActionEvent event) {
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_ABM_CLIENTE,
    				BTCancelar
    		);
    	
    	ControladorVistaABMCliente CVABMC =
    			NavegadorDeVistasSingleton
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABMC.iniciar();
    	
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cambiarVista(
    				BTCancelar,
    				"Cliente"
    		);
    }

    @FXML
    private void initialize() {
    	this.radioButtons();
    	this.limitarATextoNumerico(txtDNI);
    	this.limitarATextoNumerico(txtCodigoPostal);
    	
    	this.DPFechaIng.setEditable(false);
    	this.DPFechaNac.setEditable(false);
    }
}


// CODIGO SIN USAR //

//// AVISO CARGA EXELENTE
//Alert alerta = new Alert(AlertType.ERROR);
//alerta.setTitle("Error!");
//alerta.setHeaderText("ERROR DE CONEXION A BASE DE DATOS!");
//alerta.setContentText("No se encuentra una base de datos activa!");
//alerta.showAndWait();