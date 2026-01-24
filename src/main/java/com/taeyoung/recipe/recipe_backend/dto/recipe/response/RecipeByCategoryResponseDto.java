package com.taeyoung.recipe.recipe_backend.dto.recipe.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeByCategoryResponseDto {
    private Long id;
    private String title;
    private String subTitle;
    private String imgUrl;

    public RecipeByCategoryResponseDto(Long id, String title, String subTitle, String imgUrl) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.imgUrl = imgUrl;
    }
}
