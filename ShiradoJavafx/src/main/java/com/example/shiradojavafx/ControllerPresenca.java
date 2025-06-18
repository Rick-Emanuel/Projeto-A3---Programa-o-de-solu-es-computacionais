
package com.example.shiradojavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ControllerPresenca implements Initializable {

    @FXML private TableView<Presenca> tableView;
    @FXML private TableColumn<Presenca, Integer> colRa;
    @FXML private TableColumn<Presenca, String> colNome;
    @FXML private TableColumn<Presenca, String> colCurso;
    @FXML private TableColumn<Presenca, String> colTurno;
    @FXML private TableColumn<Presenca, LocalDate> colData;
    @FXML private TableColumn<Presenca, Boolean> colPresente;

    @FXML private TextField searchField;
    @FXML private Button buscarButton;
    @FXML private ComboBox<String> filtroCurso;
    @FXML private ComboBox<String> filtroTurno;

    private ObservableList<Presenca> presencas = FXCollections.observableArrayList();
    private ObservableList<Presenca> presencasOriginais = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        popularComboBoxes();
        carregarAlunosParaPresenca();
        configurarFiltros();
    }

    private void configurarColunas() {
        colRa.setCellValueFactory(new PropertyValueFactory<>("ra"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        colPresente.setCellFactory(column -> new PresencaRadioCell());

        tableView.setItems(presencas);
        tableView.setEditable(true);
    }

    private void popularComboBoxes() {
        try {
            List<CadastroDeEstudante> alunos = AlunoDAO.listarTodosAlunos();

            Set<String> cursosSet = new HashSet<>();
            Set<String> turnosSet = new HashSet<>();


            for (CadastroDeEstudante aluno : alunos) {
                cursosSet.add(aluno.getCurso());
                turnosSet.add(aluno.getTurno());
            }


            ObservableList<String> cursos = FXCollections.observableArrayList(cursosSet);
            cursos.add(0, "Todos os cursos"); // Adicionar opção "Todos"
            filtroCurso.setItems(cursos);
            filtroCurso.setValue("Todos os cursos");


            ObservableList<String> turnos = FXCollections.observableArrayList(turnosSet);
            turnos.add(0, "Todos os turnos"); // Adicionar opção "Todos"
            filtroTurno.setItems(turnos);
            filtroTurno.setValue("Todos os turnos");

        } catch (SQLException e) {
            showAlert("Erro", "Falha ao carregar opções de filtro: " + e.getMessage());
        }
    }

    private void configurarFiltros() {

        buscarButton.setOnAction(e -> aplicarFiltros());
        searchField.setOnAction(e -> aplicarFiltros());


        filtroCurso.setOnAction(e -> aplicarFiltros());
        filtroTurno.setOnAction(e -> aplicarFiltros());
    }

    private void aplicarFiltros() {
        String termoBusca = searchField.getText().toLowerCase().trim();
        String cursoSelecionado = filtroCurso.getValue();
        String turnoSelecionado = filtroTurno.getValue();

        ObservableList<Presenca> presencasFiltradas = FXCollections.observableArrayList();

        for (Presenca presenca : presencasOriginais) {
            boolean incluir = true;


            if (!termoBusca.isEmpty()) {
                if (!presenca.getNomeCompleto().toLowerCase().contains(termoBusca)) {
                    incluir = false;
                }
            }


            if (cursoSelecionado != null && !cursoSelecionado.equals("Todos os cursos")) {
                if (!presenca.getCurso().equals(cursoSelecionado)) {
                    incluir = false;
                }
            }


            if (turnoSelecionado != null && !turnoSelecionado.equals("Todos os turnos")) {
                if (!presenca.getTurno().equals(turnoSelecionado)) {
                    incluir = false;
                }
            }

            if (incluir) {
                presencasFiltradas.add(presenca);
            }
        }

        presencas.setAll(presencasFiltradas);
    }


    private static class PresencaRadioCell extends TableCell<Presenca, Boolean> {
        private final RadioButton radioSim = new RadioButton("Sim");
        private final RadioButton radioNao = new RadioButton("Não");
        private final HBox container = new HBox(10, radioSim, radioNao);
        private final ToggleGroup group = new ToggleGroup();

        public PresencaRadioCell() {
            radioSim.setToggleGroup(group);
            radioNao.setToggleGroup(group);

            radioSim.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) atualizarPresenca(true);
            });

            radioNao.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) atualizarPresenca(false);
            });
        }

        private void atualizarPresenca(boolean presente) {
            Presenca presenca = getTableRow().getItem();
            if (presenca != null) {
                presenca.setPresente(presente);
            }
        }

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                setGraphic(null);
            } else {
                Presenca presenca = getTableRow().getItem();
                if (presenca.isPresente()) {
                    radioSim.setSelected(true);
                } else {
                    radioNao.setSelected(true);
                }
                setGraphic(container);
            }
        }
    }

    private void carregarAlunosParaPresenca() {
        try {
            List<CadastroDeEstudante> alunos = AlunoDAO.listarTodosAlunos();
            LocalDate dataAtual = LocalDate.now();

            presencasOriginais.clear();
            for (CadastroDeEstudante aluno : alunos) {
                presencasOriginais.add(new Presenca(
                        aluno.getRa(),
                        aluno.getNomeCompleto(),
                        aluno.getCurso(),
                        aluno.getTurno(),
                        dataAtual,
                        false
                ));
            }


            presencas.setAll(presencasOriginais);

        } catch (SQLException e) {
            showAlert("Erro", "Falha ao carregar alunos: " + e.getMessage());
        }
    }

    @FXML
    private void handleSalvarPresencas() {
        try {

            PresencaDAO.salvarPresencas(presencas);
            showAlert("Sucesso", "Presenças salvas com sucesso!");
        } catch (SQLException e) {
            showAlert("Erro", "Falha ao salvar presenças: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}