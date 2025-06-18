package com.example.shiradojavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
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
    @FXML
    private Button criarContaButton;
    @FXML
    private Hyperlink recuperarSenhaLink;

    private ProfessorManager professorManager = ProfessorManager.getInstance();

    @FXML
    void handleLogin(ActionEvent event) {

        Button sourceButton = (Button) event.getSource();

        if (sourceButton.getText().equals("Criar conta")) {
            handleCriarConta();
            return;
        }

        String email = emailField.getText();
        String senha = senhaField.getText();

        if (professorManager.validarLogin(email, senha)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/presenca.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root, 1080, 720));
                stage.setTitle("Sistema de Cadastro");
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

    @FXML
    void handleRecuperarSenha(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recuperar-senha.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root, 1080, 720));
            stage.setTitle("Recuperar Senha");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao abrir tela de recuperação");
            alert.setContentText("Não foi possível carregar a tela de recuperação de senha.");
            alert.showAndWait();
        }
    }

    private void handleCriarConta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cadastro.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root, 1080, 720));
            stage.setTitle("Cadastro de Professor");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao abrir tela de cadastro");
            alert.setContentText("Não foi possível carregar a tela de cadastro.");
            alert.showAndWait();
        }
    }
}