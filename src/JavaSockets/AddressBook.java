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
	
	public static final String ADDRESS_NOT_FOUND = "ADDRESS_NOT_FOUND";
	
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
		
		Element rootElement = (Element)document.getElementsByTagName("java_sockets_addresses").item(0);
		
		Element addressItem = document.createElement("address");
		
		rootElement.appendChild(addressItem);
		
		addressItem.setAttribute("id", item.getHostName());
		addressItem.setAttribute("host_address", item.getHostAddress());
		addressItem.setAttribute("host_port", item.getHostPort());
		addressItem.setAttribute("host_username", item.getHostUsername());
		addressItem.setAttribute("host_password", item.getHostPassword());
		
		try {
			
			writeAddressBookFile();
		} catch (TransformerException e) {
			
			e.printStackTrace();
		}
	}
	
	public void delete(String itemName) {
		
		// TODO: questo non funziona
		
		try {
			
			AddressBookItem itemToDelete = itemFromString(itemName);
			System.out.print(itemToDelete.getHostName());
			
			Element rootElement = (Element)document.getElementsByTagName("java_sockets_addresses").item(0);
			
			Element elementToRemove = document.getElementById(itemToDelete.getHostName());
			rootElement.removeChild(elementToRemove);
			
			try {
				writeAddressBookFile();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			
			System.out.println("L'host [" + itemName + "] non è stato trovato tra gli host salvati");
		}
	}
	
	// TODO: scrivere i messaggi d'errore
	
	public void list() {
		
		System.out.println("Host salvati:\n");
		
		document.getDocumentElement().normalize();
		
		NodeList addressNodes = document.getElementsByTagName("address");
		
		List<Node> nodes = convertNodeList(addressNodes);
		
		nodes.forEach((addressNode) -> {
			Element nodo = (Element)addressNode;
			
			AddressBookItem addressItem;
			
			try {
				
				addressItem = itemFromElement(nodo);
				
				if (addressItem.getHostUsername() != "" && addressItem.getHostPassword() != "") {
					
					System.out.println("	* [" + addressItem.getHostName() + "] [auth] {address: " + addressItem.getHostAddress() + ", port: " + addressItem.getHostPort() + "}\n");
				} else {
					
					System.out.println("	* [" + addressItem.getHostName() + "] [no auth] {address: " + addressItem.getHostAddress() + ", port: " + addressItem.getHostPort() + "}" + "\n");
				}
				
			} catch (IOException e) {

				e.printStackTrace();
			}
			
		});
		
		System.out.println("Per connetterti a un host usa il comando <connect to nome_host>");
		
	}
	
	// TODO: scrivere i messaggi d'errore
	
	public AddressBookItem itemFromString(String itemString) throws IOException {
		
		document.getDocumentElement().normalize();
		
		NodeList addressNodes = document.getElementsByTagName("address");
		
		List<Node> nodes = convertNodeList(addressNodes);
		
		AddressBookItem retreivedItem = new AddressBookItem();
		
		nodes.forEach((addressNode) -> {
			Element nodo = (Element)addressNode;
			
			AddressBookItem addressItem;
			
			try {
				
				addressItem = itemFromElement(nodo);
				
				if (addressItem.getHostName().equals(itemString)) {
					
					retreivedItem.set(addressItem);
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		});
		
		if (retreivedItem.isEmpty()) {
			
			throw new IOException();
		} else {
			
			return retreivedItem;
		}
	}
	
	public AddressBookItem itemFromElement(Element itemElement) throws IOException {
		
		String hostName = itemElement.getAttribute("id");
		String hostAddress = itemElement.getAttribute("host_address");
		String hostPort = itemElement.getAttribute("host_port");
		
		String hostUsername = itemElement.getAttribute("host_username");
		String hostPassword = itemElement.getAttribute("host_password");
		
		AddressBookItem retreivedItem = new AddressBookItem(hostName, hostAddress, hostPort, hostUsername, hostPassword);
		
		return retreivedItem;
	}
	
	// TODO: scrivere i messaggi d'errore
	
	private List<AddressBookItem> fetchAddresses() {
		
		List<AddressBookItem> addressesList = new ArrayList<AddressBookItem>();
		
		document.getDocumentElement().normalize();
		
		NodeList addressNodes = document.getElementsByTagName("address");
		
		List<Node> nodes = convertNodeList(addressNodes);
		
		nodes.forEach((addressNode) -> {
			Element nodo = (Element)addressNode;
			
			try {
				
				AddressBookItem convertedAddress = itemFromElement(nodo);
				addressesList.add(convertedAddress);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		});
		
		return addressesList;
	}
	
	private List<Node> convertNodeList(NodeList addressNodes) {
		
		return IntStream.range(0, addressNodes.getLength())
			    .mapToObj(addressNodes::item)
			    .collect(Collectors.toList());
	}
	
	// TODO: scrivere i messaggi d'errore
	
	private void writeAddressBookFile() throws TransformerException {
		
		try (FileOutputStream output = new FileOutputStream(addressBookFile)) {
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        
	        DOMSource source = new DOMSource(document);
	        StreamResult result = new StreamResult(output);
	        
	        transformer.transform(source, result);
			
		} catch (IOException | TransformerException e) {
			
			e.printStackTrace();
			
		}
		
	}

}
