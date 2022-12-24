package tests;

import pojo.Film;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import parsing.FileParser;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileTest {
    FileParser fileParser = new FileParser("qaguru_hw_8.zip");
    String jsonFile = "Film.json";
    ClassLoader classLoader = FileTest.class.getClassLoader();

    @Test
    public void verifyPdfTest() {
        String pdfContent = fileParser.readPdfFromZip(true);

        assertTrue(pdfContent.contains("Мой дядя"));
        assertTrue(pdfContent.contains("и день и ночь"));
        assertTrue(pdfContent.contains("лекарство"));
    }

    @Test
    public void verifyXlsxTest() {
        String cellContent = fileParser.readXlsxFromZip(0, 2, 0, true);

        assertEquals("Щелкунчик", cellContent);
    }

    @Test
    public void verifyCsvTest() {
        String csvContent = fileParser.readCsvFromZip(4, 0, true);

        assertEquals("Тихий Дон", csvContent);
    }

    @Test
    public void jsonTest() throws IOException {
        Film film = new ObjectMapper().readValue(classLoader.getResourceAsStream(jsonFile), Film.class);

        assertEquals("Wednesday", film.getName());
        assertEquals("crime", film.getGenres().get(2));
        assertEquals("Tim Burton", film.getCrew().get(0).getName());
        assertEquals("Enid Sinclair", film.getActors().get(1).getCharacter());
    }
}
