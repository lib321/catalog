package alibek_a.catalog;

import alibek_a.catalog.entity.catalog.Category;
import alibek_a.catalog.entity.catalog.CharValue;
import alibek_a.catalog.entity.catalog.Characteristic;
import alibek_a.catalog.entity.catalog.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.List;

public class JpaLesson {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        //Product product = manager.find(Product.class, 3L);
        //System.out.println(product.getName());
        //System.out.println(product.getPrice());

        /*
        List<CharValue> charValue = product.getCharValues();
        System.out.println(product.getName());
        for (CharValue value : charValue) {
            System.out.println(value.getCharacteristic().getName() + " : " + value.getValue());
        }
        */

        /*
        long categoryId = 1;
        String characteristic = "Производитель";
        String charValue = "AMD";

        Category category = manager.find(Category.class, categoryId);
        List<Product> products = category.getProducts();
        for (Product product : products) {
            for (CharValue value : product.getCharValues()) {
                if (value.getValue().equals(charValue) && value.getCharacteristic().getName().equals(characteristic)) {
                    System.out.println(product.getName());
                }
            }
        }
        */

       /* long categoryId = 1;
        long minPrice = 85000;
        long maxPrice = 200000;
        String name = "AMD";
        Category category = manager.find(Category.class, categoryId);
        List<Product> products = category.getProducts();
        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice && product.getName().contains(name)) {
                System.out.println(product.getName());
            }
        }
        */

        //TypedQuery<Category> categoryTypedQuery = manager.createQuery("select c from Category c", Category.class);
        //List<Category> categories = categoryTypedQuery.getResultList();
        /*for (Category category : categories) {
            System.out.println(category.getName() + ": ");
            for (Product product : category.getProducts()) {
                System.out.println(product.getName());
            }
        }*/

        /*for (Category category : categories) {
            System.out.println(category.getName() + ": ");
            TypedQuery<Product> productTypedQuery = manager.createQuery("select p from Product p where p.price <= 100000 " +
                    "and p.category.id = " + category.getId(), Product.class);
            List<Product> products = productTypedQuery.getResultList();
            for (Product product : products) {
                System.out.println(product.getName());
            }
        }*/

       /*String name = "Bill";
        int age = 45;
        String query = "select u from user u";
        System.out.println(query + ".name = " + "'" + name + "'" + " and u.age = " + age);*/

        /*long minPrice = 80000L;
        long maxPrice = 100000L;
        TypedQuery<Product> productTypedQuery = manager.createQuery("select p from Product p where p.price >= ?1 and p.price <= ?2", Product.class);
        productTypedQuery.setParameter(1, minPrice);
        productTypedQuery.setParameter(2, maxPrice);
        List<Product> products = productTypedQuery.getResultList();
        for (Product product : products) {
            System.out.println(product.getName());
        }*/

        /*
        String categoryName = "Процессоры";
        TypedQuery<Category> categoryTypedQuery = manager.createQuery("select c from Category c where c.name = ?1", Category.class);
        categoryTypedQuery.setParameter(1, categoryName);
        List<Category> categories = categoryTypedQuery.getResultList();
        if (categories.isEmpty()) {
            System.out.println("Не существует");
        } else {
            System.out.println("Существует");
        }
        */

        String productName = "Monitor";
        TypedQuery<Product> productTypedQuery = manager.createQuery("select p from Product p where p.name like ?1", Product.class);
        productTypedQuery.setParameter(1, "%" + productName + "%");
        List<Product> products = productTypedQuery.getResultList();
        for (Product product : products) {
            System.out.println(product.getName());
        }

    }
}
