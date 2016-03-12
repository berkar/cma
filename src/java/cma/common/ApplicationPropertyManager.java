package cma.common;

import cma.Constants;

import java.io.*;
import java.util.Properties;

/**
 *
 */
public class ApplicationPropertyManager
{
    private Properties prop;
    private File rootDir = null;

    /** Field ourInstance ... */
    private static ApplicationPropertyManager ourInstance;

    /**
     * Singleton interface method
     * @param applicationName
     * @return ApplicationPropertyManager
     */
    public static synchronized ApplicationPropertyManager getInstance(String applicationName)
    {
        if (ourInstance == null) {
            ourInstance = new ApplicationPropertyManager(applicationName);
        }
        return ourInstance;
    }

    /**
     * Private constructor. Only te be used by the singleton method <code>{@link #getInstance}</code>
     * @param applicationName
     */
    private ApplicationPropertyManager(String applicationName)
    {
        // Make sure that path to property file exists
        rootDir = new File(
            System.getProperties().getProperty("user.home") +
            System.getProperties().getProperty("file.separator") +
            "." + applicationName
        );
        rootDir.mkdirs();

        try {
            InputStream is = new FileInputStream(
                rootDir.getPath() +
                System.getProperties().getProperty("file.separator") +
                "config.properties"
            );
            if (is != null) {
                System.out.println("Found property file!");
                prop = new Properties();
                prop.load(is);
                is.close();
            }
            else {
                System.out.println("Could not find property file!");
                prop = new Properties();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Create new properties! No file properties could be found");
            prop = new Properties();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println(e);
        }
        //prop.list(System.out);
    }

    public synchronized void save()
    {
        // Save properties persistent

        try {
            OutputStream os = new FileOutputStream(
                rootDir.getPath() +
                System.getProperties().getProperty("file.separator") +
                "config.properties"
            );
            PrintStream ps = new PrintStream(os);
            for (Object o : prop.keySet()) {
                String key = (String) o;
                ps.println(key + "=" + prop.getProperty(key));
            }
            os.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    private String getProperty(String key)
    {
        if (prop != null) {
            String returnValue;
            returnValue = prop.getProperty(key);
            return returnValue;
        }
        return null;
    }

    private void setProperty(String key, String value)
    {
        if (prop != null) {
            prop.setProperty(key, value);
        }
    }

    public String getLatestCompetition()
    {
        return getProperty("latest-competition");
    }

    public void setLatestCompetition(String latestCompetition)
    {
        setProperty("latest-competition", latestCompetition);
    }

    public String getBrowserPath()
    {
        return getProperty("browser-path");
    }

    public void setBrowserPath(String latestCompetition)
    {
        setProperty("browser-path", latestCompetition);
    }

    public static void main(String[] args) throws Exception
    {

        System.out.println("Starting ...");
        ApplicationPropertyManager pm = ApplicationPropertyManager.getInstance(Constants.APPLICATION_NAME);
        String comp = pm.getLatestCompetition();
        System.out.println("Comp: " + comp);

        pm.setLatestCompetition(pm.rootDir.toURL().toString());
        pm.save();

        System.out.println("Path: " + pm.rootDir.getPath());
        System.out.println("Path: " + pm.rootDir.toURI());
        System.out.println("Path: " + pm.rootDir.toURL());
        System.out.println("Path: " + pm.rootDir.toURL().toExternalForm());
/*
        System.out.println();
        System.getProperties().list(System.out);
*/
    }
}

