/*
 * CLASE CONTROLADORA PARA VISTA DE CARGA CLIENTE
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.time.LocalDate;

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

    private void radioButtons() {
    	// RADIO BUTTON
    	ToggleGroup grupo = new ToggleGroup();
    	this.RBFemenino.setToggleGroup(grupo);
    	this.RBMasculino.setToggleGroup(grupo);    	
    }
    
    @FXML
    private void leerYCargarDatos(ActionEvent event) {
//    	System.out.println("Si, el boton funciona!");
//    	System.out.println(this.txtNombre.getText());
//    	System.out.println(this.RBMasculino.isSelected());
    	
    	String nombre, apellido, ciudad, provincia;
    	int DNI, codPost;
    	char sexo;
    	LocalDate fechaNac, fechaIng;
    	
    	if (this.txtDNI.getText().isBlank()) {
    		System.out.println("A");
    		return;
    	}
    	
    	if (this.txtNombre.getText().isBlank()) {
    		System.out.println("B");
    		return;
    	}
    	
    	if (this.txtApellido.getText().isBlank()) {
    		System.out.println("C");
    		return;
    	}
    	
    	// SE NECESITA ALMENOS 1 DE LOS 2 RADIOBUTTON PRESIONADOS
    	if (!this.RBMasculino.isSelected() && !this.RBFemenino.isSelected()) {
    		System.out.println("D");
    		return;
    	}
    	
    	if (this.txtCiudad.getText().isBlank()) {
    		System.out.println("G");
    		return;
    	}
    	
    	if (this.txtProvincia.getText().isBlank()) {
    		System.out.println("H");
    		return;
    	}
    	
    	if (this.txtCodigoPostal.getText().isBlank()) {
    		System.out.println("I");
    		return;
    	}
    	
    	if (this.DPFechaNac.getValue() == null) {
    		System.out.println("J");
    		return;
    	}
    	
    	if (this.DPFechaIng.getValue() == null) {
    		System.out.println("K");
    		return;
    	}
    	
    	// MOMENTANEAMENTE CASTEAREMOS ASI, LA IDEA ES USAR TEXTFORMATTER MAS ADELANTE
    	DNI = Integer.parseInt(this.txtDNI.getText().replaceAll(" ", ""));
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
    	
//    	System.out.println("Ponele que carga el cliente, todavía faltan los datepicker...");
    	
    	this.scli.cargarCliente(new Cliente(
    			DNI,
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
    public void initialize() {
    	this.radioButtons();
    }
}
