package me.honki12345.boardproject.controller;

import lombok.RequiredArgsConstructor;
import me.honki12345.boardproject.dto.UserAccountDTO;
import me.honki12345.boardproject.dto.request.ArticleCommentRequest;
import me.honki12345.boardproject.service.ArticleCommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest request) {
        // TODO: 인증 정보를 넣어줘야 한다.
        articleCommentService.saveArticleComment(request.toDTO(UserAccountDTO.of(
                "uno", "pw", "uno@mail.com", null, null
        )));
        return "redirect:/articles/" + request.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {
        articleCommentService.deleteArticleComment(commentId);
        return "redirect:/articles/" + articleId;
    }
}
