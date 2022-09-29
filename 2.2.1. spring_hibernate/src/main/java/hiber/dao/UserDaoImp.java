package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }
   /*
   Достаем юзера по модели и серии автомобиля
    */
   @Override
   public User getFrom(String model, int series) {
      TypedQuery<User> query = null;
      try {
         query = sessionFactory.getCurrentSession()
                 .createQuery("from User user where user.car.model = :model and user.car.series = :series");
         query.setParameter("model", model).setParameter("series", series);
         return query.setMaxResults(1).getSingleResult();
      } catch (NoResultException ex) {
         System.out.println("User с запрошенным авто не найден");
         return null;
      }
   }

}
