package fr.fms.web;

import fr.fms.business.ArticleIJob;
import fr.fms.business.CategoryIJob;
import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import fr.fms.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ArticleIJob articleIJob;

    @Autowired
    CategoryIJob categoryIJob;


    @GetMapping("/cart")
    public String cart(Model model) {
        Map<Long, Article> cart = articleIJob.getCart();
        List<Article> articles = new ArrayList<>(cart.values());
        model.addAttribute("cartList", articles);
        return "cart";

    }

    // Créer le client
    @GetMapping("/customer")
    public String showCustomerForm(Model model) {
         model.addAttribute("customer", new Customer());
        return "customer";
    }

    // Recuperer vers le récap
    @PostMapping("/saveCustomer")
    public String submitCustomerInfo(RedirectAttributes redirectAttributes, Customer customer,BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Customer customer1 = new Customer();
            model.addAttribute("customer", customer1);
            return "customer";
        }
        redirectAttributes.addFlashAttribute("customer", customer);
        return "redirect:/order";
    }

    @GetMapping("/order")
    public String showOrderForm(Model model) {
        Map<Long, Article> cart = articleIJob.getCart();
        List<Article> articles = new ArrayList<>(cart.values());
        model.addAttribute("cartList", articles);
        return "order";
    }

    @GetMapping("/addToCart")
    public String addToCart(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Optional<Article> getOneArticle = articleIJob.getOne(id);
        if (getOneArticle.isPresent()) {
            Article article = getOneArticle.get();
            Map<Long, Article> articles = articleIJob.addToCart(article);
            List<Article> articlesList = new ArrayList<>(articles.values());
            redirectAttributes.addFlashAttribute("cartList", articlesList);
        }
        return "redirect:/cart";
    }


    @GetMapping("/403")
    public String error() {
        return "403";
    }

    @GetMapping({"/index", "/"})
    public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {

        Page<Article> articles = articleIJob.getAll(kw, page);

        model.addAttribute("listArticles", articles.getContent());
        model.addAttribute("page", IntStream.range(0, articles.getTotalPages()).boxed().collect(Collectors.toList()));
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        List<Category> categories = categoryIJob.getAll();
        model.addAttribute("listCategories", categories);
        return "articles";
    }

    @GetMapping("/delete")
    public String delete(RedirectAttributes redirectAttributes, @RequestParam Long id,
                         @RequestParam int page,
                         @RequestParam String keyword) {
        articleIJob.deleteOne(id);
        redirectAttributes.addFlashAttribute("successDeleted", "Article supprimé");
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/article")
    public String article(Model model, @RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            Optional<Article> article = articleIJob.getOne(id);
            article.ifPresent(value -> model.addAttribute("article", value));
        } else {
            model.addAttribute("article", new Article());
        }
        List<Category> categories = categoryIJob.getAll();
        model.addAttribute("listCategories", categories);

        return "article";
    }

    @PostMapping({"/save", "/save/{id}"})
    public String save(Model model, @Valid Article article, BindingResult bindingResult, @PathVariable(required = false) Long id) {
        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryIJob.getAll();
            model.addAttribute("listCategories", categories);
            return "article";
        }
        if (id != null) {
            articleIJob.getOne(id).ifPresent(articleUpdate -> {
                articleUpdate.setBrand(article.getBrand());
                articleUpdate.setDescription(article.getDescription());
                articleUpdate.setPrice(article.getPrice());
                articleUpdate.setCategory(article.getCategory());
                articleIJob.createOne(articleUpdate);
            });
        } else articleIJob.createOne(article);
        return "redirect:/index";
    }


    @PostMapping("/update")
    public String update(Model model, @Valid Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("listCategories", categories);
            return "article";
        }
        System.out.println(article + "     test");
        Optional<Article> isArticle = articleRepository.findById(article.getId());

        if (isArticle.isPresent()) {
            Article updatedArticle = isArticle.get();
            updatedArticle.setBrand(article.getBrand());
            updatedArticle.setDescription(article.getDescription());
            updatedArticle.setPrice(article.getPrice());
            updatedArticle.setCategory(article.getCategory());
            articleRepository.save(updatedArticle);
        } else {
            return "redirect:/index";
        }
        return "redirect:/index";
    }


    @GetMapping("/articlesByCategory")
    public String getArticlesByCategory(@RequestParam Long categoryId, Model model) {
        List<Article> articles = articleIJob.findByAttribute(categoryId);
        List<Category> categories = categoryIJob.getAll();
        model.addAttribute("listCategories", categories);
        model.addAttribute("listArticles", articles);
        model.addAttribute("categoryId", categoryId);
        return "articles";
    }

}
