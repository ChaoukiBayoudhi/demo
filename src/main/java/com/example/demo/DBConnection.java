package com.example.demo;
import java.sql.*;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class DBConnection {
    private static final String url="jdbc:postgresql://localhost:5432/registrationdb";
    private static final String userName="user01";
    private static final String password="user01";
    private static Connection con;
    public static void connectToDB()
    {
        try {
            con= DriverManager.getConnection(url,userName,password);
            System.out.println("DB Connection is Ready...");

        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    public static void disconnectFromDB() throws SQLException {
        if(con!=null && !con.isClosed())
            con.close();
    }
    public static boolean signIn(String login,String password)
    {
        try{
            connectToDB();
            String request="select * from users where login =? and password=crypt('"+password+"',password)";
            PreparedStatement st =con.prepareStatement(request);
            st.setString(1, login);
           // st.setString(2, );
            ResultSet rs = st.executeQuery();
            boolean res= rs.next();
            disconnectFromDB();
            rs.close();
            return res;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }
    //DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement st = null;
        ResultSet resultSet = null;
        RowSetFactory aFactory = RowSetProvider.newFactory();
        CachedRowSet crs = aFactory.createCachedRowSet();        try {
            //Connect to DB (Establish Oracle Connection)
            connectToDB();
            System.out.println("Select statement: " + queryStmt + "\n");
            //Create statement
            st = con.createStatement();
            //Execute select (query) operation
            resultSet = st.executeQuery(queryStmt);
            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (st != null) {
                //Close Statement
                st.close();
            }
            //Close connection
            disconnectFromDB();
        }
        //Return CachedRowSet
        return crs;
    }
    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement st = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            connectToDB();
            //Create Statement
            st = con.createStatement();
            //Run executeUpdate operation with given sql statement
            st.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (st != null) {
                //Close statement
                st.close();
            }
            //Close connection
            disconnectFromDB();
        }
    }
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
