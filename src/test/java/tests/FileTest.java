package tests;

import org.junit.jupiter.api.Test;
import parsing.FileParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileTest {
    FileParser fileParser = new FileParser("qaguru_hw_8.zip");

    @Test
    public void verifyPdfTest(){
        String pdfContent = fileParser.readPdfFromZip(true);

        assertTrue(pdfContent.contains("Мой дядя"));
        assertTrue(pdfContent.contains("и день и ночь"));
        assertTrue(pdfContent.contains("лекарство"));
    }

    @Test
    public void verifyXlsxTest(){
        String cellContent = fileParser.readXlsxFromZip(0, 2, 0, true);

        assertEquals("Щелкунчик", cellContent);
    }

    @Test
    public void verifyCsvTest(){
        String csvContent = fileParser.readCsvFromZip(4, 0,true);

        assertEquals("Тихий Дон", csvContent);
    }
}
