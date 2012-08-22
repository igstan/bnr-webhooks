package ro.bnr.hooks.core;

import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

public class FxRates {

  public static final URL XSD_FILE;
  public static final URI BNR_URI = URI.create("http://bnr.ro/nbrfxrates.xml");

  static {
    XSD_FILE = Resources.getResource("nbrfxrates.xsd");
  }

  private FxRates() {
  }

  /**
   * Validate an xml blob against an internal schema
   *
   * @param xmlBlob
   */
  public static String checkXmlValidity(String xmlBlob) {
    Source xml = new StreamSource(new ByteArrayInputStream(xmlBlob.getBytes()));
    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    try {
      Schema schema = schemaFactory.newSchema(XSD_FILE);
      Validator validator = schema.newValidator();

      validator.validate(xml);
      return xmlBlob;

    } catch (SAXException e) {
      throw new IllegalArgumentException("Unable to validate against internal schema file", e);

    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to validate against internal schema file", e);
    }
  }

  /**
   * Retrieves and validated the xml blob file from an external well know server
   *
   * @return
   */
  public static String get() throws IOException {
    return get(BNR_URI);
  }

  public static String get(URI uri) throws IOException {
    InputStream in = uri.toURL().openStream();
    String blob = CharStreams.toString(new InputStreamReader(in));
    return checkXmlValidity(blob);
  }
}
