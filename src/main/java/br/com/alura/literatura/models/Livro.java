package br.com.alura.literatura.models;

import br.com.alura.literatura.dto.LivroDTO;
import jakarta.persistence.*;



@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Linguagem idioma;

    private double downloadCount;


    public Livro(String titulo, Autor autor, Linguagem idioma, double downloadCount) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.downloadCount = downloadCount;
    }

    public Livro(LivroDTO livroDTO) {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Linguagem getIdioma() {
        return idioma;
    }

    public void setIdioma(Linguagem idioma) {
        this.idioma = idioma;
    }

    public double getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(double downloadCount) {
        this.downloadCount = downloadCount;
    }
}