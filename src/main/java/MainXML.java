import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainXML {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        String fileName = "data.xml";
        List<Employee> list = parseXML(fileName);
        String json = listToJson(list);
        writeString(json);
    }

    private static List<Employee> parseXML(String fileName) throws IOException, SAXException, ParserConfigurationException, NullPointerException {

        // Получим объект Document для нашего XML-файла
        Document doc;
        doc = buildDocument(fileName);

        // Получим корневой узел документа
        Node staff = doc.getDocumentElement();
        System.out.println("Корневой элемент: " + staff.getNodeName());

        //Получаем узлы с именем employee
        NodeList nodeList = doc.getElementsByTagName("employee");

        // Создадим из него список объектов Employee
        List<Employee> employeeList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            employeeList.add(getEmployee(nodeList.item(i)));
        }
        for (Employee employee : employeeList) {
            System.out.println(employee.toString());
        }

        return employeeList;
    }

    private static Document buildDocument(String fileName) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(fileName));
    }

    private static Employee getEmployee(Node node) {
        Employee employee = new Employee();
        if (Node.ELEMENT_NODE == node.getNodeType()) {
            Element element = (Element) node;
            employee.setId(Integer.parseInt(getTagValue("id", element)));
            employee.setFirstName(getTagValue("firstName", element));
            employee.setLastName(getTagValue("lastName", element));
            employee.setCountry(getTagValue("country", element));
            employee.setAge(Integer.parseInt(getTagValue("age", element)));
        }
        return employee;
    }

    // Получаем значения по указанному тегу
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    private static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(list, listType);
        System.out.println(json);
        return json;

    }

    private static void writeString(String json) {
        try (FileWriter file = new FileWriter("data2.json")) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

