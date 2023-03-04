from fastapi import FastAPI, Depends, HTTPException, status
from fastapi.middleware.cors import CORSMiddleware
from app.config import settings
from app.controller import personel_api
#import py_eureka_client.eureka_client as eureka_client

# port = 8082
# client = eureka_client.init(
#     eureka_server="http://127.0.0.1:8761/eureka",
#     #eureka_protocol="http",
#     #eureka_context="/eureka/v2",
#     app_name="category-service",
#     #instance_ip="192.168.1.21/
#     # 24",
#     instance_port=port)


app = FastAPI()



origins = [settings.CLIENT_ORIGIN]
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)



@app.get('/api/health')
def root():
    return {'response': 'Category Service FastApi'}
