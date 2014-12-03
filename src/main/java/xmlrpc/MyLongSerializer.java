package xmlrpc;

import org.apache.xmlrpc.serializer.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class MyLongSerializer extends TypeSerializerImpl {
    // Tag name of an string.
    public static final String LONG_TAG = "double";

    public void write(ContentHandler pHandler, Object pObject) throws SAXException {
        write(pHandler, LONG_TAG, pObject.toString());
    }
}
