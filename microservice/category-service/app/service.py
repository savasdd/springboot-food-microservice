from sqlalchemy.orm import Session
from fastapi import HTTPException, status, Response
from . import models, schemas
import uuid
import base64


def get_personel_by_id(db: Session, personel_id: uuid):
    model = db.query(models.Personel).filter(
        models.Personel.id == personel_id).first()
    return model


def get_personel_by_email(db: Session, email: str):
    return db.query(models.Personel).filter(models.Personel.email == email).first()


def get_all_personel(db: Session, skip: int = 0, limit: int = 100):
    return db.query(models.Personel).offset(skip).limit(limit).all()


def create_personel(db: Session, dto: schemas.CreatePersonel):
    hashed_password = base64.b64encode(dto.password.encode("ascii"))
    model = models.Personel(
        name=dto.name,
        email=dto.email,
        password=hashed_password)

    db.add(model)
    db.commit()
    db.refresh(model)
    return model


def update_personel(db: Session, dto: schemas.CreatePersonel, personel_id: uuid):

    query = db.query(models.Personel).filter(models.Personel.id == personel_id)
    model = query.first()

    if not model:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND,
                            detail=f'No note with this id: {personel_id} found')

    update_data = dto.dict(exclude_unset=True)
    query.filter(models.Personel.id == personel_id).update(
        update_data, synchronize_session=False)

    db.commit()
    db.refresh(model)

    return model


def delete_personel(db: Session,  personel_id: uuid):

    query = db.query(models.Personel).filter(models.Personel.id == personel_id)
    model = query.first()

    if not model:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND,
                            detail=f'No note with this id: {personel_id} found')

    query.delete(synchronize_session=False)

    db.commit()
    return Response(status_code=status.HTTP_204_NO_CONTENT)
