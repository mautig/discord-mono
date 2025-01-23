package com.mautig.clone.discord.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mautig.clone.discord.entity.Guild;
import com.mautig.clone.discord.model.GuildDto;
import com.mautig.clone.discord.service.GuildService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
public class GuildControllerTest {

  @Autowired
  private GuildController guildController;

  @MockitoBean
  private GuildService guildService;

  private MockMvc mockMvc;

  private List<Guild> guilds;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(guildController).build();
    guilds = List.of(
        Guild.builder().id(1L).name("Guild1").build(),
        Guild.builder().id(2L).name("Guild2").build());
  }

  @Test
  void getGuilds_shouldReturnOk() throws Exception {
    given(guildService.findAll()).willReturn(guilds);
    mockMvc.perform(get("/api/guild"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("Guild1"))
        .andExpect(jsonPath("$[1].id").value(2L))
        .andExpect(jsonPath("$[1].name").value("Guild2"));
  }

  @Test
  void getGuilds_shouldReturnEmptyList() throws Exception {
    given(guildService.findAll()).willReturn(List.of());
    mockMvc.perform(get("/api/guild"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  void saveGuild_shouldReturnNoContent() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    GuildDto guildDto = GuildDto.builder().name("Guild3").build();
    mockMvc.perform(post("/api/guild")
        .content(objectMapper.writeValueAsString(guildDto))
        .contentType("application/json"))
        .andExpect(status().isNoContent());
  }

  @Test
  void updateGuild_shouldReturnOk() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    GuildDto guildDto = GuildDto.builder().name("Guild3").build();
    given(guildService.findById(1L)).willReturn(java.util.Optional.of(guilds.get(0)));
    mockMvc.perform(put("/api/guild/1")
        .content(objectMapper.writeValueAsString(guildDto))
        .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Guild3"));
  }

  @Test
  void updateGuild_shouldReturnNotFound() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    GuildDto guildDto = GuildDto.builder().name("Guild3").build();
    given(guildService.findById(1L)).willReturn(java.util.Optional.empty());
    mockMvc.perform(put("/api/guild/1")
        .content(objectMapper.writeValueAsString(guildDto))
        .contentType("application/json"))
        .andExpect(status().isNotFound());
  }

  @Test
  void getGuild_shouldReturnOk() throws Exception {
    given(guildService.findById(1L)).willReturn(java.util.Optional.of(guilds.get(0)));
    mockMvc.perform(get("/api/guild/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Guild1"));
  }

  @Test
  void getGuild_shouldReturnNotFound() throws Exception {
    given(guildService.findById(1L)).willReturn(java.util.Optional.empty());
    mockMvc.perform(get("/api/guild/1"))
        .andExpect(status().isNotFound());
  }
}