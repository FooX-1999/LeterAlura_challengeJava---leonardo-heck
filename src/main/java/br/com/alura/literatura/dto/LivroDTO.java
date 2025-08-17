package br.com.alura.literatura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDTO(@JsonAlias("title") String titulo,
                       @JsonAlias("languages") List<String> idiomas,
                       @JsonAlias("authors")List<AutorDTO> autores,
                       @JsonAlias("download_count")Double downloads) {

}