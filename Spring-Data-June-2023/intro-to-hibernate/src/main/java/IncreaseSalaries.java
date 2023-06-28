import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class IncreaseSalaries {

    public static void main(String[] args) {
        EntityManager em = Utils.createEntityManager();
        em.getTransaction().begin();
        List<Employee> employeeToUpdateSalary =
                em.createQuery("FROM Employee WHERE department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                        .getResultList();

        for (Employee employee : employeeToUpdateSalary) {
            BigDecimal multiplier = BigDecimal.valueOf(0.12);
            BigDecimal newSalary = employee.getSalary().add(employee.getSalary().multiply(multiplier));
            employee.setSalary(newSalary);
        }

        em.flush();
        em.getTransaction().commit();
        em.close();

        employeeToUpdateSalary.forEach(e -> System.out.printf("%s %s ($%s)\n", e.getFirstName(),
                e.getLastName(), e.getSalary().toString()));

    }
}
