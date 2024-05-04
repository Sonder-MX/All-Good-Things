from .ulogger import ULogger
from .urequest import send_data

logger = ULogger()

__all__ = ["logger", "send_data"]
