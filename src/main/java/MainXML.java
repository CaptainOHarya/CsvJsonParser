import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jdk.nashorn.internal.ir.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.List;


public class MainXML {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        List<Employee> list = parseXML("data.xml");
        //String json = listToJson(list);
       // writeString(json);
    }

    private static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> data = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        // Node staff = doc.getDocumentElement();

        Node staff = doc.getDocumentElement();
        System.out.println("Корневой элемент: " + staff.getNodeName());
        read(staff);

        return data;
        }
    private static void read(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = (Node) nodeList.item(i);
            if (Node.ELEMENT_NODE == node_.getNodeType());
            System.out.println("Текущий узел: " + node_.getNodeName());
            Element element = (Element) node_;
            NamedNodeMap map = element.getAttributes();
            for (int a = 0; a < map.getLength(); a++) {
                String attrName = map.item(a).getNodeName();
                String attrValue = map.item(a).getNodeValue();
                System.out.println("Атрибут: " + attrName + "; значение: " + atrrValue);
            }
            read(node_);


        }
    }
    private static String listToJson(List<Employee> list){
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(list, listType);
        System.out.println(json);
        return json;

    }
    private static void writeString(String json){
        try (FileWriter file = new FileWriter("data.json")){
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }

