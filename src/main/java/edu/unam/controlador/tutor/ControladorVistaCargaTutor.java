/*
 * CLASE CONTROLADORA DE CARGA DE CLIENTE
 * 
 * (ODIO EL FRONTEND)
 */

package edu.unam.controlador.tutor;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.UnaryOperator;
import edu.unam.modelo.Tutor;
import edu.unam.servicio.TutorServicio;
import utilidades.bd.ComprobarConexionBD;
import utilidades.bd.EMFSingleton;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;

/*
 * 
 * NOTA:
 * 
 * 
 */

public class ControladorVistaCargaTutor {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
	@FXML
    private Button BTCancelar;

    @FXML
    private Button BTFinalizar;

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
    
    private TutorServicio stutor = new TutorServicio();

    
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
    	LocalDate fechaNac;
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
//    	LocalDate fNac = this.DPFechaNac.getValue();
//    	System.out.printf("Fecha nac: %td/%tm/%tY %nFecha ing: %td/%tm/%tY%n", fIng, fIng, fIng, fNac, fNac, fNac);
    	
    	// EVITA QUE SE INGRESE UNA FECHA DE NACIMIENTO POSTERIOR A LA DEL INGRESO AL GYM
//    	if (!this.DPFechaNac.getValue().isBefore(this.DPFechaIng.getValue())) {
//    		this.lanzarMensaje(
//    				AlertType.ERROR, "Error!",
//    				"ERROR DE CAMPOS!",
//    				"La fecha de ingreso no puede ser menor a la de nacimiento..."
//    		);
//    		// LOG
//    		System.err.println("[ ERROR ] > Incoherencia en fecha de nacimiento y de ingreso!");
//    		return;
//    	}
    	
    	// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Crear tutor",
    			"OPERACIÓN CREAR", "Los datos ingresados son correctos?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Carga cancelada!"); // LOG
        	return;
    	}
    	
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
//    	fechaIng = this.DPFechaIng.getValue();
    	
    	// POR SI FALLA LA CONEXION CON LA BD POR X MOTIVO (NO CREO QUE HAGA FALTA)
    	if (!ComprobarConexionBD.hayConexion(EMFSingleton.getInstancia().getEMF())) {
			this.lanzarMensaje(
					AlertType.ERROR, "Error!",
					"ERROR DE CONEXIÓN A BASE DE DATOS",
					"No se encuentra una base de datos activa..."
			);
			// LOG
			System.out.println(
					"[ ERROR ] > No hay conexión a una BD o la misma está caída!"
			);
			return;
		}
    	
    	// CREA Y CARGA UN CLIENTE A LA BD
    	boolean cargaCorrecta = this.stutor.cargarTutor(
    			new Tutor(
	    			dni,
	    			nombre,
	    			apellido,
	    			fechaNac,
	    			sexo,
	    			ciudad,
	    			provincia,
	    			codPost
    	));
    	
    	/*
    	 * BUG: EL CONTROLADOR MARCA COMO CARGA CORRECTA AUN CUANDO SE INGRESA
    	 * UN TUTOR QUE YA ESTÁ EN LA BD.
    	 */
    	// POR SI OCURREN ALGUN PROBLEMA DESPUES DE HABER HECHO LA TRANSACCION
//    	if (stutor.obtenerTutor(dni, false) == null) {
//    		this.lanzarMensaje(
//    				AlertType.ERROR, "Error!",
//    				"ERROR DE OPERACIÓN",
//    				"Algo falló al cargar el tutor..."
//    		);
//    		return;
//    	}
    	
    	/*
    	 * NOTE: COMPRUEBA LA CARGA DE LA ENTIDAD
    	 */
    	if (!cargaCorrecta) {
			this.lanzarMensaje(
					AlertType.ERROR, "Error!",
					"ERROR DE OPERACIÓN",
					"Algo falló al cargar el tutor..."
			);
    		System.err.println("[ ERROR ] > Fallo al cargar el tutor!"); // LOG
			return;
    	}
    	
    	this.lanzarMensaje(
				AlertType.INFORMATION, "Éxito!",
				"OPERACIÓN REALIZADA",
				"El Tutor fue cargado con éxito..."
		);
    	
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_ABM_TUTOR
    		);
    	
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cambiarVista(
    				BTFinalizar,
    				"Tutor"
    		);
    }
    
    @FXML
    private void eventoBTCancelar(ActionEvent event) {
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_ABM_TUTOR
    		);
    	NavegadorDeVistasSingleton
    		.getInstancia()
    		.cambiarVista(
    				BTCancelar,
    				"Tutor"
    		);
    }

    @FXML
    private void initialize() {
    	this.radioButtons();
    	this.limitarATextoNumerico(txtDNI);
    	this.limitarATextoNumerico(txtCodigoPostal);
    	
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