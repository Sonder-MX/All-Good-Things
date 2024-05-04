import redis
from redis.exceptions import ConnectionError

from settings import RsKey
from utils import logger


class RedisOpt:
    def __init__(self, host: str, port: int, db: int, password: str = None):
        self._r = redis.Redis(host=host, port=port, db=db, password=password)
        try:
            if self._r.ping():
                logger.info("Redis OPT 连接成功...")
            else:
                logger.error("Redis OPT 连接失败...")
        except ConnectionError:
            logger.error("Redis OPT 连接失败...")

    def __del__(self):
        self._r.close()
        logger.warning("Redis OPT 连接关闭...")

    def set(self, key: str, value: str):
        self._r.set(key, value)

    def set_base_panel(self, value: str):
        self.set(RsKey.BASE_PANEL.value, value)

    def set_big_screen(self, value: str):
        self.set(RsKey.BIG_SCREEN.value, value)
