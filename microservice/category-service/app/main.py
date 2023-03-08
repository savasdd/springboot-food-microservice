from fastapi import FastAPI, Depends, HTTPException, status
from fastapi.middleware.cors import CORSMiddleware
from app.config import settings
from app.controller import personel_api
from kafka import KafkaProducer
import json


app = FastAPI()


origins = [settings.CLIENT_ORIGIN]
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@app.get('/api/categorys/send')
def root():
    AaccountEvent = {
        "name": "Elma Kategorisi: 250",
    }

    producer = KafkaProducer(bootstrap_servers='localhost:9092',
                             value_serializer=lambda v: json.dumps(v).encode('utf-8'))
    producer.send('FOOD', AaccountEvent)
    producer.flush()
    return {'response': 'Category Service FastApi'}
