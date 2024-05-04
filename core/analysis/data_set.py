import pandas as pd

from db import sqlite


def user_data_set() -> pd.DataFrame:
    user_data = sqlite.read_to_pandas("user_user")
    user_data = user_data.drop(columns=["id", "password", "last_login", "avatar", "idnum", "username"])
    user_data = user_data.dropna(subset=["wid", "phone"])
    user_data = user_data[user_data["wid"].str.isalnum()]
    user_data = user_data[user_data["phone"].str.match(r"^1\d{10}$")]
    user_data = user_data[user_data["is_superuser"] == False]
    return user_data


def route_data_set() -> pd.DataFrame:
    route_sql = """
    select
        ri.rdate,
        ri.rtime,
        ri.car_id,
        rst.rname,
        rst.rtype,
        si.seat as seat_list,
        ci.cnum,
        ci.cseat
    from
        route_info ri
    left join
        route_station rst on ri.route_station_id = rst.id
    left join
        seat_info si on ri.id = si.route_id
    left join
        car_info ci on ri.car_id = ci.id;
    """

    route_data = sqlite.read_to_pandas_by_sql(route_sql)
    # 删除rname最后一个字符
    route_data["rname"] = route_data["rname"].str[:-1]
    # seat字段中的值为[6, 13]字符串或None，将其转为列表，然后新建字段存储长度
    route_data["seat_list"] = route_data["seat_list"].apply(lambda x: eval(x) if x else [])
    route_data["used_seat"] = route_data["seat_list"].apply(lambda x: len(x))
    # 排序字段
    route_data = route_data[["rdate", "rtime", "car_id", "rname", "rtype", "cnum", "cseat", "used_seat", "seat_list"]]
    return route_data


def order_data_set() -> pd.DataFrame:
    order_sql = """
    select
        od.number,
        od.status,
        od.station,
        od.seat,
        od.created,
        od.updated,
        ri.rdate,
        ri.rtime,
        rst.rname
    from
        orders od
    join
        route_info ri on od.route_id = ri.id
    join
        route_station rst on ri.route_station_id = rst.id;
    """

    order_data = sqlite.read_to_pandas_by_sql(order_sql)

    # 其中station的字段为 `南关十字(7:00)`，需要将其拆分为两个字段：station_name和station_time
    order_station = order_data["station"].str.extract(r"(.+)\((\d{1,2}:\d{2})\)")
    order_data["station_name"] = order_station[0]
    order_data["station_time"] = order_station[1]
    # 删除字段rname中的最后一个字符
    order_data["rname"] = order_data["rname"].str[:-1]

    # 删除字段station，并将字段重新排序
    order_data = order_data.drop(columns=["station"])
    order_data = order_data[
        ["number", "status", "station_name", "station_time", "seat", "created", "updated", "rdate", "rtime", "rname"]
    ]
    return order_data


def map_data_set():
    return {
        "天水路北口": [103.870745, 36.07734],
        "南关十字": [103.832267, 36.061207],
        "二十二嘉园": [103.78698, 36.063002],
        "理工大后家属院": [103.785809, 36.065084],
        "广场西口": [103.844979, 36.060069],
        "杨家桥": [103.758399, 36.059711],
        "公园路口": [103.637171, 36.09668],
        "Q5公交站": [103.707723, 36.064911],
        "学校": [103.762653, 36.564132],
        "海关": [103.716345, 36.100344],
        "西关什字": [103.823213, 36.064768],
        "盘旋路": [103.860888, 36.056185],
        "黄金大厦": [103.777846, 36.073756],
        "富润家园": [103.74435, 36.055243],
        "龚一小对面": [103.750659, 36.058734],
        "建工局": [103.789412, 36.071994],
        "寺儿沟": [103.617935, 36.10352],
        "中铁21局": [103.690671, 36.108046],
        "瑞岭雅苑北门": [103.6965, 36.490827],
        "元通桥": [103.832021, 36.072326],
        "瑞岭国际酒店": [103.67773, 36.489847],
        "新黄河市场": [103.774382, 36.086738],
        "西湖公园": [103.8059, 36.068987],
        "大砂坪小学": [103.840883, 36.083125],
        "培黎广场": [103.751489, 36.105106],
        "民乐路东口": [103.767221, 36.063099],
        "中海河山郡": [103.677153, 36.119464],
        "恒大翡翠华庭": [103.722877, 36.06945],
        "龚一小": [103.750659, 36.058734],
        "天银宾馆": [103.796728, 36.067508],
        "深安桥南": [103.684215, 36.096712],
        "大砂坪小学公交站": [103.84075, 36.082158],
        "甘农厂": [103.769616, 36.064054],
        "交通大学": [103.731761, 36.111419],
        "刘家堡": [103.700328, 36.113079],
        "旋路": [102.864176, 36.348526],
    }


def order_data_with_map():
    order_data = order_data_set().dropna(subset=["station_name"])
    map_data = map_data_set()
    order_data["longitude"] = order_data["station_name"].apply(lambda x: map_data.get(x, [0, 0])[0])
    order_data["latitude"] = order_data["station_name"].apply(lambda x: map_data.get(x, [0, 0])[1])
    return order_data
