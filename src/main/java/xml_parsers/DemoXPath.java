package xml_parsers;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DemoXPath {

    public static void main(String[] args) {
        var factory = XPathFactory.newInstance();
        var parser = factory.newXPath();
        try {
            var query = parser.compile("/files/file[@id='1']/authors/author/text()");
            var fileReader = new FileReader("path_your_xml_file/files.xml");
            NodeList listAuthors = (NodeList) query.evaluate(new InputSource(fileReader), XPathConstants.NODESET);
            for(var i = 0; i < listAuthors.getLength(); i++) {
                System.out.println(listAuthors.item(i).getNodeValue());
            }
        } catch (XPathExpressionException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
