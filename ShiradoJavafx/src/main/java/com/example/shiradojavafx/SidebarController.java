package com.example.shiradojavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.IOException;

public class SidebarController {

    @FXML
    private void switchToPresenca(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/presenca.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void switchToCadastro(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/cadastro_aluno.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void switchToLista(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lista_alunos.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void switchToPresencaList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lista_presenca.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Registro de Presenças");
        stage.show();
}
}