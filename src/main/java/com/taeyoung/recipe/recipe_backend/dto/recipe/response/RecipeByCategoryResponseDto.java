package com.taeyoung.recipe.recipe_backend.dto.recipe.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeByCategoryResponseDto {
    private String title;
    private String subTitle;
    private String imgUrl;

    public RecipeByCategoryResponseDto(String title, String subTitle, String imgUrl) {
        this.title = title;
        this.subTitle = subTitle;
        this.imgUrl = imgUrl;
    }
}
