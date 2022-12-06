package br.org.femass.gui;

import java.net.URL;
import java.util.ResourceBundle;

import br.org.femass.dao.AutorDao;
import br.org.femass.dao.LivroDao;
import br.org.femass.dao.Persistencia;
import br.org.femass.model.Autor;
import br.org.femass.model.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LivroController implements Initializable {
    
    private Livro livro;
    private Boolean alterando;
    private Persistencia<Livro> livroDao = new LivroDao();
    private Persistencia<Autor> autorDao = new AutorDao();


    @FXML
    private Button BtnIncluir;

    @FXML
    private TextField TxtCodigo;

    @FXML
    private TextField TxtTitulo;

    @FXML
    private ListView<Livro> LstLivros;

    @FXML
    private ListView<Autor> LstAutores;

    @FXML
    private ComboBox<Autor> CboAutor;

    @FXML
    private Button BtnAdicionarAutor;

    @FXML
    private TextField TxtAno;

    @FXML
    private Button BtnExcluir;

    @FXML
    private Button BtnAlterar;

    @FXML
    private Button BtnGravar;

    @FXML
    private Button BtnCancelar;

    private void habilitarInterface(Boolean edicao){
        TxtTitulo.setEditable(edicao);
        TxtAno.setEditable(edicao);
        CboAutor.setDisable(!edicao);
        LstAutores.setDisable(!edicao);
        BtnGravar.setDisable(!edicao);
        BtnCancelar.setDisable(!edicao);
        BtnIncluir.setDisable(edicao);
        BtnAlterar.setDisable(edicao);
        BtnExcluir.setDisable(edicao);
        BtnAdicionarAutor.setDisable(!edicao);
    }

    private void preencherLista(){
        try {
            ObservableList<Livro> livros = FXCollections.observableArrayList(livroDao.getDados());
            LstLivros.setItems(livros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void preencherCombo(){
        try {
            ObservableList<Autor> autores = FXCollections.observableArrayList(autorDao.getDados());
            CboAutor.setItems(autores);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherAutores(){
        ObservableList<Autor> autores = FXCollections.observableArrayList(livro.getAutores());
        LstAutores.setItems(autores);

    }
    private void exibirDados() {
        livro = LstLivros.getSelectionModel().getSelectedItem();
        TxtCodigo.setText(livro.getCodigo().toString());
        TxtAno.setText(livro.getAno().toString());
        TxtTitulo.setText(livro.getTitulo());
        preencherAutores();
    }

    @FXML
    void LstLivros_KeyPressed(KeyEvent event) {
        exibirDados();
    }

    @FXML
    void LstLivros_MouseClicked(MouseEvent event) {
        exibirDados();
    }

    @FXML
    void BtnIncluir_Action(ActionEvent event) {
        alterando = false;
        this.livro = new Livro();
        TxtAno.setText("");
        TxtCodigo.setText("");
        TxtTitulo.setText("");
        CboAutor.getSelectionModel().clearSelection();
        preencherAutores();
        habilitarInterface(true);
        TxtTitulo.requestFocus();

       
    }

    @FXML
    void BtnAlterar_Action(ActionEvent event) {
        alterando = true;
        livro = LstLivros.getSelectionModel().getSelectedItem();
        habilitarInterface(true);
    }

    @FXML
    void BtnExcluir_Action(ActionEvent event) {
        try {
            livroDao.excluir(livro);
        } catch (Exception e) {
            e.printStackTrace();
        }

        preencherLista();
    }

    @FXML
    void BtnGravar_Action(ActionEvent event) {
        livro.setTitulo(TxtTitulo.getText());
        livro.setAno(Integer.parseInt(TxtAno.getText()));
                
        try {
            if (alterando) {
                livroDao.alterar(livro);
            } else {
                livroDao.gravar(livro);
                TxtCodigo.setText(livro.getCodigo().toString());
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

    @FXML
    void BtnAdicionarAutor_Action(ActionEvent event) {
        Autor autor = CboAutor.getSelectionModel().getSelectedItem();
        livro.adiconarAutor(autor);
        preencherAutores();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherLista();
        preencherCombo();
    }    
}
