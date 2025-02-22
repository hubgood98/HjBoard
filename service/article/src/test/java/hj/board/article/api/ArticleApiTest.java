package hj.board.article.api;


import hj.board.article.service.request.ArticleCreateRequest;
import hj.board.article.service.response.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArticleApiTest {
    // 테스트 대상 서버 주소
    private final RestClient restClient = RestClient.create("http://localhost:9000");

    /**
     * 1) 게시글 생성
     * 2) 생성된 게시글 아이디로 조회
     * 3) 수정
     * 4) 수정된 게시글 조회
     * 5) 삭제
     * 6) 삭제 후 조회 시 에러 확인
     */

    @Test
    void createTest() {
        ArticleCreateRequest request = new ArticleCreateRequest("hi", "my content 희준 킴", 1L, 1L);

        ArticleResponse response = restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve().body(ArticleResponse.class);

        // 3) 결과 확인
        assertNotNull(response, "응답이 null이면 안 됩니다.");
        assertNotNull(response.getArticleId(), "생성된 게시글 ID가 null이면 안 됩니다.");

        System.out.println("[CREATE] response = " + response);
    }
}
