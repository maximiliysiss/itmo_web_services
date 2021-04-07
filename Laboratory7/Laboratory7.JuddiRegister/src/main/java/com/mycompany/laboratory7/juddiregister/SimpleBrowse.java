/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory7.juddiregister;

/**
 *
 * @author maxim
 */
import java.rmi.RemoteException;
import java.util.Random;
import org.apache.juddi.v3.client.UDDIConstants;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BindingTemplates;
import org.uddi.api_v3.BusinessDetail;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessInfo;
import org.uddi.api_v3.BusinessList;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.BusinessServices;
import org.uddi.api_v3.DiscardAuthToken;
import org.uddi.api_v3.FindBusiness;
import org.uddi.api_v3.GetAuthToken;
import org.uddi.api_v3.Name;
import org.uddi.api_v3.SaveBusiness;
import org.uddi.api_v3.SaveService;
import org.uddi.api_v3.ServiceDetail;
import org.uddi.api_v3.ServiceInfo;
import org.uddi.v3_service.DispositionReportFaultMessage;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;

/**
 * * A Simple UDDI Browser that dumps basic information to console** @author
 * <a href="mailto:alexoree@apache.org">Alex O'Ree</a>
 */
public class SimpleBrowse {

    private static UDDISecurityPortType security = null;
    private static UDDIInquiryPortType inquiry = null;
    private static UDDIPublicationPortType businessService;

    /**
     * * This sets up the ws proxies using uddi.xml in META-INF
     */
    public SimpleBrowse() {
        try {
            // create a manager and read the config in the archive; 
            // you can use your config file name
            UDDIClient client = new UDDIClient("uddi.xml");
            // a UDDIClient can be a client to multiple UDDI nodes, so // supply the nodeName (defined in your uddi.xml.// The transport can be WS, inVM, RMI etc which is defined in the uddi.xml
            Transport transport = client.getTransport("default");// Now you create a reference to the UDDI API
            security = transport.getUDDISecurityService();
            inquiry = transport.getUDDIInquiryService();
            businessService = transport.getUDDIPublishService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * * Main entry point** @param args
     */
    public static void main(String args[]) {
        SimpleBrowse sp = new SimpleBrowse();
        sp.Browse(args);
    }

    public void Browse(String[] args) {
        try {
            String token = GetAuthKey("uddi", "uddi");

            BusinessDetail newBusiness = CreateBussines(token);

            assert newBusiness != null;

            ServiceDetail newService = CreateService(token, newBusiness.getBusinessEntity().get(0));

            assert newService != null;

            security.discardAuthToken(new DiscardAuthToken(token));

            printBusiness(token);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateString(String characters, int length) {
        Random random = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    private BusinessDetail CreateBussines(String token) throws RemoteException, DispositionReportFaultMessage {

        SaveBusiness sb = new SaveBusiness();
        sb.setAuthInfo(token);

        BusinessEntity e = new BusinessEntity();
        e.setBusinessKey(generateString("abc", 24));
        Name n = new Name();
        n.setValue("Test Business Entity");
        n.setLang("Russian");
        e.getName().add(n);
        sb.getBusinessEntity().add(e);

        return businessService.saveBusiness(sb);
    }

    private ServiceDetail CreateService(String token, BusinessEntity be) throws RemoteException {

        String serviceKey = generateString("xyz", 12);

        SaveService ss = new SaveService();
        ss.setAuthInfo(token);
        BusinessService bs = new BusinessService();
        bs.setBusinessKey(be.getBusinessKey());
        bs.setServiceKey(serviceKey);
        bs.setBindingTemplates(new BindingTemplates());
        BindingTemplate bt = new BindingTemplate();
        bt.setBindingKey(null);
        bt.setServiceKey(serviceKey);
        bt.setAccessPoint(new AccessPoint("http://localhost", "wsdl"));
        bs.getBindingTemplates().getBindingTemplate().add(bt);
        bs.getName().add(new Name("Joe's bs", null));
        be.setBusinessServices(new BusinessServices());
        be.getBusinessServices().getBusinessService().add(bs);

        ss.getBusinessService().add(bs);

        return businessService.saveService(ss);
    }

    private void Test(String token) throws RemoteException {

        String businessKey = generateString("abc", 255);
        String serviceKey = generateString("xyz", 255);

        SaveBusiness sb = new SaveBusiness();
        sb.setAuthInfo(token);
        BusinessEntity be = new BusinessEntity();
        Name n = new Name();
        n.setValue("JUDDI_712_SaveService2WithSignature");
        be.getName().add(n);
        be.setBusinessKey(businessKey);
        sb.getBusinessEntity().add(be);
        BusinessDetail saveBusiness = null;
        saveBusiness = businessService.saveBusiness(sb);

        SaveService ss = new SaveService();
        ss.setAuthInfo(token);
        BusinessService bs = new BusinessService();
        bs.setBusinessKey(businessKey);
        bs.setServiceKey(serviceKey);
        bs.setBindingTemplates(new BindingTemplates());
        BindingTemplate bt = new BindingTemplate();
        bt.setBindingKey(null);
        bt.setServiceKey(serviceKey);
        bt.setAccessPoint(new AccessPoint("http://localhost", "wsdl"));
        bs.getBindingTemplates().getBindingTemplate().add(bt);
        bs.getName().add(new Name("Joe's bs", null));
        be.setBusinessServices(new BusinessServices());
        be.getBusinessServices().getBusinessService().add(bs);

        sb.getBusinessEntity().add(be);
        ss.getBusinessService().add(bs);

        businessService.saveService(ss);
    }

    private void printBusiness(String token) throws RemoteException {
        FindBusiness business = new FindBusiness();
        business.setAuthInfo(token);
        org.uddi.api_v3.FindQualifiers fq = new org.uddi.api_v3.FindQualifiers();
        fq.getFindQualifier().add(UDDIConstants.APPROXIMATE_MATCH);
        business.setFindQualifiers(fq);
        Name searchname = new Name();
        searchname.setValue(UDDIConstants.WILDCARD);
        business.getName().add(searchname);

        int index = 1;
        String b_indent = "  ";
        String indent = "    ";

        BusinessList findBusiness = inquiry.findBusiness(business);
        for (BusinessInfo bi : findBusiness.getBusinessInfos().getBusinessInfo()) {
            System.out.println("Business #" + index++);
            System.out.println(b_indent + bi.getBusinessKey());
            System.out.println(b_indent + bi.getDescription());

            if (bi.getServiceInfos() != null && bi.getServiceInfos().getServiceInfo() != null) {
                int s_index = 1;
                System.out.println("Services:");
                for (ServiceInfo si : bi.getServiceInfos().getServiceInfo()) {
                    System.out.println(indent + "Service #" + s_index++);
                    System.out.println(indent + b_indent + si.getServiceKey());
                    System.out.println(indent + b_indent + si.getName());
                }
            }
        }
    }

    private enum AuthStyle {
        HTTP_BASIC, HTTP_DIGEST, HTTP_NTLM, UDDI_AUTH, HTTP_CLIENT_CERT
    }

    /**
     * * Gets a UDDI style auth token, otherwise, appends credentials to the*
     * ws proxies (not yet implemented)** @param username* @param password
     *
     *
     * @param style
     *
     * 87* @ return
     */
    private String GetAuthKey(String username, String password) {
        try {
            GetAuthToken getAuthTokenRoot = new GetAuthToken();
            getAuthTokenRoot.setUserID(username);
            getAuthTokenRoot.setCred(password);// Making API call that retrieves the authentication token for the user.
            AuthToken rootAuthToken = security.getAuthToken(getAuthTokenRoot);
            System.out.println(username + " AUTHTOKEN = (don't log auth tokens!");
            return rootAuthToken.getAuthInfo();
        } catch (Exception ex) {
            System.out.println("Could not authenticate with the provided credentials " + ex.getMessage());
        }
        return null;
    }
}
