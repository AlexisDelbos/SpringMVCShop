/**
 * Application console de simulation de l'administration de gestion d'un stock d'articles avec un menu permettant d'ajouter, supprimer, mettre à jour un article ou les afficher par pagination ou pas
 * De même pour les catégories d'articles : ajouter, supprimer, maj ou afficher les catégories ou tous les articles d'une catégorie
 * 
 * @author El-Babili - 2023
 */

package fr.fms;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

@SpringBootApplication
public class SpringShopJpaApplication implements CommandLineRunner {
	@Autowired
	ArticleRepository articleRepository;
	@Autowired
	CategoryRepository categoryRepository;


	public static void main(String[] args) {
		SpringApplication.run(SpringShopJpaApplication.class, args);
	}


	@Override
	public void run(String... args) {
		dataArticles();
//		List<Article> articles = articleRepository.findAll();
//		articles.forEach(article -> System.out.println(article));
	}

	private void dataArticles() {
		Category smartphone = categoryRepository.save(new Category(0, "Téléphone mobile", "Smartphones", null));
		Category pc = categoryRepository.save(new Category(0, "Ordinateur", "PC & Laptops", null));
		Category tablet = categoryRepository.save(new Category(0, "Tablettes", "Tablets", null));
		Category printer = categoryRepository.save(new Category(0, "Imprimantes", "Impressions", null));
		Category camera = categoryRepository.save(new Category(0, "Caméra", "Cameras", null));
		Category tv = categoryRepository.save(new Category(0, "Télévision", "TV & Écrans", null));

		articleRepository.save(new Article(0, "Sony", "Xperia Z", 250, smartphone));
		articleRepository.save(new Article(0, "Huawei", "P40", 280, smartphone));
		articleRepository.save(new Article(0, "Nokia", "8.3", 450, smartphone));
		articleRepository.save(new Article(0, "Oppo", "Find X3", 320, smartphone));
		articleRepository.save(new Article(0, "Realme", "GT 5G", 180, smartphone));
		articleRepository.save(new Article(0, "LG", "V60 ThinQ", 400, smartphone));
		articleRepository.save(new Article(0, "Xiaomi", "Redmi Note 9", 130, smartphone));

		articleRepository.save(new Article(0, "HP", "Pavilion 15", 600, pc));
		articleRepository.save(new Article(0, "Lenovo", "ThinkPad X1", 900, pc));
		articleRepository.save(new Article(0, "Acer", "Aspire 7", 650, pc));
		articleRepository.save(new Article(0, "Microsoft", "Surface Laptop 3", 1100, pc));
		articleRepository.save(new Article(0, "Razer", "Blade Stealth", 1300, pc));

		articleRepository.save(new Article(0, "Samsung", "Galaxy Tab S6", 350, tablet));
		articleRepository.save(new Article(0, "Huawei", "MediaPad M5", 450, tablet));

		articleRepository.save(new Article(0, "Brother", "HL-L2350DW", 80, printer));
		articleRepository.save(new Article(0, "Xerox", "WorkCentre 6515", 120, printer));
		articleRepository.save(new Article(0, "Lexmark", "B2236DW", 90, printer));
		articleRepository.save(new Article(0, "Samsung", "SL-M2026W", 70, printer));

		articleRepository.save(new Article(0, "Fujifilm", "X-T4", 1200, camera));
		articleRepository.save(new Article(0, "Sony", "Alpha 7C", 1500, camera));

		articleRepository.save(new Article(0, "Samsung", "Q80T", 1400, tv));
		articleRepository.save(new Article(0, "LG", "OLED55CX", 700, tv));
	}
}