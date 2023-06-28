import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;

public class EmployeesWithASalaryOver50000 {

    public static void main(String[] args) {
        EntityManager em = Utils.createEntityManager();
        em.getTransaction().begin();

        List<Employee> employeeList = em.createQuery("from Employee where salary>50000", Employee.class).getResultList();
        for (Employee employee : employeeList) {
            System.out.println(employee.getFirstName());
        }

        em.getTransaction().commit();
        em.close();


    }
}
