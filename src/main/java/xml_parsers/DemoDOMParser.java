package xml_parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DemoDOMParser {

    public static void main(String[] args) {
        //Указываем именно такой путь, т.к. протокол С интерпретируется как протокол URL.
        var pathFile = "file:///path_your_xml_file/files.xml";
        parser(pathFile);
    }

    private static void parser(String path) {
        //создание объект фабрики. Фабрика содержит методы xml парсинга
        var factory = DocumentBuilderFactory.newInstance();
        try {
            //активация фабрики
            var builder = factory.newDocumentBuilder();
            //doc содержит всю структуру xml
            var doc = builder.parse(path);
            if (doc != null) {
                showDocument(doc);
            }
        } catch (ParserConfigurationException e) {
            e.fillInStackTrace();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private static void showDocument(Node node) {
        //получаем тип элемента
        var type = node.getNodeType();
        switch(type) {
            case Node.DOCUMENT_NODE://<?>
                System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
                //берем следующий элемент следующего документа
                var documentElement = ((Document) node).getDocumentElement();
                showDocument(documentElement);
                break;
            case Node.ELEMENT_NODE://встретили тэг
                System.out.print("<");
                System.out.print(node.getNodeName());//получили имя тэга
                var attrs = node.getAttributes();
                if (attrs != null) {
                    for(var i = 0; i < attrs.getLength(); i++) {
                        //каждый атрибут отправляем на проверку в метод showDocument
                        showDocument(attrs.item(i));
                    }
                }
                System.out.print(">");
                if(node.hasChildNodes()) {
                    var child = node.getChildNodes();//Получаем все дочерние теги
                    for(var i = 0; i < child.getLength(); i++) {
                        //каждый атрибут отправляем на проверку в метод showDocument
                        showDocument(child.item(i));
                    }
                }
                System.out.print("</" + node.getNodeName() + ">");
                break;
            case Node.ATTRIBUTE_NODE:
                System.out.print(" " + node.getNodeName() + " = '" + node.getNodeValue() + "'");
                break;
            case Node.TEXT_NODE:
                System.out.print(node.getNodeValue());
        }
    }

}
