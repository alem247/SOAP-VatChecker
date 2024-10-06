package implementation;

import eu.vies.vat.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;
import java.time.LocalDate;


public class CheckVatNumber {
    public static void main(String[] args) {
        if (args.length != 2) {
                System.out.println("Uporaba: java -jar CheckVatNumber.java <CountryCode> <VatNumber>");
            return;
        }


        String countryCode = args[0];
        String vatNumber = args[1];

        try {
            CheckVatService service = new CheckVatService();
            CheckVatPortType port = service.getCheckVatPort();

            // tole ne dela a moralo bi delat!! v CheckVatPortType.java je definiran requestWrapper checkVat in responseWrapper checkVatResponse za checkVat
            /**
            CheckVat checkVat = new CheckVat();
            checkVat.setCountryCode(countryCode);
            checkVat.setVatNumber(vatNumber);

            CheckVatResponse response = port.checkVat(checkVat); */

            // tko da je narejeno manuelno
            Holder<String> countryCodeHolder = new Holder<String>(countryCode);
            Holder<String> vatNumberHolder = new Holder<String>(vatNumber);
            Holder<XMLGregorianCalendar> requestDateHolder = new Holder<XMLGregorianCalendar>(DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDate.now().toString()));
            Holder<Boolean> validHolder = new Holder<Boolean>(false);
            Holder<String> nameHolder = new Holder<String>("?");
            Holder<String> addressHolder = new Holder<String>("?");
            port.checkVat(countryCodeHolder, vatNumberHolder, requestDateHolder, validHolder, nameHolder, addressHolder);

            if (validHolder.value) {
                System.out.println("Podatki zavezanca za DDV:");
                System.out.println("Ime podjetja: " + nameHolder.value);
                System.out.println("Naslov: " + addressHolder.value);
                System.out.println("DDV številka je veljavna.");
            } else {
                System.out.println("DDV številka ni veljavna.");
            }

        } catch (WebServiceException e) {
            System.out.println("Napaka pri klicu storitve: " + e.getMessage());
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
