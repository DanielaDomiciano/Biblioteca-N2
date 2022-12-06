package br.org.femass.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.org.femass.dao.LeitorDao;
import br.org.femass.dao.Persistencia;
import br.org.femass.model.Aluno;
import br.org.femass.model.Leitor;
import br.org.femass.model.Professor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LeitorController implements Initializable {
    
    private Leitor leitor;
    private Boolean alterando;
    private Persistencia<Leitor> leitorDao = new LeitorDao();


    
    @FXML
    private ComboBox<String> CboTipo;
    @FXML
    private Button BtnIncluir;

    @FXML
    private TextField Txt;

    @FXML
    private TextField TxtNome;

    @FXML
    private ListView<Leitor> LstLeitores;

    @FXML
    private Label Lbl;

    @FXML
    private Button BtnExcluir;

    @FXML
    private Button BtnAlterar;

    @FXML
    private Button BtnGravar;

    @FXML
    private Button BtnCancelar;

    @FXML
    private TextField TxtCpf;

    private void habilitarInterface(Boolean edicao){
        TxtNome.setEditable(edicao);
        TxtCpf.setEditable(edicao);
        Txt.setEditable(edicao);
        CboTipo.setDisable(!edicao);
        BtnGravar.setDisable(!edicao);
        BtnCancelar.setDisable(!edicao);
        BtnIncluir.setDisable(edicao);
        BtnAlterar.setDisable(edicao);
        BtnExcluir.setDisable(edicao);
    }

    private void preencherLista(){
        try {
            ObservableList<Leitor> leitores = FXCollections.observableArrayList(leitorDao.getDados());
            LstLeitores.setItems(leitores);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherCombo(){
        List<String> dados = new ArrayList<String>();
        dados.add("Aluno");
        dados.add("Professor");
        
        ObservableList<String> opcoes = FXCollections.observableArrayList(dados);
        CboTipo.setItems(opcoes);
        CboTipo.getSelectionModel().select("Aluno");
    }

    private void exibirDados() {
        leitor= LstLeitores.getSelectionModel().getSelectedItem();
        TxtCpf.setText(leitor.getCpf());
        TxtNome.setText(leitor.getNome());

        if(leitor instanceof Aluno){
            Aluno aluno  = (Aluno) leitor;
            Lbl.setText("Matricula");
            Txt.setText(aluno.getMatricula());
            CboTipo.getSelectionModel().select("Aluno");
        }else{
            Professor professor = (Professor) leitor;
            Lbl.setText("Disciplina");
            Txt.setText(professor.getDisciplina());
            CboTipo.getSelectionModel().select("Professor");
        }
        
    }

    private void exibir() {
        if (CboTipo.getSelectionModel().getSelectedItem().equals("Aluno")) {
            Lbl.setText("Matrícula");
        } else {
            Lbl.setText("Disciplina");
        }
    }
    @FXML
    private void CboTipo_KeyPressed(KeyEvent event) {
        exibir();
    }

    @FXML
    private void CboTIpo_MouseClicked(MouseEvent event) {
        exibir();
    }

    @FXML
    private void LstAutores_KeyPressed(KeyEvent event) {
        exibirDados();
    }

    @FXML
    private void LstAutores_MouseClicked(MouseEvent event) {
        exibirDados();
    }

    @FXML
    private void BtnIncluir_Action(ActionEvent event) {
        alterando = false;
        habilitarInterface(true);
        Txt.setText("");
        TxtCpf.setText("");
        TxtNome.setText("");
        CboTipo.getSelectionModel().select("Aluno");
        Lbl.setText("Matricula");
        TxtCpf.requestFocus();


    }

    @FXML
    private void BtnAlterar_Action(ActionEvent event) {
        alterando = true;
        leitor = LstLeitores.getSelectionModel().getSelectedItem();
        habilitarInterface(true);
        TxtCpf.setDisable(true);
        TxtNome.requestFocus();
        CboTipo.setDisable(true);
    }

    @FXML
    private void BtnExcluir_Action(ActionEvent event) {
        try {
            leitorDao.excluir(leitor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        preencherLista();
    }

    @FXML
    private void BtnGravar_Action(ActionEvent event) {        
        try {
            if (alterando) {
                if (leitor instanceof Aluno) {
                    Aluno aluno = (Aluno) leitor;
                    aluno.setMatricula(Txt.getText());
                    aluno.setNome(TxtNome.getText());
                    leitorDao.alterar(aluno);
                } else {
                    Professor professor = (Professor) leitor;
                    professor.setDisciplina(Txt.getText());
                    professor.setNome(TxtNome.getText());
                    leitorDao.alterar(professor);
                }
            } else {
                if (CboTipo.getSelectionModel().getSelectedItem().equals("Aluno")) {
                    Aluno aluno  = new Aluno();
                    aluno.setCpf(TxtCpf.getText());
                    aluno.setMatricula(Txt.getText());
                    aluno.setNome(TxtNome.getText());
                    leitorDao.gravar(aluno);
                } else {
                    Professor professor = new Professor();
                    professor.setCpf(TxtCpf.getText());
                    professor.setDisciplina(Txt.getText());
                    professor.setNome(TxtNome.getText());
                    leitorDao.gravar(professor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        preencherLista();
        habilitarInterface(false);
    }

    @FXML
    private void BtnCancelar_Action(ActionEvent event) {
        habilitarInterface(false);
    }

    @FXML
    private void LstLeitores_KeyPressed(ActionEvent event) {
        exibirDados();
    }

    @FXML
    private void LstLeitores_MouseClicked(ActionEvent event) {
        exibirDados();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherLista();
        preencherCombo();
    }    

    
}
