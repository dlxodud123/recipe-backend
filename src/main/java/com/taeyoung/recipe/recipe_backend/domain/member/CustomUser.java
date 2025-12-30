package com.taeyoung.recipe.recipe_backend.domain.member;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {

    public Long id;
    private ProviderType provider;

    public CustomUser(Long id, String username, String password, ProviderType provider, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.provider = provider;
    }
}
