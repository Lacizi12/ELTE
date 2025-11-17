from pydantic import BaseModel, EmailStr, conint, confloat
from typing import List

ShopName='Webshop'

NonNegativeInt = conint(ge=0)

NonNegativeFloat = confloat(ge=0.0)


class Item(BaseModel):
    item_id: NonNegativeInt
    name: str
    brand: str
    price: NonNegativeFloat
    quantity: NonNegativeInt

    class Config:
        from_attributes = True


class User(BaseModel):
    id: NonNegativeInt
    name: str
    email: EmailStr 

    class Config:
        from_attributes = True


class Basket(BaseModel):
    id: NonNegativeInt 
    user_id: NonNegativeInt
    items: List[Item] = []

    class Config:
        from_attributes = True