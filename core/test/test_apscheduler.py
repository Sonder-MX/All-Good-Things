import time

from apscheduler.schedulers.blocking import BlockingScheduler


def daily_task_one():
    current_time = time.strftime("%H:%M:%S", time.localtime())
    print("*任务一：当前时间：", current_time)


def daily_task_two():
    current_timestamp = time.time()
    print("**任务二：当前时间戳：", current_timestamp)


if __name__ == "__main__":
    scheduler = BlockingScheduler()

    # scheduler.add_job(daily_task_one, "cron", hour=12, minute=0)
    scheduler.add_job(daily_task_one, "interval", seconds=5)
    scheduler.add_job(daily_task_two, "interval", seconds=8)

    scheduler.start()
