FROM python:3.11-buster as builder

COPY ./core/requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

FROM python:3.11-slim-buster

COPY --from=builder /usr/local/bin /usr/local/bin
COPY --from=builder /usr/local/lib/python3.11/site-packages /usr/local/lib/python3.11/site-packages

COPY ./core .

CMD ["python3", "main.py"]
