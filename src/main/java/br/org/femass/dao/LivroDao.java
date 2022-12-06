package br.org.femass.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.org.femass.model.Autor;
import br.org.femass.model.Livro;

public class LivroDao extends Dao implements Persistencia<Livro> {
    
    @Override
    public void gravar(Livro livro) throws Exception{
        String sql = "insert into livro (titulo, ano) values (?,?)";
        PreparedStatement ps = getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
        ps.setString(1, livro.getTitulo());
        ps.setInt(2, livro.getAno());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()){
            livro.setCodigo(rs.getLong("codigo"));
        }

        for(Autor autor : livro.getAutores()){
            sql = "insert into livroautor (livro_codigo, autor_codigo) values (?, ?)";
            PreparedStatement psautor = getConexao().prepareStatement(sql);
            psautor.setLong(1, livro.getCodigo());
            psautor.setLong(2, autor.getCodigo());
            psautor.executeUpdate();
        }

    }

    @Override
    public List<Livro> getDados() throws Exception{
        String sql = "select * from livro";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        List<Livro> livros = new ArrayList<Livro>();
        while (rs.next()){
            Livro livro = new Livro();
            livro.setAno(rs.getInt("ano"));
            livro.setCodigo(rs.getLong("codigo"));
            livro.setTitulo(rs.getString("titulo"));

            sql = "select autor.codigo, autor.nome, autor.nacionalidade "
                +" from autor inner join livroautor on autor.codigo = livroautor.autor_codigo "
                + " where livroautor.livro_codigo = ?";
            
            PreparedStatement psautor = getConexao().prepareStatement(sql);
            psautor.setLong(1, livro.getCodigo());
            ResultSet rsAutor = psautor.executeQuery();

            while (rsAutor.next()){
                Autor autor = new Autor();
                autor.setCodigo(rs.getLong("codigo"));
                autor.setNome(rsAutor.getString("nome"));
                autor.setNacionalidade(rsAutor.getString("nacionalidade"));
                livro.adiconarAutor(autor);
            }
            
            livros.add(livro);
        }   
        
        return livros;
    }

    @Override
    public void alterar(Livro dado) throws Exception {
        String sql = "update livro set titulo = ?, ano = ? where codigo = ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, dado.getTitulo());
        ps.setInt(2, dado.getAno());
        ps.setLong(3, dado.getCodigo());

        ps.executeUpdate();

        sql = "delete from livroautor where livro_codigo = ?";
        ps = getConexao().prepareStatement(sql);
        ps.setLong(1, dado.getCodigo());

        ps.executeUpdate();

        for(Autor autor : dado.getAutores()){
            sql = "insert into livroautor (livro_codigo, autor_codigo) values (?, ?)";
            PreparedStatement psautor = getConexao().prepareStatement(sql);
            psautor.setLong(1, dado.getCodigo());
            psautor.setLong(2, autor.getCodigo());
            psautor.executeUpdate();
        }


    }

    @Override
    public void excluir(Livro dado) throws Exception {
        String sql = "delete from livroautor where livro_codigo = ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, dado.getCodigo().toString());
        ps.executeUpdate();

        sql="delete from livro where codigo = ?";
        ps = getConexao().prepareStatement(sql);
        ps.setLong(1, dado.getCodigo());
        ps.executeUpdate();
        
    }
        
    


}
