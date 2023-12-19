package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.PaymentRepository;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final PaymentRepository paymentRepository;

    @GetMapping("/order")
    public String createForm(Model model){

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";

    }

    @GetMapping("/order/{itemId}")
    public String createOrderForm(@PathVariable("itemId") Long itemId, Model model){

        List<Member> members = memberService.findMembers();
        Item items = itemService.findOne(itemId);

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";

    }

    @PostMapping(value = "/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch,
                            Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        model.addAttribute("payment", paymentRepository.findAll());

        return "order/orderList";
    }

    @GetMapping("/myPage")
    public String myPageList(@ModelAttribute("orderSearch")OrderSearch orderSearch,
                            Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        model.addAttribute("payment", paymentRepository.findAll());

        return "myPage/myPageList";
    }

}
