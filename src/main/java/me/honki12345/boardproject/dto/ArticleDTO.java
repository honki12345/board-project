package me.honki12345.boardproject.dto;

import me.honki12345.boardproject.domain.Article;
import me.honki12345.boardproject.domain.UserAccount;

import java.time.LocalDateTime;

public record ArticleDTO(
        Long id,
        UserAccountDTO userAccountDTO,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
        ) {

    public static ArticleDTO of(UserAccountDTO userAccountDTO, String title, String content, String hashtag) {
        return new ArticleDTO(null, userAccountDTO, title, content, hashtag, null, null, null, null);
    }

    public static ArticleDTO of(Long id, UserAccountDTO userAccountDTO, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDTO(id, userAccountDTO, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDTO from(Article entity) {
        return new ArticleDTO(
                entity.getId(),
                UserAccountDTO.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Article toEntity(UserAccount userAccount) {
        return Article.of(
                userAccount,
                title,
                content,
                hashtag
        );
    }
}
