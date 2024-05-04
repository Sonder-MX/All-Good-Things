from settings import DATABASE
from utils import logger

from .redis_opt import RedisOpt
from .sqlite_opt import SqliteOpt

# __all__ = ["SqliteOpt", "RedisOpt"]

sqlite_path = ""
try:
    sqlite_path = DATABASE["sqlite"]["path"]
except KeyError:
    logger.error("未检测到数据源，请配置sqlite")
    exit(1)

rds_cfg = DATABASE.get("redis", {})


sqlite = SqliteOpt(sqlite_path)
rds = RedisOpt(
    rds_cfg.get("host", "localhost"),
    rds_cfg.get("port", 6379),
    rds_cfg.get("db", 0),
    rds_cfg.get("password", None),
)
