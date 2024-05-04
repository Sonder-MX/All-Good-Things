from db import sqlite


def get_user_count() -> tuple[int, int]:
    user_count = sqlite.fetchcount("user_user", "is_staff=1")
    driver_count = sqlite.fetchcount("user_user", "is_staff=0")
    return user_count, driver_count
