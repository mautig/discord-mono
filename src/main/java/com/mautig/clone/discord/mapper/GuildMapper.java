package com.mautig.clone.discord.mapper;

import java.util.Collection;
import org.mapstruct.Mapper;

import com.mautig.clone.discord.entity.Guild;
import com.mautig.clone.discord.model.GuildDto;

@Mapper(componentModel = "spring")
public interface GuildMapper {
    Guild toGuild(GuildDto guildDto);

    GuildDto toGuildDto(Guild guild);

    Collection<GuildDto> toGuildDtos(Collection<Guild> guilds);

}
