package xml_parsers.SAX;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class Demo extends DefaultHandler {

    private static StringBuilder builder;

    @Override
    public void startDocument() {
        System.out.println("Парсер встретил тэг <?xml");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        builder.append("Элемент ").append(qName).append(" открыт");
        for(var i = 0; i < attributes.getLength(); i++) {
            builder.append("\nАтрибут: ")
                    .append(attributes.getQName(i))
                    .append(";value=")
                    .append(attributes.getValue(i))
                    .append("\n");
        }
    }

    public static void main(String[] args) {
        //Указываем именно такой путь, т.к. протокол С интерпретируется как протокол URL.
        var file = new File("path_your_xml_file/files.xml");
        builder = new StringBuilder();
        var factory = SAXParserFactory.newInstance();
        try {
            var parser = factory.newSAXParser();
            parser.parse(file, new Demo());
            System.out.println(builder.toString());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        builder.append("Тэг ").append(qName).append(" закрыт!");
    }
}
