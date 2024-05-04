import requests
from requests.exceptions import RequestException

from settings import UQ_REQEST_SEND  # , UQ_REQEST_DATA

from .ulogger import ULogger

logger = ULogger()


def send_data(topic: str) -> None:
    """
    Send data to web server
    :param topic: str
    :param data: str
    """
    url = f'{UQ_REQEST_SEND["server"]}?topic={topic}'
    headers = UQ_REQEST_SEND["header"]
    try:
        req = requests.post(url, headers=headers)
        if req.status_code == 200:
            logger.info(f"Send data to web server success")
        else:
            logger.error(f"Send data to web server error: {req.status_code}")
    except RequestException as e:
        logger.error(f"Send data to web server error: {e}")


def request_data():
    """
    Request data from client web server
    """
    pass
