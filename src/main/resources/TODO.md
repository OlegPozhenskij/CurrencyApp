1. Добавить DTO классы в Controller [+]

2. Добавить @Service
@RequiredArgsConstructor
public class OrdersManager каждому мэнеджеру

3. Добавить 2 бина в Application класс после psvm:
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(OrderWithItems.class)
                .addAnnotatedClass(OrderItem.class)
                .addAnnotatedClass(OrdersStatistics.class)
                .buildSessionFactory();
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

4. Вынести header и footer в Fragments (resources)

5. Поменять структуру Ресурсов

6. Сравнить фронт с моим, понять что можно исправить
