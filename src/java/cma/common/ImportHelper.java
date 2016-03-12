package cma.common;

import cma.vo.RegistrationVO;

import java.io.*;
import java.util.*;
import java.text.ParseException;

public class ImportHelper
{
    private final static String SEPARATOR = ",";

    public static Collection parseRegistrationXmlFile(InputStream is) throws ParseException, IOException
    {
        // TODO Convert into RegistrationVO through RegistrationVO.parse(...)
        return null;
    }

    /**
     * Parses a CSV file and maps it into a Collection of RegistrationVO's. Requires the following order on the fields:
     * <br/> DID,Club,Name,Dog name,Address,Telephone,Postal address,Mail address
     * @param is InputStream with CSV file
     * @param separator Which separator to use between fields
     * @return Collection of RegistrationVO's
     * @throws IOException
     */
    public static Collection parseRegistrationCsvFile(InputStream is, String separator) throws IOException
    {
        if(separator == null ||separator.trim().length() <= 0) {
            // Set default separator
            separator = SEPARATOR;
        }
        Collection result = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        int count = 0;
        String row = null;
        // Start looping
        while ((row = br.readLine()) != null) {
            count++;
            if (row.startsWith("#")) {
                // Row with comments
                continue;
            }
            if (row.indexOf(separator) != -1) {
                // There has to be 8 fields (7 separators)!!
                Collection fields = parse(row, separator);
                if(fields.size() == 8) {
                    RegistrationVO vo = new RegistrationVO();
                    Iterator iterator = fields.iterator();
                    vo.setDid((String)iterator.next());
                    vo.setClub((String)iterator.next());
                    vo.setName((String)iterator.next());
                    vo.setDogs((String)iterator.next());
                    vo.setAddress((String)iterator.next());
                    vo.setPhone((String)iterator.next());
                    vo.setPostAddress((String)iterator.next());
                    vo.setMail((String)iterator.next());
                    result.add(vo);
                }
                else {
                    System.out.println("Felaktigt format i importfilen på rad: " + count + "!");
                }
            }
            else {
                // No separators found! Jump over ...
                System.out.println("Felaktigt format i importfilen på rad: " + count + "!");
            }
        }
        System.out.println("Importerat " + result.size() + "st. registreringar!");
        return result;
    }

    private static Collection parse(String row, String separator)
    {
        Collection result = new ArrayList();
        if (row.indexOf(separator) == -1) {
            result.add(row);
        }
        else {
            int startIndex = 0;
            int endIndex = row.indexOf(separator);
            while(endIndex != -1) {
                // Add field found
                String field = row.substring(startIndex, endIndex);
                result.add(field);
                startIndex = endIndex + 1;
                endIndex = row.indexOf(separator, startIndex);
            }
            // Add last field also
            result.add(row.substring(row.lastIndexOf(separator) + 1));
        }
        return result;
    }

    public static void main(String[] args)
    {
        String row = "did,club,name,dogs,e,f,g,h\ndid1,club1,name1,dogs1,e1,f1,g1,h1";
        InputStream is = new ByteArrayInputStream(row.getBytes());
        System.out.println("Parse: " + ImportHelper.parse("PELLE", ","));
        System.out.println("Parse: " + ImportHelper.parse(row, ","));
        System.out.println("Parse: " + ImportHelper.parse(row, ";"));

        try {
            System.out.println("Parse Registration: " + ImportHelper.parseRegistrationCsvFile(is, ","));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
