package encoding;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Uncompressor {
    public static String FILE_NAME =
        "/Users/i574245/Documents/study/project/JavaSamples/resources/binary/addnl-doc-za.bin";
    public static final String XMLDefaultEncoding = "UTF-8";
    public static final String XMLDocumentType = "text/xml";

    public static void main(String[] args) throws Exception {
//        readBinaryFile(FILE_NAME);
        System.out.println("******************************* :Uncompressing File: *************************************");
        unCompressBinaryFile(FILE_NAME);
    }

    private static void unCompressBinaryFile(String fileName) throws Exception {
        InputStream plainUncompressedContent = null;
        try {
            FileInputStream plaintext = new FileInputStream(fileName);
            plainUncompressedContent = new InflaterInputStream(plaintext);
            int byteRead;
            while ((byteRead = plainUncompressedContent.read()) != -1) {
                if (byteRead == -1) {
                    break; // End of stream
                }
                // Process the byte read, e.g., print it
                System.out.print("Byte read: " + byteRead + "\n");
            }
            plaintext.close();

            InputSource inputSource = new InputSource(plainUncompressedContent);
            createDocument(inputSource);

        } catch (Exception e) {
            throw new RuntimeException("Error uncompressing content", e);
        }
    }

    private static void readBinaryFile(String fileName) throws Exception {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            int byteRead;
            while ((byteRead = fis.read()) != -1) {
                System.out.print("Byte read: " + byteRead + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading binary file: " + e.getMessage());
        }
    }


    /**
     * Parse the preamble and return the encoding specified therein,
     * or the default if none is present or the preamble cannot be
     * understood.
     * @param source the InputSource containing the document.  This
     * <strong>will</strong> be modified by replacing the stream or
     * reader with an equivalent stream or reader containing the same
     * data
     * @return the encoding as specified in the doc, or the default
     * encoding
     */
    public static String getEncodingForXML (InputSource source)
    {
        try {
            XMLPrologPeeker peeker = new XMLPrologPeeker(
                source, XMLPrologPeeker.XML_DECL_READAHEAD_GUESS
            );
            peeker.readXMLDecl();
            String enc = peeker.getXMLEncoding();
            return (enc == null ? XMLDefaultEncoding : enc);
        } catch (SAXException e) {
            return XMLDefaultEncoding;
        } catch (IOException e) {
            return XMLDefaultEncoding;
        }
    }


    /**
     * Constructs an XMLDocument from the specified source
     *
     * @param content Souce of the data
     * @exception Exception XMLDocException
     */
    public static void createDocument(InputSource content) throws Exception {
        Document doc;
        try {
            String m_encoding = getEncodingForXML(content);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(false);
            dbf.setNamespaceAware(false);
            dbf.setValidating(false);
            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            parser.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId, String systemId) {
                    // Custom entity resolution logic can be added here if needed
                    return null; // Returning null means no custom resolution
                }
            });

            doc = parser.parse(content);
            System.out.println(doc.toString());
        } catch (SAXException e) {
            throw new RuntimeException("Couldn't parse document", e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Couldn't create parser", e);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't parse document", e);
        }
    }
}
