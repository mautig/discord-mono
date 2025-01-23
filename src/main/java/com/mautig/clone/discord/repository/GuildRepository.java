package com.mautig.clone.discord.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mautig.clone.discord.entity.Guild;

public interface GuildRepository extends JpaRepository<Guild, Long> {
    Optional<Guild> findById(Long id);
}
