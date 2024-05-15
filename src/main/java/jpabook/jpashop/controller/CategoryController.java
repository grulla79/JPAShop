package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.service.CategoryService;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping("/category/new")
    public String createCategoryForm(Model model) {
        model.addAttribute("form", new Category());
        return "categories/createCategoryForm";
    }

    @PostMapping("/category/new")
    public String createCategory(Category form) {
        Category category = new Category();

        category.setName(form.getName());

        categoryService.savedCategory(category);
        return "redirect:/";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        List<Category> categories = categoryService.findCategories();
        log.info(String.valueOf(categories.size()));
        model.addAttribute("categories", categories);
        return "categories/categoryList";
    }

    @GetMapping("category/{categoryId}/edit")
    public String updateCategoryForm(@PathVariable("categoryId") Long categoryId, Model model) {

        Category c = categoryService.findOne(categoryId);
        model.addAttribute("form", c);

        return "categories/updateCategoryForm";

    }

    @PostMapping("category/{categoryId}/edit")
    public String updateCategory(@PathVariable Long categoryId, @ModelAttribute("form") Category form) {
        categoryService.updateCategory(categoryId, form.getName());
        return "redirect:/categories";
    }

    @GetMapping("/category/{categoryId}/delete")
    public String deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return "redirect:/categories";
    }
}
