package cpsky.community.provider;

import cpsky.community.dto.KeyDto;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Iterator;

/**
 * \* Author: sky
 * \* Date: 2019/7/2
 * \* Description:
 * \
 */
@Component
public class XMLProvider {
    public KeyDto getUpLoadKey() {
        KeyDto keyDto = new KeyDto();
        try {
            File f = new File("d:/key.xml");
            SAXReader reader = new SAXReader();
            Document doc = reader.read(f);
            Element root = doc.getRootElement();
            Element foo = null;
            Element gg = null;
            for (Iterator i = root.elementIterator("cloud"); i.hasNext();){
                foo = (Element)i.next();
                if (foo.attributeValue("name").equals("ali")) {
                    break;
                }
            }
            for (Iterator i = foo.elementIterator("product"); i.hasNext();){
                gg = (Element)i.next();
                if (gg.attributeValue("name").equals("oss")) {
                    break;
                }
            }
            keyDto.setKeyid(gg.element("keyid").getText());
            keyDto.setKeysecert(gg.element("keysecret").getText());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return keyDto;
    }
}