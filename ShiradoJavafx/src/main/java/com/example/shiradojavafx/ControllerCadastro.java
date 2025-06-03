package com.example.shiradojavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCadastro implements Initializable {
    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private TextField telefoneField;
    @FXML private TextField raField;
    @FXML private TextField cursoField;
    @FXML private TextField turnoField;
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

        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRA.setCellValueFactory(new PropertyValueFactory<>("ra"));
        colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));

        alunoTable.setItems(estudantes);
    }

    @FXML
    void handleEnviar(ActionEvent event) {
        try {

            if (nomeField.getText().trim().length() <= 5) {
                showAlert("Erro", "O nome deve ter mais que 5 caracteres");
                return;
            }

            int ra = Integer.parseInt(raField.getText());
            if (ra <= 0 || ra >= 100) {
                showAlert("Erro", "O RA deve ser maior que 0 e menor que 100");
                return;
            }

            if (cursoField.getText().trim().length() <= 2) {
                showAlert("Erro", "O nome do curso deve ter mais que 2 caracteres");
                return;
            }

            String turno = turnoField.getText().trim();
            if (!turno.equals("Matutino") && !turno.equals("Vespertino") && !turno.equals("Noturno")) {
                showAlert("Erro", "Os turnos disponíveis são: Matutino, Vespertino ou Noturno");
                return;
            }


            CadastroDeEstudante novoEstudante = new CadastroDeEstudante(
                    ra,
                    nomeField.getText().trim(),
                    cursoField.getText().trim(),
                    turno,
                    emailField.getText().trim(),
                    Integer.parseInt(telefoneField.getText())
            );

            estudantes.add(novoEstudante);
            limparCampos();
            showAlert("Sucesso", "Estudante cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            showAlert("Erro", "RA e Telefone devem ser números válidos");
        }
    }

    private void limparCampos() {
        nomeField.clear();
        emailField.clear();
        telefoneField.clear();
        raField.clear();
        cursoField.clear();
        turnoField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}