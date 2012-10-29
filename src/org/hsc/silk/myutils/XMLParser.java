package org.hsc.silk.myutils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hsc.silk.hbc.ProductInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLParser {

	public Map<String, ProductInfo> parseProductInfo(String xmlString) {
		Map<String,ProductInfo> pInfoMap = null;
		try {
			Document doc = loadXMLFromString(xmlString);
			pInfoMap = parseProductInfo(doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pInfoMap;

	}

	public Map<String, ProductInfo> parseProductInfo(InputStream in) {
//		List<ProductInfo> productInfoList = null;
		Map<String,ProductInfo> pInfoMap = null;
		DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = dbFac.newDocumentBuilder();
			Document doc = documentBuilder.parse(in);
			pInfoMap = parseProductInfo(doc);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pInfoMap;

	}
	private Map<String,ProductInfo> parseProductInfo(Document doc) {
//		List<ProductInfo> pInfoList = new ArrayList<ProductInfo>();
		Map<String,ProductInfo> pInfoMap = new LinkedHashMap<String, ProductInfo>();

		Element docEle = doc.getDocumentElement();

		// Print root element of the document
		System.out.println("Root element of the document: "
				+ docEle.getNodeName());

		NodeList halalInfoList = docEle.getElementsByTagName("halalInfo");

		// Print total student elements in document
		System.out.println("Total halal infos: " + halalInfoList.getLength());

		if (halalInfoList != null && halalInfoList.getLength() > 0) {
			for (int i = 0; i < halalInfoList.getLength(); i++) {

				Node node = halalInfoList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					ProductInfo pInfo = new ProductInfo();
					System.out.println("=====================");

					Element e = (Element) node;
					NodeList nodeList = e.getElementsByTagName("halalId");
					pInfo.setHalalId(nodeList.item(0).getChildNodes().item(0).getNodeValue());
					System.out.println("halalId: " + pInfo.getHalalId() );

					nodeList = e.getElementsByTagName("product");
					pInfo.setProduct(nodeList.item(0).getChildNodes().item(0).getNodeValue());
					System.out.println("product: " + pInfo.getProduct() );

					nodeList = e.getElementsByTagName("validFrom");
					pInfo.setHalalBeginDate(nodeList.item(0).getChildNodes().item(0).getNodeValue());
					System.out.println("valid from: " + pInfo.getHalalBeginDate() );

					nodeList = e.getElementsByTagName("validTo");
					pInfo.setHalalExpDate(nodeList.item(0).getChildNodes().item(0).getNodeValue());
					System.out.println("valid to: " + pInfo.getHalalExpDate() );

					nodeList = e.getElementsByTagName("otherInfo");
					pInfo.setOtherInfo(nodeList.item(0).getChildNodes().item(0).getNodeValue());
					System.out.println("other info: " + pInfo.getOtherInfo() );
					
					pInfoMap.put(pInfo.getHalalId(), pInfo);
					
				}
			}
		}

		return pInfoMap;
	}

//	private ProductInfo parseProductInfo(Document doc) {
//		ProductInfo productInfo = new ProductInfo();
//
//		System.out.println("Root element : "
//				+ doc.getDocumentElement().getNodeName());
//		NodeList nodeList = doc.getElementsByTagName("halalInfo");
//		Node node = nodeList.item(0);
//
//		NodeList childNodeList = node.getChildNodes();
//		for (int i = 0; i < childNodeList.getLength(); i++) {
//			Node childNode = childNodeList.item(i);
//			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
//				String nodeName = childNode.getNodeName();
//				Element element = (Element) childNode;
//				System.out.println(nodeName + " "
//						+ getTagValue(nodeName, element));
//			}
//		}
//
//		return productInfo;
//	}

	public static Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}
}
