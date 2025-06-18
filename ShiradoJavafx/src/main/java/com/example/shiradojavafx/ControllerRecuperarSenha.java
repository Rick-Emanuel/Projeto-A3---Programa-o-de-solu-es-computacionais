package com.example.shiradojavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;

public class ControllerRecuperarSenha {
    @FXML
    private TextField emailField;

    private ProfessorManager professorManager = ProfessorManager.getInstance();

    @FXML
    void handleRecuperar(ActionEvent event) {
        String email = emailField.getText().trim();


        if (email.isEmpty()) {
            showAlert(AlertType.WARNING, "Campo obrigatório", "Por favor, digite seu email.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            showAlert(AlertType.WARNING, "Email inválido", "Por favor, insira um email válido.");
            return;
        }


        if (professorManager.emailExiste(email)) {
            showAlert(AlertType.INFORMATION, "Email enviado",
                    "Verifique sua caixa de email para redefinir a senha.");
            voltarParaLogin();
        } else {
            showAlert(AlertType.ERROR, "Email não encontrado", "Email inválido.");
        }
    }

    @FXML
    void handleVoltarLogin(ActionEvent event) {
        voltarParaLogin();
    }

    private void voltarParaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root, 1080, 720));
            stage.setTitle("Login - Sistema Universitário");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Não foi possível voltar à tela de login.");
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}