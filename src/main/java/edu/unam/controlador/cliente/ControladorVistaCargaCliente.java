/*
 * VISTA DE CARGA DE CLIENTE
 * 
 * (ODIO EL FRONTEND)
 */

package edu.unam.controlador;

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
import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.util.function.UnaryOperator;

import edu.unam.modelo.Cliente;
import edu.unam.servicio.ClienteServicio;

public class ControladorVistaCargaCliente {
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
    
    private ClienteServicio scli = new ClienteServicio();

    // EVITA QUE SE MARQUE MAS DE UN RADIO BUTTON
    private void radioButtons() {
    	ToggleGroup grupo = new ToggleGroup();
    	this.RBFemenino.setToggleGroup(grupo); // ES UNO
    	this.RBMasculino.setToggleGroup(grupo); // U OTRO, NO AMBOS XD.
    }
    
    // EVITA QUE SE INGRESEN CARACTERES NO NUMERICOS EN UN TEXTFIELD (SI, LO COPIE, Y QUE??, QUE TE PASA??!!)
    private void limitarATextoNumerico(TextField textField) {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter(), null, integerFilter);

        textField.setTextFormatter(textFormatter);
    }
    
    @FXML
    private void eventoBTFinalizar(ActionEvent event) {
    	String nombre, apellido, ciudad, provincia;
    	int dni, codPost;
    	char sexo;
    	LocalDate fechaNac, fechaIng;
    	int camposIncompletos = 0;
    	
    	if (this.txtDNI.getText().isBlank()) {
    		camposIncompletos++;
    	}
    	
    	if (this.txtNombre.getText().isBlank()) {
    		camposIncompletos++;
    	}
    	
    	if (this.txtApellido.getText().isBlank()) {
    		camposIncompletos++;
    	}
    	
    	// SE NECESITA ALMENOS 1 DE LOS 2 RADIOBUTTON PRESIONADOS
    	if (!this.RBMasculino.isSelected() && !this.RBFemenino.isSelected()) {
    		camposIncompletos++;
    	}
    	
    	if (this.txtCiudad.getText().isBlank()) {
    		camposIncompletos++;
    	}
    	
    	if (this.txtProvincia.getText().isBlank()) {
    		camposIncompletos++;
    	}
    	
    	if (this.txtCodigoPostal.getText().isBlank()) {
    		camposIncompletos++;
    	}
    	
    	if (this.DPFechaNac.getValue() == null) {
    		camposIncompletos++;
    	}
    	
    	if (this.DPFechaIng.getValue() == null) {
    		camposIncompletos++;
    	}
    	
    	if (camposIncompletos > 0) {
    		System.out.println("[ ERROR ] > Faltan campos que completar!");
    		Alert alerta = new Alert(AlertType.ERROR);
    		alerta.setTitle("Alerta de subnormal!");
    		alerta.setHeaderText("ERROR!");
    		alerta.setContentText("Faltan campos que completar...");
    		alerta.showAndWait();
    		return;
    	}
    	
    	// CASTEO DE CADENAS A ENTEROS
    	dni = Integer.parseInt(this.txtDNI.getText().replaceAll(" ", ""));
    	codPost = Integer.parseInt(this.txtCodigoPostal.getText().replaceAll(" ", ""));
    	
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
    	
    	// CREA Y CARGA UN CLIENTE A LA BD
    	this.scli.cargarCliente(new Cliente(
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
    }
    
    @FXML
    private void eventoBTCancelar(ActionEvent event) {
    	
    }

    
    @FXML
    private void initialize() {
    	this.radioButtons();
    	this.limitarATextoNumerico(txtDNI);
    	this.limitarATextoNumerico(txtCodigoPostal);
    }
}
