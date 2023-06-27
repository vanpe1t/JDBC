import dao.EmployeeDAO;
import dao.impl.EmployeeDAOImpl;
import model.Employee;

import java.sql.*;

public class Application {

    public static void main(String[] args) {

        final String user = "postgres";
        final String password = "g@vgth0";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

        try (final Connection connection =
                     DriverManager.getConnection(url, user, password)) {

            System.out.println("Соединение установлено!");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM employee INNER JOIN city ON city.city_id = employee.city_id AND id = (?)");
            statement.setInt(1, 27);


            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int idOfEmployee = resultSet.getInt("id");
                System.out.println("ID работника: " + idOfEmployee);

                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                String cityName = resultSet.getString("city_name");

                System.out.println("Имя: " + firstName);
                System.out.println("Фамилия: " + lastName);
                System.out.println("Пол: " + gender);
                System.out.println("Город: " + cityName);
            }

            EmployeeDAO employeeDAO;
            employeeDAO = new EmployeeDAOImpl(connection);
            System.out.println(employeeDAO.readById(27));

            Employee employeeForAge = employeeDAO.readById(28);
            employeeForAge.setAge(35);
            employeeDAO.updateById(employeeForAge);
            employeeDAO.deleteById(36);
            employeeDAO.readAll().forEach(System.out::println);

        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }

    }

}
