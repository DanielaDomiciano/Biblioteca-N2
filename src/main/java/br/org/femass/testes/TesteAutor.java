package br.org.femass.testes;

import java.util.ArrayList;
import java.util.List;

import br.org.femass.dao.AutorDao;
import br.org.femass.model.Autor;

public class TesteAutor {
    
    public static void main(String[] args) {
        testarCadastrar("Peter", "Escocês");
        testarListar();

    }

    private static void testarCadastrar(String nome, String nacionalidade){
        Autor autor  = new Autor();
        autor.setNacionalidade(nacionalidade);
        autor.setNome(nome);

        try {
            new AutorDao().gravar(autor);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Código: " + autor.getCodigo());
    }

    private static void testarListar(){
        List<Autor> autores = new ArrayList<Autor>();
        
        try {
            autores = new AutorDao().getDados();            
        } catch (Exception e) {
            e.printStackTrace();
        }
                
        for (Autor a: autores) {
            System.out.println(a.getCodigo().toString() + " - " + a.getNome() + " - " + a.getNacionalidade());
        }

    }
}
