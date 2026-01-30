package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DadosLivros;
import com.alura.literalura.model.DadosResultado;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoApiLivros;
import com.alura.literalura.service.ConverteDados;

import java.util.*;


public class Main {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoApiLivros consumo = new ConsumoApiLivros();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://gutendex.com/books/";

    //private List<DadosLivros> dadosLivrosList = new ArrayList<>();

    private LivroRepository repositorio;

    private AutorRepository repositorioAutor;

    public Main(LivroRepository repositorio, AutorRepository repositorioAutor) {
        this.repositorio = repositorio;
        this.repositorioAutor = repositorioAutor;
    }

    public void exibeMenu(){
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    -----------------------------------------------
                    ESCOLHA O NÚMERO DE SUA OPÇÃO:
                    1 - buscar livro pelo título
                    2 - listar livros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos em um determinado ano
                    5 - listar livros de um determinado idioma
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
                    listarAutoresVivosPorAno();
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
        System.out.println("Insira o nome do livro que você deseja procurar: ");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + "?search=" + nomeLivro.replace(" ", "%20"));
        DadosResultado dadosR = conversor.obterDados(json, DadosResultado.class);

        if (dadosR.livros() != null && !dadosR.livros().isEmpty()) {
            DadosLivros dadosLivro = dadosR.livros().get(0);
            Livro livro = new Livro(dadosLivro);

            // --- INICIO DA ALTERAÇÃO PARA O AUTOR ---

            // 1. Primeiro, verificamos se o AUTOR já existe no banco

            Optional<Autor> autorBanco = repositorioAutor.findByNome(livro.getAutor().getNome());

            if (autorBanco.isPresent()) {
                // Se o autor já existe, nós pegamos a instância do banco e "colamos" no livro atual

                livro.setAutor(autorBanco.get());
            } else {
                // Se o autor não existe, precisamos salvá-lo no repository de Autores primeiro
                // (Assumindo que você não tem CascadeType.ALL ou quer garantir a ordem)
                Autor novoAutor = livro.getAutor();
                repositorioAutor.save(novoAutor);

                // mas agora ele tem um ID gerado pelo banco.
            }

            // --- FIM DA ALTERAÇÃO PARA O AUTOR ---


            Optional<Livro> livroExistente = repositorio.findByTituloIgnoreCase(livro.getTitulo());

            if (livroExistente.isPresent()) {
                System.out.println("\nO livro já está cadastrado no banco!");
            } else {
                repositorio.save(livro);
                System.out.println("\nLivro salvo com sucesso!");
            }

            System.out.println(livro);
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = repositorio.findAll();
        // 2. Ordena por título (opcional, para ficar bonito) e imprime
        livros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);


        //dadosLivrosList.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = repositorioAutor.findAll(); // Busca todos os autores no banco

        autores.stream()
                .sorted(Comparator.comparing(Autor::getNome)) // Ordena por nome
                .forEach(System.out::println); // Imprime
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Insira o ano que deseja pesquisar: ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        List<Autor> autores = repositorioAutor.obterAutoresVivosEmDeterminadoAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado vivo neste ano.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
            Insira o idioma para realizar a busca:
            es - espanhol
            en - inglês
            fr - francês
            pt - português
            """);
        var idioma = leitura.nextLine();

        List<Livro> livrosPorIdioma = repositorio.findByIdiomaIgnoreCase(idioma);

        if (livrosPorIdioma.isEmpty()) {
            // Se a lista estiver vazia (zero livros encontrados)
            System.out.println("Não existem livros nesse idioma no banco de dados.");
        } else {
            // Se tiver 1 ou mais livros
            livrosPorIdioma.forEach(System.out::println);
        }
    }
}

