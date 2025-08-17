package br.com.alura.literatura.models;

import br.com.alura.literatura.dto.AutorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "autor")
    private String name;

    @Column(name = "data_nascimento")
    private Integer dataNascimento;

    @Column(name = "data_falecimento")
    private Integer dataFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(String name, Integer dataNascimento, Integer dataFalecimento, List<Livro> livros) {
        this.name = name;
        this.dataNascimento = dataNascimento;
        this.dataFalecimento = dataFalecimento;
        this.livros = livros;
    }

    public Autor(AutorDTO autorDTO) {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Integer dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getDataMorte() {
        return dataFalecimento;
    }

    public void setDataMorte(Integer dataMorte) {
        this.dataFalecimento = dataFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

}