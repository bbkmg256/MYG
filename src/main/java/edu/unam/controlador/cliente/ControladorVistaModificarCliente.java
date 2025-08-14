/*
 * CLASE CONTROLADORA MODIFICACION CLIENTE
 */

package edu.unam.controlador.cliente;

import java.time.DayOfWeek;
import java.util.Optional;
// LIBRERIAS
import java.util.function.UnaryOperator;
import edu.unam.modelo.Cliente;
import edu.unam.servicio.ClienteServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.util.converter.IntegerStringConverter;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;
//import utilidades.bd.EMFSingleton;
import utilidades.parametros.ParametrosClienteTutor;

/*
 * 
 * NOTE:
 * 
 * LA CLASE ES MEDIO SIMILAR A LA DE CARGA, PERO CON ALGUNOS CAMBIOS
 * 
 * [HECHO]
 * CREO QUE HAY QUE MODIFICAR ALGO EN EL BACKEND RESPECTO A LOS METODOS DE MODIFICACION,
 * PERO SOLAMENTE AQUELLOS QUE RECIBEN COMO PARAMETRO ALGUNA CLASE "PARAMETRO" (XD),
 * YA QUE ESTOS COMPRUEBAN SI SON DISTINTOS A SU VALOR POR DEFECTO, PERO NO SI SON DISTINTOS
 * AL VALOR QUE YA TIENE EL OBJETO, POR EJEMPLO UN ATRIBUTO NOMBRE QUE SE MODIFICA CON EL MISMO
 * NOMBRE QUE YA ESTABA, NO TENDRIA SENTIDO VOLVER A CARGAR O CAMBIARLO POR QUE YA TIENE ESE VALOR.
 * 
 * (MI SANIDAD MENTAL SE ESTA VIENDO BASTANTE AFECTADA POR ESTE PROYECTO, ESTOY A 2 LINEAS DE
 * IR A TOMARME UN CAFECITO O UN MATECITO, Y SALIR A CAMINAR UN RATO DESPUES DE ESTAR 48HS
 * SEGUIDAS SOLUCIONANDO BUGS Y REESCRIBIENDO FRAGMENTOS BASTANTE IMPORTANTES DE CODIGO)
 * 
 */

public class ControladorVistaModificarCliente {
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
    private TextField txtNombre;

    @FXML
    private TextField txtProvincia;

    private ClienteServicio scli = new ClienteServicio();
    
    private ParametrosClienteTutor paramCli = new ParametrosClienteTutor();
    
    private int IDdni;
    
    
    // METODOS Y EVENTOS //
    // MENSAJE DE UI
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
    
    // NOTE: EVITA QUE SE MARQUE MAS DE UN RADIO BUTTON
    private void radioButtons() {
    	ToggleGroup grupo = new ToggleGroup();
    	this.RBFemenino.setToggleGroup(grupo); // ES UNO
    	this.RBMasculino.setToggleGroup(grupo); // U OTRO, NO AMBOS XD.
    }
    
    // NOTE: EVITA QUE SE INGRESEN CARACTERES NO NUMERICOS EN UN TEXTFIELD (SI, LO COPIE, Y QUE??, QUE TE PASA??!!)
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
    
    // NOTE: PARA EXTRAER EL CLIENTE DEL CONTROLADOR ANTERIOR (DE LA VISTA ABM)
    public void establecerClienteParaModificar(Cliente pcli) {
    	this.IDdni = pcli.getDni(); // NO SE MODIFICA EN LA VISTA
    	this.txtNombre.setText(pcli.getNombre());
    	this.txtApellido.setText(pcli.getApellido());
    	
    	if (pcli.getSexo() == Character.toLowerCase('M')) {
    		this.RBMasculino.setSelected(true);    		
    	} else {
    		this.RBFemenino.setSelected(true);
    	}
    	
    	this.txtCiudad.setText(pcli.getCiudad());
    	this.txtProvincia.setText(pcli.getProvincia());
    	this.txtCodigoPostal.setText(String.valueOf(pcli.getCodPost()));
    	this.DPFechaNac.setValue(pcli.getFechaNacimiento());
    	this.DPFechaIng.setValue(pcli.getFechaIngreso());
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
    private void eventoBTFinalizar(ActionEvent event) {
    	// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Modificación de cliente",
    			"OPERACION MODIFICAR", "Confirmar operación?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Operación cancelada!"); // LOG
        	return;
    	}
    	
    	this.paramCli.nombre = this.txtNombre.getText();
    	this.paramCli.apellido = this.txtApellido.getText();
    	
//    	if (this.RBMasculino.isSelected()) {
//    		this.paramCli.sexo = 'M';
//    	} else {
//    		this.paramCli.sexo = 'F';
//    	}
    	
    	this.paramCli.sexo = (this.RBMasculino.isSelected()) ? 'M' : 'F';
    	
    	this.paramCli.ciudad = this.txtCiudad.getText();
    	this.paramCli.provincia = this.txtProvincia.getText();
    	
    	/*
    	 * NOTE: NO NECESITAN VALIDACIÓN POR QUE EL CAMPO NO QUEDA
    	 * VACIO AL NO SER EDITABLE.
    	 */
    	this.paramCli.fechaNacimiento = this.DPFechaNac.getValue();
    	this.paramCli.fechaIngreso = this.DPFechaIng.getValue();
    	
    	boolean hayErrorDeCampos = false;
    	
    	if (this.paramCli.nombre.isBlank()) {
    		hayErrorDeCampos = true;
    		System.err.println("[ ERROR ] > Debe ingresar nombre para continuar...");
    	}
    	
    	if (!hayErrorDeCampos && this.paramCli.apellido.isBlank()) {
    		hayErrorDeCampos = true;
    		System.err.println("[ ERROR ] > Debe ingresar apellido para continuar...");
    	}
    	
    	/*
    	 * NOTE: NO ES NECESARIO POR QUE POR DEFECTO EL RADIO BUTTON
    	 * NO PUEDE QUEDAR SIN SELECCION.
    	 */
//    	if (this.paramCli.sexo == ' ') {
//    		hayErrorDeCampos = true;
//    		System.err.println("[ ERROR ] > Debe ingresar nombre para continuar...");
//    	}
    	
    	if (!hayErrorDeCampos && this.paramCli.ciudad.isBlank()) {
    		hayErrorDeCampos = true;
    		System.err.println("[ ERROR ] > Debe ingresar una ciudad para continuar..."); // LOG
    	}
    	
    	if (!hayErrorDeCampos && this.paramCli.provincia.isBlank()) {
    		hayErrorDeCampos = true;
    		System.err.println("[ ERROR ] > Debe ingresar una provincia para continuar..."); // LOG
    	}
    	
    	/*
    	 * NOTE: SE PARSEA Y ALMACENA AL PARAMETRO DESPUES,YA QUE SE TIENE QUE
    	 * COMPROBAR EN TEXTO PRIMERO,QUE NO SE DEJE EL CAMPO VACÍO.
    	 */
    	if (!hayErrorDeCampos && this.txtCodigoPostal.getText().isBlank()) {
    		hayErrorDeCampos = true;
    		System.err.println("[ ERROR ] > Debe ingresar una numero de codigo postal para continuar..."); // LOG
    	}
    	
    	if (hayErrorDeCampos) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE CAMPOS",
    				"No puede dejar campos vacios o sin seleccionar..."
    		);
    		return;
    	}
    	 
    	// NOTE: EVITA QUE SE INGRESE UNA FECHA DE NACIMIENTO POSTERIOR A LA DEL INGRESO AL GYM
    	if (this.paramCli.fechaIngreso.isBefore(this.paramCli.fechaNacimiento)) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE CAMPOS!",
    				"La fecha de ingreso no puede ser menor a la de nacimiento..."
    		);
    		System.out.println("[ ERROR ] > Incoherencia en fecha de nacimiento y de ingreso!"); // LOG
    		return;
    	}
    	
    	// NOTE: EVITA LAS FECHAS QUE CAEN DOMINGO
    	if (this.paramCli.fechaIngreso.getDayOfWeek() == DayOfWeek.SUNDAY) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE CAMPOS",
    				"La fecha de ingreso no puede ser un día domingo..."
    		);
    		// LOG
    		System.out.println("[ ERROR ] > Incoherencia en fecha de ingreso!");
    		return;    		
    	}
    	
    	// NOTE: PARSEO DEL CODIGO POSTAL AL PARAMETRO
    	this.paramCli.codigoPostal = Integer.parseInt(this.txtCodigoPostal.getText());
    	 
     	// POR SI FALLA LA CONEXION CON LA BD POR X MOTIVO     	
//     	if (!EMFSingleton.getInstancia().hayConexion()) {
//			this.lanzarMensaje(
//					AlertType.ERROR, "Error!",
//					"ERROR DE CONEXION A BASE DE DATOS!",
//					"Problemas de conexión con la BD!"
//			);
//    		return;
//    	}
    	 
     	boolean actualizacionCorrecta = scli.actualizarEstadoCliente(this.IDdni, this.paramCli);
    	
     	if (!actualizacionCorrecta) {
     		this.lanzarMensaje(
     				AlertType.ERROR, "Error!",
     				"OPERACION RELIZADA",
     				"Algo falló al modificar el cliente..."
     		);
     		System.err.println("[ ERROR ] > Fallo al modificar el cliente!"); // LOG
     		return;
     	}

     	this.lanzarMensaje(
 				AlertType.INFORMATION, "Exito!",
 				"OPERACION REALIZADA",
 				"El cliente fue modificado con exito..."
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
    private void initialize() {
    	this.radioButtons();
    	this.limitarATextoNumerico(txtCodigoPostal);
    	
    	this.DPFechaIng.setEditable(false);
    	this.DPFechaNac.setEditable(false);
    	
    	// (I ONLY WANNA DIE!)
    }
}


// CODIGO SIN USAR //

//	if (!ComprobarConexionBD.hayConexion(EMFSingleton.getInstancia().getEMF())) {
//	this.lanzarMensaje(
//			AlertType.ERROR, "Error!",
//			"ERROR DE CONEXION A BASE DE DATOS!",
//			"No se encuentra una base de datos activa..."
//	);
//	// LOG
//	System.out.println(
//			"[ ERROR ] > No hay conexión a una BD o la misma está caida!"
//	);
//	return;
//}
