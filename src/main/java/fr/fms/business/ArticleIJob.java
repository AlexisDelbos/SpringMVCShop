package fr.fms.business;

import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ArticleIJob implements IJob<Article> {

    Map<Long, Article> cart = new HashMap<Long, Article>();


    @Autowired
    ArticleRepository articleRepository;

    // Panier

    public Map<Long, Article> getCart() {
        return cart;
    }

    public Map<Long, Article> addToCart(Article article) {
        cart.put(article.getId(), article);
        return cart;
    }

    // Article

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public Page<Article> getAll(String kw, int page) {
        Page<Article> articles = articleRepository.findByDescriptionContains(kw, PageRequest.of(page, 5));
        return articles;
    }


    @Override
    public Optional<Article> getOne(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public List<Article> findByAttribute(Long categoryId) {
        return articleRepository.findByCategoryId(categoryId);
    }

    @Override
    public void deleteOne(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public void createOne(Article article) {
        articleRepository.save(article);
    }


}
