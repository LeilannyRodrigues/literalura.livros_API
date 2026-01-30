package com.alura.literalura.model;

import jakarta.persistence.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Double numeroDownloads;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {}

    // 2. Construtor de Conversão
    // Recebemos o Record e "traduzimos" para a Entidade
    public Livro(DadosLivros dadosLivro) {
        this.titulo = dadosLivro.titulo();

        // Verificamos se existe autor na lista que veio da API
        if (dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
            // 1. Pegamos o record do primeiro autor
            DadosAutor dadosAutor = dadosLivro.autores().get(0);

            // 2. Transformamos em um Objeto Autor real
            this.autor = new Autor(dadosAutor);

            //Avisa ao Autor que este livro pertence a ele (mantém a relação correta)
            this.autor.setLivros(java.util.Collections.singletonList(this));

            //Avisa ao Autor que este livro pertence a ele (mantém a relação correta)
            this.autor.setLivros(java.util.Collections.singletonList(this));

        } else {
            // Se não tiver autor, deixamos null
            this.autor = null;
        }

        this.idioma = (dadosLivro.idiomas() != null && !dadosLivro.idiomas().isEmpty())
                ? dadosLivro.idiomas().get(0)
                : "Desconhecido";

        this.numeroDownloads = dadosLivro.numeroDownloads();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }


    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Double numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return  "----- LIVRO -----\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + (autor != null ? autor.getNome() : "Desconhecido") + "\n" +
                "Idioma: " + idioma + "\n" +
                "Número de downloads: " + numeroDownloads + "\n" +
                "-----------------";
    }
}
