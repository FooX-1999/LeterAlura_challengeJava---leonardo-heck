package br.com.alura.literatura.principal;


import br.com.alura.literatura.LivroRepository.LivroRepository;
import br.com.alura.literatura.models.Autor;
import br.com.alura.literatura.models.Linguagem;
import br.com.alura.literatura.models.Livro;
import br.com.alura.literatura.services.ConsumoApi;
import br.com.alura.literatura.services.ConverteDados;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Principal {

    private static final String BASE_URL = "https://gutendex.com/books?search=";

    @Autowired
    private LivroRepository repositorio;

    @Autowired
    private ConsumoApi consumoAPI;

    @Autowired
    private ConverteDados converteDados;

    Scanner leitura = new Scanner(System.in);



    private List<Livro> livros = new ArrayList<>();
    public void exibirMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    
                    ------------------------------------------------
                    
                    
                    1 - Buscar livro pelo Título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    
                    0 - Sair                    
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");

            }

        }

    }

    private void buscarLivroPeloTitulo() {
        try {
            System.out.println("Digite o título do livro: ");
            String titulo = leitura.nextLine();
            String endereco = BASE_URL + titulo.replace(" ", "%20");
            System.out.println("URL da API: " + endereco);

            String jsonResponse = obterDados(endereco);
            System.out.println("Resposta da API: " + jsonResponse);

            processarRespostaJSON(jsonResponse);
        } catch (Exception e) {
            System.out.println("Erro ao buscar livros: " + e.getMessage());
        }
    }

    private String obterDados(String endereco) throws Exception {
        StringBuilder resultado = new StringBuilder();
        URL url = new URL(endereco);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String linha;
        while ((linha = rd.readLine()) != null) {
            resultado.append(linha);
        }
        rd.close();
        return resultado.toString();
    }

    private void processarRespostaJSON(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray livros = jsonObject.getJSONArray("results");

        if (!livros.isEmpty()) {
            JSONObject livro = livros.getJSONObject(0);
            String titulo = livro.getString("title");
            String autor = livro.getJSONArray("authors").getJSONObject(0).getString("name");

            System.out.println("Título: " + titulo);
            System.out.println("Autor: " + autor);
        } else {
            System.out.println("Nenhum livro encontrado com o título fornecido.");
        }
    }


    private void listarLivrosRegistrados() {
        livros = repositorio.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        livros = repositorio.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            livros.stream()
                    .map(Livro::getAutor)
                    .distinct()
                    .forEach(autor -> System.out.println(autor.getName()));
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano: ");
        Integer ano = leitura.nextInt();
        leitura.nextLine();



        List<Autor> autores = repositorio.findAutoresVivos(ano);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado.");
        } else {
            System.out.println("Lista de autores vivos no ano de " + ano + ":\n");

            autores.forEach(autor -> {
                if(autor.getDataMorte() != null){
                    String nomeAutor = autor.getName();
                    String anoNascimento = autor.getDataNascimento().toString();
                    String anoFalecimento = autor.getDataMorte().toString();
                    System.out.println(nomeAutor + " (" + anoNascimento + " - " + anoFalecimento + ")");
                }
            });
        }

    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
            Digite o idioma pretendido:
            
            Inglês (en)
            Português (pt)
            Francês (fr)
            """);

        String idioma = leitura.nextLine();

        List<Livro> livros = repositorio.findByIdioma(Linguagem.valueOf(idioma));
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma especificado.");
        } else {
            livros.forEach(System.out::println);
        }
    }


}