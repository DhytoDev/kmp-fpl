package dev.dhyto.fpl.shared.data.model

object MockEventStatus {
    const val SUCCESS = """
        {
            "status": [
                {
                    "bonus_added": true,
                    "date": "2024-01-12",
                    "event": 21,
                    "points": "r"
                },
                {
                    "bonus_added": true,
                    "date": "2024-01-13",
                    "event": 21,
                    "points": "r"
                },
                {
                    "bonus_added": true,
                    "date": "2024-01-14",
                    "event": 21,
                    "points": "r"
                },
                {
                    "bonus_added": false,
                    "date": "2024-01-20",
                    "event": 21,
                    "points": ""
                },
                {
                    "bonus_added": false,
                    "date": "2024-01-21",
                    "event": 21,
                    "points": ""
                },
                {
                    "bonus_added": false,
                    "date": "2024-01-22",
                    "event": 21,
                    "points": ""
                }
            ],
            "leagues": "Updated"
        }
    """
}