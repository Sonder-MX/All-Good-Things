from .data_set import route_data_set


def seat_usage() -> tuple[int, int, int]:
    """
    @return: (used, unused, total)
    """
    df = route_data_set()
    df = df[df["used_seat"] != 0]
    total_seat = int(df["cseat"].sum())
    total_used = int(df["used_seat"].sum())
    total_un = total_seat - total_used
    return total_used, total_un, total_seat


def seat_usage_rate() -> tuple[float, float]:
    """
    @return (used_rate, unuse_rate)
    """
    used, unuse, total = seat_usage()
    return used / total, unuse / total


def efficiency():
    used, _, total = seat_usage()
    return used / total


def route_with_car():
    df = route_data_set()
    return df["car_id"].value_counts().to_dict()
