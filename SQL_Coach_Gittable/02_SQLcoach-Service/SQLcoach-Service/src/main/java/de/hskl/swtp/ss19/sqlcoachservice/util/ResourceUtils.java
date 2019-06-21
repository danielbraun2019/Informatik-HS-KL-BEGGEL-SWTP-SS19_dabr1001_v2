package de.hskl.swtp.ss19.sqlcoachservice.util;


import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;


/**
 * Utils class.
 * @author  Bastian Beggel
 */
public class ResourceUtils {
    private static final Logger log = Logger.getLogger(DataSourceFactory.class);

    public static Properties loadFromResource(String fileName ) throws IOException {
        URL config = ResourceUtils.class.getClassLoader().getResource(fileName);
        if (config == null) {
            throw  new FileNotFoundException("Property File " + fileName + "not found!");
        }

        Properties prop = new Properties();
        prop.load(new FileInputStream(config.getFile()));


        Set<String> keys = prop.stringPropertyNames();
        for (String key : keys) {
            log.debug("Properties file :" + fileName  + " has property: " + key + " -> " +  prop.getProperty(key));
        }

        return prop;
    }

}
