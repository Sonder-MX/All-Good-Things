from db import sqlite


def get_order_count() -> int:
    return sqlite.fetchcount("orders")


def latest_order() -> tuple:
    return sqlite.fetchone("orders", "order by updated desc limit 1")
