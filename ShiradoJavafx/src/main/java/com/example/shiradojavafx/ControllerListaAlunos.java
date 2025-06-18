package com.example.shiradojavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerListaAlunos implements Initializable {
    @FXML private TableView<CadastroDeEstudante> alunoTable;
    @FXML private TableColumn<CadastroDeEstudante, String> colNome;
    @FXML private TableColumn<CadastroDeEstudante, String> colEmail;
    @FXML private TableColumn<CadastroDeEstudante, Integer> colRA;
    @FXML private TableColumn<CadastroDeEstudante, String> colCurso;
    @FXML private TableColumn<CadastroDeEstudante, String> colTurno;
    @FXML private TableColumn<CadastroDeEstudante, String> colTelefone; // Alterado para String
    @FXML private Button voltarButton;
    @FXML private Button atualizarButton;
    @FXML private Label totalAlunosLabel;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> filtroCurso;
    @FXML private ComboBox<String> filtroRA;
    @FXML private Button deletarButton;

    private ObservableList<CadastroDeEstudante> estudantes = FXCollections.observableArrayList();
    private ObservableList<CadastroDeEstudante> todosEstudantes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRA.setCellValueFactory(new PropertyValueFactory<>("ra"));
        colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        alunoTable.setItems(estudantes);
        carregarDadosDoBanco();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> aplicarFiltros());
        filtroCurso.setOnAction(e -> aplicarFiltros());
        filtroRA.setOnAction(e -> aplicarFiltros());
    }

    private void carregarDadosDoBanco() {
        try {
            List<CadastroDeEstudante> lista = AlunoDAO.listarTodosAlunos();
            todosEstudantes.setAll(lista);
            estudantes.setAll(lista);
            totalAlunosLabel.setText("Total de alunos: " + lista.size());

            filtroCurso.setItems(FXCollections.observableArrayList(
                    lista.stream().map(CadastroDeEstudante::getCurso).distinct().collect(Collectors.toList())));
            filtroRA.setItems(FXCollections.observableArrayList(
                    lista.stream().map(e -> String.valueOf(e.getRa())).distinct().collect(Collectors.toList())));

        } catch (SQLException e) {
            showAlert("Erro ao carregar", "Não foi possível carregar os dados do banco de dados: " + e.getMessage());
        }
    }

    private void aplicarFiltros() {
        String nomeFiltro = searchField.getText().toLowerCase();
        String cursoFiltro = filtroCurso.getValue();
        String raFiltro = filtroRA.getValue();

        estudantes.setAll(todosEstudantes.stream()
                .filter(e -> (nomeFiltro.isEmpty() || e.getNomeCompleto().toLowerCase().contains(nomeFiltro)))
                .filter(e -> (cursoFiltro == null || e.getCurso().equals(cursoFiltro)))
                .filter(e -> (raFiltro == null || String.valueOf(e.getRa()).equals(raFiltro)))
                .collect(Collectors.toList()));
    }

    @FXML
    void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cadastro_aluno.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema de Cadastro - Cadastrar Alunos");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erro", "Erro ao voltar para a tela de cadastro: " + e.getMessage());
        }
    }

    @FXML
    void handleAtualizar(ActionEvent event) {
        carregarDadosDoBanco();
        showAlert("Atualizado", "A lista de alunos foi atualizada com sucesso!");
    }

    @FXML
    void handleDeletar(ActionEvent event) {
        CadastroDeEstudante selecionado = alunoTable.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                AlunoDAO.deletarAluno(selecionado.getRa());
                carregarDadosDoBanco();
                showAlert("Sucesso", "Aluno removido com sucesso.");
            } catch (SQLException e) {
                showAlert("Erro", "Não foi possível remover o aluno: " + e.getMessage());
            }
        } else {
            showAlert("Aviso", "Selecione um aluno para excluir.");
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