from datetime import datetime
from typing import List, Union
import uuid
from pydantic import BaseModel, EmailStr, constr
from pydantic.schema import Optional, Dict


class PersonelBase(BaseModel):
    name: Optional[str]
    email: Optional[EmailStr]

    class Config:
        orm_mode = True


class CreatePersonel(PersonelBase):
    password: constr(min_length=4)
    role: str = 'user'


class PersonelResponse(PersonelBase):
    id: Optional[uuid.UUID]
    created_at: Optional[datetime]
    updated_at: Optional[datetime]


class ListPersonel(BaseModel):
    results: int
    data: List[PersonelResponse]
