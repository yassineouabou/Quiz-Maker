package net.projet.dao;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import net.projet.entity.User;
import net.projet.enums.Roles;
import net.projet.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

public class UserDoaTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private UserDoa userDao;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        userDao = new UserDoa(mockConnection) ;
    }

    @Test
    void testSaveUser_Success() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        User user = new User("Yassine", "Ouabou",  "123","yassine@gmail.com",Roles.ETUDIANT);
        boolean result = userDao.saveUser(user);
        assertTrue(result);
        verify(mockPreparedStatement, times(1)).setString(1, "Yassine");
        verify(mockPreparedStatement, times(1)).setString(2, "Ouabou");
        verify(mockPreparedStatement, times(1)).setString(3, "yassine@gmail.com");
        verify(mockPreparedStatement, times(1)).setString(4, "123");
        verify(mockPreparedStatement, times(1)).setString(5, Roles.ETUDIANT.name());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testSaveUser_Failure() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        User user = new User("Yassine", "Ouabou",  "123","yassine@gmail.com", Roles.ETUDIANT);
        boolean result = userDao.saveUser(user);
        assertFalse(result);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testSaveUser_ThrowsException() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erreur"));
        User user = new User("Yassine", "Ouabou",  "123","Yassine@gmail.com", Roles.ETUDIANT);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userDao.saveUser(user));
        assertEquals("java.sql.SQLException: Erreur", exception.getMessage());
    }

    @Test
    void testDeleteUser_Success() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean result = userDao.deleteUser(1L);
        assertTrue(result);
        verify(mockPreparedStatement, times(1)).setLong(1, 1L);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testDeleteUser_Failure() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        boolean result = userDao.deleteUser(1L);
        assertFalse(result);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testDeleteUser_ThrowsException() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erreur de suppression"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userDao.deleteUser(1L));
        assertEquals("java.sql.SQLException: Erreur de suppression", exception.getMessage());
    }

    @Test
    void testUpdateUser_Success() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        User user = new User("Yassine", "Ouabou", "123", "Yassine@gmail.com", Roles.ETUDIANT);
        boolean result = userDao.updateUser(user);
        assertTrue(result);
        verify(mockPreparedStatement, times(1)).setString(1, "Yassine");
        verify(mockPreparedStatement, times(1)).setString(2, "Ouabou");
        verify(mockPreparedStatement, times(1)).setString(3, "Yassine@gmail.com");
        verify(mockPreparedStatement, times(1)).setString(4, "123");
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testUpdateUser_Failure() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        User user = new User("Yassine", "Ouabou", "1234", "Yassine@gmail.com", Roles.PROFESSEUR);
        boolean result = userDao.updateUser(user);
        assertFalse(result);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testUpdateUser_ThrowsException() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Erreur de mise à jour"));
        User user = new User("Yassine", "Ouabou", "1234", "yassine@gmail.com", Roles.ETUDIANT);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userDao.updateUser(user));
        assertEquals("java.sql.SQLException: Erreur de mise à jour", exception.getMessage());
    }

    @Test
    void testFindAll_Success() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString(1)).thenReturn("1");
        when(mockResultSet.getString(2)).thenReturn("Yassine");
        when(mockResultSet.getString(3)).thenReturn("Ouabou");
        when(mockResultSet.getString(4)).thenReturn("yassine@gmail.com");
        when(mockResultSet.getString(5)).thenReturn("123");
        when(mockResultSet.getString(6)).thenReturn(Roles.ETUDIANT.name());

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Connection mockConnection = mock(Connection.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        UserDoa userDao = new UserDoa(mockConnection);
        ArrayList<User> users = userDao.findAll(Roles.ETUDIANT);

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("Yassine", users.get(0).getNom());
        assertEquals("Ouabou", users.get(0).getPrenom());
    }

    @Test
    void testFindAll_EmptyResult() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(false);
        when(mockResultSet.getString(anyInt())).thenReturn("");

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Connection mockConnection = mock(Connection.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        UserDoa userDao = new UserDoa(mockConnection);
        ArrayList<User> users = userDao.findAll(Roles.ETUDIANT);

        assertNotNull(users);
        assertEquals(0, users.size());
    }

    @Test
    void testFindAll_ThrowsSQLException() throws SQLException {
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Erreur lors de l'exécution de la requête"));

        Connection mockConnection = mock(Connection.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        UserDoa userDao = new UserDoa(mockConnection);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userDao.findAll(Roles.ETUDIANT));
        assertEquals("java.sql.SQLException: Erreur lors de l'exécution de la requête", exception.getMessage());
    }

    @Test
    void testLogin_Success() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString(1)).thenReturn("1");
        when(mockResultSet.getString(2)).thenReturn("Yassine");
        when(mockResultSet.getString(3)).thenReturn("Ouabou");
        when(mockResultSet.getString(4)).thenReturn("yassine@gmail.com");
        when(mockResultSet.getString(5)).thenReturn("123");
        when(mockResultSet.getString(6)).thenReturn(Roles.ETUDIANT.name());

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Connection mockConnection = mock(Connection.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        UserDoa userDao = new UserDoa(mockConnection);
        User user = userDao.login("yassine@gmail.com", "123");

        assertNotNull(user);
        assertEquals("Yassine", user.getNom());
        assertEquals("Ouabou", user.getPrenom());
    }

    @Test
    void testLogin_UserNotFound() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(false);
        when(mockResultSet.getString(anyInt())).thenReturn("");

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Connection mockConnection = mock(Connection.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        UserDoa userDao = new UserDoa(mockConnection);

        assertThrows(UserNotFoundException.class, () -> userDao.login("notexist@gmail.com", "******"));
    }





}