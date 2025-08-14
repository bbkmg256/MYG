/*
 * CLASE CONTROLADOR VISTA CARGA SEGUIMIENTOS
 */

package edu.unam.controlador.seguimiento;

import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
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
import utilidades.AlmacenadorDeEntidadesSingleton;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;
import edu.unam.servicio.SeguimientoServicio;
//import edu.unam.servicio.EjercicioServicio;
import edu.unam.servicio.EntrenamientoServicio;
import edu.unam.servicio.RutinaEntrenamientoServicio;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import edu.unam.modelo.Ejercicio;
import edu.unam.modelo.Entrenamiento;
import java.time.DayOfWeek;
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
    
//    private EjercicioServicio sej;
    
    private EntrenamientoServicio sentre;
    
    private Entrenamiento ent;
    
    private LocalDate fechaInicioEnt, fechaFinEnt;
    
    private RutinaEntrenamientoServicio res;
    
    private List<Ejercicio> ej;

    
    // METODOS Y EVENTOS //
    private void inicializarDatos() {
    	this.sseg = new SeguimientoServicio();
//    	this.sej = new EjercicioServicio();
    	this.sentre = new EntrenamientoServicio();
    	this.ent = AlmacenadorDeEntidadesSingleton.getInstancia().getEntrenamiento();
    	this.fechaInicioEnt = this.sentre.obtenerEntrenamiento(this.ent.getIdEntrenamiento()).getFechaInicio();
    	this.fechaFinEnt = this.sentre.obtenerEntrenamiento(this.ent.getIdEntrenamiento()).getFechaFin();
    	
    	// NOTE: ESTA MAMADA FUNCIONA, AHORA HAY QUE APLICAR EL FILTRO A LA CARGA DE SEGUIMIENTO
    	this.res = new RutinaEntrenamientoServicio();
    	this.ej = res.obtenerEjerciciosDeLasRutinasDelEntrenamiento(this.ent);
    }
    
    /* TODO: FILTRAR EJERCICIOS DISPONIBLES PARA CARGAR,
     * SOLO PUEDEN ESTAR LOS QUE SE ENCUENTRAN ASOCIADOS A
     * RUTINAS PERTENECIENTES A X ENTRENAMIENTO.
     */
    // ACTUALIZA Y CARGA DATOS AL COMBOBOX
    private void actualizarComboBox() {
    	this.CBEjercicio.getItems().clear();
    	this.CBEjercicio.getItems().addAll(this.ej);
    	
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
    private <T> void limitarYFiltrarEntrada(
    		TextField textField,
    		StringConverter<T> conversor,
    		String regExp
    ) {
        // FILTRO DE ENTRADA DE CARACTERES
    	UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches(regExp)) {
                return change;
            }
            return null;
        };

        // CONVERSOR DE TIPOS
        TextFormatter<T> textFormatter = 
        		new TextFormatter<>(conversor, null, doubleFilter);
        textField.setTextFormatter(textFormatter);
    }
    
    public void iniciar() {
    	this.inicializarDatos();
    	this.actualizarComboBox();
    	
//    	this.limitarATextoNumerico(this.txtPeso);
//    	this.limitarATextoNumerico(this.txtSeries);
//    	this.limitarATextoNumerico(this.txtRep);
    	
    	this.limitarYFiltrarEntrada(
    			this.txtPeso, 
    			new DoubleStringConverter(), 
    			"\\d+\\.?\\d*"
    	);
    	
    	this.limitarYFiltrarEntrada(
    			this.txtSeries, 
    			new IntegerStringConverter(), 
    			"\\d*"
    	);
    	
    	this.limitarYFiltrarEntrada(
    			this.txtRep, 
    			new IntegerStringConverter(), 
    			"\\d*"
    	);
    }
    
    @FXML
    private void eventoBTCancelar(ActionEvent event) {
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_ABM_SEG,
	    			this.BTCancelar
	    	);
	
		ControladorVistaABMSeg CVABMS =
				NavegadorDeVistasSingleton
					.getInstancia()
					.obtenerControladorDeNuevaVista();
		
		CVABMS.iniciar();
		
		NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cambiarVista(
	    			this.BTCancelar,
	    			"Seguimiento"
	    	);
    }

    @FXML
    private void eventoBTFinalizar(ActionEvent event) {
    	// RESULTADO ALMACENA LA OPCION INDICADA POR EL USUARIO EN LA ALERTA
    	Optional<ButtonType> resultado =  this.lanzarMensaje(
    			AlertType.CONFIRMATION, "Creación de seguimiento",
    			"OPERACION DE CARGA", "Confirmar operación?"
    	);
    	
    	// CONFIRMAR O DENEGAR OPERACION
    	if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
    		System.out.println("[ ! ] > Operación cancelada!"); // LOG
        	return;
    	}
    	
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
    	
    	int errorDeFecha = 0;
    	
    	// LA FECHA DE REALIZACION DEL SEGUIMIENTO DEBE ESTAR ENTRE LA FECHA DE //
    	// INICIO Y FINAL DEL ENTRENAMIENTO, DE LO CONTRARIO ES ERRONEO			//
    	if (fecha.isBefore(this.fechaInicioEnt)) {
    		errorDeFecha = 1;
    	}
    	
    	if (fecha.isAfter(this.fechaFinEnt)) {
    		errorDeFecha = 2;
    	}
    	
    	// VERIFICA QUE LA FECHA CARGADA NO CAIGA UN DOMINGO
    	if (fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
    		errorDeFecha = 3;
    	}
    	
    	switch (errorDeFecha) {
			case 1, 2:
			{
	    		this.lanzarMensaje(
	    				AlertType.ERROR,
	    				"Error!",
	    				"INCONSISTENCIA DE FECHAS",
	    				"La fecha no puede ser anterior a " +
	    				this.fechaInicioEnt +
	    				" y posterior a "+
	    				this.fechaFinEnt
	    		);
	    		System.err.println("[ ERROR ] > Fecha fuera de rango!"); // LOG
	    		return;
			}
		case 3: {
	    		this.lanzarMensaje(
	    				AlertType.ERROR,
	    				"Error!",
	    				"INCONSISTENCIA DE FECHAS",
	    				"No se puede ingresar un seguimiento un dia Domingo..."
	    		);
	    		System.err.println("[ ERROR ] > Fecha no permitida!"); // LOG
	    		return;
			}
			default: {
				// SIGUE DE LARGO
			}
		}
    	
//    	if (hayCamposErroneos) {
//    		this.lanzarMensaje(
//    				AlertType.ERROR,
//    				"Error!",
//    				"ERROR DE CAMPOS",
//    				"La fecha no puede ser anterior a " +
//    				this.fechaInicioEnt +
//    				" y posterior a "+
//    				this.fechaFinEnt
//    		);
//    		System.err.println("[ ERROR ] > Fecha fuera de rango!"); // LOG
//    		return;
//    	}

    	// PARSEO DE SERIE, REPETICION Y LA FECHA INGRESADA
    	serie = Integer.parseInt(this.txtSeries.getText());
    	repeticion = Integer.parseInt(this.txtRep.getText());
    	peso = Double.parseDouble(this.txtPeso.getText());
    	
    	Seguimiento seg = new Seguimiento(
    			fecha, ej, serie, repeticion, peso,
    			AlmacenadorDeEntidadesSingleton
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
    	
    	NavegadorDeVistasSingleton
	    	.getInstancia()
	    	.cargarNuevaVista(
	    			this.getClass(),
	    			RutasVistas.VISTA_ABM_SEG,
	    			this.BTCancelar
	    	);
	
		ControladorVistaABMSeg CVABMS =
				NavegadorDeVistasSingleton
					.getInstancia()
					.obtenerControladorDeNuevaVista();
		
		CVABMS.iniciar();
		
		NavegadorDeVistasSingleton
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
    }
}
