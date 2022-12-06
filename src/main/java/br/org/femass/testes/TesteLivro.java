package br.org.femass.testes;

import java.util.ArrayList;
import java.util.List;

import br.org.femass.dao.AutorDao;
import br.org.femass.dao.LivroDao;
import br.org.femass.model.Autor;
import br.org.femass.model.Livro;

public class TesteLivro {
    
    public static void main(String[] args) {
        testarCadastrar();
        testarListar();

    }

    private static void testarCadastrar(){
        List<Autor> autores  = new ArrayList<Autor>();
        try {
            autores = new AutorDao().getDados();
        } catch (Exception e){
            e.printStackTrace();
        }

        Livro livro = new Livro();
        livro.setAno(2020);
        livro.setTitulo("Programando com JDBC");
        
        for (int i=0;i<3;i++){
            livro.adiconarAutor(autores.get(i));
        }

        try {
            new LivroDao().gravar(livro);
        } catch (Exception e) {
            
            e.printStackTrace();
        }

        
    }

    private static void testarListar(){
        List<Livro> livros = new ArrayList<Livro>();
        
        try {
            livros = new LivroDao().getDados();            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        for (Livro livro: livros){
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Código: " + livro.getCodigo());
            System.out.println("Ano: " + livro.getAno());
            for (Autor a: livro.getAutores()) {
                System.out.println(a.getCodigo().toString() + " - " + a.getNome() + " - " + a.getNacionalidade());
            }
    
        }
        
    }
}
