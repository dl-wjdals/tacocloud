package tacos.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.TacoOrder;
import tacos.User;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping
    public String redirectToOrderForm() {
        return "redirect:/orders/current";
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user, Model model) {
        if (!model.containsAttribute("tacoOrder")) {
            TacoOrder tacoOrder = new TacoOrder();
            tacoOrder.setDeliveryName(user.getFullname());
            tacoOrder.setDeliveryStreet(user.getStreet());
            tacoOrder.setDeliveryCity(user.getCity());
            tacoOrder.setDeliveryState(user.getState());
            tacoOrder.setDeliveryZip(user.getZip());
            model.addAttribute("tacoOrder", tacoOrder);
        }

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               @AuthenticationPrincipal User user,
                               SessionStatus sessionStatus) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);
        orderRepo.save(order);
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
