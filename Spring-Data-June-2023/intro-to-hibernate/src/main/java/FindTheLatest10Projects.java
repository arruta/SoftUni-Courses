import entities.Project;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;

public class FindTheLatest10Projects {

    private static final String PRINT_PROJECT_INFO_FORMAT =
            "Project name: %s\n" +
                    "      Project Description: %s\n" +
                    "      Project Start Date: %s\n" +
                    "      Project End Date:%s\n";

    public static void main(String[] args) {
        EntityManager em = Utils.createEntityManager();
        em.getTransaction().begin();

        List<Project> lastTenProjects = em.createQuery("FROM Project ORDER BY startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();

        lastTenProjects
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf(PRINT_PROJECT_INFO_FORMAT,
                        p.getName(),
                        p.getDescription(),
                        p.getStartDate(),
                        p.getEndDate()));

        em.getTransaction().commit();
        em.close();
    }
}
