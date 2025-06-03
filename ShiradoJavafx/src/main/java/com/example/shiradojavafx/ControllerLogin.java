package com.example.shiradojavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;

public class ControllerLogin {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField senhaField;

    private Professor professor = new Professor("professor@email.com", "123456");

    @FXML
    void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (professor.validarLogin(email, senha)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cadastro_aluno.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Sistema de Cadastro - Cadastrar Alunos");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro de Login");
            alert.setHeaderText("Credenciais inválidas");
            alert.setContentText("Email ou senha incorretos. Por favor, tente novamente.");
            alert.showAndWait();
        }
    }
}
