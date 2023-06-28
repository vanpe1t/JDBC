package dao.impl;

import dao.CityDAO;
import model.City;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class CityDAOImpl implements CityDAO {

    public CityDAOImpl() {
    }

    @Override
    public void create(City city) {

        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.save(city);
            transaction.commit();
        }
    }

    @Override
    public City readById(int id) {

        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(City.class, id);
        }
        //return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(City.class, id);
    }

    @Override
    public List<City> readAll() {

        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            List<City> cities = (List<City>) session.createQuery("From City").list();
            return cities;
        }
    }

    @Override
    public void updateById(City city) {

        if (city == null) {
            return;
        }
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            session.update(city);
            transaction.commit();
        }

    }

    @Override
    public void deleteById(int id) {

        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            City result = session.get(City.class, id);
            if (result != null) {
                session.delete(session.get(City.class, id));
                transaction.commit();
            }
        }

    }
}
