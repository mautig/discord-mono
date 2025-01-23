package com.mautig.clone.discord.service;

import java.util.List;
import java.util.Optional;

import com.mautig.clone.discord.entity.Guild;

public interface GuildService {
    Guild save(Guild guild);

    List<Guild> findAll();

    Optional<Guild> findById(Long id);
}
