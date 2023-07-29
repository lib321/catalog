package alibek_a.catalog;

import alibek_a.catalog.entity.catalog.Category;
import alibek_a.catalog.entity.catalog.CharValue;
import alibek_a.catalog.entity.catalog.Characteristic;
import alibek_a.catalog.entity.catalog.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class CreateProduct {

    public static void main(String[] args) {
        // - Процессоры [1]
        // - Мониторы [2]
        // - Мебель [3]
        // Выберите категорию: 1
        // Введите название: ___
        // Введите стоимость: ___
        // Производитель: ___
        // Сокет: ___
        // Частота: ___
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("main");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Category> categoryTypedQuery = entityManager.createQuery("select c from Category c", Category.class);
            List<Category> categories = categoryTypedQuery.getResultList();
            for (Category category : categories) {
                System.out.println(" - " + category.getName() + " [" + category.getId() + "]");
            }

            System.out.print("Выберите категорию: ");
            Scanner scanner = new Scanner(System.in);
            String categoryId = scanner.nextLine();
            long categoryID1 = Integer.parseInt(categoryId);
            Category category = entityManager.find(Category.class, categoryID1);
            while (category == null) {
                System.out.print("Введите еще раз: ");
                categoryId = scanner.nextLine();
                categoryID1 = Integer.parseInt(categoryId);
                category = entityManager.find(Category.class, categoryID1);
            }
            System.out.print("Введите название: ");
            String productName = scanner.nextLine();
            Product product = new Product();
            product.setName(productName);
            product.setCategory(category);

            System.out.print("Введите стоимость: ");
            String price = scanner.nextLine();
            while (!price.matches("\\d+")) {
                System.out.print("Введите еще раз: ");
                price = scanner.nextLine();
            }
            long price1 = Integer.parseInt(price);
            product.setPrice(price1);
            entityManager.persist(product);

            List<Characteristic> characteristics = category.getCharacteristics();
            for (Characteristic characteristic : characteristics) {
                System.out.print(characteristic.getName() + ": ");
                String charValueName = scanner.nextLine();
                CharValue charValue = new CharValue();
                charValue.setValue(charValueName);
                charValue.setCharacteristic(characteristic);
                charValue.setProduct(product);
                entityManager.persist(charValue);

            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
