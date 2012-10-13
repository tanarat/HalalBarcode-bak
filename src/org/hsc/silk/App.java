package org.hsc.silk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.hsc.silk.hbc.ProductInfo;
import org.hsc.silk.myutils.JSONParser;
import org.hsc.silk.myutils.XMLParser;
import org.json.JSONObject;

import android.app.Application;
import android.util.Log;


public class App extends Application {
	private String tag = this.getClass().getName();
	private String propertiesFile = "myconfig.properties";
	private Properties properties;
	
	private Map<String, ProductInfo> mapPInfo;
	

	public ProductInfo getProductInfo(String halalId){
		return mapPInfo.get(halalId);
	}
	private void loadProperties() throws MyException {
		InputStream ins;
		try {
			ins = getResources().getAssets().open(propertiesFile);
			properties.load(ins);
			Log.i(tag, "Configuration file loaded");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MyException("Configuration file is missing or could not be loaded ");
		}
		
	}
	public void loadParseProductInfoTestData(){
		InputStream in;
		try {
			in = getResources().getAssets().open("test-halal.xml");
			XMLParser parser = new XMLParser();
			mapPInfo = parser.parseProductInfo(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public String getHalalInfoFromRest(){
		String resourcesPath =  "halals";
		String output = "";
		String url = properties.get("rest-server").toString() + resourcesPath;
//		String url = "http://10.0.2.2:8080/silk-rest/resources/" + resourcesPath;
		
		HscRestClient restClient = new HscRestClient();
//		JSONParser jsonParser = new JSONParser();
		
		try {
//			JSONObject jsonObj = jsonParser.getJSONFromUrl(url);
//			System.out.println("url : " + url);
			output = restClient.get(url);
			System.out.println("output : " + output);
//			Log.i(tag, output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
//    public ProductInfo searchHalalProductInfo(String barcode){
//    	ProductInfo productFound =  null;
//    	if(barcode.equals("790690051055")){ // 
//    	productFound = new ProductInfo();
//    	productFound.setProduct("สบู่อนาดา บริษัทฮาลคิวจำกัด");
//    	productFound.setHalalId("790690051055");
//    	productFound.setHalalBeginDate("ตุลาคม 2555");
//    	productFound.setHalalExpDate("ตุลาคม 2558");
//    	productFound.setOtherInfo("ศูนย์ผู้บริโภค 085-047-5480, www.halkew.com");
//    	}
//    	return productFound;
//    }
    

    @Override
    public void onCreate() {
        super.onCreate(); 
        Log.i(tag, "App loaded");
        properties = new Properties();
        try {
			loadProperties();
			loadParseProductInfoTestData();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			Log.e(tag, "Load configuration file fail", e);
			e.printStackTrace();
		}
       
    }
   

	@Override
    public void onTerminate() {
        super.onTerminate();
        
    }   
}

