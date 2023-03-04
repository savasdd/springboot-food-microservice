from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from typing import List, Union
from ..database import get_db
from .. import service, schemas

router = APIRouter()


@router.get("/personels", response_model=List[schemas.PersonelResponse], status_code=status.HTTP_200_OK)
def get_all_personel(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    list = service.get_all_personel(db, skip=skip, limit=limit)
    return list


@router.get("/personels/{id}", response_model=schemas.PersonelResponse, status_code=status.HTTP_200_OK)
def get_all_personel(id: str, db: Session = Depends(get_db)):
    list = service.get_personel_by_id(db, personel_id=id)

    return list


@router.post("/personels", response_model=schemas.PersonelBase, status_code=status.HTTP_201_CREATED)
def create_personel(dto: schemas.CreatePersonel, db: Session = Depends(get_db)):
    exits = service.get_personel_by_email(db, email=dto.email)

    if exits:
        raise HTTPException(status_code=400, detail="Email already exits")
    return service.create_personel(db=db, dto=dto)


@router.put("/personels/{id}", response_model=schemas.PersonelBase)
def update_personel(id: str, dto: schemas.CreatePersonel, db: Session = Depends(get_db)):
    return service.update_personel(db=db, dto=dto, personel_id=id)


@router.delete("/personels/{id}")
def delete_personel(id: str, db: Session = Depends(get_db)):
    return service.delete_personel(db=db, personel_id=id)

@router.get('/health')
def root():
    return {'response': 'Hello FastApi'}