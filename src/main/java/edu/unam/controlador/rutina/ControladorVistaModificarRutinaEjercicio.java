package edu.unam.controlador.rutina;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

import edu.unam.modelo.Ejercicio;
import edu.unam.modelo.Rutina;
import edu.unam.modelo.RutinaEjercicio;
import edu.unam.servicio.EjercicioServicio;
import edu.unam.servicio.RutinaEjercicioServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import utilidades.NavegadorDeVistas;
import utilidades.RutasVistas;
import utilidades.parametros.ParametrosRutinaEjercicio;

public class ControladorVistaModificarRutinaEjercicio {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTCancelar;

    @FXML
    private Button BTFinalizar;

    @FXML
    private ComboBox<Ejercicio> CBEjercicio;

    @FXML
    private TextField txtRep;

    @FXML
    private TextField txtSeries;
    
    private EjercicioServicio sejer = new EjercicioServicio();
    
    private Rutina rutina = null;
    
    private RutinaEjercicioServicio sre = new RutinaEjercicioServicio();
    
    private ParametrosRutinaEjercicio paramRE = new ParametrosRutinaEjercicio();
    
    
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
    
    private void actualizarComboBox(List<Ejercicio> listaEj) {
    	this.CBEjercicio.getItems().clear();
    	this.CBEjercicio.getItems().addAll(listaEj);
    	
    	// DEFINE EL FORMATO DE VISUALIZACION DEL CB
    	this.CBEjercicio.setConverter(new StringConverter<Ejercicio>() {
            @Override
            public String toString(Ejercicio ej) {
                if (ej == null) {
                    return null; // SI NO EXISTE ELEMENTO EN EL CB
                }
                return ej.getNombreEjercicio();
            }
            
			@Override
			public Ejercicio fromString(String string) {
				return null;
			}
    	});
    }
    
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
    
    // RECIBE NO SOLO LA RUTINA PARA MANTENER CONTEXTO, SINO TAMBIEN EL ELEMENTO QUE MODIFICA
    // OSEA EL REGISTRO SELECCIONADO DE LA ENTIDAD "RutinaEjercicio".
    public void establecerRutinaYRE(Rutina regRut, RutinaEjercicio regRE) {
    	// LA RUTINA
    	this.rutina = regRut;
    	
    	// ELEMENTOS DE RutinaEjercicio
    	this.CBEjercicio.setValue(regRE.getEjercicio());
    	this.txtSeries.setText(Integer.toString(regRE.getSerie()));
    	this.txtRep.setText(Integer.toString(regRE.getRepeticion()));
    }

    @FXML
    private void eventoBTCancelar(ActionEvent event) {
    	NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_RUTEJ
			);
    	
    	ControladorVistaABMRutinaEjercicio CVABMRE =
    			NavegadorDeVistas
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABMRE.establecerRutina(this.rutina);
    	
    	NavegadorDeVistas
			.getInstancia()
			.cambiarVista(
					this.BTFinalizar,
					"Rutina"
			);
    }

    @FXML
    private void eventoBTFinalizar(ActionEvent event) {
    	if (this.CBEjercicio.getSelectionModel().getSelectedItem() == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE CAMPO",
    				"Debe seleccionar un ejercicio..."
    		);
    		System.out.println(
    				"[ ERROR ] > Debe seleccionar un " +
    				"ejercicio para la rutina"
    		);
    		return;
    	}
    	
    	if (this.txtSeries.getText().isBlank() || this.txtRep.getText().isBlank()) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE CAMPO",
    				"Tiene que agregar un numero de serie y de repeticion para la rutina..."
    		);
    		System.out.println("[ ERROR ] > Faltan completar campos");
    		return;
    	}
    	
//    	Ejercicio ej = this.CBEjercicio.getSelectionModel().getSelectedItem();
//    	int serie, rep;
//    	serie = Integer.parseInt(this.txtSeries.getText());
//    	rep = Integer.parseInt(this.txtRep.getText());
//    	RutinaEjercicio re = new RutinaEjercicio(this.rutina, ej, serie, rep);
    	
    	this.paramRE.ejercicio = this.CBEjercicio.getSelectionModel().getSelectedItem();
    	this.paramRE.serie = Integer.parseInt(this.txtSeries.getText());
    	this.paramRE.repeticion = Integer.parseInt(this.txtRep.getText());
    	Boolean actualizacionCorrecta = this.sre.actualizarEstadoRutina(this.rutina.getIdRutina(), this.paramRE);
    	
//    	this.sre.crearRE(re);
//    	
//    	if (this.sre.obtenerRE(re.getId()) == null) {
//    		this.lanzarMensaje(
//    				AlertType.ERROR, "Error!",
//    				"OPERACION FALLIDA",
//    				"Ocurrio un fallo al cargar los elementos a la rutina..."
//    		);
//    		System.out.println(
//    				"[ ERROR ] > No se cargaron correctamente " +
//    				"los elementos a la rutina"
//    		);
//    		return;
//    	}
    	
    	if (!actualizacionCorrecta) {
			this.lanzarMensaje(
					AlertType.ERROR, "Error!",
					"OPERACION FALLIDA",
					"Ocurrio un fallo al actualizar los elementos a la rutina..."
			);
			System.out.println(
					"[ ERROR ] > No se actualizaron correctamente " +
					"los elementos a la rutina"
			);
			return;
    	}
    	
    	this.lanzarMensaje(
    			AlertType.INFORMATION, "Exito!",
    			"OPERACION REALIZADA",
    			"Los elementos fueron actualizados con exito..."
    	);
    	
    	NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_RUTEJ
			);
    	
    	ControladorVistaABMRutinaEjercicio CVABMRE =
    			NavegadorDeVistas
    				.getInstancia()
    				.obtenerControladorDeNuevaVista();
    	
    	CVABMRE.establecerRutina(this.rutina);
    	
    	NavegadorDeVistas
			.getInstancia()
			.cambiarVista(
					this.BTFinalizar,
					"Rutina"
			);
    }
    
    @FXML
    private void initialize() {
    	this.actualizarComboBox(
    			this.sejer.obtenerTodosLosEjercicios()
    	);
    	
    	this.limitarATextoNumerico(this.txtSeries);
    	this.limitarATextoNumerico(this.txtRep);
    }
}
