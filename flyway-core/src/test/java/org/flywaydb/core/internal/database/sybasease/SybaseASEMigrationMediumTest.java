/*
 * Copyright 2010-2017 Boxfuse GmbH
 *
 * INTERNAL RELEASE. ALL RIGHTS RESERVED.
 */
package org.flywaydb.core.internal.database.sybasease;

import org.flywaydb.core.DbCategory;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.util.jdbc.DriverDataSource;
import org.flywaydb.core.migration.ConcurrentMigrationTestCase;
import org.flywaydb.core.migration.MigrationTestCase;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * Test to demonstrate the migration functionality using Sybase ASE with the Jtds driver.
 */
@SuppressWarnings({"JavaDoc"})
@Category(DbCategory.SybaseASE.class)
public class SybaseASEMigrationMediumTest extends MigrationTestCase {
    private static final String JDBC_URL_JTDS = "jdbc:jtds:sybase://127.0.0.1:62080/guest";
    private static final String JDBC_URL_JCONNECT = "jdbc:sybase:Tds:127.0.0.1:62080/guest";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "password";

    @Override
    protected DataSource createDataSource(Properties customProperties) {
        return new DriverDataSource(Thread.currentThread().getContextClassLoader(), null,
                JDBC_URL_JCONNECT, JDBC_USER, JDBC_PASSWORD);
    }

    @Test
    public void concurrent() throws Exception {
        ConcurrentMigrationTestCase testCase = new ConcurrentMigrationTestCase() {
            @Override
            protected DataSource createDataSource(Properties customProperties) {
                return new DriverDataSource(Thread.currentThread().getContextClassLoader(), null,
                        JDBC_URL_JTDS, JDBC_USER, JDBC_PASSWORD);
            }

            @Override
            protected String getBasedir() {
                return "migration/database/sybasease/sql/sql";
            }

            @Override
            protected String getTableName() {
                return "test_user";
            }

            @Override
            protected boolean needsBaseline() {
                return true;
            }
        };

        testCase.setUp();
        testCase.migrateConcurrently();
    }

    @Test
    public void jTDS() throws Exception {
        flyway = new Flyway();
        flyway.setDataSource(JDBC_URL_JTDS, JDBC_USER, JDBC_PASSWORD);
        flyway.clean();
        flyway.setLocations(getMigrationDir() + "/sql");
        assertEquals(4, flyway.migrate());
    }

    @Override
    protected String getQuoteLocation() {
        //Sybase does not support quoting table names
        return getValidateLocation();
    }

    @Ignore("Table quote is not supported in Sybase ASE")
    @Override
    public void quotesAroundTableName() {
    }

    @Override
    protected String getMigrationDir() {
        return "migration/database/sybasease/sql";
    }

    @Override
    @Ignore("Not supported on Sybase ASE Server")
    public void setCurrentSchema() throws Exception {
        //Skip
    }

    @Override
    @Ignore("Schema reference is not supported on SAP ASE")
    public void migrateMultipleSchemas() throws Exception {
        //Skip
    }

    @Override
    @Ignore("Schema reference is not supported on SAP ASE")
    public void schemaExists() throws SQLException {
        //Skip
    }

    @Override
    @Ignore("Table name quote is not supported on SAP ASE")
    public void quote() throws Exception {
        //skip
    }

    @Override
    public void failedMigration() throws Exception {
        // It is line 22 as a go statement is added for Sybase
        doFailedMigration(22);
    }
}