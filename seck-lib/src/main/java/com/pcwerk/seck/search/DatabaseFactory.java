/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pcwerk.seck.search;

import java.io.IOException;

/**
 *
 * @author nelson
 */
public class DatabaseFactory {
    
    public enum Type
    {
        POJO, HBASE, MYSQL
    }
    
    public static DatabaseDao getDatabase(Type type) throws IOException
    {
        switch (type)
        {
            case POJO:
                return new PojoImpl();
                
            case HBASE:
                return new HBaseImpl();
                
            case MYSQL:
                return new MySQLImpl();
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
