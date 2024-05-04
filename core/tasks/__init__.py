from apscheduler.schedulers.blocking import BlockingScheduler

from .big_screen_task import bs_task
from .panel_info_task import panel_task


def pre_hanele():
    panel_task()
    bs_task()


def start_scheduler():
    pre_hanele()
    # scheduler = BlockingScheduler()
    # scheduler.add_job(panel_task, "cron", hour=1)  # 每天凌晨1点执行
    # scheduler.add_job(bs_task, "cron", hour=1, minute=15)
    # scheduler.start()
