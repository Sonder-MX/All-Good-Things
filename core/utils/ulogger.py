import logging
import os
from logging.handlers import TimedRotatingFileHandler

from settings import LOGGING

if not os.path.exists("./logs"):
    os.makedirs("./logs")


class ULogger:
    def __init__(self):
        self.logger = logging.getLogger(__name__)
        self.logger.setLevel(LOGGING["lever"])
        self.formatter = logging.Formatter("%(asctime)s - %(name)s- %(levelname)s - %(message)s")
        self.is_save = LOGGING["is_save"]
        if self.is_save:
            self.save_path = LOGGING["save_path"]
            self.file_encoding = LOGGING["file_encoding"]
            self.file_handler = TimedRotatingFileHandler(
                self.save_path, when="M", interval=1, encoding=self.file_encoding
            )
            self.file_handler.setFormatter(self.formatter)
            self.logger.addHandler(self.file_handler)
        self.console_handler = logging.StreamHandler()
        self.console_handler.setFormatter(self.formatter)
        self.logger.addHandler(self.console_handler)

    def debug(self, msg):
        self.logger.debug(msg)

    def info(self, msg):
        self.logger.info(msg)

    def warning(self, msg):
        self.logger.warning(msg)

    def error(self, msg):
        self.logger.error(msg)

    def critical(self, msg):
        self.logger.critical(msg)
