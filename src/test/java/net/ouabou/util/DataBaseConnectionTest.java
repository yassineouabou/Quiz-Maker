package net.ouabou.util;

import net.projet.util.DataBaseConnection;
import org.junit.Test;
import java.sql.Connection;
import static org.junit.Assert.*;


public class DataBaseConnectionTest {
    @Test
    public void testGetConnection() {
        try {
            Connection connection = DataBaseConnection.getConnection();
            assertNotNull(connection);
        } catch (Exception e) {
            fail("Une exception a été levée: " + e.getMessage());
        }
    }
}