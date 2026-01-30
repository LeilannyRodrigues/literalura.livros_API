<h1 align="center">
  LiterAlura Challenge 📚
</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange" alt="Java 17" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.0-green" alt="Spring Boot 3.0" />
  <img src="https://img.shields.io/badge/PostgreSQL-DB-blue" alt="PostgreSQL" />
  <img src="https://img.shields.io/static/v1?label=Status&message=CONCLUIDO&color=GREEN&style=for-the-badge"/>
</p>

## 💻 Descrição

O **LiterAlura** é uma aplicação de console desenvolvida durante o Challenge da Alura. O objetivo principal é consumir a API **Gutendex** para buscar livros, salvar dados em um banco relacional (**PostgreSQL**) e realizar consultas complexas, como listar autores vivos em determinado ano e filtrar livros por idioma.

O grande diferencial deste projeto é a persistência de dados inteligente, que evita duplicidade de autores no banco de dados e gerencia corretamente o relacionamento *One-to-Many* entre Autores e Livros.

## 🔨 Funcionalidades

O projeto oferece um menu interativo com as seguintes opções:

- [x] **Buscar livro pelo título:** Consome a API, converte os dados e salva no banco.
- [x] **Listar livros registrados:** Exibe todos os livros salvos no banco local.
- [x] **Listar autores registrados:** Mostra os autores e seus livros associados.
- [x] **Listar autores vivos em um determinado ano:** Uma consulta complexa que verifica datas de nascimento e falecimento (tratando campos nulos).
- [x] **Listar livros em um determinado idioma:** Filtra os livros por EN, ES, FR ou PT.

## 📺 Demonstração
![- literalura – Main java](https://github.com/user-attachments/assets/358dca17-6a69-4839-bc8b-0c5d25e741af)

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot** (Framework principal)
- **Spring Data JPA** (Para persistência de dados)
- **PostgreSQL** (Banco de dados relacional)
- **Hibernate** (ORM)
- **Gutendex API** (Fonte de dados externa)
- **Maven** (Gerenciador de dependências)

## 🚀 Como rodar o projeto

### Pré-requisitos
Antes de começar, você vai precisar ter instalado em sua máquina:
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)
- Uma IDE (IntelliJ IDEA recomendado).

### Passo a passo

1. **Clone o repositório**
2. **Configure as variáveis de ambiente no application.properties**
   
