import json

from analysis import order_analysis as oa
from analysis import popular_analysis as pa
from analysis import router_analysis as ra
from db import rds
from panel_info import order_info, route_info, user_info
from settings import RsKey
from utils import logger, send_data


def _panel_data():
    latest_order = order_info.latest_order()
    updated = latest_order[7].split(".")[0]
    return {
        "order": order_info.get_order_count(),
        "route": route_info.get_total_route(),
        "user": user_info.get_user_count()[0],
        "updateTime": updated,
    }


def _popu_station():
    top10 = pa.popular_station()[0:10]
    xdata = [item[0] for item in top10]
    series = [item[1] for item in top10]
    return {"xdata": xdata, "ydata": series}


def _popu_route():
    top4 = pa.popular_route()[0:4]
    data = [f"{item[0]}）线: {item[1]}" if "（" in item[0] else f"{item[0]}线: {item[1]}" for item in top4]
    return data


def _res_propu():
    used, unuse = ra.seat_usage_rate()
    return [{"value": used, "name": "已使用"}, {"value": unuse, "name": "未使用"}]


def _car_status():
    data = ra.route_with_car()
    return [[str(key) + "号" for key in data.keys()], [value for value in data.values()]]


def _enc_data():
    data = {}
    data[RsKey.PANEL_DATA.value] = _panel_data()
    data[RsKey.HOUR_ORDER.value] = oa.hourly_orders()
    data[RsKey.WEEK_ORDER.value] = oa.weekly_orders()
    data[RsKey.POPU_STATION.value] = _popu_station()
    data[RsKey.POPU_ROUTE.value] = _popu_route()
    data[RsKey.RES_PROPU.value] = _res_propu()
    data[RsKey.SEAT_USAGE.value] = ra.seat_usage()
    data[RsKey.CAR_STATUS.value] = _car_status()
    data[RsKey.LIQUID.value] = ra.efficiency()
    return json.dumps({"topic": RsKey.BIG_SCREEN.value, "data": data})


def bs_task():
    logger.info("start big screen task")
    data = _enc_data()
    rds.set_big_screen(data)
    send_data(RsKey.BIG_SCREEN.value)
    logger.info("end big screen task")
