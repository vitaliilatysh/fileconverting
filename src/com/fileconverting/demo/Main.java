package com.fileconverting.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {
	public static void main(String[] args) {

		Scanner scanner;

		try {
			scanner = new Scanner(
					new File("C:\\Users\\admin\\eclipse-workspace\\fileconverting\\resources\\inputTestFlat.txt"));

			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				Document doc = docBuilder.newDocument();
				Element testtdc = doc.createElement("TESTDC");
				doc.appendChild(testtdc);

				Element idoc = doc.createElement("IDOC");
				testtdc.appendChild(idoc);

				idoc.setAttribute("BEGIN", "1");

				Element doc_inf = doc.createElement("DOC_INF");
				idoc.appendChild(doc_inf);

				Element tabname = doc.createElement("TABNAME");
				tabname.setTextContent("DOCUMENT INFORMATION");
				doc_inf.appendChild(tabname);

				Element currentdate = doc.createElement("CURRENTDATE");
				currentdate.setTextContent(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
				doc_inf.appendChild(currentdate);

				Element doc_body = doc.createElement("DOC_BODY");
				idoc.appendChild(doc_body);

				List<String> lines = new ArrayList<>();

				while (scanner.hasNext()) {
					lines.add(scanner.nextLine());
				}

				Element hnum = doc.createElement("HNUM");
				hnum.setTextContent(lines.get(0).substring(9, 24).trim());
				doc_body.appendChild(hnum);

				Element hcode = doc.createElement("HCODE");
				hcode.setTextContent(lines.get(0).substring(24, 34).trim());
				doc_body.appendChild(hcode);

				Element inf = doc.createElement("INF");
				inf.setTextContent(lines.get(0).substring(56, 80).trim());
				doc_body.appendChild(inf);

				Element other = doc.createElement("OTHER");
				other.setTextContent(lines.get(0).substring(80, 89).trim());
				doc_body.appendChild(other);

				for (int i = 1; i < lines.size(); i++) {
					Element doc_detail = doc.createElement("DOC_DETAIL");
					doc_body.appendChild(doc_detail);

					Element dcrt = doc.createElement("DCRT");
					dcrt.setTextContent("000" + i);
					doc_detail.appendChild(dcrt);

					Element dnum = doc.createElement("DNUM");
					dnum.setTextContent(lines.get(i).substring(28, 34).trim());
					doc_detail.appendChild(dnum);

					Element acc = doc.createElement("ACC");
					acc.setTextContent(lines.get(i).substring(34, 40).trim());
					doc_detail.appendChild(acc);

					if (!lines.get(i).substring(40, 56).trim().isEmpty()) {
						Element adr = doc.createElement("ADR");
						adr.setTextContent(lines.get(i).substring(40, 56).trim());
						doc_detail.appendChild(adr);
					}

					Element otherDoc_detail = doc.createElement("OTHER");
					if(!lines.get(i).substring(80, 89).trim().isEmpty()) {
						otherDoc_detail.setTextContent(lines.get(i).substring(80, 89).trim());
					} else {
						otherDoc_detail.setTextContent("DEFAULTINFO");
					}
					doc_detail.appendChild(otherDoc_detail);
				}
				// }

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				transformer.setOutputProperty(OutputKeys.STANDALONE, "true");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(
						new File("C:\\Users\\admin\\eclipse-workspace\\fileconverting\\resources\\outputXmlFile.xml"));

				transformer.transform(source, result);

			} catch (ParserConfigurationException | TransformerException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}