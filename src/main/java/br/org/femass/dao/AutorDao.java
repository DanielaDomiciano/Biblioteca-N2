package br.org.femass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.org.femass.model.Autor;

public class AutorDao extends Dao implements Persistencia<Autor>{

    @Override
    public void gravar(Autor autor) throws Exception {
        String sql = "Insert into autor (nome, nacionalidade) values (?, ?)";
        Connection conexao = getConexao();
        PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,autor.getNome());
        ps.setString(2, autor.getNacionalidade());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()){
            autor.setCodigo(rs.getLong("codigo"));
        }
        
    }

    @Override
    public List<Autor> getDados() throws Exception {
        String sql = "select * from autor order by nome";
        Connection conexao = getConexao();
        PreparedStatement ps = conexao.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        
        List<Autor> autores = new ArrayList<Autor>();
        
        while (rs.next()) {
            Autor autor = new Autor();
            autor.setCodigo(rs.getLong("codigo"));
            autor.setNome(rs.getString("nome"));
            autor.setNacionalidade(rs.getString("nacionalidade"));

            autores.add(autor);
        }

        return autores;

    }

    @Override
    public void alterar(Autor dado) throws Exception {
        String sql = "update autor set nome=?, nacionalidade = ? where codigo = ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, dado.getNome());
        ps.setString(2, dado.getNacionalidade());
        ps.setLong(3, dado.getCodigo());
        ps.executeUpdate();
        
    }

    @Override
    public void excluir(Autor dado) throws Exception {
        String sql = "delete from autor where codigo = ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setLong(1, dado.getCodigo());
        ps.executeUpdate();
        
    }
    
}
