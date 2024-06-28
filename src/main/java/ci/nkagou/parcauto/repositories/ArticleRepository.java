package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
