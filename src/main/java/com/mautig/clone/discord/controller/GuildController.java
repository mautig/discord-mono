package com.mautig.clone.discord.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mautig.clone.discord.mapper.GuildMapper;
import com.mautig.clone.discord.model.GuildDto;
import com.mautig.clone.discord.service.GuildService;

@RestController
@RequestMapping("/api/guild")
public class GuildController {

    private final GuildService guildService;
    private final GuildMapper guildMapper;

    public GuildController(GuildService guildService, GuildMapper guildMapper) {
        this.guildService = guildService;
        this.guildMapper = guildMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAlls() {
        return ResponseEntity.ok(guildMapper.toGuildDtos(guildService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        var guild = guildService.findById(id);
        if (guild.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guildMapper.toGuildDto(guild.get()));
    }

    @PostMapping
    public ResponseEntity<?> save(GuildDto guildDto) {
        var guild = guildMapper.toGuild(guildDto);
        guildService.save(guild);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuildDto> update(@PathVariable Long id, @RequestBody GuildDto guildDto) {
        var guild = guildService.findById(id);
        if (guild.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        guild.get().setName(guildDto.getName());
        guildService.save(guild.get());
        return ResponseEntity.ok(guildMapper.toGuildDto(guild.get()));
    }
}
