/*
 * CLASE CONTROLADORA VISTA CARGA EJERCICIO
 */

package edu.unam.controlador.ejercicio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;
import edu.unam.modelo.GrupoMuscular;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import edu.unam.servicio.EjercicioServicio;
import edu.unam.servicio.GMServicio;
import edu.unam.modelo.Ejercicio;

/*
 * 
 * NOTA:
 * 
 * DESPUES AGREGARLE LOS MENSAJES DE ERROR/AVISO DE VENTANA DONDE HAGA FALTA!
 * 
 */

public class ControladorVistaCargaEjercicio {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTCancelar;

    @FXML
    private Button BTFinalizar;
    
    @FXML
    private Label LTitulo;

    @FXML
    private ComboBox<GrupoMuscular> CBGM;

    @FXML
    private TextField txtNombre;
    
    private GMServicio sgm = new GMServicio();
    
    private EjercicioServicio sejer = new EjercicioServicio();

//  private List<GrupoMuscular> listaGM = new ArrayList();


    // METODOS Y EVENTOS //
    private void actualizarComboBox(List<GrupoMuscular> listaGM) {
    	this.CBGM.getItems().clear();
    	this.CBGM.getItems().addAll(listaGM);
    	
    	// DEFINE EL FORMATO DE VISUALIZACION DEL CB
    	this.CBGM.setConverter(new StringConverter<GrupoMuscular>() {
            @Override
            public String toString(GrupoMuscular gm) {
                if (gm == null) {
                    return null; // SI NO EXISTE ELEMENTO EN EL CB
                }
                return gm.getNombreGrupo();
            }
            
			@Override
			public GrupoMuscular fromString(String string) {
				return null;
			}
    	});
    }
    
    // EVITA QUE SE INGRESEN DIGITOS NUMERICOS EN UN TEXTFIELD
    private void limitarATextoSinNumeros(TextField textField) {
    	// FILTRO
    	UnaryOperator<TextFormatter.Change> stringFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z]*")) {
                return change;
            }
            return null;
        };

        // NO LLEVA CONVERSOR POR QUE NO SE CASTEA EN ESTE CASO
        TextFormatter<String> textFormatter = new TextFormatter<>(stringFilter);
        textField.setTextFormatter(textFormatter);
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
    
    @FXML
    void eventoBTFinalizar(ActionEvent event) {
    	String nombreEj = this.txtNombre.getText();
    	GrupoMuscular regGm = this.CBGM.getSelectionModel().getSelectedItem();
    	
    	if (nombreEj.isBlank()) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE CAMPOS",
    				"Faltan campos que completar..."
    		);
    		System.out.println("[ ERROR ] > No puede haber campos en blanco!"); // LOG
    		return;
    	}
    	
    	if (regGm == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE CAMPOS",
    				"Elija un grupo muscular..."
    		);
    		System.out.println("[ ERROR ] > No se ah seleccionado un grupo muscular!"); // LOG
    		return;
    	}
    	
    	// BLOQUE DE VERIFICACIÓN DE NOMBRE REPETIDO //
    	List<Ejercicio> listaEj = this.sejer.obtenerTodosLosEjercicios();
    	
    	boolean ejConNombreRepetido = false;
    	for (Ejercicio regEj : listaEj) {
    		if (regEj.getNombreEjercicio().equals(nombreEj.toLowerCase())) {
    			ejConNombreRepetido = true;
    			break;
    		}
    	}
    	
    	if (ejConNombreRepetido) {
        	this.lanzarMensaje(
        			AlertType.WARNING, "Atención!",
        			"NOMBRES DUPLICADOS",
        			"Ya existe un ejercicio con este "
        			+ "nombre asociado a un grupo muscular..."
        	);
    		System.err.println("[ ! ] > Existencia de nombre repetidos!");
        	return;
    	}
    	
    	// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Creación de ejercicio",
    			"OPERACION DE CARGA", "Confirmar operación?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Operación cancelada!"); // LOG
        	return;
    	}
    	
    	Ejercicio ej = new Ejercicio(nombreEj, regGm);
    	
    	this.sejer.cargarEjercicio(ej);
    	
    	if (this.sejer.obtenerEjercicio(ej.getIdEjercicio()) == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"OPERACION FALLIDA", "Ocurrio un fallo al cargar el ejercicio..."
    		);
    		System.out.println("[ ERROR ] > Fallo al cargar el ejercicio!"); // LOG
    		return;
    	}
    	
    	this.lanzarMensaje(
    			AlertType.INFORMATION, "Exito!",
    			"OPERACION REALIZADA",
    			"El ejercicio fue cargado con exito..."
    	);
    	
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_EJERCICIO,
					BTFinalizar
			);
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					BTFinalizar,
					"Ejercicio"
			);
    }

    @FXML
    void eventoBTCancelar(ActionEvent event) {
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_EJERCICIO,
					BTCancelar
			);
    	NavegadorDeVistasSingleton
			.getInstancia()
			.cambiarVista(
					BTCancelar,
					"Ejercicio"
			);
    }
    
    @FXML
    private void initialize() {
    	this.actualizarComboBox(this.sgm.obtenerTodosLosGM());
    	this.limitarATextoSinNumeros(txtNombre);
    	
    	this.LTitulo.setText("Cargar ejercicio");
    }
}
