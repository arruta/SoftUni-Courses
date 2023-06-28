import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Set;

public class GetEmployeesWithProject {


    public static void main(String[] args) {
        EntityManager em = Utils.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        int employeeId = Integer.parseInt(scanner.nextLine());
        em.getTransaction().begin();
        Employee employee = em.createQuery("FROM Employee WHERE id = :employeeId", Employee.class)
                .setParameter("employeeId", employeeId)
                .getSingleResult();


        printEmployeeInfo(employee);

        em.getTransaction().commit();
        em.close();
    }

    private static void printEmployeeInfo(Employee employee) {
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        String jobTittle = employee.getJobTitle();
        Set<Project> employeeProjects = employee.getProjects();

        if (!employeeProjects.isEmpty()) {
            System.out.printf("%s %s - %s\n", firstName, lastName, jobTittle);
            employeeProjects.stream()
                    .sorted(Comparator.comparing(Project::getName))
                    .forEach(p -> System.out.printf("      %s\n", p.getName()));
        }
    }

}
