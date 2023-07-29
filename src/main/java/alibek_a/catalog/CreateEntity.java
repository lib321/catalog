package alibek_a.catalog;

import alibek_a.catalog.entity.catalog.Category;
import alibek_a.catalog.entity.catalog.Characteristic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class CreateEntity {

    public static void main(String[] args) {
        // Введите название категории: Мебель
        // Введите характеристики категории: Материал, Ширина, Высота
        // Категория с таким названием существует
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите название категории: ");
        String categoryName = scanner.nextLine();

        try {
            manager.getTransaction().begin();
            TypedQuery<Category> categoryTypedQuery = manager.createQuery("select c from Category c where c.name = ?1", Category.class);
            categoryTypedQuery.setParameter(1, categoryName);
            List<Category> categories = categoryTypedQuery.getResultList();
            if (categories.isEmpty()) {
                Category category = new Category();
                category.setName(categoryName);
                manager.persist(category);
                System.out.print("Введите характеристики категории: ");
                String characteristics = scanner.nextLine();
                String[] split = characteristics.split(",\\s");
                for (int i = 0; i < split.length; i++) {
                    Characteristic characteristic = new Characteristic();
                    characteristic.setName(split[i]);
                    characteristic.setCategory(category);
                    manager.persist(characteristic);
                }
            } else {
                System.out.println("Категория с таким названием существует\n");
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
