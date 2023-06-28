package dao.impl;

import dao.EmployeeDAO;
import model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {


    //private final Connection connection;

    public EmployeeDAOImpl() {}

    @Override
    public void create(Employee employee) {

        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        }

    }

    @Override
    public Employee readById(int id) {
        Employee employee;
        employee = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Employee.class, id);
        return employee;
    }

    @Override
    public List<Employee> readAll() {

        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        List<Employee> employees = (List<Employee>) session.createQuery("From Employee").list();

        session.close();

        return employees;
    }

    @Override
    public void updateById(Employee employee) {

        if (employee == null) {
            return;
        }
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            session.update(employee);
            transaction.commit();
        }

    }

    @Override
    public void deleteById(int id) {

        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee result = session.get(Employee.class, id);
            if (result != null) {
                session.delete(session.get(Employee.class, id));
                transaction.commit();
            }
        }

    }


//    private final Connection connection;
//
//    public EmployeeDAOImpl(Connection connection) {
//        this.connection = connection;
//    }
//
//    @Override
//    public void create(Employee employee) {
//
//        try(PreparedStatement statement = connection.prepareStatement(
//                "INSERT INTO employee (last_name, first_name, gender, age) VALUES ((?), (?), (?), (?))")) {
//
//            // INSERT INTO employee (last_name, first_name, gender, age)
//            statement.setString(1, employee.getLastName());
//            statement.setString(2, employee.getFirstName());
//            statement.setString(3, employee.getGender());
//            statement.setInt(3, employee.getAge());
//
//            statement.executeQuery();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public Employee readById(int id) {
//
//        Employee employee = null;
//
//        try (PreparedStatement statement = connection.prepareStatement(
//                "SELECT * FROM employee INNER JOIN city ON employee.city_id = city.city_id AND id = (?)")) {
//
//            statement.setInt(1, id);
//            statement.setMaxRows(1);
//
//            ResultSet resultSet = statement.executeQuery();
//            resultSet.next();
//
//            employee = new Employee(id, resultSet.getString("first_name"),
//                    resultSet.getString("last_name"),
//                    resultSet.getString("gender"),
//                    resultSet.getInt("age"),
//                    resultSet.getInt("city_id"));
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return employee;
//    }
//
//    @Override
//    public List<Employee> readAll() {
//
//        List<Employee> list = new ArrayList<>();
//
//        try (PreparedStatement statement = connection.prepareStatement(
//                "SELECT * FROM employee INNER JOIN city ON employee.city_id = city.city_id")) {
//
//            ResultSet resultSet = statement.executeQuery();
//
//            while(resultSet.next()) {
//
//                list.add(new Employee(resultSet.getInt("id"), resultSet.getString("first_name"),
//                        resultSet.getString("last_name"),
//                        resultSet.getString("gender"),
//                        resultSet.getInt("age"),
//                        resultSet.getInt("city_id")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
//
//    @Override
//    public void updateById(Employee employee) {
//
//        try (PreparedStatement statement = connection.prepareStatement(
//                "UPDATE employee SET age = (?) WHERE id = (?)")) {
//
//            statement.setInt(1, employee.getAge());
//            statement.setInt(2, employee.getId());
//
//            statement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void deleteById(int id) {
//
//        try (PreparedStatement statement = connection.prepareStatement(
//                "DELETE FROM employee WHERE id = (?)")) {
//
//
//            statement.setInt(1, id);
//
//            statement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

}
