package br.org.femass.gui;

import java.net.URL;
import java.util.ResourceBundle;

import br.org.femass.dao.UsuarioDao;
import br.org.femass.dao.Persistencia;
import br.org.femass.model.Usuario;
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

public class UsuarioController implements Initializable {
    
    private Usuario usuario;
    private Boolean alterando;
    private Persistencia<Usuario> usuarioDao = new UsuarioDao();


    @FXML
    private Button BtnIncluir;

    @FXML
    private TextField TxtNome;

    @FXML
    private TextField TxtLogin;

    @FXML
    private ListView<Usuario> LstUsuarios;

    @FXML
    private TextField TxtSenha;

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
        TxtLogin.setEditable(edicao);
        TxtSenha.setEditable(edicao);
        BtnGravar.setDisable(!edicao);
        BtnCancelar.setDisable(!edicao);
        BtnIncluir.setDisable(edicao);
        BtnAlterar.setDisable(edicao);
        BtnExcluir.setDisable(edicao);
    }

    private void preencherLista(){
        try {
            ObservableList<Usuario> usuarios = FXCollections.observableArrayList(usuarioDao.getDados());
            LstUsuarios.setItems(usuarios);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exibirDados() {
        usuario= LstUsuarios.getSelectionModel().getSelectedItem();
        TxtNome.setText(usuario.getNome());
        TxtLogin.setText(usuario.getLogin());
        TxtSenha.setText(usuario.getSenha());
    }

    @FXML
    void LstUsuarios_KeyPressed(KeyEvent event) {
        exibirDados();
    }

    @FXML
    void LstUsuarios_MouseClicked(MouseEvent event) {
        exibirDados();
    }

    @FXML
    void BtnIncluir_Action(ActionEvent event) {
        alterando = false;
        this.usuario = new Usuario();
        TxtLogin.setText("");
        TxtNome.setText("");
        TxtSenha.setText("");
        habilitarInterface(true);
        TxtLogin.requestFocus();
        


    }

    @FXML
    void BtnAlterar_Action(ActionEvent event) {
        alterando = true;
        usuario = LstUsuarios.getSelectionModel().getSelectedItem();
        habilitarInterface(true);
    }

    @FXML
    void BtnExcluir_Action(ActionEvent event) {
        try {
            usuarioDao.excluir(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        preencherLista();
    }

    @FXML
    void BtnGravar_Action(ActionEvent event) {
        usuario.setNome(TxtNome.getText());
        usuario.setSenha(TxtSenha.getText());
        usuario.setLogin(TxtLogin.getText());

        
        try {
            if (alterando) {
                usuarioDao.alterar(usuario);
            } else {
                usuarioDao.gravar(usuario);
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
