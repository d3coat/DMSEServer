
public class ParseHelper {

	public static String ReadXMLValue(String locateStr, String inputXML) {		
		int start = inputXML.indexOf("<"+locateStr+">")+locateStr.length()+2;
		int end = inputXML.indexOf("</"+locateStr+">");
		if(start!=-1 && end != -1 && start < end){
			return inputXML.substring(start, end);
		}
		return null;
	}

}
