object MockGeneralInfo {
    const val SUCCESS = """
        {
            "events": [
                 {
                    "id": 1,
                    "name": "Gameweek 1",
                    "deadline_time": "2023-08-11T17:30:00Z",
                    "average_entry_score": 64,
                    "finished": true,
                    "data_checked": true,
                    "highest_scoring_entry": 3383750,
                    "deadline_time_epoch": 1691775000,
                    "deadline_time_game_offset": 0,
                    "highest_score": 127,
                    "is_previous": false,
                    "is_current": false,
                    "is_next": false,
                    "cup_leagues_created": false,
                    "h2h_ko_matches_created": false,
                    "chip_plays": [
                        {
                            "chip_name": "bboost",
                            "num_played": 163222
                        },
                        {
                            "chip_name": "3xc",
                            "num_played": 287198
                        }
                    ],
                    "most_selected": 355,
                    "most_transferred_in": 1,
                    "top_element": 395,
                    "top_element_info": {
                        "id": 395,
                        "points": 14
                    },
                    "transfers_made": 0,
                    "most_captained": 355,
                    "most_vice_captained": 19
                }
            ],
            "game_settings": {
                 "league_join_private_max": 30,
                "league_join_public_max": 5,
                "league_max_size_public_classic": 20,
                "league_max_size_public_h2h": 16,
                "league_max_size_private_h2h": 16,
                "league_max_ko_rounds_private_h2h": 3,
                "league_prefix_public": "League",
                "league_points_h2h_win": 3,
                "league_points_h2h_lose": 0,
                "league_points_h2h_draw": 1,
                "league_ko_first_instead_of_random": false,
                "cup_start_event_id": null,
                "cup_stop_event_id": null,
                "cup_qualifying_method": null,
                "cup_type": null,
                "featured_entries": [],
                "percentile_ranks": [
                    1,
                    5,
                    10,
                    15,
                    20,
                    25,
                    30,
                    35,
                    40,
                    45,
                    50,
                    55,
                    60,
                    65,
                    70,
                    75,
                    80,
                    95,
                    90,
                    95
                ],
                "squad_squadplay": 11,
                "squad_squadsize": 15,
                "squad_team_limit": 3,
                "squad_total_spend": 1000,
                "ui_currency_multiplier": 10,
                "ui_use_special_shirts": false,
                "ui_special_shirt_exclusions": [],
                "stats_form_days": 30,
                "sys_vice_captain_enabled": true,
                "transfers_cap": 20,
                "transfers_sell_on_fee": 0.5,
                "league_h2h_tiebreak_stats": [
                    "+goals_scored",
                    "-goals_conceded"
                ],
                "timezone": "UTC"
            },
            "total_players": 10000000,
            "teams": [
                 {
                    "code": 43,
                    "draw": 0,
                    "form": null,
                    "id": 13,
                    "loss": 0,
                    "name": "Man City",
                    "played": 0,
                    "points": 0,
                    "position": 0,
                    "short_name": "MCI",
                    "strength": 5,
                    "team_division": null,
                    "unavailable": false,
                    "win": 0,
                    "strength_overall_home": 1350,
                    "strength_overall_away": 1355,
                    "strength_attack_home": 1320,
                    "strength_attack_away": 1340,
                    "strength_defence_home": 1320,
                    "strength_defence_away": 1370,
                    "pulse_id": 11
                }
            ],
            "elements": [
                 {
                    "chance_of_playing_next_round": 50,
                    "chance_of_playing_this_round": 0,
                    "code": 223094,
                    "cost_change_event": 0,
                    "cost_change_event_fall": 0,
                    "cost_change_start": -1,
                    "cost_change_start_fall": 1,
                    "dreamteam_count": 4,
                    "element_type": 4,
                    "ep_next": "0.2",
                    "ep_this": "0.0",
                    "event_points": 0,
                    "first_name": "Erling",
                    "form": "0.0",
                    "id": 355,
                    "in_dreamteam": true,
                    "news": "Foot injury - 50% chance of playing",
                    "news_added": "2023-12-10T18:00:10.148960Z",
                    "now_cost": 139,
                    "photo": "223094.jpg",
                    "points_per_game": "7.5",
                    "second_name": "Haaland",
                    "selected_by_percent": "53.9",
                    "special": false,
                    "squad_number": null,
                    "status": "d",
                    "team": 13,
                    "team_code": 43,
                    "total_points": 112,
                    "transfers_in": 1708258,
                    "transfers_in_event": 318130,
                    "transfers_out": 5195098,
                    "transfers_out_event": 252844,
                    "value_form": "0.0",
                    "value_season": "8.1",
                    "web_name": "Haaland",
                    "minutes": 1294,
                    "goals_scored": 14,
                    "assists": 5,
                    "clean_sheets": 4,
                    "goals_conceded": 16,
                    "own_goals": 0,
                    "penalties_saved": 0,
                    "penalties_missed": 1,
                    "yellow_cards": 1,
                    "red_cards": 0,
                    "saves": 0,
                    "bonus": 15,
                    "bps": 417,
                    "influence": "588.6",
                    "creativity": "197.5",
                    "threat": "810.0",
                    "ict_index": "159.8",
                    "starts": 15,
                    "expected_goals": "14.95",
                    "expected_assists": "1.17",
                    "expected_goal_involvements": "16.12",
                    "expected_goals_conceded": "13.61",
                    "influence_rank": 6,
                    "influence_rank_type": 1,
                    "creativity_rank": 105,
                    "creativity_rank_type": 12,
                    "threat_rank": 2,
                    "threat_rank_type": 1,
                    "ict_index_rank": 7,
                    "ict_index_rank_type": 2,
                    "corners_and_indirect_freekicks_order": null,
                    "corners_and_indirect_freekicks_text": "",
                    "direct_freekicks_order": 3,
                    "direct_freekicks_text": "",
                    "penalties_order": 1,
                    "penalties_text": "",
                    "expected_goals_per_90": 1.04,
                    "saves_per_90": 0.0,
                    "expected_assists_per_90": 0.08,
                    "expected_goal_involvements_per_90": 1.12,
                    "expected_goals_conceded_per_90": 0.95,
                    "goals_conceded_per_90": 1.11,
                    "now_cost_rank": 1,
                    "now_cost_rank_type": 1,
                    "form_rank": 772,
                    "form_rank_type": 99,
                    "points_per_game_rank": 2,
                    "points_per_game_rank_type": 1,
                    "selected_rank": 3,
                    "selected_rank_type": 2,
                    "starts_per_90": 1.04,
                    "clean_sheets_per_90": 0.28
                }
            ],
            "element_types": [
                {
                    "id": 2,
                    "plural_name": "Defenders",
                    "plural_name_short": "DEF",
                    "singular_name": "Defender",
                    "singular_name_short": "DEF",
                    "squad_select": 5,
                    "squad_min_play": 3,
                    "squad_max_play": 5,
                    "ui_shirt_specific": false,
                    "sub_positions_locked": [],
                    "element_count": 250
                },
                {
                    "id": 3,
                    "plural_name": "Midfielders",
                    "plural_name_short": "MID",
                    "singular_name": "Midfielder",
                    "singular_name_short": "MID",
                    "squad_select": 5,
                    "squad_min_play": 2,
                    "squad_max_play": 5,
                    "ui_shirt_specific": false,
                    "sub_positions_locked": [],
                    "element_count": 338
                },
                {
                    "id": 4,
                    "plural_name": "Forwards",
                    "plural_name_short": "FWD",
                    "singular_name": "Forward",
                    "singular_name_short": "FWD",
                    "squad_select": 3,
                    "squad_min_play": 1,
                    "squad_max_play": 3,
                    "ui_shirt_specific": false,
                    "sub_positions_locked": [],
                    "element_count": 99
                }
            ],
            "element_stats": [
                {
                    "label": "Minutes played",
                    "name": "minutes"
                }
            ],
            "phases": [
                 {
                    "id": 1,
                    "name": "Overall",
                    "start_event": 1,
                    "stop_event": 38
                },
                {
                    "id": 2,
                    "name": "August",
                    "start_event": 1,
                    "stop_event": 3
                }
            ]
        }
    """
}