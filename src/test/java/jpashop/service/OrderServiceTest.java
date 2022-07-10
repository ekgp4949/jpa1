package jpashop.service;

import jpashop.domain.Address;
import jpashop.domain.Member;
import jpashop.domain.Order;
import jpashop.domain.OrderStatus;
import jpashop.domain.item.Book;
import jpashop.domain.item.Item;
import jpashop.exception.NotEnoughStockException;
import jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    Member member;
    Item item;

    @BeforeEach
    void setup() {
        member = new Member();
        member.setName("member1");
        member.setAddress(new Address("seoul", "ganga", "12345"));
        em.persist(member);

        item = new Book();
        item.setName("jpa");
        item.setPrice(10000);
        item.setStockQuantity(10);
        em.persist(item);
    }

    @Test
    void 상품주문() {
        //given
        int orderCount = 3;
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        Assertions.assertThat(7).isEqualTo(item.getStockQuantity());
    }

    @Test
    void 주문취소() {
        //given
        int orderCount = 3;
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);

        //when
        orderService.cancleOrder(orderId);

        //then
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("상품주문_재고수량초과")
    void 상품주문_재고수량초과() {
        //given
        int orderCount = 100;
        //when
        //then
        assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), item.getId(), orderCount));
    }
}