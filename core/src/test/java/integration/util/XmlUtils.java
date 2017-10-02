package integration.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author msd-sk
 *
 */
public class XmlUtils {
	
	/**
	 * @param xml
	 * @return
	 */
	public static Document toDom(String xml){
		try{
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			return b.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param targetNode
	 * @return
	 */
	public static String toXml(Node targetNode){
		return toXml(targetNode, null);
	}

	/**
	 * @param targetNode
	 * @param omitXmlDeclaration
	 * @return
	 */
	public static String toXml(Node targetNode, boolean omitXmlDeclaration){
		Properties trProps = new Properties();
		trProps.put(OutputKeys.OMIT_XML_DECLARATION, omitXmlDeclaration ? "yes" : "no");
		return toXml(targetNode, trProps);
	}
	/**
     * @param targetNode
     * @param omitXmlDeclaration
     * @return
     */
    public static String toIndentedXml(Node targetNode, boolean omitXmlDeclaration){
        Properties trProps = new Properties();
        trProps.put(OutputKeys.OMIT_XML_DECLARATION, omitXmlDeclaration ? "yes" : "no");
        trProps.put(OutputKeys.INDENT, "yes");
        trProps.put("{http://xml.apache.org/xslt}indent-amount", "2");
        return toXml(targetNode, trProps);
    }
	/**
	 * @param targetNode
	 * @param transformProps
	 * @return
	 */
	public static String toXml(Node targetNode, Properties transformProps){
		try {
			StringWriter sw = new StringWriter();
			TransformerFactory tfactory = TransformerFactory.newInstance(); 
			Transformer transformer = tfactory.newTransformer();
			if(null != transformProps && !transformProps.isEmpty()){
				transformer.setOutputProperties(transformProps);
			}
			transformer.transform(new DOMSource(targetNode), new StreamResult(sw));
			return sw.toString();
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		} 
	}

	
	/**
	 * @return
	 */
	public static XPath getXpath(){
		try{
			XPathFactory f = XPathFactory.newInstance();
			return f.newXPath();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * returns formatted date string
	 * @param cal the date
	 * @return formatted date string
	 * @deprecated xml standard date format
	 */
	@Deprecated
	public static String toXmlDateFormat(Calendar cal){
		return String.format("%1$tY-%1$tm-%1$tdT%1$TH:%1$TM:%1$TS", cal);
	}
	
	
	/**
	 * @author msd-sk
	 *
	 */
	private static class NodeListIterator implements Iterator<Node>{
		private NodeList nodeList;
		private int index = 0;
		private Node cache = null;

		public NodeListIterator(NodeList nodeList){
			this.nodeList = nodeList;
		}
		
		@Override
		public boolean hasNext() {
			return index < nodeList.getLength();
		}

		@Override
		public Node next() {
			cache = nodeList.item(index++);
			if(null == cache)throw new NoSuchElementException();
			return cache;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * @author msd-sk
	 *
	 */
	private static class IterableNodeList implements Iterable<Node>{
		private NodeList nodeList;
		
		public IterableNodeList(NodeList nodeList){
			this.nodeList = nodeList;
		}
		
		@Override
		public Iterator<Node> iterator() {
			return new NodeListIterator(nodeList);
		}
		
	}

	/**
	 * @param nodeList
	 * @return
	 */
	public static Iterable<Node> toNodes(NodeList nodeList){
		return new IterableNodeList(nodeList);
	}
	/**
	 * @param compiledXpath
	 * @param inputSource
	 * @return
	 */
	public static Iterable<Node> toNodes(XPathExpression compiledXpath, Object inputSource){
		try{
			return new IterableNodeList((NodeList)compiledXpath.evaluate(inputSource, XPathConstants.NODESET));
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * @param xpathInstance
	 * @param xpathLocation
	 * @param inputSource
	 * @return
	 */
	public static Iterable<Node> toNodes(XPath xpathInstance, String xpathLocation, Object inputSource){
		try{
			return new IterableNodeList((NodeList)xpathInstance.evaluate(xpathLocation, inputSource, XPathConstants.NODESET));
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * returns xml encoded string(not appliable to attribute value)
	 * @param tgt target string
	 * @return xml escaped string
	 */
	public static String xmlEncode(String tgt){
		return toXml(toDom("<ROOT/>").createTextNode(tgt), true);
	}
}
