package me.honki12345.boardproject.dto.request;

import me.honki12345.boardproject.dto.ArticleCommentDTO;
import me.honki12345.boardproject.dto.UserAccountDTO;

public record ArticleCommentRequest(
        Long articleId,
        String content
) {
    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDTO toDTO(UserAccountDTO userAccountDTO) {
        return ArticleCommentDTO.of(
                articleId,
                userAccountDTO,
                content
        );
    }

}
