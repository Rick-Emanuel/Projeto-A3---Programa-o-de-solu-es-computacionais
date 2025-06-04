
module sistema.cadastro.estudantes {

    
    requires javafx.controls;  
    requires javafx.fxml;      
    requires javafx.base;      

    
    requires javafx.graphics;
    exports com.example.shiradojavafx;

   
    opens com.example.shiradojavafx to javafx.fxml;
}
