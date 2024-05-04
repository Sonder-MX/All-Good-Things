import os
import sys

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__))))

from tasks import start_scheduler
from utils import logger


def main():
    start_scheduler()


if __name__ == "__main__":
    logger.info("analysis core start...")
    main()
