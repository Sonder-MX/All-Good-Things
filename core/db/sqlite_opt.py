import sqlite3

import pandas as pd

from utils import logger


class SqliteOpt:
    def __init__(self, path: str):
        self._conn = sqlite3.connect(path)
        self._cursor = self._conn.cursor()
        logger.info("Sqlire OPT 初始化...")

    def __del__(self):
        self._cursor.close()
        self._conn.close()
        logger.warning("Sqlire OPT 关闭...")

    def execute(self, sql: str):
        self._cursor.execute(sql)
        return self._cursor

    def fetchall(self, tb: str, fields: list = ["*"], condition: str = "") -> list:
        fds = ",".join(fields)
        if condition:
            condition = f" WHERE {condition}"
        self.execute(f"SELECT {fds} FROM {tb}{condition}")
        return self._cursor.fetchall()

    def fetchcount(self, tb: str, condition: str = "") -> int:
        if condition:
            condition = f" WHERE {condition}"
        self.execute(f"SELECT COUNT(*) FROM {tb}{condition}")
        return self._cursor.fetchone()[0]

    def fetchone(self, tb: str, cond: str = None):
        if cond:
            self.execute(f"SELECT * FROM {tb} {cond}")
        else:
            self.execute(f"SELECT * FROM {tb}")
        return self._cursor.fetchone()

    def read_to_pandas(self, tb: str) -> pd.DataFrame:
        return pd.read_sql(f"SELECT * FROM {tb}", self._conn)

    def read_to_pandas_by_sql(self, sql: str) -> pd.DataFrame:
        return pd.read_sql(sql, self._conn)
