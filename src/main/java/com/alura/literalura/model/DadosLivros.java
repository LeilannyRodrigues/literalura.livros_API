package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DadosAutor> autores, // Tem que ser lista!
        @JsonAlias("languages") List<String> idiomas,   // Tem que ser lista!
        @JsonAlias("download_count") Double numeroDownloads
) {
    @Override
    public String toString() {
        return "------------ LIVRO ------------" +
                "\nTitulo: " + titulo +
                "\nAutor: " + ((autores != null && !autores.isEmpty()) ? autores.get(0).nome() : "Desconhecido") +
                "\nIdioma: " + ((idiomas != null && !idiomas.isEmpty()) ? idiomas.get(0) : "Desconhecido") +
                "\nNúmero de downloads: " + numeroDownloads +
                "\n-------------------------------";
    }
}
