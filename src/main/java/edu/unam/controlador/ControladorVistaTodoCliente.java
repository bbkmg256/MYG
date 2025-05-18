/*
 * CONTROLADOR DE LA VISTA DE CLIENTES
 */

package edu.unam.controlador;

// JavaFX
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
// import javafx.event.ActionEvent;

// Varios
import java.time.LocalDate;
import java.util.List;

// Entidad
import edu.unam.modelo.Cliente;

// Servicios
import edu.unam.servicio.ClienteServicio;


public class ControladorVistaTodoCliente {
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

    private void asignarValoresColumnas(TableColumn columna, String valor) {
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
    

    public void initialize() {
		this.actualizarTabla();
    }
}
