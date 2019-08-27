package cn.hqweay.core.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by hqweay on 2019/8/22 11:22
 */
public class ClassRelativeResourceLoaderTests {



  private static final Logger logger = LogManager.getLogger(ClassRelativeResourceLoaderTests.class);

  @Test
  public void getResource() {
    // 现在路径必须加 「/」
    Resource resource = new ClassRelativeResourceLoader().getResource("/defaults.xml");

    logger.debug(resource);
  }


  @Test
  public void TestT() {


    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = null;
      documentBuilder = factory.newDocumentBuilder();
//      InputStream inputStream = this.getClass().getResourceAsStream("/defaults.xml");

      URL resource = ResourceLoader.class.getResource("/defaults.xml");
      URLConnection urlConnection = resource.openConnection();
      urlConnection.connect();
      InputStream inputStream =  urlConnection.getInputStream();


      // fixme 转换为 null
      Document document = documentBuilder.parse(inputStream);

      System.out.println(document);

    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

}