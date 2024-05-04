import logging
from enum import Enum


class RsKey(Enum):
    # Python数据处理接口
    PRE_USER = "pre_user_data"  # 预处理用户数据
    PRE_ROUTE = "pre_route_data"  # 预处理路线数据
    PRE_ORDER = "pre_order_data"  # 预处理订单数据

    ### SpringBoot接口
    BASE_PANEL = "base_panel"  # 基础面板数据
    BIG_SCREEN = "big_screen_data"  # 大屏数据
    # 大屏 chart key
    HOUR_ORDER = "ctHourOrder"  # 24小时订单量
    RES_PROPU = "ctResPropu"  # 资源使用
    SEAT_USAGE = "ctSeatUsage"  # 座位使用
    POPU_STATION = "ctPopuStation"
    POPU_ROUTE = "ctPopuRoute"
    CAR_STATUS = "ctCarStatus"
    WEEK_ORDER = "ctWeekOrder"  # 周订单量
    LIQUID = "ctLiquid"  # [0]
    PANEL_DATA = "ctPanelData"  # 面板数据
    ###


LOGGING = {
    "lever": logging.DEBUG,
    "is_save": False,
    "save_path": "./logs/logs.log",
    "file_encoding": "utf-8",
}


DATABASE = {
    "sqlite": {
        "path": "./db.sqlite3",
    },
    "redis": {
        "host": "127.0.0.1",
        "port": 6379,
        "password": "123456",
        "db": 0,
    },
}

# utils
## qkrequest
UQ_REQEST_SEND = {
    "server": "http://127.0.0.1:8080/py_api",
    "header": {
        "User-Agent": "data-analysis-core-api",
        "token": "core-1.0.0",
        "Content-Type": "application/json",
        "Accept": "application/json",
    },
}

UQ_REQEST_DATA = {
    "server": "",
    "account": "",
    "password": "",
    "token": "",
    "header": {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36",
    },
}

with open("./.env", "r", encoding="utf-8") as f:
    lines = f.readlines()
    for line in lines:
        if line.startswith("UQ_REQEST_DATA"):
            kv = line.split("=")
            UQ_REQEST_DATA[kv[0].split(".")[-1]] = kv[1].strip()
