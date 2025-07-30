/*
 * CLASE CONTROLADORA MODIFICACION CLIENTE
 */

package edu.unam.controlador.tutor;

// LIBRERIAS
import java.util.function.UnaryOperator;
import edu.unam.modelo.Tutor;
import edu.unam.servicio.TutorServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.util.converter.IntegerStringConverter;
import utilidades.bd.EMFSingleton;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;
import utilidades.parametros.ParametrosClienteTutor;

/*
 * 
 * NOTAS:
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
 */

public class ControladorVistaModificarTutor {
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
    private TextField txtNombre;

    @FXML
    private TextField txtProvincia;

    private TutorServicio stutor = new TutorServicio();
    
    private ParametrosClienteTutor paramTutor = new ParametrosClienteTutor();
    
    private int IDdni;
    
    
    // METODOS Y EVENTOS //
    private void lanzarMensaje(
    		AlertType tipoDeAlerta, String titulo,
    		String cabecera, String contenido
    ) {
    	Alert alerta = new Alert(tipoDeAlerta);
    	alerta.setTitle(titulo);
    	alerta.setHeaderText(cabecera);
    	alerta.setContentText(contenido);
    	alerta.showAndWait();
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
    
    // PARA EXTRAER EL CLIENTE DEL CONTROLADOR ANTERIOR (DE LA VISTA ABM)
    public void establecerTutorParaModificar(Tutor ptutor) {
    	this.IDdni = ptutor.getDni(); // NO SE MODIFICA EN LA VISTA
    	this.txtNombre.setText(ptutor.getNombre());
    	this.txtApellido.setText(ptutor.getApellido());
    	
    	if (ptutor.getSexo() == Character.toLowerCase('M')) {
    		this.RBMasculino.setSelected(true);    		
    	} else {
    		this.RBFemenino.setSelected(true);
    	}
    	
    	this.txtCiudad.setText(ptutor.getCiudad());
    	this.txtProvincia.setText(ptutor.getProvincia());
    	this.txtCodigoPostal.setText(String.valueOf(ptutor.getCodPost()));
    	this.DPFechaNac.setValue(ptutor.getFechaNacimiento());
//    	this.DPFechaIng.setValue(ptutor.getFechaIngreso());
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
					"Cliente"
			);
    }

    @FXML
    private void eventoBTFinalizar(ActionEvent event) {
    	this.paramTutor.nombre = this.txtNombre.getText();
    	this.paramTutor.apellido = this.txtApellido.getText();
    	
//    	if (this.RBMasculino.isSelected()) {
//    		this.paramTutor.sexo = 'M';
//    	} else {
//    		this.paramTutor.sexo = 'F';
//    	}
    	
    	this.paramTutor.sexo = (this.RBMasculino.isSelected()) ? 'M' : 'F';
    	
    	this.paramTutor.ciudad = this.txtCiudad.getText();
    	this.paramTutor.provincia = this.txtProvincia.getText();
    	
    	this.paramTutor.fechaNacimiento = this.DPFechaNac.getValue();

    	boolean hayErrorDeCampos = false;
    	
    	if (this.paramTutor.nombre.isBlank()) {
    		hayErrorDeCampos = true;
    		System.err.println("[ ERROR ] > Debe ingresar nombre para continuar...");
    	}
    	
    	if (!hayErrorDeCampos && this.paramTutor.apellido.isBlank()) {
    		hayErrorDeCampos = true;
    		System.err.println("[ ERROR ] > Debe ingresar apellido para continuar...");
    	}
    	
    	if (!hayErrorDeCampos && this.paramTutor.ciudad.isBlank()) {
    		hayErrorDeCampos = true;
    		System.err.println("[ ERROR ] > Debe ingresar una ciudad para continuar...");
    	}
    	
    	if (!hayErrorDeCampos && this.paramTutor.provincia.isBlank()) {
    		hayErrorDeCampos = true;
    		System.err.println("[ ERROR ] > Debe ingresar una provincia para continuar...");
    	}
    	
    	if (!hayErrorDeCampos && this.txtCodigoPostal.getText().isBlank()) {
    		hayErrorDeCampos = true;
    		System.err.println("[ ERROR ] > Debe ingresar un codigo postal para continuar...");
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
    	
    	this.paramTutor.codigoPostal = Integer.parseInt(this.txtCodigoPostal.getText());
    	
     	boolean actualizacionCorrecta = stutor.actualizarEstadoTutor(this.IDdni, this.paramTutor);
    	
     	if (!actualizacionCorrecta) {
         	this.lanzarMensaje(
     				AlertType.ERROR, "Error!",
     				"ERROR DE OPERACION",
     				"Algo falló al modificar el tutor..."
     		);
     		System.err.println("[ ERROR ] > Fallo al modificar el tutor!");
     		return;
     	}
     	
     	// MOMENTANEAMENTE VAMOS A HACERNOS LOS BOLUDOS Y SUPONDREMOS QUE LOS CAMBIOS SON EXITOSOS
     	this.lanzarMensaje(
 				AlertType.INFORMATION, "Exito!",
 				"OPERACION RELIZADA",
 				"El tutor fue modificado con exito..."
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
     				"tutor"
     		);
    }
    
    @FXML
    private void initialize() {
    	this.radioButtons();
    	this.limitarATextoNumerico(txtCodigoPostal);
    	
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

//paramTutor.fechaIngreso = this.DPFechaIng.getValue();

// EVITA QUE SE INGRESE UNA FECHA DE NACIMIENTO POSTERIOR A LA DEL INGRESO AL GYM
//if (!this.DPFechaNac.getValue().isBefore(this.DPFechaIng.getValue())) {
//	this.lanzarMensaje(
//			AlertType.ERROR, "Error!",
//			"ERROR DE CAMPOS!",
//			"La fecha de ingreso no puede ser menor a la de nacimiento..."
//	);
//	System.out.println("[ ERROR ] > Incoherencia en fecha de nacimiento y de ingreso!"); // LOG
//	return;
//} 
 
	// POR SI FALLA LA CONEXION CON LA BD POR X MOTIVO (CREO QUE NOO HACE FALTA)
//	if (!EMFSingleton.getInstancia().hayConexion()) {
//	this.lanzarMensaje(
//			AlertType.ERROR, "Error!",
//			"ERROR DE CONEXION A BASE DE DATOS!",
//			"Problemas de conexión con la BD!"
//	);
//	return;
//}
