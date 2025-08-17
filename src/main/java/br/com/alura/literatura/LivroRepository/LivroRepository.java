package br.com.alura.literatura.LivroRepository;

import br.com.alura.literatura.models.Autor;
import br.com.alura.literatura.models.Linguagem;
import br.com.alura.literatura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l WHERE LOWER(l.titulo) = LOWER(:titulo)")
    List<Livro> findByTitulo(String titulo);

    @Query("SELECT a FROM Autor a WHERE a.dataNascimento <= :ano AND (a.dataFalecimento IS NULL OR a.dataFalecimento >= :ano)")
    List<Autor> findAutoresVivos(int ano);


    List<Livro> findByIdioma(Linguagem idioma);

}