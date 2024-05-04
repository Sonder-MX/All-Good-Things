import json

from analysis import popular_analysis
from db import rds
from panel_info import car_info, order_info, route_info, user_info
from settings import RsKey
from utils import logger, send_data


def _enc_data() -> str:
    user_count = user_info.get_user_count()
    latest_order = order_info.latest_order()
    updated = latest_order[7].split(".")[0]
    data = [
        {"title": "最后更新", "content": updated},
        {"title": "用户总数", "content": user_count[0]},
        {"title": "司机总数", "content": user_count[1]},
        {"title": "订单总数", "content": order_info.get_order_count()},
        {"title": "运行线路", "content": route_info.get_active_route()},
        {"title": "路线总数", "content": route_info.get_total_route()},
        {"title": "车辆总数", "content": car_info.get_car_count()},
        {
            "title": "最活跃线路",
            "content": popular_analysis.popular_route()[0][0],
            "value": popular_analysis.popular_route()[0][1],
        },
        {
            "title": "最热门站点",
            "content": popular_analysis.popular_station()[0][0],
            "value": popular_analysis.popular_station()[0][1],
        },
    ]
    return json.dumps({"topic": RsKey.BASE_PANEL.value, "data": data})


def panel_task():
    logger.info("start panel task")
    data = _enc_data()
    rds.set_base_panel(data)
    send_data(RsKey.BASE_PANEL.value)
    logger.info("end panel task")
