package me.honki12345.boardproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/articles")
@Controller
public class ArticleController {
    @GetMapping
    public String articles(ModelMap map) {
        map.addAttribute("articles", List.of());
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(
            @PathVariable Long articleId, ModelMap map
    ) {
        // TODO: 구혛날 때 여기에 실제 데이터를 넣어주어야한다
        map.addAttribute("article", "article");
        map.addAttribute("articleComments", List.of());

        return "articles/detail";
    }
}
