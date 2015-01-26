/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sirfireys.rana.verifiem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author rana
 */
public class Result_Download {
    String[] p;

    public Result_Data result(String sem, String prf, String table_name) {


//        <?xml version="1.0" encoding="UTF-8"?><data><student enroll_no="11SETBTCS3095"
// sem="7" _id="1" dob="29121993" name="SANDEEP RANA"
// father_name="Mr. RAJKUMAR RANA"
// course="B.TECH(CSE)"
// marks="Crypto: Ex,Mob Comp:Ex,SPM: Ex,P.A: Ex,I.C:Ex" prf="5187" pulish="Y" extra="Pulished"/></data>

        String urlString = "http://sandeeprana011.hostfree.us/result/verifiem.php?table_name=" + table_name + "&prf=" + prf + "&sem=" + sem;

        try {
//            String urlString = "http://sandeeprana011.hostfree.us/result/result.php?table_name=" + table_name + "&sem= "+ sem + "&prf=" + prf;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            URL url = new URL(urlString);
            InputStream ins = url.openStream();
            Document doc = dBuilder.parse(ins);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("student");
            int j = nList.getLength();

            int k = 0;
            ;

            for (int i = 0; i < j; i++) {
                Element item = (Element) nList.item(i);

                p = new String[]{
                        item.getAttribute("enroll_no"),
                        item.getAttribute("sem"),
                        item.getAttribute("_id"),
                        item.getAttribute("dob"),
                        item.getAttribute("name"),
                        item.getAttribute("father_name"),
                        item.getAttribute("course"),
                        item.getAttribute("marks"),
                        item.getAttribute("prf"),
                        item.getAttribute("pulish"),
                        item.getAttribute("extra"),
                        item.getAttribute("password")
                };


                k++;
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Result_Download.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Result_Download.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Result_Download.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Result_Download.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (p != null) {
            return new Result_Data(p);
        } else {
            return null;
        }
    }


}
