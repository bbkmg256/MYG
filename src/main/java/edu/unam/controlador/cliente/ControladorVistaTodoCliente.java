/*
 * CONTROLADOR DE LA VISTA DE CLIENTES
 */

package edu.unam.controlador.cliente;

// JavaFX
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.Label;
// import javafx.event.ActionEvent;

// Varios
import java.time.LocalDate;
import java.util.List;
//import java.util.function.UnaryOperator;

// Entidad
import edu.unam.modelo.Cliente;

// Servicios
import edu.unam.servicio.ClienteServicio;

/*
 * 
 * NOTA: clase/controlador sin uso!
 * 
 */

public class ControladorVistaTodoCliente {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
	@FXML
	private Label etiquetaClientes;
	
	@FXML
    private TableView<Cliente> tablaClientes;
	
	@FXML
	private TableColumn<Cliente, Integer> columnaId;

	@FXML
	private TableColumn<Cliente, String> columnaNombre;

	@FXML
    private TableColumn<Cliente, String> columnaApellido;

	@FXML
	private TableColumn<Cliente, LocalDate> columnaFDI;

	@FXML
	private TableColumn<Cliente, Character> columnaSexo;
    
	@FXML
    private TableColumn<Cliente, String> columnaCiudad;

    @FXML
    private TableColumn<Cliente, String> columnaProvincia;

    @FXML
    private TableColumn<Cliente, Integer> columnaCodPos;

    @FXML
    private TableColumn<Cliente, LocalDate> columnaFDN;

    private ClienteServicio cs = new ClienteServicio();
    
    private List<Cliente> clientes = cs.obtenerTodosLosClientes();

    
    // METODOS Y EVENTOS //
    private <S, T> void asignarValoresColumnas(TableColumn<S, T> columna, String valor) {
    	columna.setCellValueFactory(new PropertyValueFactory<>(valor));
    }

    private void actualizarTabla() {    	
    	tablaClientes.getItems().clear(); // Limpia los items de la tabla
    	tablaClientes.getItems().addAll(clientes); // Muestra items en la tabla
    	tablaClientes.refresh();
    	
    	// Esta verga no busca atributos, busca getters de los atributos de las clases!!! >:(
    	this.asignarValoresColumnas(columnaId, "dni");
    	this.asignarValoresColumnas(columnaNombre, "nombre");
    	this.asignarValoresColumnas(columnaApellido, "apellido");
    	this.asignarValoresColumnas(columnaSexo, "sexo");
    	this.asignarValoresColumnas(columnaCiudad, "ciudad");
    	this.asignarValoresColumnas(columnaProvincia, "provincia");
    	this.asignarValoresColumnas(columnaCodPos,"codPost"); // <-- Esta mierda está dando error y no entiendo por que!! (Ahora funciona, estaba pasando mal el nombre del getter)
    	this.asignarValoresColumnas(columnaFDN, "fechaNacimiento");
    	this.asignarValoresColumnas(columnaFDI, "fechaIngreso");
    }
    
    @FXML
    public void initialize() {
		this.actualizarTabla();
    }
}
