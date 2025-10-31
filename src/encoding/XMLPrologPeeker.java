package encoding;

import java.io.InputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLPrologPeeker {
    protected String xmlVersion = null;
    protected String xmlEncoding = null;
    protected String xmlStandalone = null;

    protected String doctypeName = null;
    protected String doctypeSysId = null;
    protected String doctypePubId = null;

    protected int index = 0;
    protected String doc;
    protected int length;

    /**
     This is a good guess for the "readAhead" argument to the
     constructor if you are planning on peeking only the XML
     declaration.
     */
    public static final int XML_DECL_READAHEAD_GUESS = 100;

    /**
     This is a decent guess for the "readAhead" argument to the
     constructor if you are planning on peeking the DOCTYPE
     declaration.  However, no number can guarantee that you will
     successfully read the DOCTYPE declaration.  See the comments
     for readDoctypeDecl() for more information.
     */
    public static final int DOCTYPE_DECL_READAHEAD_GUESS = 1024;

    /**
     Constructs an XMLPrologPeeker for peeking at the prolog of the
     XML document specified by the InputSource.  The InputSource
     will be changed in order to accomodate the "peeking" operation;
     the character and byte streams may be different objects after
     this call, but the data contained in the streams will be the
     same.
     @param in The InputSource representing the document to be
     "peeked".  The streams in this InputSource may be changed, but
     the streams will still read the same data.
     @param readAhead the number of bytes or characters to peek into
     the stream.  This is tricky.  See the descriptions of
     readXMLDecl() and readDoctypeDecl() for more information.
     @exception java.io.UnsupportedEncodingException thrown if "in" contains
     a byte stream encoded with an unsupported encoding family, such
     as UCS-4.  The UTF-8/US-ASCII/ISO-8859 families are supported,
     as well as UTF-16, so this shouldn't generally be a problem.
     @exception java.io.IOException if any other IOException is thrown, all
     bets are off and the streams in the InputSource may be corrupt
     or unreadable, or may have been rendered corrupt or unreadable
     by this constructor.
     */
    public XMLPrologPeeker (InputSource in, int readAhead) throws IOException
    {
        if (in.getCharacterStream() != null)
            init(peekCharStream(in.getCharacterStream(), in, readAhead));
        else if (in.getByteStream() != null)
            init(peekByteStream(in.getByteStream(), in, readAhead));
        else if (in.getSystemId() != null)
            init(peekByteStream(new URL(in.getSystemId()).openStream(),
                in, readAhead));
        else
            throw new IOException(
                "The InputSource contained no character stream, " +
                    "byte stream, or system ID"
            );
    }

    public XMLPrologPeeker (byte[] content, int readAhead)
        throws UnsupportedEncodingException
    {
        this(content, 0, content.length, readAhead);
    }

    public XMLPrologPeeker (byte[] content, int offset, int length,
                            int readAhead)
        throws UnsupportedEncodingException
    {
        init(new String(content, offset, Math.min(length, readAhead),
            autodetectXMLEncodingFamily(content, offset, length)));
    }

    /**
     Reads the XML declaration from the document specified in the
     constructor.  If the readAhead specified in the constructor was
     too small, this method may fail by throwing a SAXException.
     However, if you specified at least XML_DECL_READAHEAD_GUESS, it
     should nearly always work.  This is because the XML
     declaration, if present, must be the first thing in a document
     and its length is well bounded by the grammar.  If you call
     this method, you must call it before readDoctypeDecl() or it
     will fail.
     @exception org.xml.sax.SAXException thrown if the XML declaration is
     malformed or could not be located.
     */
    public void readXMLDecl () throws SAXException
    {
        // The skipWhitespace() is a hack... we shouldn't allow it...
        skipWhitespace();

        requireString("<?xml");

        requireWhitespace();
        requireString("version");
        skipWhitespace();
        requireString("=");
        skipWhitespace();
        xmlVersion = readQuotedString();
        skipWhitespace();

        if (tryString("encoding")) {
            skipWhitespace();
            requireString("=");
            skipWhitespace();
            xmlEncoding = readQuotedString();
            skipWhitespace();
        }

        if (tryString("standalone")) {
            skipWhitespace();
            requireString("=");
            skipWhitespace();
            xmlStandalone = readQuotedString();
            skipWhitespace();
        }

        requireString("?>");
    }

    /**
     Returns the XML version specified in the XML declaration.  This
     will not return valid results unless you have previously called
     readXMLDecl().
     @return The version specified (without the enclosing quotes),
     or null if none was found.
     */
    public String getXMLVersion ()
    {
        return xmlVersion;
    }

    /**
     Returns the XML encoding specified in the XML declaration.
     This will not return valid results unless you have previously
     called readXMLDecl().
     @return The encoding specified (without the enclosing quotes),
     or null if none was found.
     */
    public String getXMLEncoding ()
    {
        return xmlEncoding;
    }

    /**
     Returns the XML standalone specified in the XML declaration.
     This will not return valid results unless you have previously
     called readXMLDecl().
     @return The standalone specified (without the enclosing quotes),
     or null if none was found.
     */
    public String getXMLStandalone ()
    {
        return xmlStandalone;
    }

    /**
     Reads the DOCTYPE declaration from the XML document specified
     in the constructor.  This method may fail if the readAhead
     specified in the constructor was too small.  Unfortunately, the
     readAhead required to find the DOCTYPE declaration could be
     arbitrarily large, so this should not be considered a 100%
     reliable method of finding the DOCTYPE declaration.  However,
     if you have some prior knowledge of the documents you will be
     parsing (e.g., they will not have anything in the prolog except
     the XML declaration and the DOCTYPE declaration, and the
     DOCTYPE declaration will not contain an internal subset), you
     may find this useful.
     @exception org.xml.sax.SAXException if the DOCTYPE decl was malformed,
     contained an internal subset, or could not be found.
     */
    public void readDoctypeDecl () throws SAXException
    {
        findString("<!DOCTYPE");
        requireWhitespace();
        doctypeName = readName();
        skipWhitespace();

        if (tryString("SYSTEM")) {
            requireWhitespace();
            doctypeSysId = readQuotedString();
        } else if (tryString("PUBLIC")) {
            requireWhitespace();
            doctypePubId = readQuotedString();
            requireWhitespace();
            doctypeSysId = readQuotedString();
        }

        skipWhitespace();

        if (tryString("[")) {
            // Internal subset.... yuck...  We can just throw an
            // exception here, because if there is an internal
            // subset, we are not interested in caching the DTD
            // anyway.
            throw new SAXException("Internal subset not supported");
        }

        requireString(">");
    }

    /**
     Returns the root name specified in the DOCTYPE declaration.
     This will not return valid results unless you have previously
     called readDoctypeDecl().
     @return The root name specified
     */
    public String getDoctypeName ()
    {
        return doctypeName;
    }

    /**
     Returns the public ID specified in the DOCTYPE declaration.
     This will not return valid results unless you have previously
     called readDoctypeDecl().
     @return The public ID specified, or null if none was found
     */
    public String getDoctypePublicId ()
    {
        return doctypePubId;
    }

    /**
     Returns the system ID specified in the DOCTYPE declaration.
     This will not return valid results unless you have previously
     called readDoctypeDecl().
     @return The system ID specified
     */
    public String getDoctypeSystemId ()
    {
        return doctypeSysId;
    }

    protected boolean isWhitespace ()
    {
        char c = doc.charAt(index);
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    protected void requireWhitespace () throws SAXException
    {
        if (!isWhitespace())
            throw new SAXException("Whitespace required");
        skipWhitespace();
    }

    protected void skipWhitespace ()
    {
        while (index < length && isWhitespace())
            index++;
    }

    protected boolean tryString (String str)
    {
        int strlen = str.length();
        if (index + strlen > length)
            return false;
        for (int i = 0; i < strlen; i++) {
            if (str.charAt(i) != doc.charAt(index + i))
                return false;
        }
        index += strlen;
        return true;
    }

    protected void requireString (String str) throws SAXException
    {
        if (!tryString(str))
            throw new SAXException("String not found");
    }

    protected void findString (String str) throws SAXException
    {
        int i = doc.indexOf(str, index);
        if (i < 0)
            throw new SAXException("String not found");
        index = i + str.length();
    }

    /** Read a string terminated by whitespace or > */
    protected String readName ()
    {
        StringBuffer buf = new StringBuffer();
        while (index < length && !isWhitespace() &&
            doc.charAt(index) != '>')
            buf.append(doc.charAt(index++));
        return buf.toString();
    }

    /** Read a string starting and ending with a double or single
     * quote */
    protected String readQuotedString () throws SAXException
    {
        char c = doc.charAt(index);
        if (c != '"' && c != '\'')
            throw new SAXException("Not a quoted string");
        int start = index + 1;
        int end = doc.indexOf(c, start);
        if (end < 0)
            throw new SAXException("Not a quoted string");
        index = end + 1;
        return doc.substring(start, end);
    }

    protected void init (String doc)
    {
        this.doc = doc;
        this.length = doc.length();
    }

    protected String peekCharStream (Reader stream, InputSource in,
                                     int readAhead)
        throws IOException
    {
        char buf[] = new char[readAhead];
        int charsRead = 0;
        int r;
        while (charsRead < buf.length &&
            (r = stream.read(buf,
                charsRead,
                buf.length - charsRead)) >= 0)
            charsRead += r;

        if (charsRead > 0) {
            PushbackReader pbreader =
                new PushbackReader(stream, charsRead);
            pbreader.unread(buf, 0, charsRead);
            in.setCharacterStream(pbreader);
        }
        return new String(buf, 0, charsRead);
    }

    protected String peekByteStream (InputStream stream, InputSource in,
                                     int readAhead)
        throws IOException
    {
        byte buf[] = new byte[readAhead];
        int bytesRead = 0;
        int r;
        while (bytesRead < buf.length &&
            (r = stream.read(buf,
                bytesRead,
                buf.length - bytesRead)) >= 0)
            bytesRead += r;

        if (bytesRead > 0) {
            PushbackInputStream pbstream =
                new PushbackInputStream(stream, bytesRead);
            pbstream.unread(buf, 0, bytesRead);
            in.setByteStream(pbstream);
        }
        String encoding = in.getEncoding();
        if (encoding == null)
            encoding = autodetectXMLEncodingFamily(buf, 0, bytesRead);
        return new String(buf, 0, bytesRead, encoding);
    }

    // Without Byte Order Mark
    protected static final byte[] preambleUTF8 = {
        0x3c, 0x3f, 0x78, 0x6d
    };

    // With Byte Order Mark (BOM)
    protected static final byte[] preambleUTF16BE_BOM = {
        /* 0xfe, 0xff*/
        -0x02, -0x01
    };
    protected static final byte[] preambleUTF16LE_BOM = {
        /* 0xff, 0xfe */
        -0x01, -0x02
    };

    public static boolean arraycmp (byte test[], int start, int len,
                                    byte check[])
    {
        if (len < check.length)
            return false;
        for (int i = 0; i < check.length; i++)
            if (check[i] != test[start + i])
                return false;
        return true;
    }

    /**
     Auto detect the encoding <em>family</em>.  This will nearly
     always return ISO8859_1, which represents the whole UTF8 family
     of encodings (UTF8, US-ASCII, ISO8859_*, EUC, JIS, Big5, GBK,
     KOI8_R, etc.).  This is because ISO8859_1 is the easiest way to
     decode thi UTF8 family, since 8859-1 is a single-byte encoding
     and nearly all prologs will be in US-ASCII.

     It could also return UnicodeBig or UnicodeLittle for the two
     byte-order variations of UTF-16.

     See the XML spec, Appendix F for more information.
     @return a String that can be used as an encoding name for the
     encoding family detected
     @exception java.io.UnsupportedEncodingException if an unsupported
     encoding family (such as the four-byte UCS-4) is encountered
     */
    public static String autodetectXMLEncodingFamily (
        byte buf[], int start, int len
    )
        throws UnsupportedEncodingException
    {
        // First check for standard UTF8.
        if (arraycmp(buf, start, len, preambleUTF8))
            return "ISO8859_1";

        // Big and little endian UTF16 with BOM.  Not only must the
        // BOM be present, but it must also be the case that the next
        // two bytes are not both 0x00, because then we would have
        // some variant of UCS-4 (which isn't supported).
        if (arraycmp(buf, start, len, preambleUTF16BE_BOM)) {

            if ((buf[start + preambleUTF16BE_BOM.length] == 0x00) &&
                (buf[start + preambleUTF16BE_BOM.length + 1] == 0x00))
                throw new UnsupportedEncodingException(
                    "UCS-4 encoding not supported"
                );

            return "UnicodeBig";
        }

        if (arraycmp(buf, start, len, preambleUTF16LE_BOM)) {

            if ((buf[start + preambleUTF16LE_BOM.length] == 0x00) &&
                (buf[start + preambleUTF16LE_BOM.length + 1] == 0x00))
                throw new UnsupportedEncodingException(
                    "UCS-4 encoding not supported"
                );

            return "UnicodeLittle";
        }

        // We could check here for the various flavors of UCS-4 or
        // EBCDIC, if they were supported or it were worthwhile.

        // And, if none of that makes any sense, just return ISO8859_1
        // anyway (this is the correct behavior -- any XML doc could
        // be in UTF8 without an <?xml?> declaration, and so could
        // start with any random garbage).
        return "ISO8859_1";
    }
}
