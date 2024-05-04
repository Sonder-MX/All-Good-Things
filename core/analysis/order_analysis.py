import pandas as pd

from .data_set import order_data_set


def hourly_orders():
    order_data = order_data_set()
    # 将'created'列转换为datetime格式
    order_data["created"] = pd.to_datetime(order_data["created"])
    # 创建一个新列'hour'，表示小时
    order_data["hour"] = order_data["created"].dt.hour
    # 对'hour'列进行分组，计算每个小时的预约数量
    return order_data.groupby("hour").size().tolist()


def weekly_orders():
    order_data = order_data_set()
    # 将'created'列转换为datetime格式
    order_data["created"] = pd.to_datetime(order_data["created"])
    # 创建一个新列'week'，表示星期
    # 创建一个新列'day_of_week'，表示星期几
    order_data["day_of_week"] = order_data["created"].dt.dayofweek
    # 对'day_of_week'列进行分组，计算每个星期几的预约数量
    return order_data.groupby("day_of_week").size().tolist()
