package com.example.shiradojavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerPresenca implements Initializable {

    @FXML private TableView<Presenca> tableView;
    @FXML private TableColumn<Presenca, Integer> colRa;
    @FXML private TableColumn<Presenca, String> colNome;
    @FXML private TableColumn<Presenca, LocalDate> colData;
    @FXML private TableColumn<Presenca, Boolean> colPresente;

    private ObservableList<Presenca> presencas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colRa.setCellValueFactory(new PropertyValueFactory<>("ra"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        colPresente.setCellFactory(col -> new TableCell<Presenca, Boolean>() {
            private final Button btnPresente = new Button("Presente");

            {
                btnPresente.setStyle("-fx-background-color: #4299e1; -fx-text-fill: white; -fx-font-weight: bold;");
                btnPresente.setOnAction(event -> {
                    Presenca presenca = getTableView().getItems().get(getIndex());
                    presenca.setPresente(true);
                    try {
                        PresencaDAO.salvarPresencas(List.of(presenca));
                        btnPresente.setText("Confirmado");
                        btnPresente.setDisable(true);
                    } catch (SQLException e) {
                        showAlert("Erro", "Erro ao salvar presença: " + e.getMessage());
                    }
                });
            }

            @Override
            protected void updateItem(Boolean presente, boolean empty) {
                super.updateItem(presente, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Presenca p = getTableView().getItems().get(getIndex());
                    if (p.isPresente()) {
                        btnPresente.setText("Confirmado");
                        btnPresente.setDisable(true);
                    } else {
                        btnPresente.setText("Presente");
                        btnPresente.setDisable(false);
                    }
                    setGraphic(btnPresente);
                }
            }
        });

        tableView.setItems(presencas);
        tableView.setEditable(true);

        carregarAlunosParaPresenca();
    }

    private void carregarAlunosParaPresenca() {
        try {
            List<CadastroDeEstudante> alunos = AlunoDAO.listarTodosAlunos();
            LocalDate dataAtual = LocalDate.now();

            for (CadastroDeEstudante aluno : alunos) {
                presencas.add(new Presenca(
                        aluno.getRa(),
                        aluno.getNomeCompleto(),
                        dataAtual,
                        false // começa como ausente
                ));
            }
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
