package me.honki12345.boardproject.dto.request;

import me.honki12345.boardproject.dto.ArticleDTO;
import me.honki12345.boardproject.dto.UserAccountDTO;

public record ArticleRequest(
        String title,
        String content,
        String hashtag
) {
    public static ArticleRequest of(String title, String content, String hashtag) {
        return new ArticleRequest(title, content, hashtag);
    }

    public ArticleDTO toDTO(UserAccountDTO userAccountDTO) {
        return ArticleDTO.of(
                userAccountDTO,
                title,
                content,
                hashtag
        );
    }
}
