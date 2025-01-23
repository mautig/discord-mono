package com.mautig.clone.discord.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mautig.clone.discord.entity.Guild;
import com.mautig.clone.discord.repository.GuildRepository;
import com.mautig.clone.discord.service.GuildService;

@Service
public class GuildServiceImpl implements GuildService {

    private final GuildRepository guildRepository;

    public GuildServiceImpl(GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
    }

    @Override
    public Guild save(Guild guild) {
        return guildRepository.save(guild);
    }

    @Override
    public List<Guild> findAll() {
        return guildRepository.findAll();
    }

    @Override
    public Optional<Guild> findById(Long id) {
        return guildRepository.findById(id);
    }

}
