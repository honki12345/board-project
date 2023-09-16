package me.honki12345.boardproject.service;

import lombok.RequiredArgsConstructor;
import me.honki12345.boardproject.dto.ArticleCommentDTO;
import me.honki12345.boardproject.repository.ArticleCommentRepository;
import me.honki12345.boardproject.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {
    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDTO> searchArticleComment(Long articleId) {
        return List.of();
    }

    public void saveArticleComment(ArticleCommentDTO dto) {
    }
}
