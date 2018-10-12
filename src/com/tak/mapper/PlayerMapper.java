package com.tak.mapper;

import com.tak.pojo.Player;

import java.util.List;

public interface PlayerMapper {
    List<Player> listPlayers();
    int addPlayer(Player p);
    int removePlayer(int player_id);
}
