/**
 * Module descriptor para o Sistema de Cadastro de Estudantes JavaFX
 *
 * Este módulo define as dependências necessárias para executar
 * a aplicação JavaFX com interface gráfica e controles FXML.
 */
module sistema.cadastro.estudantes {

    // Dependências do JavaFX necessárias
    requires javafx.controls;  // Para controles básicos (Button, TextField, etc.)
    requires javafx.fxml;      // Para carregamento de arquivos FXML
    requires javafx.base;      // Para estruturas básicas do JavaFX

    // Opcional: Para recursos gráficos avançados
    requires javafx.graphics;

    // Opcional: Para componentes web (se necessário no futuro)
    // requires javafx.web;

    // Opcional: Para mídia (áudio/vídeo, se necessário no futuro)
    // requires javafx.media;

    // Exporta o pacote principal para que o JavaFX possa acessar
    exports com.example.shiradojavafx;

    // Permite que o JavaFX acesse os controllers via reflexão
    // Necessário para o FXML conseguir instanciar os controllers
    opens com.example.shiradojavafx to javafx.fxml;

    // Se você tiver subpacotes com controllers, adicione também:
    // opens com.example.shiradojavafx.controllers to javafx.fxml;
    // opens com.example.shiradojavafx.models to javafx.fxml;
}