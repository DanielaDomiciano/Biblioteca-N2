package br.org.femass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.org.femass.model.Aluno;
import br.org.femass.model.Leitor;
import br.org.femass.model.Professor;

public class LeitorDao extends Dao implements Persistencia<Leitor> {

    @Override
    public void gravar(Leitor dado) throws Exception {
        
        if(dado instanceof Aluno ){
            Aluno aluno = (Aluno) dado;
            gravarAluno(aluno);
        }else{
            Professor professor = (Professor)dado;
            gravarProfessor(professor);
        }
        

       
    }

    private void gravarAluno (Aluno aluno) throws Exception{
        String sql = "Insert into leitor (cpf, nome, matricula, tipo) values (?, ?, ?,?)";
        Connection conexao = getConexao();
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1,aluno.getCpf());
        ps.setString(2, aluno.getNome());
        ps.setString(3, aluno.getMatricula());
        ps.setString(4, "aluno");
        ps.executeUpdate();


    }

    private void gravarProfessor (Professor professor) throws Exception{
        String sql = "Insert into leitor (cpf, nome, disciplina, tipo) values (?, ?, ?,?)";
        Connection conexao = getConexao();
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, professor.getCpf());
        ps.setString(2, professor.getNome());
        ps.setString(3, professor.getDisciplina());
        ps.setString(4, "professor");
        ps.executeUpdate();


    }

    @Override
    public List<Leitor> getDados() throws Exception {
        String sql = "select * from leitor order by nome";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Leitor> leitores = new ArrayList<Leitor>();

        while (rs.next()){
            if(rs.getString("tipo").equals("aluno")){
                Aluno aluno  = new Aluno();
                aluno.setCpf(rs.getString("cpf"));
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setNome(rs.getString("nome"));
                leitores.add(aluno);
            }else {
                Professor professor = new Professor();
                professor.setCpf(rs.getString("cpf"));
                professor.setDisciplina(rs.getString("disciplina"));
                professor.setNome(rs.getString("nome"));
                leitores.add(professor);

            }
        }
        return leitores;
    }

    @Override
    public void alterar(Leitor dado) throws Exception {
       if (dado instanceof Aluno){
           Aluno aluno = (Aluno) dado;
           alterarAluno(aluno);
       }else{
           Professor professor = (Professor) dado;
           alterarProfessor(professor);
       }
        
    }

    private void alterarAluno(Aluno aluno) throws Exception{
        String sql = "update leitor set nome = ?, matricula = ?, where cpf = ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, aluno.getNome());
        ps.setString(2, aluno.getMatricula());
        ps.setString(3, aluno.getCpf());

        ps.executeUpdate();
    }
    
    private void alterarProfessor(Professor professor) throws Exception{
        String sql = "update leitor set nome = ?, disciplina = ?, where cpf = ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, professor.getNome());
        ps.setString(2, professor.getDisciplina());
        ps.setString(3, professor.getCpf());

        ps.executeUpdate();
    }

    @Override
    public void excluir(Leitor dado) throws Exception {
        String sql = "delete from leitor where cpf = ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, dado.getCpf());
        ps.executeUpdate();
    }
    
}
