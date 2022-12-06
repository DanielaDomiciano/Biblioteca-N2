package br.org.femass.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class MainController implements Initializable {
    
    
    private void abrirTela(String fxml, String titulo){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/"+ fxml +".fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    void BtnAutores_Action(ActionEvent event) {
        abrirTela("Autor", "Cadastro de Autores");
        
    }

    @FXML
    void BtnUsuario_Action(ActionEvent event) {
        abrirTela("Usuario", "Cadastro de Usuários");
    }
    
    @FXML
    void BtnLivro_Action(ActionEvent event){
        abrirTela("Livro", "Cadastro de Livros");
    }

    @FXML
    void BtnLeitroes_Action(ActionEvent event) {
        abrirTela("Leitor", "Cadastro de Leitores");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
