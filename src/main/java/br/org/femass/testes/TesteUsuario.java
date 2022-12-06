package br.org.femass.testes;

import java.sql.SQLException;
import java.util.List;

import br.org.femass.dao.UsuarioDao;
import br.org.femass.model.Usuario;

public class TesteUsuario {

    public static void main(String[] args) {
        testarCriarUsuario("dani", "123456", "Daniela Domiciano");
        testarListarUsuarios();
        
    }

    /**
     * 
     */
    private static void testarListarUsuarios(){
        try {
            final List<Usuario> usuarios = new UsuarioDao().getDados();
            
            for(Usuario u : usuarios){
                System.out.println("Nome: " + u.getNome());
                System.out.println("Login: " + u.getLogin());
                System.out.println("Senha: " + u.getSenha());
                System.out.println("=============================");
            }
        } catch (Exception e) {
            
            e.printStackTrace();
        }

    }

    private static void testarAutenticar(String login, String senha){
        Usuario usuario;
        try {
            usuario = new UsuarioDao().autenticar(login, senha);
            System.out.println(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    private static void testarCriarUsuario(String login, String senha, String nome){
        Usuario usuario = new Usuario();

        usuario.setLogin(login);
        usuario.setNome(nome);
        usuario.setSenha(senha);

        try {
            new UsuarioDao().gravar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
