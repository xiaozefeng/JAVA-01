package io.github.mickey.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author mickey
 * @date 2/17/21 19:31
 */
public class Main {

    private static final String dbUrl = "jdbc:mysql://localhost:3306/foo";
    private static final String username = "root";
    private static final String password = "root";

    public static void main(String[] args)  {
        String sql = "INSERT INTO `user` (`name`, `age`)VALUES ('lucy', 30)";
        try (Connection connection = DriverManager.getConnection(dbUrl,
                username,
                password)) {
            //final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //preparedStatement.setString(1, "rose");
            //preparedStatement.setInt(2, 20);
            //
            //final int rowInserted = preparedStatement.executeUpdate();
            //System.out.println(rowInserted);

            final Statement s = connection.createStatement();
            final int row = s.executeUpdate(sql);
            System.out.println(row);

            //String sel = "select * from user";
            //final Statement statement = connection.createStatement();
            //final ResultSet result= statement.executeQuery(sel);
            //while (result.next()) {
            //    final String name = result.getString("name");
            //    final int age = result.getInt("age");
            //    System.out.println("name:" + name +",age:" + age);
            //}

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
