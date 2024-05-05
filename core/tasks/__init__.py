from apscheduler.schedulers.blocking import BlockingScheduler

from settings import TIME_TASKS

from .big_screen_task import bs_task
from .panel_info_task import panel_task


def get_task_config(task_name):
    return TIME_TASKS.get(task_name, {"interval": {"seconds": 60 * 60 * 24}})


def pre_hanele():
    panel_task()
    bs_task()


def start_scheduler():
    pre_hanele()
    scheduler = BlockingScheduler()
    panel_config = get_task_config("panel")
    scheduler.add_job(
        panel_task,
        list(panel_config.keys())[0],
        **(list(panel_config.values())[0]),
    )

    bs_config = get_task_config("big_screen")
    scheduler.add_job(
        bs_task,
        list(bs_config.keys())[0],
        **(list(bs_config.values())[0]),
    )

    scheduler.start()
