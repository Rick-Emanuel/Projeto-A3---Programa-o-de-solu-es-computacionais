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

public class ControllerCadastroProf {
    @FXML
    private TextField nomeField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField senhaField;
    @FXML
    private PasswordField confirmarSenhaField;

    private ProfessorManager professorManager = ProfessorManager.getInstance();

    @FXML
    void handleCadastro(ActionEvent event) {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        String senha = senhaField.getText();
        String confirmarSenha = confirmarSenhaField.getText();


        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            showAlert(AlertType.WARNING, "Campos obrigatórios", "Todos os campos devem ser preenchidos.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            showAlert(AlertType.WARNING, "Email inválido", "Por favor, insira um email válido.");
            return;
        }

        if (senha.length() < 6) {
            showAlert(AlertType.WARNING, "Senha muito curta", "A senha deve ter pelo menos 6 caracteres.");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            showAlert(AlertType.WARNING, "Senhas não coincidem", "A senha e a confirmação devem ser iguais.");
            return;
        }


        if (professorManager.cadastrarProfessor(email, senha, nome)) {
            showAlert(AlertType.INFORMATION, "Sucesso", "Professor cadastrado com sucesso! Você será redirecionado para a tela de login.");
            voltarParaLogin();
        } else {
            showAlert(AlertType.ERROR, "Email já cadastrado", "Este email já está em uso. Por favor, utilize outro email.");
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
            Stage stage = (Stage) nomeField.getScene().getWindow();
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