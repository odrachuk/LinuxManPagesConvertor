/*******************************************************************************
 * Created by o.drachuk on 22/05/2014. 
 *
 * Copyright Oleksandr Drachuk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.softsandr.man.converter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * This class used for...
 */
public class SQLiteJDBC {
    private final String tableName;

    public SQLiteJDBC(String tableName) {
        this.tableName = tableName;
    }

    public void insertData(SQLiteManRecord manRecord) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:man/man.sqlite");
            c.setAutoCommit(false);
            System.out.println("Database opened successfully.");

            stmt = c.createStatement();
            String sql = "INSERT INTO " + tableName + " (name,synopsis,file) " +
                    "VALUES (\'" + manRecord.getName() + "\', \'"
                    + manRecord.getSynopsis() + "\', \'"
                    + manRecord.getFile() + "\');";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.err.println(manRecord);
            System.exit(0);
        }
    }
}
