package hj.board.article.service;

import hj.board.article.entity.Article;
import hj.board.article.repository.ArticleRepository;
import hj.board.article.service.request.ArticleCreateRequest;
import hj.board.article.service.request.ArticleUpdateRequest;
import hj.board.article.service.response.ArticleResponse;
import kuke.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request){
        Article article = articleRepository.save(
                Article.create(
                        snowflake.nextId(),
                        request.getTitle(),
                        request.getContent(),
                        request.getBoardId(),
                        request.getWriterId())
        );
        Long generatedId = snowflake.nextId();
        System.out.println("Generated ID: " + generatedId);


        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request){
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.getTitle(), request.getContent());

        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse read(Long articleId){
        return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
    }

    @Transactional
    public void delete(Long articleId){
        articleRepository.deleteById(articleId);
    }
}
