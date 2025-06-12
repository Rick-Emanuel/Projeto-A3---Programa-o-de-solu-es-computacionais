package com.example.shiradojavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerCadastro implements Initializable {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private TextField telefoneField;
    @FXML private TextField raField;
    @FXML private SplitMenuButton cursoSplitMenuButton;
    @FXML private SplitMenuButton turnoSplitMenuButton;
    @FXML private Button enviarButton;
    @FXML private TableView<CadastroDeEstudante> alunoTable;
    @FXML private TableColumn<CadastroDeEstudante, String> colNome;
    @FXML private TableColumn<CadastroDeEstudante, String> colEmail;
    @FXML private TableColumn<CadastroDeEstudante, Integer> colRA;
    @FXML private TableColumn<CadastroDeEstudante, String> colCurso;
    @FXML private TableColumn<CadastroDeEstudante, String> colTurno;

    private ObservableList<CadastroDeEstudante> estudantes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cursoSplitMenuButton.getItems().clear();
        String[] cursos = {
                "Administração",
                "Analise e Desenvolvimento de Sistemas",
                "Engenharia",
                "Direito",
                "Medicina"
        };
        for (String curso : cursos) {
            MenuItem item = new MenuItem(curso);
            item.setOnAction(e -> cursoSplitMenuButton.setText(curso));
            cursoSplitMenuButton.getItems().add(item);
        }

        turnoSplitMenuButton.getItems().clear();
        String[] turnos = {"Matutino", "Vespertino", "Noturno"};
        for (String turno : turnos) {
            MenuItem item = new MenuItem(turno);
            item.setOnAction(e -> turnoSplitMenuButton.setText(turno));
            turnoSplitMenuButton.getItems().add(item);
        }

        cursoSplitMenuButton.setText("Selecione o Curso");
        turnoSplitMenuButton.setText("Selecione o Turno");

        if (colNome != null) colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        if (colEmail != null) colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (colRA != null) colRA.setCellValueFactory(new PropertyValueFactory<>("ra"));
        if (colCurso != null) colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        if (colTurno != null) colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));

        if (alunoTable != null) alunoTable.setItems(estudantes);
    }

    @FXML
    void handleEnviar(ActionEvent event) {
        try {
            String nome = nomeField.getText().trim();
            if (nome.length() <= 5) {
                showAlert("Erro", "O nome deve ter mais que 5 caracteres");
                return;
            }

            int ra = Integer.parseInt(raField.getText());
            if (ra <= 0 || ra >= 100) {
                showAlert("Erro", "O RA deve ser maior que 0 e menor que 100");
                return;
            }

            String curso = cursoSplitMenuButton.getText();
            if (curso.equals("Selecione o Curso")) {
                showAlert("Erro", "Por favor, selecione um curso válido");
                return;
            }

            String turno = turnoSplitMenuButton.getText();
            if (turno.equals("Selecione o Turno")) {
                showAlert("Erro", "Por favor, selecione um turno válido");
                return;
            }

            String email = emailField.getText().trim();
            int telefone = Integer.parseInt(telefoneField.getText());

            CadastroDeEstudante novoEstudante = new CadastroDeEstudante(
                    ra,
                    nome,
                    curso,
                    turno,
                    email,
                    telefone
            );

            try {
                AlunoDAO.inserirAluno(novoEstudante);
                estudantes.add(novoEstudante);
                limparCampos();
                showAlert("Sucesso", "Estudante cadastrado com sucesso!");
            } catch (SQLException e) {
                showAlert("Erro", "Falha ao salvar no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }


        } catch (NumberFormatException e) {
            showAlert("Erro", "RA e Telefone devem ser números válidos");
        }
    }

    private void limparCampos() {
        nomeField.clear();
        emailField.clear();
        telefoneField.clear();
        raField.clear();
        cursoSplitMenuButton.setText("Selecione o Curso");
        turnoSplitMenuButton.setText("Selecione o Turno");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
