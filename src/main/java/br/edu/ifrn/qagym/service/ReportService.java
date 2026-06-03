package br.edu.ifrn.qagym.service;

import br.edu.ifrn.qagym.model.Book;
import br.edu.ifrn.qagym.model.Loan;

import java.util.List;
import java.util.Map;

public class ReportService {

    public int total(List<Book> books) {
        return books.size();
    }

    public int countAvailableBooks(List<Book> books) {
        int n = 0;
    for (Book book : books) {
        if (book.isAvailable()) {
            n++;
        }
    }
        return n;
    }

    public int countUnavailableBooks(List<Book> books) {
        // TODO: implementar contagem de livros indisponíveis
        return 0;
    }

    public Map<String, Long> booksByAuthor(List<Book> books) {
        // TODO: retornar mapa de autor -> quantidade de livros
        return Map.of();
    }

    public int activeLoanCount(List<Loan> loans) {
        // TODO: contar empréstimos ativos (não devolvidos)
        return 0;
    }
}
