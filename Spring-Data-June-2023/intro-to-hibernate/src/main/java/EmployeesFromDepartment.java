import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;

public class EmployeesFromDepartment {


    public static void main(String[] args) {
        EntityManager em = Utils.createEntityManager();
        em.getTransaction().begin();

        List<Employee> employeeByDepartment = em
                .createQuery("from Employee where department.name = :depName order by salary, id", Employee.class)
                .setParameter("depName", "Research and Development")
                .getResultList();

        em.getTransaction().commit();

        for (Employee employee : employeeByDepartment) {
            System.out.printf("%s %s from Research and Development - $%.02f\n", employee.getFirstName(), employee.getLastName(), employee.getSalary());
        }
        em.close();


    }
}
