package br.org.femass.gui;

import java.net.URL;
import java.util.ResourceBundle;

import br.org.femass.dao.AutorDao;
import br.org.femass.dao.Persistencia;
import br.org.femass.model.Autor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AutorController implements Initializable {
    
    private Autor autor;
    private Boolean alterando;
    private Persistencia<Autor> autorDao = new AutorDao();


    @FXML
    private Button BtnIncluir;

    @FXML
    private TextField TxtCodigo;

    @FXML
    private TextField TxtNome;

    @FXML
    private ListView<Autor> LstAutores;

    @FXML
    private TextField TxtNacionalidade;

    @FXML
    private Button BtnExcluir;

    @FXML
    private Button BtnAlterar;

    @FXML
    private Button BtnGravar;

    @FXML
    private Button BtnCancelar;

    private void habilitarInterface(Boolean edicao){
        TxtNome.setEditable(edicao);
        TxtNacionalidade.setEditable(edicao);
        BtnGravar.setDisable(!edicao);
        BtnCancelar.setDisable(!edicao);
        BtnIncluir.setDisable(edicao);
        BtnAlterar.setDisable(edicao);
        BtnExcluir.setDisable(edicao);
    }

    private void preencherLista(){
        try {
            ObservableList<Autor> autores = FXCollections.observableArrayList(autorDao.getDados());
            LstAutores.setItems(autores);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exibirDados() {
        autor = LstAutores.getSelectionModel().getSelectedItem();
        TxtCodigo.setText(autor.getCodigo().toString());
        TxtNacionalidade.setText(autor.getNacionalidade());
        TxtNome.setText(autor.getNome());
    }

    @FXML
    void LstAutores_KeyPressed(KeyEvent event) {
        exibirDados();
    }

    @FXML
    void LstAutores_MouseClicked(MouseEvent event) {
        exibirDados();
    }

    @FXML
    void BtnIncluir_Action(ActionEvent event) {
        alterando = false;
        this.autor = new Autor();
        TxtCodigo.setText("");
        TxtNacionalidade.setText("");
        TxtNome.setText("");
        habilitarInterface(true);
        TxtNome.requestFocus();


    }

    @FXML
    void BtnAlterar_Action(ActionEvent event) {
        alterando = true;
        autor = LstAutores.getSelectionModel().getSelectedItem();
        habilitarInterface(true);
    }

    @FXML
    void BtnExcluir_Action(ActionEvent event) {
        try {
            autorDao.excluir(autor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        preencherLista();
    }

    @FXML
    void BtnGravar_Action(ActionEvent event) {
        autor.setNome(TxtNome.getText());
        autor.setNacionalidade(TxtNacionalidade.getText());

        
        try {
            if (alterando) {
                autorDao.alterar(autor);
            } else {
                autorDao.gravar(autor);
                TxtCodigo.setText(autor.getCodigo().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        preencherLista();
        habilitarInterface(false);
    }

    @FXML
    void BtnCancelar_Action(ActionEvent event) {
        habilitarInterface(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherLista();
    }    
}
