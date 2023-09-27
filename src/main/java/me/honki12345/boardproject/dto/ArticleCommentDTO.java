package me.honki12345.boardproject.dto;

import me.honki12345.boardproject.domain.Article;
import me.honki12345.boardproject.domain.ArticleComment;
import me.honki12345.boardproject.domain.UserAccount;

import java.time.LocalDateTime;

public record ArticleCommentDTO(
        Long id,
        Long articleId,
        UserAccountDTO userAccountDTO,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static ArticleCommentDTO of(Long articleId, UserAccountDTO userAccountDTO, String content) {
        return new ArticleCommentDTO(null, articleId, userAccountDTO, content, null, null, null, null);
    }

    public static ArticleCommentDTO of(Long id, Long articleId, UserAccountDTO userAccountDTO, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleCommentDTO(id, articleId, userAccountDTO, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public ArticleComment toEntity(Article article, UserAccount userAccount) {
        return ArticleComment.of(
                article,
                userAccount,
                content
        );
    }

    public static ArticleCommentDTO from(ArticleComment entity) {
        return new ArticleCommentDTO(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDTO.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public ArticleComment toEntity(Article entity) {
        return ArticleComment.of(
                entity,
                userAccountDTO.toEntity(),
                content
        );
    }
}
