package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.entities.Article;
import ci.nkagou.parcauto.repositories.ArticleRepository;
import ci.nkagou.parcauto.services.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;

    @Override
    public List<Article> all() {
        return articleRepository.findAll();
    }
}
