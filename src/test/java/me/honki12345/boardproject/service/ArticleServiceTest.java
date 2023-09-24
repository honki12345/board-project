package me.honki12345.boardproject.service;

import me.honki12345.boardproject.domain.Article;
import me.honki12345.boardproject.domain.UserAccount;
import me.honki12345.boardproject.domain.type.SearchType;
import me.honki12345.boardproject.dto.ArticleDTO;
import me.honki12345.boardproject.dto.ArticleWithCommentsDTO;
import me.honki12345.boardproject.dto.UserAccountDTO;
import me.honki12345.boardproject.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks private ArticleService sut;
    @Mock private ArticleRepository articleRepository;

    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다")
    @Test
    void givenNoSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {
        // given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        // when
        Page<ArticleDTO> articles = sut.searchArticles(null, null, pageable);   // 제목, 본문, ID, 닉네임, 해시태그

        // then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);

    }

    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {
        // given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());

        // when
        Page<ArticleDTO> articles = sut.searchArticles(searchType, searchKeyword, pageable);

        // then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining(searchKeyword, pageable);

    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticles() {
        // given
        long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));


        // when
        ArticleWithCommentsDTO dto = sut.getArticle(articleId);// 제목, 본문, ID, 닉네임, 해시태그

        // then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("없는 게시글을 조회하면, 예외를 던진다.")
    @Test
    void givenNonexistentArticleId_whenSearchingArticle_thenThrowsException() {
        // given
        long articleId = 0L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());

        // when
        Throwable t = catchThrowable(() -> sut.getArticle(articleId));

        // then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("게시글이 없습니다 - articleId: " + articleId);
        then(articleRepository).should().findById(articleId);

    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        // given
        ArticleDTO dto = createArticleDTO();
        given(articleRepository.save(any(Article.class))).willReturn(createArticle());

        // when
        sut.saveArticle(dto);

        // then
        then(articleRepository).should().save(any(Article.class));

    }

    @DisplayName("게시글의 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenModifiedArticleInfo_whenUpdatingArticle_thenUpdatesArticle() {
        // given
        Article article = createArticle();
        ArticleDTO dto = createArticleDTO("새 타이틀", "새 내용", "#springboot");
        given(articleRepository.getReferenceById(dto.id())).willReturn(article);

        // when
        sut.updateArticle(dto);

        // then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", dto.title())
                .hasFieldOrPropertyWithValue("content", dto.content())
                .hasFieldOrPropertyWithValue("hashtag", dto.hashtag());
        then(articleRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("없는 게시글의 수정 정보를 입력하면, 경고 로그를 찍고 아무 것도 하지 않는다.")
    @Test
    void givenNonexistentArticleInfo_whenUpdatingArticle_thenLogsWarningAndDoesNothing() {
        // given
        ArticleDTO dto = createArticleDTO("새 타이틀", "새 내용", "#springboot");
        given(articleRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // when
        sut.updateArticle(dto);

        // then
        then(articleRepository).should().getReferenceById(dto.id());

    }

    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
        // given
        long articleId = 1L;
        willDoNothing().given(articleRepository).deleteById(articleId);

        // when
        sut.deleteArticle(articleId);

        // then
        then(articleRepository).should().deleteById(articleId);
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "honki12345",
                "password",
                "honki12345@mail.com",
                "honki12345",
                null
        );
    }

    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }

    private ArticleDTO createArticleDTO() {
        return createArticleDTO("title", "content", "#java");
    }

    private ArticleDTO createArticleDTO(String title, String content, String hashtag) {
        return ArticleDTO.of(
                1L,
                createUserAccountDTO(),
                title,
                content,
                hashtag,
                LocalDateTime.now(),
                "honki12345",
                LocalDateTime.now(),
                "honki12345"
        );
    }

    private UserAccountDTO createUserAccountDTO() {
        return UserAccountDTO.of(
                1L,
                "honki12345",
                "password",
                "honki12345@mail.com",
                "honki12345",
                "memo",
                LocalDateTime.now(),
                "honki12345",
                LocalDateTime.now(),
                "honki12345"
        );
    }
}