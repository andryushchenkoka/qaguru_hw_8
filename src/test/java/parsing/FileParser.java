package parsing;

import com.codeborne.pdftest.PDF;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FileParser {
    private static final ClassLoader classLoader = FileParser.class.getClassLoader();

    static String resourcesPath = "src/test/resources/";
    static String zipArchiveName = "qaguru_hw_8.zip",
            pdfFileName = "Eugene_Onegin.pdf",
            xlsxFileName = "Tchaikovskii.xlsx",
            csvFileName = "Russian_novels.csv";

    public static String readPdfFromZip(String zipPath, boolean toPrintContent) {

        PDF content = null;

        try (
                InputStream resource = classLoader.getResourceAsStream(zipPath);
                ZipInputStream zipInputStream = new ZipInputStream(resource);
        ) {
            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String filename = zipEntry.getName();
                if (filename.contains("pdf")) {
                    content = new PDF(zipInputStream);
                    if (toPrintContent) {
                        System.out.printf("Название файла: %s\n\n", filename);
                        System.out.println(content.text);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.text;
    }

    public static void main(String[] args) throws Exception {

        String content = readPdfFromZip(zipArchiveName, false);

    }
}
