package com.bezkoder.spring.data.mongodb.service;
import com.lowagie.text.DocumentException;
//import dev.simplesolution.pdf.service.PdfGenerateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

@Service
public class ReportePdf  {
    private Logger logger = LoggerFactory.getLogger(ReportePdf.class);

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${pdf.directory}")
    private String pdfDirectory;

    public ByteArrayOutputStream  generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) 
    throws DocumentException {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        System.out.println("htmlContent"+htmlContent);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
		try {

			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(htmlContent);

			renderer.layout();
			renderer.createPDF(bos, false);
			renderer.finishPDF();
			logger.info("PDF created correctamente");

		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("Error creando pdf", e);
				}
			}
		}
		return bos;
        
    }
}