package br.edu.ifrn.qagym.util;

import br.edu.ifrn.qagym.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// 1. Corrigido o nome da classe para evitar conflito
public class BookValidatorTest {
    private BookValidator validator;

    @BeforeEach
    void setUp() {
        // Agora o Java sabe exatamente que estamos instanciando a classe utilitária real
        validator = new BookValidator();
    }

    @Test
    void deveRetornarFalsoQuandoAutorForNulo() {
        // 2. Corrigido 'new Book' e o nome da variável para minúsculo (boa prática)
        Book livroAutorNulo = new Book("12345", "O hobbit", null, 1902);

        boolean resultado = validator.isValid(livroAutorNulo);

        assertFalse(resultado, "O validador deveria rejeitar um autor vazio ou em branco.");
    }

    @Test
    void deveRetornarVerdadeiroQuandoAutorValido() {
        // 2. Corrigido 'new Book' aqui também
        Book livroComAutor = new Book("12345", "O hobbit", "João Pedro", 1902);

        // 3. Corrigido o erro de digitação de 'resultador' para 'resultado'
        boolean resultado = validator.isValid(livroComAutor);
        assertTrue(resultado, "O validador deveria aceitar um autor válido.");
    }
}