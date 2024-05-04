from db import sqlite


def get_total_route() -> int:
    return sqlite.fetchcount("route_info")


def get_active_route() -> int:
    return sqlite.fetchcount("route_station")
