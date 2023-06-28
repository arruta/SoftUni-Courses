import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class FindEmployeesByFirstName {
    public static void main(String[] args) {
        EntityManager em = Utils.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine() + "%";
        em.getTransaction().begin();
        List<Employee> resultList = em.createQuery("FROM Employee WHERE firstName LIKE :pattern", Employee.class)
                .setParameter("pattern", pattern)
                .getResultList();

        for (Employee employee : resultList) {
            System.out.printf("%s %s - %s - ($%s)\n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getJobTitle(),
                    employee.getSalary());

        }

        em.getTransaction().commit();
        em.close();
    }
}
