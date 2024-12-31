package fr.fms.web;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {
        Page<Article> articles = articleRepository.findByDescriptionContains(kw, PageRequest.of(page, 5));
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("listCategories", categories);
        model.addAttribute("listArticles", articles.getContent());
        model.addAttribute("page", IntStream.range(0, articles.getTotalPages()).boxed().collect(Collectors.toList()));
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        return "articles";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id,
                         @RequestParam int page,
                         @RequestParam String keyword) {
        articleRepository.deleteById(id);
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/article")
    public String article(Model model, @RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            Optional<Article> article = articleRepository.findById(id);
            model.addAttribute("article", article.get());
        } else {
            model.addAttribute("article", new Article());
        }
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("listCategories", categories);
        return "article";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("listCategories", categories);
            return "article";
        }
        articleRepository.save(article);
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
        List<Article> articles = articleRepository.findByCategoryId(categoryId);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("listCategories", categories);
        model.addAttribute("listArticles", articles);
        model.addAttribute("categoryId", categoryId);
        return "articles";
    }

}
