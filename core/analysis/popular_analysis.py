import geopandas as gpd
from shapely.geometry import Point

from .data_set import order_data_with_map


def _geo_df():
    order_data = order_data_with_map()
    geometry = [Point(xy) for xy in zip(order_data["longitude"], order_data["latitude"])]
    return gpd.GeoDataFrame(order_data, geometry=geometry)


def popular_station():
    gdf = _geo_df()
    return list(gdf["station_name"].value_counts().items())


def popular_route():
    gdf = _geo_df()
    return list(gdf["rname"].value_counts().items())
