package de.hskl.swtp.ss19.sqlcoachservice.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * Generate DataSource for Database connection pooling.
 *
 * @author  Bastian Beggel
 */

public class DataSourceFactory {

    private static final Logger log = Logger.getLogger(DataSourceFactory.class);

    public static DataSource generateFromResourceFile(String resourceFile) throws IOException {

        log.info("Loading DataSource properties file :" + resourceFile);
        Properties prop = ResourceUtils.loadFromResource(resourceFile);

        return generate(prop);
    }

    public static DataSource generate(Properties properties) {

        logProperties(properties);
        HikariConfig config = new HikariConfig(properties);
        HikariDataSource ds = new HikariDataSource(config);
        return ds;

    }

    private static  void logProperties ( Properties prop) {
        Set<String> keys = prop.stringPropertyNames();
        for (String key : keys) {
            log.info("Initialize Connection DataSource with property: " + key + " -> " + prop.getProperty(key));
        }

    }

}
