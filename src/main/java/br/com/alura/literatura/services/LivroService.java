package br.com.alura.literatura.services;

import br.com.alura.literatura.LivroRepository.LivroRepository;
import br.com.alura.literatura.dto.LivroDTO;
import br.com.alura.literatura.models.Autor;
import br.com.alura.literatura.models.Livro;
import org.springframework.stereotype.Service;



@Service
public class LivroService {

    // Injeção do repositório
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void salvarLivro(LivroDTO livroDTO) {
        // 1. Converte o DTO para Entidade
        Livro livro = new Livro(livroDTO, autor);

        // 2. Persiste a entidade no banco de dados
        livroRepository.save(livro);
    }
}