package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Review;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.CategoryService;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping("/items/new")
    public String createForm(Model model) {

        List<Category> categories = categoryService.findCategories();

        model.addAttribute("form", new Item());
        model.addAttribute("categories", categories);
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(Item form, @RequestParam String categoryId) {
        Item item = new Item();

        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());

        log.info("카테고리" + categoryId);

        item.setCategory(categoryId);

        itemService.savedItem(item);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();

        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}")
    public String showItem(@PathVariable("itemId") Long itemId, Model model) {

        Item item = itemService.findOne(itemId);
        List<Review> review = itemService.findReview(itemId);
        log.info(String.valueOf(review.size()));

        model.addAttribute("form", item);
        model.addAttribute("reviews", review);

        return "items/itemInfo";
    }


    /**
     * 값을 수정하는 메소드
     */
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        Item item = itemService.findOne(itemId);
        List<Category> categories = categoryService.findCategories();

        model.addAttribute("form", item);
        model.addAttribute("categories", categories);
        return "items/updateItemForm";

    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") Item form, @RequestParam String categoryId) {

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity(), categoryId);

        return "redirect:/items";

    }

    @GetMapping("items/{itemId}/delete")
    public String deleteItem(@PathVariable Long itemId) {

        itemService.deleteItem(itemId);

        return "redirect:/items";

    }

    @GetMapping("items/{itemId}/review")
    public String createReviewForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);

        Review r = new Review();
        r.setId(itemId);
        model.addAttribute("form", r);
        model.addAttribute("item", item);
        return "items/reviewForm";

    }

    @PostMapping("items/{itemId}/review")
    public String createReview(@PathVariable("itemId") Long itemId, @ModelAttribute("form") Review form) {
        Review r = new Review();
        r.setItem_id(itemId);
        r.setPoint(form.getPoint());
        r.setMemo(form.getMemo());

        itemService.savedReview(r);

        return "redirect:/items/{itemId}";
    }
}
