package xmlrpc;

import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.serializer.TypeSerializer;
import org.xml.sax.SAXException;

public class StringTypeFactory extends TypeFactoryImpl {
    public StringTypeFactory(XmlRpcController pController) {
        super(pController);
    }

    public TypeSerializer getSerializer(XmlRpcStreamConfig pConfig, Object pObject) throws SAXException  {  
        if (pObject instanceof String){
            return new MyStringSerializer();
        } else if(pObject instanceof Long){
        	return new MyLongSerializer();
        } else{
            return super.getSerializer(pConfig, pObject);
        }
    }
}