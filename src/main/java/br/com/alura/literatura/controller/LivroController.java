package br.com.alura.literatura.controller;

import br.com.alura.literatura.LivroRepository.AutorRepository;
import br.com.alura.literatura.LivroRepository.LivroRepository;
import br.com.alura.literatura.dto.AutorDTO;
import br.com.alura.literatura.dto.LivroDTO;
import br.com.alura.literatura.models.Autor;
import br.com.alura.literatura.models.Livro;
import br.com.alura.literatura.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class LivroController {
    @Autowired
    private LivroService service;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;
    public void salvarLivroComAutor(LivroDTO livroDTO) {
        // Extrai o DTO do autor, que está dentro do DTO do livro
        AutorDTO autorDTO = livroDTO.autores().get(0);

        // Busca o autor no banco de dados para evitar duplicatas
        Optional<Autor> autorExistente = autorRepository.findByNome(autorDTO.nome());

        Autor autor;
        if (autorExistente.isPresent()) {
            // Se o autor já existe, usa a instância do banco de dados
            autor = autorExistente.get();
        } else {
            // Se o autor não existe, cria uma nova entidade e a salva
            autor = new Autor(autorDTO);
            autorRepository.save(autor);
        }

        // Cria a entidade Livro e associa o autor
        Livro livro = new Livro(livroDTO);

        // Salva o livro no banco de dados
        livroRepository.save(livro);

        System.out.println("Livro e autor salvos com sucesso!");
    }
}


