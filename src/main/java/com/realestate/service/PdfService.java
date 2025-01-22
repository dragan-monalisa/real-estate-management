package com.realestate.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.realestate.exception.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Slf4j
@Service
public class PdfService {

    public byte[] generatePdf(String title, String content) {
        
        try (var out = new ByteArrayOutputStream()) {
            var writer = new PdfWriter(out);
            var pdfDocument = new PdfDocument(writer);

            var document = new Document(pdfDocument);
            document.add(new Paragraph(title));
            document.add(new Paragraph(content));
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            log.error("Failed to generate pdf due to technical errors: {}", e.getMessage());

            throw new TechnicalException("Error generating PDF");
        }
    }

}
