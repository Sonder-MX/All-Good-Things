from db import sqlite


def get_car_count():
    return sqlite.fetchcount("car_info")
