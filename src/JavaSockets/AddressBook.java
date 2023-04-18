package JavaSockets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AddressBook {
	
	File addressBookFile;
	
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	Document document;
	
	public AddressBook(String addressBookFile) {
		this.addressBookFile = new File(addressBookFile);
		
		this.factory = DocumentBuilderFactory.newInstance();
		
		try {
			
			this.builder = factory.newDocumentBuilder();
			this.document = builder.parse(addressBookFile);
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void add(AddressBookItem item) {
		List<AddressBookItem> items = Arrays.asList(item);
		writeItems(items);
	}
	
	public void edit(AddressBookItem item, String hostAddress, String hostPort, String username, String password) {
		
	}
	
	public void delete(AddressBookItem item) {
		
	}
	
	public void list() {
		
		// TODO: mi sono fermato qua prima che mi venisse mal di testa
		
		document.getDocumentElement().normalize();
		
		NodeList addressNodes = document.getElementsByTagName("address");
		
		List<Node> nodes = IntStream.range(0, addressNodes.getLength())
			    .mapToObj(addressNodes::item)
			    .collect(Collectors.toList());
		
		nodes.forEach((addressNode) -> {
			Element nodo = (Element)addressNode;
			Node hostName = nodo.getElementsByTagName("host_name").item(0);
			System.out.println("Nome: " + hostName.getTextContent());
		});
		
	}
	
	public AddressBookItem itemFromString(String itemString) {
		
		return new AddressBookItem();
	}
	
	private List<AddressBookItem> fetchItems() {
		
		return null;
	}
	
	private void writeItems(List<AddressBookItem> items) {
		
		Element rootElement = document.createElement("java_sockets_addresses");
		document.appendChild(rootElement);
		
		items.forEach((item) -> {
			
			Element addressItem = document.createElement("address");
			
			rootElement.appendChild(addressItem);
			
			addressItem.setAttribute("host_name", item.getHostName());
			addressItem.setAttribute("host_address", item.getHostAddress());
			addressItem.setAttribute("host_port", item.getHostPort());
			addressItem.setAttribute("host_username", item.getHostUsername());
			addressItem.setAttribute("host_password", item.getHostPassword());
		});
		
		try (FileOutputStream output = new FileOutputStream(addressBookFile)) {
			
			writeFile(document, output);
			
		} catch (IOException | TransformerException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private void writeFile(Document document, OutputStream outputStream) throws TransformerException {
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(outputStream);
        
        transformer.transform(source, result);
		
	}

}
