import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EmployeesMaximumSalaries {

    public static void main(String[] args) {
        EntityManager em = Utils.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("SELECT department.name, MAX(salary)" +
                " FROM Employee GROUP BY department.name" +
                " HAVING MAX(salary) NOT BETWEEN 30000 AND 70000", Object[].class).getResultList()
                .forEach(objects -> System.out.println(objects[0] + " " + objects[1]));


        em.getTransaction().commit();
        em.close();
    }


}
