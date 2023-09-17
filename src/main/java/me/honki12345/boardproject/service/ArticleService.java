package me.honki12345.boardproject.service;

import lombok.RequiredArgsConstructor;
import me.honki12345.boardproject.domain.type.SearchType;
import me.honki12345.boardproject.dto.ArticleDTO;
import me.honki12345.boardproject.dto.ArticleWithCommentsDTO;
import me.honki12345.boardproject.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDTO> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDTO getArticle(long articleId) {
        return null;
    }

    public void saveArticle(ArticleDTO dto) {

    }

    public void updateArticle(ArticleDTO dto) {
    }

    public void deleteArticle(long articleId) {

    }
}
