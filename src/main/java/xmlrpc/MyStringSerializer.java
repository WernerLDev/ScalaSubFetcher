package xmlrpc;

import org.apache.xmlrpc.serializer.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class MyStringSerializer extends TypeSerializerImpl {
    // Tag name of an string.
    public static final String STRING_TAG = "string";

    public void write(ContentHandler pHandler, Object pObject) throws SAXException {
        write(pHandler, STRING_TAG, pObject.toString());
    }
}
