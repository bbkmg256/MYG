/*
 * CLASE CONTROLADOR VISTA CARGA SEGUIMIENTOS
 */

package edu.unam.controlador.seguimiento;

import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import utilidades.AlmacenadorDeEntidades;
import utilidades.NavegadorDeVistas;
import utilidades.RutasVistas;
import edu.unam.servicio.SeguimientoServicio;
import edu.unam.servicio.EjercicioServicio;
import edu.unam.servicio.EntrenamientoServicio;
import java.util.Optional;
import java.util.function.UnaryOperator;
import edu.unam.modelo.Ejercicio;
import edu.unam.modelo.Entrenamiento;
import java.time.LocalDate;
import edu.unam.modelo.Seguimiento;

/*
 * 
 */

public class ControladorVistaCargarSeg {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTCancelar;

    @FXML
    private Button BTFinalizar;

    @FXML
    private ComboBox<Ejercicio> CBEjercicio;

    @FXML
    private DatePicker DPFecha;

    @FXML
    private Label LBTitulo;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtRep;

    @FXML
    private TextField txtSeries;
    
    private SeguimientoServicio sseg;
    
    private EjercicioServicio sej;
    
    private EntrenamientoServicio sentre;
    
    private Entrenamiento ent;
    
    private LocalDate fechaInicioEnt, fechaFinEnt;

    
    // METODOS Y EVENTOS //
    private void inicializarDatos() {
    	this.sseg = new SeguimientoServicio();
    	this.sej = new EjercicioServicio();
    	this.sentre = new EntrenamientoServicio();
    	this.ent = AlmacenadorDeEntidades.getInstancia().getEntrenamiento();
    	this.fechaInicioEnt = this.sentre.obtenerEntrenamiento(this.ent.getIdEntrenamiento()).getFechaInicio();
    	this.fechaFinEnt = this.sentre.obtenerEntrenamiento(this.ent.getIdEntrenamiento()).getFechaFin();
    }
    
    // ACTUALIZA Y CARGA DATOS AL COMBOBOX
    private void actualizarComboBox() {
    	this.CBEjercicio.getItems().clear();
    	this.CBEjercicio.getItems().addAll(this.sej.obtenerTodosLosEjercicios());
    	
    	this.CBEjercicio.setConverter(new StringConverter<Ejercicio>() {
    		@Override
    		public String toString(Ejercicio ej) {
    			return (ej == null) ? null : ej.getNombreEjercicio();
    		}
    		
    		@Override
    		public Ejercicio fromString(String string) {
    			return null;
    		}
    	});
    }
    
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
    
    // FILTRA LA ENTRADA DE LOS TEXTFIELD
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
    
    public void iniciar() {
    	this.inicializarDatos();
    	this.actualizarComboBox();
    }
    
    @FXML
    private void eventoBTCancelar(ActionEvent event) {
    	NavegadorDeVistas
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_ABM_SEG
	    	);
	
		ControladorVistaABMSeg CVABMS =
				NavegadorDeVistas
					.getInstancia()
					.obtenerControladorDeNuevaVista();
		
		CVABMS.iniciar();
		
		NavegadorDeVistas
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTCancelar,
	    			"Seguimiento"
	    	);
    }

    @FXML
    private void eventoBTFinalizar(ActionEvent event) {
    	LocalDate fecha = this.DPFecha.getValue();
    	Ejercicio ej =  this.CBEjercicio.getValue();
    	
    	// SE PARSEA DESPUES DE LAS COMPROBACIONES
    	int serie, repeticion;
    	double peso;
    	
    	boolean hayCamposErroneos = false;
    	
    	if (this.txtSeries.getText().isBlank()) {
    		hayCamposErroneos = true;
    		System.err.println("[ ERROR ] > Debe ingresar un numero de series para continuas!"); // LOG
    	}
    	
    	if (!hayCamposErroneos && this.txtRep.getText().isBlank()) {
    		hayCamposErroneos = true;
    		System.err.println("[ ERROR ] > Debe ingresar un numero de repeticiones para continuar!"); // LOG
    	}
    	
    	if (!hayCamposErroneos && this.txtPeso.getText().isBlank()) {
    		hayCamposErroneos = true;
    		System.err.println("[ ERROR ] > Debe ingresar un numero de peso trabajado para continuar!"); // LOG
    	}
    	
    	if (!hayCamposErroneos && ej == null) {
    		hayCamposErroneos = true;
    		System.err.println("[ ERROR ] > Debe seleccionar un ejercicio para continuar!"); // LOG
    	}
    	
    	if (!hayCamposErroneos && fecha == null) {
    		hayCamposErroneos = true;
    		System.err.println("[ ERROR ] > Debe seleccionar una fecha para continuar!"); // LOG
    	}
    	
    	if (hayCamposErroneos) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE CAMPOS",
    				"No puede dejar campos sin rellenar o completar..."
    		);
    		return;
    	}
    	
    	// LA FECHA DE REALIZACION DEL SEGUIMIENTO DEBE ESTAR ENTRE LA FECHA DE //
    	// INICIO Y FINAL DEL ENTRENAMIENTO, DE LO CONTRARIO ES ERRONEO			//
    	if (fecha.isBefore(this.fechaInicioEnt)) {
    		hayCamposErroneos = true;
    	}
    	
    	if (fecha.isAfter(this.fechaFinEnt)) {
    		hayCamposErroneos = true;
    	}
    	
    	if (hayCamposErroneos) {
    		this.lanzarMensaje(
    				AlertType.ERROR,
    				"Error!",
    				"ERROR DE CAMPOS",
    				"La fecha no puede ser anterior a " +
    				this.fechaInicioEnt +
    				" y posterior a "+
    				this.fechaFinEnt
    		);
    		System.err.println("[ ERROR ] > Fecha fuera de rango!"); // LOG
    		return;
    	}

    	// PARSEO DE SERIE, REPETICION Y LA FECHA INGRESADA
    	serie = Integer.parseInt(this.txtSeries.getText());
    	repeticion = Integer.parseInt(this.txtRep.getText());
    	peso = Double.parseDouble(this.txtPeso.getText());
    	
    	Seguimiento seg = new Seguimiento(
    			fecha, ej, serie, repeticion, peso,
    			AlmacenadorDeEntidades
    				.getInstancia()
    				.getEntrenamiento()
    	);
    	
    	// PERSISTE //
    	this.sseg.cargarSeguimiento(seg);
    	
    	// COMRPUEBA SI LA PERSISTENCIA EXISTE //
    	if (this.sseg.obtenerSeguimiento(seg.getIdSeguimiento()) == null) {
	    	this.lanzarMensaje(
	    			AlertType.ERROR, 
	    			"Error!", 
	    			"ERROR DE OPERACION", 
	    			"Fallo al cargar el seguimiento, intentelo denuevo..."
	    	);
	    	System.err.println(
	    			"[ ERROR ] > Ocurrieron problemas " +
	    			"al cargar el seguimiento!"
	    	);
    		return;
    	}
    	
    	// TOD0 CORRECTO //
    	this.lanzarMensaje(
    			AlertType.INFORMATION, 
    			"Exito!", 
    			"OPERACION REALIZADA", 
    			"El seguimiento se cargó correctamente..."
    	);
    	
    	NavegadorDeVistas
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_ABM_SEG
	    	);
	
		ControladorVistaABMSeg CVABMS =
				NavegadorDeVistas
					.getInstancia()
					.obtenerControladorDeNuevaVista();
		
		CVABMS.iniciar();
		
		NavegadorDeVistas
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTCancelar,
	    			"Seguimiento"
	    	);
    }
    
    @FXML
    private void initialize() {
    	this.LBTitulo.setText("Cargar seguimiento");
    	
    	this.DPFecha.setEditable(false);
    	
    	this.limitarATextoNumerico(this.txtPeso);
    	this.limitarATextoNumerico(this.txtSeries);
    	this.limitarATextoNumerico(this.txtRep);
    }
}
