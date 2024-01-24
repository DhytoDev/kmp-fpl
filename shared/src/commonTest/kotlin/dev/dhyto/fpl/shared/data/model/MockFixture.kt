package dev.dhyto.fpl.shared.data.model

object MockFixture {
    const val SUCCESS = """
        [
             {
                "code": 2367746,
                "event": 21,
                "finished": true,
                "finished_provisional": true,
                "id": 209,
                "kickoff_time": "2024-01-13T17:30:00Z",
                "minutes": 90,
                "provisional_start_time": false,
                "started": true,
                "team_a": 13,
                "team_a_score": 3,
                "team_h": 15,
                "team_h_score": 2,
                "stats": [
                    {
                        "identifier": "goals_scored",
                        "a": [
                            {
                                "value": 1,
                                "element": 344
                            },
                            {
                                "value": 1,
                                "element": 345
                            },
                            {
                                "value": 1,
                                "element": 349
                            }
                        ],
                        "h": [
                            {
                                "value": 1,
                                "element": 412
                            },
                            {
                                "value": 1,
                                "element": 415
                            }
                        ]
                    },
                    {
                        "identifier": "assists",
                        "a": [
                            {
                                "value": 1,
                                "element": 349
                            },
                            {
                                "value": 1,
                                "element": 365
                            },
                            {
                                "value": 1,
                                "element": 369
                            }
                        ],
                        "h": [
                            {
                                "value": 1,
                                "element": 406
                            },
                            {
                                "value": 1,
                                "element": 407
                            }
                        ]
                    },
                    {
                        "identifier": "own_goals",
                        "a": [],
                        "h": []
                    },
                    {
                        "identifier": "penalties_saved",
                        "a": [],
                        "h": []
                    },
                    {
                        "identifier": "penalties_missed",
                        "a": [],
                        "h": []
                    },
                    {
                        "identifier": "yellow_cards",
                        "a": [
                            {
                                "value": 1,
                                "element": 344
                            },
                            {
                                "value": 1,
                                "element": 365
                            }
                        ],
                        "h": [
                            {
                                "value": 1,
                                "element": 406
                            }
                        ]
                    },
                    {
                        "identifier": "red_cards",
                        "a": [],
                        "h": []
                    },
                    {
                        "identifier": "saves",
                        "a": [
                            {
                                "value": 3,
                                "element": 361
                            }
                        ],
                        "h": [
                            {
                                "value": 8,
                                "element": 409
                            }
                        ]
                    },
                    {
                        "identifier": "bonus",
                        "a": [
                            {
                                "value": 3,
                                "element": 349
                            },
                            {
                                "value": 2,
                                "element": 344
                            },
                            {
                                "value": 2,
                                "element": 345
                            },
                            {
                                "value": 2,
                                "element": 369
                            }
                        ],
                        "h": []
                    },
                    {
                        "identifier": "bps",
                        "a": [
                            {
                                "value": 35,
                                "element": 349
                            },
                            {
                                "value": 26,
                                "element": 344
                            },
                            {
                                "value": 26,
                                "element": 345
                            },
                            {
                                "value": 26,
                                "element": 369
                            },
                            {
                                "value": 22,
                                "element": 365
                            },
                            {
                                "value": 17,
                                "element": 350
                            },
                            {
                                "value": 16,
                                "element": 356
                            },
                            {
                                "value": 16,
                                "element": 616
                            },
                            {
                                "value": 15,
                                "element": 343
                            },
                            {
                                "value": 13,
                                "element": 361
                            },
                            {
                                "value": 11,
                                "element": 342
                            },
                            {
                                "value": 10,
                                "element": 353
                            },
                            {
                                "value": 2,
                                "element": 352
                            },
                            {
                                "value": 2,
                                "element": 678
                            }
                        ],
                        "h": [
                            {
                                "value": 25,
                                "element": 409
                            },
                            {
                                "value": 24,
                                "element": 412
                            },
                            {
                                "value": 23,
                                "element": 407
                            },
                            {
                                "value": 23,
                                "element": 415
                            },
                            {
                                "value": 17,
                                "element": 405
                            },
                            {
                                "value": 17,
                                "element": 406
                            },
                            {
                                "value": 15,
                                "element": 427
                            },
                            {
                                "value": 14,
                                "element": 430
                            },
                            {
                                "value": 9,
                                "element": 402
                            },
                            {
                                "value": 9,
                                "element": 635
                            },
                            {
                                "value": 5,
                                "element": 204
                            },
                            {
                                "value": 3,
                                "element": 421
                            }
                        ]
                    }
                ],
                "team_h_difficulty": 5,
                "team_a_difficulty": 4,
                "pulse_id": 93529
            }
        ]
    """
}