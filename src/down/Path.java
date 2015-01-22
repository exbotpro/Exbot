package down;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Path extends XMLHandler{
	
	private String repositoryDescriptor = "http://swquality.cafe24.com/exbot/repository/manifest.xml";
	private String repository = "http://swquality.cafe24.com/exbot/repository/";
	private String path = "camera/";
	private String app = "camera.jar";
	private String localPath= "C:/Users/zuna/git/Exbot/lib";
	
	
	public String getLocalPath() {
		return localPath;
	}

	public String getRepository() {
		return repository;
	}
	
	public void findApp(String id){
		
		try{
			String fullPath = this.findPath(id);
			
			if(fullPath==null){
				throw new NotFoundOperatorException("There is no app for the device [" + id + "] "
						+ "you have been inserted in the repository");
			}else {
				this.app = this.findAppName(fullPath);
			}

		}catch(NotFoundOperatorException e){
			
		}
	}
	
	public String findPath(String id){
		
		Document doc = super.getXMLDocument(repositoryDescriptor);
		NodeList nList = doc.getElementsByTagName("Apps");
		Element e = null;
		for (int appIdx = 0; appIdx < nList.getLength(); appIdx++) {
			Node nNode = nList.item(appIdx);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) nNode;
	 			if(e.getAttribute("id").equals(id)){
	 				return e.getAttribute("path");
	 			}
			}
		}
		
		return null;
	}
	
	public String findAppName(String path){
		String[] elements = path.split("/");
		this.path = "";
		for(int i = 0; i < elements.length-1 ; i++){
			this.path += elements[i];
		}
		
		return elements[elements.length-1];
	}

	public String getPath() {
		return path;
	}
	
	public String getApp() {
		return app;
	}
	
}
