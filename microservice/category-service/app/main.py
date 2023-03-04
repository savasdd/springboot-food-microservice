from fastapi import FastAPI, Depends, HTTPException, status
from fastapi.middleware.cors import CORSMiddleware
from app.config import settings
from app.controller import personel_api
from kafka import KafkaProducer


app = FastAPI()


origins = [settings.CLIENT_ORIGIN]
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@app.get('/api/categoriys/send')
def root():
    producer = KafkaProducer(bootstrap_servers='localhost:9092')
    producer.send('FOOD', b'Elma Kategorisi: 250')
    producer.flush()
    return {'response': 'Category Service FastApi'}
