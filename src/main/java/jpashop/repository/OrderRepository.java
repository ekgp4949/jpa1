package jpashop.repository;

import jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllByString(OrderSearch orderSearch) {
        String jpql ="select o from Order o join o.member m where 1=1";
        if(orderSearch.getOrderStatus() != null) {
            jpql += " and o.status = :status";
        }

        if(StringUtils.hasText(orderSearch.getMemberName())) {
            jpql += " and m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class);
        if(orderSearch.getOrderStatus() != null) {
            query.setParameter("status", orderSearch.getOrderStatus());
        }

        if(StringUtils.hasText(orderSearch.getMemberName())) {
            query.setParameter("name", orderSearch.getMemberName());
        }
        return query
                .setMaxResults(1000)
                .getResultList();
    }

    /**
     * JPA Criteria 유지보수 어려움
     */
//    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Order> query = cb.createQuery(Order.class);
//
//    }

}
