import java.sql.*;


//EX: 驱动，前向, SUID 操作
public class Ex81_AccessConn {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //jdbc4 or later  Class.forName() can be ignore if the DRIVERS can be auto self-registration.
        /* // Access
		   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	       Connection conn = DriverManager.getConnection("jdbc:ucanaccess://d:/1.mdb");
		*/
		
	      /*//SQL Server
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
			String dbUrl = "jdbc:sqlserver://localhost:1433;" +
			                                     "databaseName=lesson;integratedSecurity=true;"; 
			//eclipse jvm --> native library path  --> authjdbc.dll 
		    //System.out.println(System.getProperty("java.library.path"));      
		 */

        //mysql
        Class.forName("com.mysql.jdbc.Driver");
        String dbUrl = "jdbc:mysql://localhost:3306/lesson";
        Connection conn = DriverManager.getConnection(dbUrl, "root", "");


        // DatabaseMetaData dbmd = conn.getMetaData();
        // DatabaseMetaData dbmd = conn.getMetaData();
        //  System.out.println(dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));


        String sql = "select * from student";
        Statement stmt = conn.createStatement();
        // Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

        ResultSet rs = stmt.executeQuery(sql);
		     /*ResultSetMetaData rsmd = rs.getMetaData();
		     rsmd.getColumnCount();  //1-3
		     rsmd.getColumnName(1);
		     rsmd.getColumnName(2);
		     rsmd.getColumnName(3);
		     */

        //求长度
        int len = 0;
        while (rs.next()) {
            len++;
        }
        System.out.println(len);

        String sql1 = "delete from student where id=5";
        stmt.executeUpdate(sql1);

        sql1 = "insert into student (sname,score) values(\"zs5\",50)";
        stmt.executeUpdate(sql1);


        sql1 = "update student  set sname=\"update\" where id=1";
        stmt.executeUpdate(sql1);


        Statement stmt1 = conn.createStatement();
        ResultSet rs1 = stmt1.executeQuery(sql);
        while (rs1.next()) {
            System.out.print(rs1.getInt(1) + "\t");
            System.out.print(rs1.getString(2) + "\t");
            System.out.print(rs1.getDouble(3) + "\n");
        }


        rs.close();
        stmt.close();

        rs1.close();
        stmt1.close();

        conn.close();

    }

}
