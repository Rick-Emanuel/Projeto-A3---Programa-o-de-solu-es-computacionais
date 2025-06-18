package com.example.shiradojavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ControllerListaPresenca implements Initializable {


    @FXML private TextField searchField;
    @FXML private ComboBox<String> comboCurso;
    @FXML private ComboBox<String> comboTurno;
    @FXML private ComboBox<String> comboStatus;
    @FXML private DatePicker datePicker;
    @FXML private Button btnLimparFiltros;


    @FXML private TableView<Presenca> tableView;
    @FXML private TableColumn<Presenca, Integer> colRa;
    @FXML private TableColumn<Presenca, String> colNome;
    @FXML private TableColumn<Presenca, String> colCurso;
    @FXML private TableColumn<Presenca, String> colTurno;
    @FXML private TableColumn<Presenca, LocalDate> colData;
    @FXML private TableColumn<Presenca, String> colPresente;

    private ObservableList<Presenca> todasPresencas = FXCollections.observableArrayList();
    private ObservableList<Presenca> presencasFiltradas = FXCollections.observableArrayList();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        configurarComboBoxes();
        configurarFiltros();
        carregarDados();

        System.out.println("Controller inicializado com filtros");
    }

    private void configurarColunas() {

        if (colRa != null) {
            colRa.setCellValueFactory(new PropertyValueFactory<>("ra"));
            colRa.setPrefWidth(80);
        }

        if (colNome != null) {
            colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
            colNome.setPrefWidth(200);
        }

        if (colCurso != null) {
            colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
            colCurso.setPrefWidth(150);
        }

        if (colTurno != null) {
            colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
            colTurno.setPrefWidth(100);
        }

        if (colData != null) {
            colData.setCellValueFactory(new PropertyValueFactory<>("data"));
            colData.setPrefWidth(120);
        }

        if (colPresente != null) {
            colPresente.setCellValueFactory(cellData -> {
                boolean presente = cellData.getValue().isPresente();
                return new javafx.beans.property.SimpleStringProperty(presente ? "Presente" : "Ausente");
            });
            colPresente.setPrefWidth(100);
        }

        if (tableView != null) {
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
    }

    private void configurarComboBoxes() {

        if (comboStatus != null) {
            comboStatus.getItems().addAll("Todos", "Presente", "Ausente");
            comboStatus.setValue("Todos");
        }
    }

    private void configurarFiltros() {

        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> aplicarFiltros());
        }


        if (comboCurso != null) {
            comboCurso.valueProperty().addListener((observable, oldValue, newValue) -> aplicarFiltros());
        }


        if (comboTurno != null) {
            comboTurno.valueProperty().addListener((observable, oldValue, newValue) -> aplicarFiltros());
        }


        if (comboStatus != null) {
            comboStatus.valueProperty().addListener((observable, oldValue, newValue) -> aplicarFiltros());
        }


        if (datePicker != null) {
            datePicker.valueProperty().addListener((observable, oldValue, newValue) -> aplicarFiltros());
        }
    }

    private void carregarDados() {
        executor.execute(() -> {
            try {
                List<Presenca> presencas = PresencaDAO.listarPresencas();

                javafx.application.Platform.runLater(() -> {
                    todasPresencas.setAll(presencas);
                    presencasFiltradas.setAll(presencas);

                    if (tableView != null) {
                        tableView.setItems(presencasFiltradas);
                    }


                    preencherComboBoxes(presencas);

                    System.out.println("Dados carregados: " + presencas.size() + " registros");
                });

            } catch (SQLException e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() ->
                        mostrarAlerta("Erro", "Falha ao carregar presenças: " + e.getMessage()));
            }
        });
    }

    private void preencherComboBoxes(List<Presenca> presencas) {

        if (comboCurso != null) {
            List<String> cursos = presencas.stream()
                    .map(Presenca::getCurso)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            comboCurso.getItems().clear();
            comboCurso.getItems().add("Todos os cursos");
            comboCurso.getItems().addAll(cursos);
            comboCurso.setValue("Todos os cursos");
        }


        if (comboTurno != null) {
            List<String> turnos = presencas.stream()
                    .map(Presenca::getTurno)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            comboTurno.getItems().clear();
            comboTurno.getItems().add("Todos os turnos");
            comboTurno.getItems().addAll(turnos);
            comboTurno.setValue("Todos os turnos");
        }
    }

    private void aplicarFiltros() {
        List<Presenca> listaFiltrada = todasPresencas.stream()
                .filter(this::passaNoFiltroRA)
                .filter(this::passaNoFiltroCurso)
                .filter(this::passaNoFiltroTurno)
                .filter(this::passaNoFiltroStatus)
                .filter(this::passaNoFiltroData)
                .collect(Collectors.toList());

        presencasFiltradas.setAll(listaFiltrada);


        if (listaFiltrada.isEmpty() && !todasPresencas.isEmpty()) {
            System.out.println("Nenhum registro encontrado com os filtros aplicados");
        } else {
            System.out.println("Filtros aplicados: " + listaFiltrada.size() + " registros encontrados");
        }
    }

    private boolean passaNoFiltroRA(Presenca presenca) {
        if (searchField == null || searchField.getText() == null || searchField.getText().trim().isEmpty()) {
            return true;
        }

        try {
            int raFiltro = Integer.parseInt(searchField.getText().trim());
            return presenca.getRa() == raFiltro;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean passaNoFiltroCurso(Presenca presenca) {
        if (comboCurso == null || comboCurso.getValue() == null ||
                "Todos os cursos".equals(comboCurso.getValue())) {
            return true;
        }
        return presenca.getCurso().equals(comboCurso.getValue());
    }

    private boolean passaNoFiltroTurno(Presenca presenca) {
        if (comboTurno == null || comboTurno.getValue() == null ||
                "Todos os turnos".equals(comboTurno.getValue())) {
            return true;
        }
        return presenca.getTurno().equals(comboTurno.getValue());
    }

    private boolean passaNoFiltroStatus(Presenca presenca) {
        if (comboStatus == null || comboStatus.getValue() == null ||
                "Todos".equals(comboStatus.getValue())) {
            return true;
        }

        boolean presente = presenca.isPresente();
        String statusSelecionado = comboStatus.getValue();

        return (statusSelecionado.equals("Presente") && presente) ||
                (statusSelecionado.equals("Ausente") && !presente);
    }

    private boolean passaNoFiltroData(Presenca presenca) {
        if (datePicker == null || datePicker.getValue() == null) {
            return true;
        }
        return presenca.getData().equals(datePicker.getValue());
    }

    @FXML
    private void limparFiltros() {
        if (searchField != null) searchField.clear();
        if (comboCurso != null) comboCurso.setValue("Todos os cursos");
        if (comboTurno != null) comboTurno.setValue("Todos os turnos");
        if (comboStatus != null) comboStatus.setValue("Todos");
        if (datePicker != null) datePicker.setValue(null);


        aplicarFiltros();

        System.out.println("Filtros limpos");
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }


    public void shutdown() {
        executor.shutdown();
    }
}