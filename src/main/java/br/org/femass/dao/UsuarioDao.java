package br.org.femass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.org.femass.model.Usuario;

public class UsuarioDao extends Dao implements Persistencia<Usuario>{
    
    @Override
    public void gravar(Usuario usuario) throws Exception{
        String sql = "Insert into usuario (nome, login, senha) values (?, ?, ?)";
        Connection conexao = getConexao();
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, usuario.getNome());
        ps.setString(2, usuario.getLogin());
        ps.setString(3, usuario.getSenha());
        ps.executeUpdate();
    }

    @Override
    public List<Usuario> getDados() throws Exception{
        String sql = "Select * from usuario";
        Connection conexao = getConexao();
        PreparedStatement ps = conexao.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        List<Usuario> usuarios = new ArrayList<Usuario>();

        while(rs.next()){
            Usuario u = new Usuario();
            u.setLogin(rs.getString("login"));
            u.setNome(rs.getString("nome"));
            u.setSenha(rs.getString("senha"));

            usuarios.add(u);
        }

        return usuarios;
    }


    public Usuario autenticar(String login, String senha) throws SQLException{
        String sql = "select * from usuario where login = ? and senha = ?";
        Connection conexao = getConexao();
        PreparedStatement ps = conexao.prepareStatement(sql);

        ps.setString(1, login);
        ps.setString(2, senha);

        ResultSet rs = ps.executeQuery();

        Usuario usuario = null;

        while(rs.next()){
            usuario = new Usuario();
            usuario.setLogin(rs.getString("login"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
        }

        return usuario;

    }

    @Override
    public void alterar(Usuario dado) throws Exception {
        String sql = "update usuario set nome = ?, senha = ? where login = ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, dado.getNome());
        ps.setString(2, dado.getSenha());
        ps.setString(3, dado.getLogin());
        ps.executeUpdate();
        
    }


    @Override
    public void excluir(Usuario dado) throws Exception {
        String sql = "delete from usuario where login = ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, dado.getLogin());
        ps.executeUpdate();
    }

}
