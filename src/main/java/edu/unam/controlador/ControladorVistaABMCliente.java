/*
 * CLASE CONTROLADORA PARA VISTA BUSCAR - MODIFICAR - ELIMINAR CLIENTE
 * LAS 3 OPERACIONES SE PUEDEN HACER EN LA MISMA VISTA.
 * 
 */

package edu.unam.controlador;

// LIBRERIAS
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import java.time.LocalDate;

import edu.unam.modelo.Cliente;

public class ControladorVistaBMECliente {
    @FXML
    private Button BTBuscar;

    @FXML
    private Button BTCancelar;

    @FXML
    private Button BTEliminar;

    @FXML
    private Button BTModificar;

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

    @FXML
    private TextField txtDNI;

    @FXML
    void eventoBTBuscar(ActionEvent event) {

    }

    @FXML
    void eventoBTCancelar(ActionEvent event) {

    }

    @FXML
    void eventoBTEliminar(ActionEvent event) {

    }

    @FXML
    void eventoBTModificar(ActionEvent event) {

    }
    
    @FXML
    public void initialize() {
    	
    }
}
